package com.stylefeng.guns.rest.modular.film;

import com.alibaba.dubbo.config.annotation.Reference;
import com.style.guns.api.film.FilmServiceApi;
import com.stylefeng.guns.rest.modular.film.vo.FilmIndexVO;
import com.stylefeng.guns.rest.modular.vo.ResponseVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/film/")
public class FilmController {

    @Reference(interfaceClass = FilmServiceApi.class)
    private FilmServiceApi filmServiceApi;

    /*
        API Gateway:
            1. api group
               - 6 interfaces, one http request
               - only expose one interface
               - data might be two large
     */
    @RequestMapping(value = "getIndex", method = RequestMethod.GET)
    public ResponseVo getIndex() {
        FilmIndexVO filmIndexVO = new FilmIndexVO();

        filmIndexVO.setBannerVOList(filmServiceApi.getBanners());
        filmIndexVO.setBoxRanking(filmServiceApi.getBoxRanking());
        filmIndexVO.setExpectRanking(filmServiceApi.getExpectRanking());
        filmIndexVO.setHotFilms(filmServiceApi.getHotFilms(true, 8));
        filmIndexVO.setSoonFilms(filmServiceApi.getSoonFilms(true, 8));
        filmIndexVO.setTop100(filmServiceApi.getTop());
        return ResponseVo.serviceSuccess(filmIndexVO);
    }
}
