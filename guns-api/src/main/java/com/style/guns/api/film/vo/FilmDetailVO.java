package com.style.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class FilmDetailVO implements Serializable {

    private String filmName;
    private String fileEnName;
    private String imgAddress;
    private String score;
    private String scoreNum;
    private String totalBox;
    private String categoryInfo;
    private String originInfo;
    private String releaseTimeInfo;
}
