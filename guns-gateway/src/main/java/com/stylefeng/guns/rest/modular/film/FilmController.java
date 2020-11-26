package com.stylefeng.guns.rest.modular.film;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.RpcContext;
import com.style.guns.api.film.FilmAsyncServiceApi;
import com.style.guns.api.film.FilmServiceApi;
import com.style.guns.api.film.vo.*;
import com.stylefeng.guns.rest.modular.film.vo.FilmConditionVO;
import com.stylefeng.guns.rest.modular.film.vo.FilmIndexVO;
import com.stylefeng.guns.rest.modular.film.vo.FilmRequestVO;
import com.stylefeng.guns.rest.modular.vo.ResponseVo;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/film/")
public class FilmController {

    private static final String IMG_PRE = "test.com";

    @Reference(interfaceClass = FilmServiceApi.class)
    private FilmServiceApi filmServiceApi;

    @Reference(interfaceClass = FilmAsyncServiceApi.class, async = true)
    private FilmAsyncServiceApi filmAsyncServiceApi;

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
        filmIndexVO.setHotFilms(filmServiceApi.getHotFilms(true, 8, 1, 1, 99, 99, 99));
        filmIndexVO.setSoonFilms(filmServiceApi.getSoonFilms(true, 8, 1, 1, 99, 99, 99));
        filmIndexVO.setTop100(filmServiceApi.getTop());
        return ResponseVo.serviceSuccess(IMG_PRE, filmIndexVO);
    }

    @RequestMapping(value = "getConditionList", method = RequestMethod.GET)
    public ResponseVo getConditionList(@RequestParam(name = "catId", required = false, defaultValue = "99") String catId,
                                       @RequestParam(name = "sourceId", required = false, defaultValue = "99") String sourceId,
                                       @RequestParam(name = "yearId", required = false, defaultValue = "99") String yearId) {


        FilmConditionVO filmConditionVO = new FilmConditionVO();

        // find cats
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

        // find source
        boolean isSourceMatch = false;
        List<SourceVO> sourceVOList = filmServiceApi.getSources();
        List<SourceVO> sourceVOResult = new ArrayList<>();
        SourceVO defaultSource = null;

        for (SourceVO sourceVO : sourceVOList) {
            // defaultCatId
            if (sourceVO.getSourceId().equals("99")) {
                defaultSource = sourceVO;
                break;
            }

            if (sourceVO.getSourceId().equals(sourceId)) {
                isSourceMatch = true;
                sourceVO.setActive(true);
            } else {
                sourceVO.setActive(false);
            }
            sourceVOResult.add(sourceVO);
        }

        if (!isSourceMatch) {
            // set defaultCat active true
            if (defaultSource != null) {
                defaultSource.setActive(true);
                sourceVOResult.add(defaultSource);
            }
        } else {
            if (defaultSource != null) {
                defaultSource.setActive(false);
                sourceVOResult.add(defaultSource);
            }
        }

        // find years
        boolean isYearMatch = false;
        List<YearVO> yearVOList = filmServiceApi.getYears();
        List<YearVO> yearVoResult = new ArrayList<>();
        YearVO defaultYear = null;
        for (YearVO year : yearVOList) {
            if (year.getYearId().equals("99")) {
                defaultYear = year;
                break;
            }
            if (year.getYearId().equals(catId)) {
                isYearMatch = true;
                year.setActive(true);
            } else {
                year.setActive(false);
            }
            yearVoResult.add(year);
        }
        // 如果不存在，则默认将全部变为Active状态
        if (!isYearMatch) {
            if (defaultYear != null) {
                defaultYear.setActive(true);
                yearVoResult.add(defaultYear);
            }
        } else {
            if (defaultYear != null) {
                defaultYear.setActive(false);
                yearVoResult.add(defaultYear);
            }
        }

        filmConditionVO.setCatInfo(catVOResult);
        filmConditionVO.setSourceInfo(sourceVOResult);
        filmConditionVO.setYearInfo(yearVoResult);


        return ResponseVo.serviceSuccess(filmConditionVO);
    }

    @RequestMapping(value = "getFilms", method = RequestMethod.GET)
    public ResponseVo getFilms(FilmRequestVO filmRequestVO) {
        FilmVO filmVO = null;
        String imgPre = "www.google.com";
        switch (filmRequestVO.getShowType()) {
            case 1:
                filmVO = filmServiceApi.getHotFilms(
                        false,
                        filmRequestVO.getPageSize(),
                        filmRequestVO.getNowPage(),
                        filmRequestVO.getSortId(),
                        filmRequestVO.getSourceId(),
                        filmRequestVO.getYearId(),
                        filmRequestVO.getCatId()
                );
                break;

            case 2:
                filmVO = filmServiceApi.getSoonFilms(
                        false,
                        filmRequestVO.getPageSize(),
                        filmRequestVO.getNowPage(),
                        filmRequestVO.getSortId(),
                        filmRequestVO.getSourceId(),
                        filmRequestVO.getYearId(),
                        filmRequestVO.getCatId()
                );
                break;
            case 3:
                filmVO = filmServiceApi.getClassicFilms(
                        filmRequestVO.getPageSize(),
                        filmRequestVO.getNowPage(),
                        filmRequestVO.getSortId(),
                        filmRequestVO.getSourceId(),
                        filmRequestVO.getYearId(),
                        filmRequestVO.getCatId()
                );
                break;
            default:
                filmVO = filmServiceApi.getHotFilms(
                        false,
                        filmRequestVO.getPageSize(),
                        filmRequestVO.getNowPage(),
                        filmRequestVO.getSortId(),
                        filmRequestVO.getSourceId(),
                        filmRequestVO.getYearId(),
                        filmRequestVO.getCatId()
                );
                break;
        }

        return ResponseVo.serviceSuccess(
                filmVO.getNowPage(),
                filmVO.getTotalPage(),
                imgPre,
                filmVO.getFilmInfoList()
        );
    }

    @RequestMapping(value = "films/{searchParam}", method = RequestMethod.GET)
    public ResponseVo films(@PathVariable("searchParam") String searchParam,
                            int searchType) throws ExecutionException, InterruptedException {
        FilmDetailVO filmDetailVO = filmServiceApi.getFilmDetail(searchType, searchParam);

        if (filmDetailVO == null) {
            return ResponseVo.serviceFail("cannot find films");
        } else if (filmDetailVO.getFilmId() == null || filmDetailVO.getFilmId().trim().length() == 0) {
            return ResponseVo.serviceFail("cannot find films");
        }

        String filmId = filmDetailVO.getFilmId();

        // get film detail
        filmAsyncServiceApi.getFilmDesc(filmId);
        Future<FilmDescVO> filmDescVOFuture = RpcContext.getContext().getFuture();

        // get film img
        filmAsyncServiceApi.getImgs(filmId);
        Future<ImgVO> imgVOFuture = RpcContext.getContext().getFuture();

        // get directors
        filmAsyncServiceApi.getDirectorVO(filmId);
        Future<ActorVO> directorFuture = RpcContext.getContext().getFuture();

        // get actor
        filmAsyncServiceApi.getActors(filmId);
        Future<List<ActorVO>> actorsFuture = RpcContext.getContext().getFuture();

        ActorRequestVO actorRequestVO = new ActorRequestVO();
        actorRequestVO.setActors(actorsFuture.get());
        actorRequestVO.setDirector(directorFuture.get());

        InfoRequestVO infoRequestVO = new InfoRequestVO();
        infoRequestVO.setFilmId(filmId);
        infoRequestVO.setActors(actorRequestVO);
        infoRequestVO.setBiography(filmDescVOFuture.get().getBiography());
        infoRequestVO.setImgVO(imgVOFuture.get());

        filmDetailVO.setRequestVOInfo(infoRequestVO);

        return ResponseVo.serviceSuccess("https://www.google.com", filmDetailVO);
    }
}
