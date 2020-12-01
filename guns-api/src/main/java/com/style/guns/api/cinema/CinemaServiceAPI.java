package com.style.guns.api.cinema;

import com.baomidou.mybatisplus.plugins.Page;
import com.style.guns.api.cinema.vo.*;

import java.util.List;

public interface CinemaServiceAPI {

    Page<CinemaVO> getCinemas(CinemaQueryVO cinemaQueryVO);

    List<BrandVO> getBrands(int brandId);

    List<AreaVO> getAreas(int areaId);

    List<HallTypeVO> getHallTypes(int hallType);

    CinemaInfoVO getCinemaInfo(int cinemaId);

    List<FilmInfoVO> getFilmInfoByCinemaId(int cinemaId);

    FilmInfoVO getFilmInfoByFieldId(int fieldId);

    HallInfoVo getFilmFieldInfo(int fieldId);

}
