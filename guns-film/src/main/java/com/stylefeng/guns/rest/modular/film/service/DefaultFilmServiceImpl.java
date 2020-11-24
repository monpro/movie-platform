package com.stylefeng.guns.rest.modular.film.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
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
@Service(interfaceClass = FilmServiceApi.class)
public class DefaultFilmServiceImpl implements FilmServiceApi {

    @Autowired
    private MoocBannerTMapper moocBannerTMapper;

    @Autowired
    private MoocFilmTMapper moocFilmTMapper;

    @Autowired
    private MoocCatDictTMapper moocCatDictTMapper;

    @Autowired
    private MoocYearDictTMapper moocYearDictTMapper;

    @Autowired
    private MoocSourceDictTMapper moocSourceDictTMapper;

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

    @Override
    public List<CatVO> getCats() {
        // get entity - MoocCatDictT
        List<MoocCatDictT> moocCatDictTList = moocCatDictTMapper.selectList(null);

        List<CatVO> catVOList = new ArrayList<>();

        for (MoocCatDictT moocCatDictT : moocCatDictTList) {
            // entity - vo CatVO
            CatVO catVO = new CatVO();
            catVO.setCatId(moocCatDictT.getUuid() + "");
            catVO.setCatName(moocCatDictT.getShowName());
            catVOList.add(catVO);
        }
        return catVOList;
    }

    @Override
    public List<SourceVO> getSources() {
        List<SourceVO> sourceVOList = new ArrayList<>();
        List<MoocSourceDictT> sourceDictTList = moocSourceDictTMapper.selectList(null);

        for (MoocSourceDictT moocSourceDictT : sourceDictTList) {
            SourceVO sourceVO = new SourceVO();
            sourceVO.setSourceId(moocSourceDictT.getUuid() + "");
            sourceVO.setSourceName(moocSourceDictT.getShowName());
            sourceVOList.add(sourceVO);
        }
        return sourceVOList;
    }

    @Override
    public List<YearVO> getYears() {
        List<MoocYearDictT> moocYearDictTList = moocYearDictTMapper.selectList(null);

        List<YearVO> yearVOList = new ArrayList<>();
        for (MoocYearDictT moocYearDictT : moocYearDictTList) {
            YearVO yearVO = new YearVO();
            yearVO.setYearId(moocYearDictT.getUuid() + "");
            yearVO.setYearName(moocYearDictT.getShowName());
            yearVOList.add(yearVO);
        }

        return yearVOList;
    }
}
