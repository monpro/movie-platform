package com.stylefeng.guns.rest.modular.film;

import com.stylefeng.guns.rest.modular.vo.ResponseVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/film/")
public class FilmController {
    /*
        API Gateway:
            1. api group
               - 6 interfaces, one http request
               - only expose one interface
               - data might be two large
     */
    @RequestMapping(value = "getIndex", method = RequestMethod.GET)
    public ResponseVo getIndex() {
        // banner info

        // hot movies showing

        // movies coming soon

        // movie rate

        // movie popular

        // top 100 movies
        return null;
    }
}
