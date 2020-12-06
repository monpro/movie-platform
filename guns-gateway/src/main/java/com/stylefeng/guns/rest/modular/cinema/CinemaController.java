package com.stylefeng.guns.rest.modular.cinema;


import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.plugins.Page;
import com.style.guns.api.cinema.CinemaServiceAPI;
import com.style.guns.api.cinema.vo.CinemaQueryVO;
import com.style.guns.api.cinema.vo.CinemaVO;
import com.stylefeng.guns.rest.modular.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/cinema/")
public class CinemaController {

    @Reference(interfaceClass = CinemaServiceAPI.class, check = false)
    private CinemaServiceAPI cinemaServiceAPI;

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
            log.error("exception at cinema controller", e);
            return ResponseVo.serviceFail("get cinemas failed");
        }
    }

    @RequestMapping(value = "getCondition")
    public ResponseVo getCondition(CinemaQueryVO cinemaQueryVO) {

        return null;
    }

    @RequestMapping(value = "getFields")
    public ResponseVo getFields(Integer cinemaId) {

        return null;
    }

    @RequestMapping(value = "getFieldInfo", method = RequestMethod.POST)
    public ResponseVo getFieldInfo(Integer cinemaId, Integer fieldId) {

        return null;
    }
}
