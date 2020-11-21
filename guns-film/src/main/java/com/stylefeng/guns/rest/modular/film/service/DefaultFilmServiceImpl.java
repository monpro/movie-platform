package com.stylefeng.guns.rest.modular.film.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.style.guns.api.film.FilmServiceApi;
import com.style.guns.api.film.vo.BannerVO;
import com.style.guns.api.film.vo.FilmInfo;
import com.style.guns.api.film.vo.FilmVO;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.rest.common.persistence.dao.MoocBannerTMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MoocFilmTMapper;
import com.stylefeng.guns.rest.common.persistence.model.MoocBannerT;
import com.stylefeng.guns.rest.common.persistence.model.MoocFilmT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Service(interfaceClass = FilmServiceApi.class)
public class DefaultFilmServiceImpl implements FilmServiceApi {

    @Autowired
    private MoocBannerTMapper moocBannerTMapper;

    @Autowired
    private MoocFilmTMapper moocFilmTMapper;

    @Override
    public List<BannerVO> getBanners() {
        List<BannerVO> result = new ArrayList<>();
        List<MoocBannerT> moocBanners = moocBannerTMapper.selectList(null);
        for (MoocBannerT moocBannerT : moocBanners) {
            BannerVO bannerVO = new BannerVO();
            bannerVO.setBannerId(moocBannerT.getUuid() + "");
            bannerVO.setBannerAddress(moocBannerT.getBannerAddress());
            bannerVO.setBannerUrl(moocBannerT.getBannerUrl());
            result.add(bannerVO);
        }
        return result;
    }

    @Override
    public FilmVO getHotFilms(boolean isLimit, int nums) {
        // isLimit is true if at main page
        FilmVO result = new FilmVO();
        List<FilmInfo> filmInfoList = new ArrayList<>();
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        // 1 means movies showing
        entityWrapper.eq("film_status", "1");
        if (isLimit) {
            Page<MoocFilmT> page = new Page<>(1, nums);
            List<MoocFilmT> moocFilms = moocFilmTMapper.selectPage(page, entityWrapper);
            filmInfoList = getFilmInfoList(moocFilms);
            result.setFilmInfoList(filmInfoList);
        } else {
            // TODO
        }
        return result;
    }

    private List<FilmInfo> getFilmInfoList(List<MoocFilmT> moocFilms) {
        List<FilmInfo> result = new ArrayList<>();
        for (MoocFilmT moocFilmT : moocFilms) {
            FilmInfo filmInfo = new FilmInfo();
            filmInfo.setBoxNum(moocFilmT.getFilmBoxOffice());
            filmInfo.setExpectNum(moocFilmT.getFilmPresalenum());
            filmInfo.setFilmId(moocFilmT.getUuid() + "");
            filmInfo.setFilmName(moocFilmT.getFilmName());
            filmInfo.setFilmScore(moocFilmT.getFilmScore());
            filmInfo.setFilmType(moocFilmT.getFilmType());
            filmInfo.setImgAddress(moocFilmT.getImgAddress());
            filmInfo.setScore(moocFilmT.getFilmScore());
            filmInfo.setShowTime(DateUtil.getDay(moocFilmT.getFilmTime()));

            result.add(filmInfo);
        }
        return result;
    }

    @Override
    public FilmVO getSoonFilms(boolean isLimit, int nums) {
        // isLimit is true if at main page
        FilmVO result = new FilmVO();
        List<FilmInfo> filmInfoList = new ArrayList<>();
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        // 1 means movies showing
        entityWrapper.eq("film_status", "1");
        if (isLimit) {
            Page<MoocFilmT> page = new Page<>(1, nums);
            List<MoocFilmT> moocFilms = moocFilmTMapper.selectPage(page, entityWrapper);
            filmInfoList = getFilmInfoList(moocFilms);
            result.setFilmInfoList(filmInfoList);
        } else {
            // TODO: add other logic
        }
        return result;
    }

    @Override
    public List<FilmInfo> getBoxRanking() {
        // films showing boxing top 10
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        // 1 means movies showing
        entityWrapper.eq("film_status", "1");
        Page<MoocFilmT> page = new Page<>(1, 10, "film_box_office");
        List<MoocFilmT> moocFilms = moocFilmTMapper.selectPage(page, entityWrapper);
        return getFilmInfoList(moocFilms);
    }

    @Override
    public List<FilmInfo> getExpectRanking() {
        // films coming soon
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        // 2 means movies coming soon
        entityWrapper.eq("film_status", "2");
        Page<MoocFilmT> page = new Page<>(1, 10, "film_preSaleNum");
        List<MoocFilmT> moocFilms = moocFilmTMapper.selectPage(page, entityWrapper);
        return getFilmInfoList(moocFilms);
    }

    @Override
    public List<FilmInfo> getTop() {
        // films showing score top 10
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        // 1 means movies showing
        entityWrapper.eq("film_status", "1");
        Page<MoocFilmT> page = new Page<>(1, 10, "film_score");
        List<MoocFilmT> moocFilms = moocFilmTMapper.selectPage(page, entityWrapper);
        return getFilmInfoList(moocFilms);
    }
}
