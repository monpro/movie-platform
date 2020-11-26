package com.style.guns.api.film;

import com.style.guns.api.film.vo.ActorVO;
import com.style.guns.api.film.vo.FilmDescVO;
import com.style.guns.api.film.vo.ImgVO;

import java.util.List;

public interface FilmAsyncServiceApi {

    FilmDescVO getFilmDesc(String filmId);

    ImgVO getImgs(String filmId);

    ActorVO getDirectorVO(String filmId);

    List<ActorVO> getActors(String filmId);

}
