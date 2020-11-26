package com.style.guns.api.film.vo;

import com.style.guns.api.film.vo.ActorVO;
import lombok.Data;

import java.util.List;

@Data
public class ActorRequestVO {

    private ActorVO director;
    private List<ActorVO> actors;
}
