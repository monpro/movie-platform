package com.style.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class HallInfoVo implements Serializable {

    private String hallField;
    private String hallName;
    private String price;
    private String seatFile;
    private String soldSeats;
}
