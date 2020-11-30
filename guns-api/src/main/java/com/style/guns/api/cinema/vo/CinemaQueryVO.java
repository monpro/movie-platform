package com.style.guns.api.cinema.vo;

import lombok.Data;

@Data
public class CinemaQueryVO {

    private Integer brandId = 99;
    private Integer districtId = 99;
    private Integer hallType = 99;
    private Integer pageSize = 12;
    private Integer nowPage = 1;

}
