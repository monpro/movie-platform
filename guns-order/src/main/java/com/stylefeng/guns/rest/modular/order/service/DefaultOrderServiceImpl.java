package com.stylefeng.guns.rest.modular.order.service;


import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.style.guns.api.order.OrderServiceAPI;
import com.style.guns.api.order.vo.OrderVO;
import com.stylefeng.guns.rest.common.persistence.dao.MoocOrderTMapper;
import com.stylefeng.guns.rest.common.util.FTPUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Service(interfaceClass = OrderServiceAPI.class)
public class DefaultOrderServiceImpl implements OrderServiceAPI {

    @Autowired
    private MoocOrderTMapper moocOrderTMapper;

    @Autowired
    private FTPUtil ftpUtil;

    @Override
    public boolean isTrueSeats(String fieldId, String seats) {

        String seatPath = moocOrderTMapper.getSeatsByField(fieldId);

        String fileStr = ftpUtil.getFileStrByAddress(seatPath);

        JSONObject jsonObject = JSONObject.parseObject(fileStr);

        String ids = jsonObject.get("ids").toString();

        String[] seatAddrs = seats.split(",");
        String[] idAddrs = ids.split(",");

        int isTrue = 0;
        for (String id : idAddrs) {
            for (String seat : seatAddrs) {
                if (seat.equalsIgnoreCase(id)) {
                    isTrue += 1;
                }
            }
        }
        return seatAddrs.length == isTrue;

        return false;
    }

    @Override
    public boolean isNotSoldSeats(String fieldId, String seats) {
        return false;
    }

    @Override
    public OrderVO saveOrderInfo(Integer fieldId, String soldSeats, String seatsName, Integer userId) {
        return null;
    }

    @Override
    public Page<OrderVO> getOrderByUserId(Integer userId, Page<OrderVO> page) {
        return null;
    }

    @Override
    public String getSoldSeatsByFieldId(Integer fieldId) {
        return null;
    }

    @Override
    public OrderVO getOrderInfoById(String orderId) {
        return null;
    }
}
