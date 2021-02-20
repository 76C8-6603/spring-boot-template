package com.ming.dictionary.common.exception;

/**
 * @author ming
 */
public abstract class BaseException extends RuntimeException {

    /**
     * 给前端展示的信息
     */
    private String viewMessage;

    public BaseException(){
        super();
    }

    public BaseException(String message) {
        super(message);
        viewMessage = message;
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
        viewMessage = message;
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        viewMessage = message;
    }

    public ResultEntity getResultEntity() {
        ResultEntity resultEntity = new ResultEntity();
        resultEntity.setData(null);
        resultEntity.setCode(getInfoEnum().getCode());
        resultEntity.setMsg(viewMessage == null ? getInfoEnum().getMessage() : viewMessage);
        return resultEntity;
    }

    /**
     * 获取{@link ResponseInfoEnum}
     * @return
     */
    protected abstract ResponseInfoEnum getInfoEnum();

}
