package com.stylefeng.guns.rest.modular.vo;

import lombok.Data;

@Data
public class ResponseVo<T> {

    // 0 success, 1 fail, 500 error

    private int status;

    private String msg;

    private T data;

    private String imgPre;

    private ResponseVo(){}

    public static <T> ResponseVo serviceSuccess(String imgPre, T t) {
        ResponseVo responseVo = new ResponseVo();
        responseVo.setStatus(0);
        responseVo.setData(t);
        responseVo.setImgPre(imgPre);
        return responseVo;
    }

    public static<T> ResponseVo serviceSuccess(T t){
        ResponseVo responseVo = new ResponseVo();
        responseVo.setStatus(0);
        responseVo.setData(t);

        return responseVo;
    }

    public static <T> ResponseVo serviceSuccess(String msg) {
        ResponseVo responseVo = new ResponseVo();
        responseVo.setStatus(0);
        responseVo.setMsg(msg);

        return responseVo;
    }

    public static<T> ResponseVo serviceFail(String msg){
        ResponseVo responseVo = new ResponseVo();
        responseVo.setStatus(1);
        responseVo.setMsg(msg);

        return responseVo;
    }

    public static<T> ResponseVo appFail(String msg){
        ResponseVo responseVo = new ResponseVo();
        responseVo.setStatus(500);
        responseVo.setMsg(msg);

        return responseVo;
    }

}
