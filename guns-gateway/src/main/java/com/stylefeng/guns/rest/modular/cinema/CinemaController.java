package com.stylefeng.guns.rest.modular.cinema;


import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.plugins.Page;
import com.style.guns.api.cinema.CinemaServiceAPI;
import com.style.guns.api.cinema.vo.*;
import com.stylefeng.guns.rest.modular.cinema.vo.CinemaConditionResponseVO;
import com.stylefeng.guns.rest.modular.cinema.vo.CinemaFieldResponseVO;
import com.stylefeng.guns.rest.modular.cinema.vo.CinemaFieldsResponseVO;
import com.stylefeng.guns.rest.modular.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/cinema/")
public class CinemaController {

    @Reference(interfaceClass = CinemaServiceAPI.class, check = false)
    private CinemaServiceAPI cinemaServiceAPI;

    private static final String IMG_PRE = "https://www.google.com";
    @RequestMapping(value = "getCinemas")
    public ResponseVo getCinemas(CinemaQueryVO cinemaQueryVO) {

        try {
            Page<CinemaVO> cinemas = cinemaServiceAPI.getCinemas(cinemaQueryVO);
            if (cinemas.getRecords() == null || cinemas.getRecords().size() == 0) {
                return ResponseVo.serviceSuccess("no cinemas record");
            } else {
                return ResponseVo.serviceSuccess(
                        cinemas.getCurrent(),
                        (int) cinemas.getPages(),
                        "",
                        cinemas.getRecords()
                );
            }
        } catch (Exception e) {
            log.error("exception at getCinemas controller", e);
            return ResponseVo.serviceFail("getCinemas failed");
        }
    }

    @RequestMapping(value = "getCondition")
    public ResponseVo getCondition(CinemaQueryVO cinemaQueryVO) {
        try {
            List<BrandVO> brands = cinemaServiceAPI.getBrands(cinemaQueryVO.getBrandId());
            List<AreaVO> areas = cinemaServiceAPI.getAreas(cinemaQueryVO.getDistrictId());
            List<HallTypeVO> hallTypes = cinemaServiceAPI.getHallTypes(cinemaQueryVO.getHallType());
            CinemaConditionResponseVO responseVO = new CinemaConditionResponseVO();
            responseVO.setBrandList(brands);
            responseVO.setAreaList(areas);
            responseVO.setHallTypeList(hallTypes);
            return ResponseVo.serviceSuccess(responseVO);
        } catch (Exception e) {
            log.error("exception at getCondition controller", e);
            return ResponseVo.serviceFail("getCondition failed");
        }
    }

    @RequestMapping(value = "getFields")
    public ResponseVo getFields(Integer cinemaId) {

        try {
            CinemaInfoVO cinemaInfo = cinemaServiceAPI.getCinemaInfo(cinemaId);
            List<FilmInfoVO> filmInfoByCinemaId = cinemaServiceAPI.getFilmInfoByCinemaId(cinemaId);

            CinemaFieldsResponseVO responseVO = new CinemaFieldsResponseVO();
            responseVO.setCinemaInfoVO(cinemaInfo);
            responseVO.setFilmList(filmInfoByCinemaId);
            return ResponseVo.serviceSuccess(IMG_PRE, responseVO);

        } catch (Exception e) {
            log.error("exception at getFields controller", e);
            return ResponseVo.serviceFail("getFields failed");
        }
    }

    @RequestMapping(value = "getFieldInfo")
    public ResponseVo getFieldInfo(Integer cinemaId, Integer fieldId) {

        try {
            CinemaInfoVO cinemaInfo = cinemaServiceAPI.getCinemaInfo(cinemaId);
            HallInfoVo filmFieldInfo = cinemaServiceAPI.getFilmFieldInfo(fieldId);
            FilmInfoVO filmInfoByFieldId = cinemaServiceAPI.getFilmInfoByFieldId(fieldId);

            // hardcode value
            // will be removed in future
            filmFieldInfo.setSoldSeats("1,2,3");
            CinemaFieldResponseVO responseVO = new CinemaFieldResponseVO();
            responseVO.setCinemaInfo(cinemaInfo);
            responseVO.setFilmInfo(filmInfoByFieldId);
            responseVO.setHallInfo(filmFieldInfo);

            return ResponseVo.serviceSuccess(IMG_PRE, responseVO);
        } catch (Exception e) {
            log.error("exception at getFieldInfo controller", e);
            return ResponseVo.serviceFail("getFieldInfo failed");
        }
    }
}
