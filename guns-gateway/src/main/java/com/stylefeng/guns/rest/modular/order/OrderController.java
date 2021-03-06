package com.stylefeng.guns.rest.modular.order;

import com.stylefeng.guns.rest.modular.vo.ResponseVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/order/")
public class OrderController {

    @RequestMapping(value = "buyTickets", method = RequestMethod.POST)
    public ResponseVo buyTickets(Integer field, String soldSeats, String seatsName) {

        return null;
    }

    @RequestMapping(value = "getOrderInfo", method = RequestMethod.POST)
    public ResponseVo getOrderInfo(
            @RequestParam(name = "nowPage", required = false, defaultValue = "1") Integer nowPage,
            @RequestParam(name = "pageSize", required = false, defaultValue = "5") Integer pageSize
    ) {
        return null;
    }


}
