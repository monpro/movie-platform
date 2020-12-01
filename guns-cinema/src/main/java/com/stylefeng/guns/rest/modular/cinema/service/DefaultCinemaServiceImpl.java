package com.stylefeng.guns.rest.modular.cinema.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.style.guns.api.cinema.CinemaServiceAPI;
import com.style.guns.api.cinema.vo.*;
import com.stylefeng.guns.rest.common.persistence.dao.*;
import com.stylefeng.guns.rest.common.persistence.model.MoocAreaDictT;
import com.stylefeng.guns.rest.common.persistence.model.MoocBrandDictT;
import com.stylefeng.guns.rest.common.persistence.model.MoocCinemaT;
import com.stylefeng.guns.rest.common.persistence.model.MoocHallDictT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Service(interfaceClass = CinemaServiceAPI.class)
public class DefaultCinemaServiceImpl implements CinemaServiceAPI {

    @Autowired
    private MoocCinemaTMapper moocCinemaTMapper;

    @Autowired
    private MoocAreaDictTMapper moocAreaDictTMapper;

    @Autowired
    private MoocBrandDictTMapper moocBrandDictTMapper;

    @Autowired
    private MoocHallDictTMapper moocHallDictTMapper;

    @Autowired
    private MoocHallFilmInfoTMapper moocHallFilmInfoTMapper;

    @Autowired
    private MoocFieldTMapper moocFieldTMapper;

    @Override
    public Page<CinemaVO> getCinemas(CinemaQueryVO cinemaQueryVO) {

        List<CinemaVO> cinemaVOS = new ArrayList<>();
        EntityWrapper<MoocCinemaT> entityWrapper = new EntityWrapper<>();
        Page<MoocCinemaT> page = new Page<>(cinemaQueryVO.getNowPage(), cinemaQueryVO.getPageSize());

        if (cinemaQueryVO.getBrandId() != 99) {
            entityWrapper.eq("brand_id", cinemaQueryVO.getBrandId());
        }
        if (cinemaQueryVO.getDistrictId() != 99) {
            entityWrapper.eq("area_id", cinemaQueryVO.getDistrictId());
        }
        if (cinemaQueryVO.getHallType() != 99) {
            entityWrapper.like("hall_ids", "%#+" + cinemaQueryVO.getHallType() + "+#%");
        }

        //DO to VO
        List<MoocCinemaT> moocCinemaDOs = moocCinemaTMapper.selectPage(page, entityWrapper);
        for (MoocCinemaT moocCinemaDO : moocCinemaDOs) {
            CinemaVO cinemaVO = new CinemaVO();
            cinemaVO.setUuid(moocCinemaDO.getUuid() + "");
            cinemaVO.setMinimumPrice(moocCinemaDO.getMinimumPrice() + "");
            cinemaVO.setCinemaName(moocCinemaDO.getCinemaName());
            cinemaVO.setAddress(moocCinemaDO.getCinemaAddress());
            cinemaVOS.add(cinemaVO);
        }

        long counts = moocCinemaTMapper.selectCount(entityWrapper);

        Page<CinemaVO> result = new Page<>();
        result.setRecords(cinemaVOS);
        result.setSize(cinemaQueryVO.getPageSize());
        result.setTotal(counts);

        return result;
    }

    @Override
    public List<BrandVO> getBrands(int brandId) {
        boolean isDefault = false;
        List<BrandVO> brandVOS = new ArrayList<>();

        MoocBrandDictT moocBrandDictT = moocBrandDictTMapper.selectById(brandId);

        if (brandId == 99 || moocBrandDictT == null || moocBrandDictT.getUuid() == null) {
            isDefault = true;
        }

        List<MoocBrandDictT> moocBrandDictTList = moocBrandDictTMapper.selectList(null);
        for (MoocBrandDictT brandDictT : moocBrandDictTList) {
            BrandVO brandVO = new BrandVO();
            brandVO.setBrandId(brandDictT.getUuid() + "");
            brandVO.setBrandName(brandDictT.getShowName());
            if (isDefault) {
                if (brandId == 99) {
                    brandVO.setActive(true);
                }
            } else {
                if (brandDictT.getUuid() == brandId) {
                    brandVO.setActive(true);
                }
            }
            brandVOS.add(brandVO);
        }

        return brandVOS;
    }

    @Override
    public List<AreaVO> getAreas(int areaId) {
        boolean isDefault = false;
        List<AreaVO> areaVOS = new ArrayList<>();

        MoocAreaDictT moocAreaDictT = moocAreaDictTMapper.selectById(areaId);

        if (areaId == 99 || moocAreaDictT == null || moocAreaDictT.getUuid() == null) {
            isDefault = true;
        }

        List<MoocAreaDictT> moocAreaDictTList = moocAreaDictTMapper.selectList(null);
        for (MoocAreaDictT areaDictT : moocAreaDictTList) {
            AreaVO areaVO = new AreaVO();
            areaVO.setAreaId(areaDictT.getUuid() + "");
            areaVO.setAreaName(areaDictT.getShowName());
            if (isDefault) {
                if (areaId == 99) {
                    areaVO.setActive(true);
                }
            } else {
                if (areaDictT.getUuid() == areaId) {
                    areaVO.setActive(true);
                }
            }
            areaVOS.add(areaVO);
        }

        return areaVOS;
    }

    @Override
    public List<HallTypeVO> getHallTypes(int hallType) {
        boolean isDefault = false;
        List<HallTypeVO> hallTypeVOS = new ArrayList<>();

        MoocHallDictT moocHallDictT = moocHallDictTMapper.selectById(hallType);

        if (hallType == 99 || moocHallDictT == null || moocHallDictT.getUuid() == null) {
            isDefault = true;
        }

        List<MoocHallDictT> moocHallDictTList = moocHallDictTMapper.selectList(null);
        for (MoocHallDictT moocHallDict : moocHallDictTList) {
            HallTypeVO hallTypeVO = new HallTypeVO();
            hallTypeVO.setHallTypeId(moocHallDict.getUuid() + "");
            hallTypeVO.setHallTypeName(moocHallDict.getShowName());
            if (isDefault) {
                if (hallType == 99) {
                    hallTypeVO.setActive(true);
                }
            } else {
                if (moocHallDict.getUuid() == hallType) {
                    hallTypeVO.setActive(true);
                }
            }
            hallTypeVOS.add(hallTypeVO);
        }

        return hallTypeVOS;
    }

    @Override
    public CinemaInfoVO getCinemaInfo(int cinemaId) {

        MoocCinemaT moocCinemaT = moocCinemaTMapper.selectById(cinemaId);

        CinemaInfoVO cinemaInfoVO = new CinemaInfoVO();

        cinemaInfoVO.setCinemaAddress(moocCinemaT.getCinemaAddress());
        cinemaInfoVO.setCinemaId(moocCinemaT.getUuid() + "");
        cinemaInfoVO.setImgUrl(moocCinemaT.getImgAddress());
        cinemaInfoVO.setCinemaPhone(moocCinemaT.getCinemaPhone());
        cinemaInfoVO.setCinemaName(moocCinemaT.getCinemaName());

        return cinemaInfoVO;
    }

    @Override
    public List<FilmInfoVO> getFilmInfoByCinemaId(int cinemaId) {
        List<FilmInfoVO> filmInfos = moocFieldTMapper.getFilmInfos(cinemaId);
        return filmInfos;
    }

    @Override
    public FilmInfoVO getFilmInfoByFieldId(int fieldId) {
        return null;
    }

    @Override
    public FilmFieldVO getFilmFieldInfo(int field) {
        return null;
    }
}
