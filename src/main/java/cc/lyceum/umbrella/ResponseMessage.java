package cc.lyceum.umbrella;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;

/**
 * @param <T>
 * @author lamZe
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ResponseMessage<T> implements Serializable {

    private static final int STATUS_SUCCESS = 0;
    private static final int STATUS_FAIL = 1;

    /**
     * 成功为0，失败为1
     */
    private int code;
    /**
     * 返回的响应信息
     */
    private String msg;
    /**
     * 返回对象信息
     */
    private T data;

    ResponseMessage() {
    }

    ResponseMessage(int code) {
        this.code = code;
    }

    ResponseMessage(int code, T data) {
        this.code = code;
        this.data = data;
    }

    ResponseMessage(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    ResponseMessage(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return this.code == STATUS_SUCCESS;
    }

    @JsonIgnore
    public boolean isNotSuccess() {
        return !isSuccess();
    }

    public int getCode() {
        return this.code;
    }

    public T getData() {
        return this.data;
    }

    public String getMsg() {
        return this.msg;
    }


    public static <T> ResponseMessage<T> createBySuccess() {
        return new ResponseMessage<T>(STATUS_SUCCESS,"成功");
    }


    public static <T> ResponseMessage<T> createBySuccessMessage(String msg) {
        return new ResponseMessage<T>(STATUS_SUCCESS, msg);
    }

    public static <T> ResponseMessage<T> createBySuccess(T data) {
        return new ResponseMessage<T>(STATUS_SUCCESS, data);
    }

    public static <T> ResponseMessage<T> createBySuccess(String msg, T data) {
        return new ResponseMessage<T>(STATUS_SUCCESS, msg, data);
    }

    public static <T> ResponseMessage<T> createByError() {
//        return new ResponseMessage<T>(STATUS_FAIL, "请与管理员联系");
        return new ResponseMessage<T>(STATUS_FAIL, "失败");
    }

    public static <T> ResponseMessage<T> createByError(T data) {
        return new ResponseMessage<T>(STATUS_FAIL, data);
    }

    public static <T> ResponseMessage<T> createByErrorMessage(String msg) {
        return new ResponseMessage<T>(STATUS_FAIL, msg);
    }

    public static <T> ResponseMessage<T> createByErrorCodeMessage(int errorCode, String msg) {
        return new ResponseMessage<T>(errorCode, msg);
    }

    public static <T> ResponseMessage<T> createByErrorCodeData(int errorCode, T data) {
        return new ResponseMessage<T>(errorCode, data);
    }

    public static ResponseMessage isSuccess(Integer i) {
        if (i > 0) {
            return ResponseMessage.createBySuccess();
        }
        return ResponseMessage.createByErrorMessage("系统繁忙，请稍后再试");
    }


    public static ResponseMessage isSuccess(Integer i,String errorMsg) {
        if (i > 0) {
            return ResponseMessage.createBySuccess();
        }
        return ResponseMessage.createByErrorMessage(errorMsg);
    }


    public static <T> ResponseMessage<T> exists(T t) {
        if (t != null) {
            return ResponseMessage.createBySuccess(t);
        }
        return ResponseMessage.createByError();
    }


    public static <T> ResponseMessage<T> exists(T t,String errorMsg) {
        if (t != null) {
            return ResponseMessage.createBySuccess(t);
        }
        return ResponseMessage.createByErrorMessage(errorMsg);
    }



    @Override
    public String toString() {
        return "ResponseMessage{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
