package com.stylefeng.guns.rest.modular.film;

import com.alibaba.dubbo.config.annotation.Reference;
import com.style.guns.api.film.FilmServiceApi;
import com.style.guns.api.film.vo.CatVO;
import com.stylefeng.guns.rest.modular.film.vo.FilmConditionVO;
import com.stylefeng.guns.rest.modular.film.vo.FilmIndexVO;
import com.stylefeng.guns.rest.modular.vo.ResponseVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/film/")
public class FilmController {

    private static final String IMG_PRE = "test.com";

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
        return ResponseVo.serviceSuccess(IMG_PRE, filmIndexVO);
    }

    @RequestMapping(value = "getConditionList", method = RequestMethod.GET)
    public ResponseVo getConditionList(@RequestParam(name = "catId", required = false, defaultValue = "99") String catId,
                                       @RequestParam(name = "sourceId", required = false, defaultValue = "99") String sourceId,
                                       @RequestParam(name = "yearId", required = false, defaultValue = "99") String yearId) {


        FilmConditionVO filmConditionVO = new FilmConditionVO();

        // get List<CatVO>
        boolean isCatMatch = false;

        List<CatVO> catVOList = filmServiceApi.getCats();
        List<CatVO> catVOResult = new ArrayList<>();
        CatVO defaultCat = null;

        for (CatVO catVO : catVOList) {
            // defaultCatId
            if (catVO.getCatId().equals("99")) {
                defaultCat = catVO;
                break;
            }

            if (catVO.getCatId().equals(catId)) {
                isCatMatch = true;
                catVO.setActive(true);
            } else {
                catVO.setActive(false);
            }
            catVOResult.add(catVO);
        }

        if (!isCatMatch) {
            // set defaultCat active true
            if (defaultCat != null) {
                defaultCat.setActive(true);
                catVOResult.add(defaultCat);
            }
        } else {
            if (defaultCat != null) {
                defaultCat.setActive(false);
                catVOResult.add(defaultCat);
            }
        }


        return null;
    }
}
