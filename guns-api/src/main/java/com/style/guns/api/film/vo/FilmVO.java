package com.style.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class FilmVO implements Serializable {

    private int filmNum;
    private List<FilmInfo> filmInfoList;
    private int nowPage;
    private int totalPage;
}
