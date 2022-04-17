package com.cloudinwind.selfmanage.controller.time;


import com.cloudinwind.selfmanage.entity.time.ReturnBean;

public class BaseController {

    // Long... count 表示有若干个count
    public ReturnBean success(Object object, Long... count){
        ReturnBean<Object> returnBean = new ReturnBean<>();
        returnBean.setMsg("成功");
        returnBean.setCode(0);
        returnBean.setData(object);
        if (count!=null && count.length>0) returnBean.setCount(count[0]);
        return returnBean;
    }

    public ReturnBean success(Object object, String message){
        ReturnBean<Object> returnBean = new ReturnBean<>();
        returnBean.setMsg(message);
        returnBean.setCode(0);
        returnBean.setData(object);
        return returnBean;
    }

    public ReturnBean fail(Object object, Long... count){
        ReturnBean<Object> returnBean = new ReturnBean<>();
        returnBean.setMsg("失败");
        returnBean.setCode(1);
        returnBean.setData(object);
        if (count!=null && count.length>0) returnBean.setCount(count[0]);
        return returnBean;
    }

    public ReturnBean fail(Object object){
        ReturnBean<Object> returnBean = new ReturnBean<>();
        returnBean.setMsg("失败");
        returnBean.setCode(1);
        returnBean.setData(object);
        return returnBean;
    }

    public ReturnBean fail(Object object, String message){
        ReturnBean<Object> returnBean = new ReturnBean<>();
        returnBean.setMsg(message);
        returnBean.setCode(1);
        returnBean.setData(object);
        return returnBean;
    }
}
