package com.stylefeng.guns.rest.modular.film.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.style.guns.api.film.FilmAsyncServiceApi;
import com.style.guns.api.film.FilmServiceApi;
import com.style.guns.api.film.vo.*;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.rest.common.persistence.dao.*;
import com.stylefeng.guns.rest.common.persistence.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Service(interfaceClass = FilmAsyncServiceApi.class)
public class AsyncFilmServiceImpl implements FilmAsyncServiceApi {

    @Autowired
    private MoocFilmInfoTMapper moocFilmInfoTMapper;

    @Autowired
    private MoocActorTMapper moocActorTMapper;

    private MoocFilmInfoT getFilmInfo(String filmId) {
        MoocFilmInfoT filmInfoT = new MoocFilmInfoT();
        filmInfoT.setFilmId(filmId);

        filmInfoT = moocFilmInfoTMapper.selectOne(filmInfoT);

        return filmInfoT;
    }

    @Override
    public FilmDescVO getFilmDesc(String filmId) {
        MoocFilmInfoT filmInfoT = getFilmInfo(filmId);

        FilmDescVO filmDescVO = new FilmDescVO();
        filmDescVO.setBiography(filmInfoT.getBiography());
        filmDescVO.setBiography(filmId);

        return filmDescVO;
    }

    @Override
    public ImgVO getImgs(String filmId) {
        MoocFilmInfoT filmInfoT = getFilmInfo(filmId);
        String filmImgStr = filmInfoT.getFilmImgs();

        String[] filmImgs = filmImgStr.split(",");

        ImgVO imgVO = new ImgVO();
        imgVO.setMainImg(filmImgs[0]);
        imgVO.setImg01(filmImgs[1]);
        imgVO.setImg02(filmImgs[2]);
        imgVO.setImg03(filmImgs[3]);
        imgVO.setImg04(filmImgs[4]);

        return imgVO;
    }

    @Override
    public ActorVO getDirectorVO(String filmId) {
        MoocFilmInfoT filmInfoT = getFilmInfo(filmId);
        Integer directorId = filmInfoT.getDirectorId();

        MoocActorT director = moocActorTMapper.selectById(directorId);

        ActorVO actorVO = new ActorVO();
        actorVO.setImgAddress(director.getActorImg());
        actorVO.setDirectorName(director.getActorName());

        return actorVO;
    }

    @Override
    public List<ActorVO> getActors(String filmId) {
        List<ActorVO> actors = moocActorTMapper.getActors(filmId);

        return actors;
    }
}
