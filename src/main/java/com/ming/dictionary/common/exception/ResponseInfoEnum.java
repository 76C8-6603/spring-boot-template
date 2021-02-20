package com.ming.dictionary.common.exception;

/**
 * @author ming
 */
public enum ResponseInfoEnum {
    /**
     * 对应一种业务状态
     */
    SUCCESS(1000, "success")
    , UNKNOWN_EXCEPTION(-1, "系统错误")
    , WORD_EXCEPTION(1001, "单词操作异常")
    , TOO_MANY_REQUEST(1002, "服务正忙")
    , POST_CONTENT_OVERLOAD(1003, "非法请求内容");
    /**
     * 异常码
     */
    private int code;


    /**
     * 异常信息
     */
    private String msg;


    ResponseInfoEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultEntity buildResultEntity() {
        ResultEntity<Object> resultEntity = new ResultEntity<>();
        resultEntity.setCode(code);
        resultEntity.setMsg(msg);
        return resultEntity;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
