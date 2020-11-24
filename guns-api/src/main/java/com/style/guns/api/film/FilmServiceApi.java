package com.style.guns.api.film;

import com.style.guns.api.film.vo.*;

import java.util.List;

public interface FilmServiceApi {

    // get banners
    List<BannerVO> getBanners();

    // get hot films
    FilmVO getHotFilms(boolean isLimit, int nums);

    FilmVO getSoonFilms(boolean isLimit, int nums);

    List<FilmInfo> getBoxRanking();

    List<FilmInfo> getExpectRanking();

    // get top 100
    List<FilmInfo> getTop();

    // interface film condition
    List<CatVO> getCats();

    List<SourceVO> getSources();

    List<YearVO> getYears();

}
