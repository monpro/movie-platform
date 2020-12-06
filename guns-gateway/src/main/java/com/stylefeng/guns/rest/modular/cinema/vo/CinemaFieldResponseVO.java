package com.stylefeng.guns.rest.modular.cinema.vo;

import com.style.guns.api.cinema.vo.CinemaInfoVO;
import com.style.guns.api.cinema.vo.FilmInfoVO;
import com.style.guns.api.cinema.vo.HallInfoVo;
import lombok.Data;

@Data
public class CinemaFieldResponseVO {

    private CinemaInfoVO cinemaInfo;
    private FilmInfoVO filmInfo;
    private HallInfoVo hallInfo;
}
