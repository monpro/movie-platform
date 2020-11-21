package com.style.guns.api.film;

import com.style.guns.api.film.vo.BannerVO;
import com.style.guns.api.film.vo.FilmInfo;
import com.style.guns.api.film.vo.FilmVO;

import java.util.List;

public interface FilmServiceApi {

    // get banners
    BannerVO getBanners();

    // get hot films
    FilmVO getHotFimls(boolean isLimit, int nums);

    FilmVO getSoonFilms(boolean isLimit, int nums);

    List<FilmInfo> getBoxRanking();

    List<FilmInfo> getExpectRanking();

    // get top 100
    List<FilmInfo> getTop();

}
