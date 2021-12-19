package cn.xavier.hrm.constant;

public enum GlobalStatusCode {
    TENANT_SETTLEMENT_ERROR(5001, "机构入驻异常");

    private Integer code;
    private String message;
    GlobalStatusCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
