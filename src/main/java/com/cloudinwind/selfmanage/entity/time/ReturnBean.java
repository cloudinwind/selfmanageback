package com.cloudinwind.selfmanage.entity.time;

public class ReturnBean<T> {
    private Integer code;

    private Long count;

    private String msg;

    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ReturnBean{" +
                "code=" + code +
                ", count=" + count +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
