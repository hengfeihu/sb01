package com.heng.sb01.entity;

public class Result {

    private volatile static Result instance;

    // http 状态码
    private int code;

    // 返回信息
    private String msg;

    // 返回的数据
    private Object data;

    public Result() {

    }

    public static Result getInstance() {
        if (instance == null) {
            synchronized (Result.class) {
                if (instance == null) {
                    instance = new Result();
                }
            }
        }
        return instance;
    }

    public Result success(String msg, Object data) {
        return getInstance().setCode(200).setMsg(msg).setData(data);
    }

    public Result error(String msg, Object data) {
        return getInstance().setCode(500).setMsg(msg).setData(data);
    }

    public Result error(int code, String msg, Object data) {
        return error(msg, data).setCode(code);
    }

    public int getCode() {
        return code;
    }

    public Result setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Result setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getData() {
        return data;
    }

    public Result setData(Object data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "Result{code=" + code + ", msg='" + msg + ", data=" + data + '}';
    }
}
