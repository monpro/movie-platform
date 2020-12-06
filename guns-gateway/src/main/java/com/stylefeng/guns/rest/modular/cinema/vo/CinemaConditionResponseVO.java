package com.stylefeng.guns.rest.modular.cinema.vo;

import com.style.guns.api.cinema.vo.AreaVO;
import com.style.guns.api.cinema.vo.BrandVO;
import com.style.guns.api.cinema.vo.HallTypeVO;
import lombok.Data;

import java.util.List;

@Data
public class CinemaConditionResponseVO {

    private List<BrandVO> brandList;
    private List<AreaVO> areaList;
    private List<HallTypeVO> hallTypeList;

}
