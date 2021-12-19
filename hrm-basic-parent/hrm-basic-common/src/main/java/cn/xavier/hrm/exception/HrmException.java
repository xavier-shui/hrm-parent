package cn.xavier.hrm.exception;

import cn.xavier.hrm.constant.GlobalStatusCode;

//全局异常
public class HrmException extends RuntimeException {
    //异常码
    private Integer code = 0;

    public HrmException(String message) {
        super(message);
    }

    public HrmException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    //从错误枚举中获取错误信息和错误码
    public HrmException(GlobalStatusCode globalStatusCode) {
        super(globalStatusCode.getMessage());
        this.code = globalStatusCode.getCode();
    }

}