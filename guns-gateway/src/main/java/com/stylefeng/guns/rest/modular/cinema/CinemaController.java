package com.stylefeng.guns.rest.modular.cinema;


import com.alibaba.dubbo.config.annotation.Reference;
import com.style.guns.api.cinema.CinemaServiceAPI;
import com.style.guns.api.cinema.vo.CinemaQueryVO;
import com.stylefeng.guns.rest.modular.vo.ResponseVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cinema/")
public class CinemaController {

    @Reference(interfaceClass = CinemaServiceAPI.class, check = false)
    private CinemaServiceAPI cinemaServiceAPI;

    @RequestMapping(value = "getCinemas")
    public ResponseVo getCinemas(CinemaQueryVO cinemaQueryVO) {

        return null;
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
