package com.stylefeng.guns.rest.modular.cinema.vo;

import com.style.guns.api.cinema.vo.CinemaInfoVO;
import com.style.guns.api.cinema.vo.FilmInfoVO;
import lombok.Data;

import java.util.List;

@Data
public class CinemaFieldResponseVO {

    private CinemaInfoVO cinemaInfoVO;
    private List<FilmInfoVO> filmList;
}
