package cc.lyceum.umbrella.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.sql.Date;

/**
 * @author Lyceum Hewun
 * @date 2019-05-13 23:43
 */
@Data
public class User implements Serializable {

    private Long id;
    /**
     * 手机号码国家代码
     */
    private String countryCode;
    private String phone;
    private String username;
    private String password;
    /**
     * 头像
     * 七牛云存储对应的文件名, 不保存完整url
     */
    private String avatar;
    /**
     * 自我介绍
     */
    private String introduction;
    private String website;
    /**
     * 用户类型
     */
    private Integer type;
    private Date createTime;
    private Date modifyTime;
    /**
     * 用户状态
     */
    private Integer status;

    @Getter
    @AllArgsConstructor
    public enum Type {
        GENERAL_USER(1, "普通用户");

        private int code;
        private String message;
    }

    @Getter
    @AllArgsConstructor
    public enum Status {
        NORMAL(1, "正常"),
        BAN(2, "禁止登录");

        private int code;
        private String message;
    }
}
