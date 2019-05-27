package cc.lyceum.umbrella.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.sql.Date;

/**
 * @author Lyceum Hewun
 * @date 2019-05-13 23:39
 */
@Data
public class Tweets {

    private Long id;
    private Long userId;
    private String pictures;
    private String content;
    /**
     * 查看权限
     */
    private Integer permissionType;
    /**
     * 推文类型
     */
    private Integer type;
    private Date createTime;
    @JsonIgnore
    private Date modifyTime;
    @JsonIgnore
    private Boolean deleted;

    @Getter
    @AllArgsConstructor
    public enum PermissionType {
        PUBLIC(1, "公开"),
        PUBLIC_TO_FRIENDS(2, "仅朋友可见"),
        PRIVATE(3, "仅自己可见");

        private int code;
        private String message;
    }

    @Getter
    @AllArgsConstructor
    public enum Type {
        NORMAL(1, "普通类型"),
        INTERESTING(2, "趣图类型");

        private int code;
        private String message;
    }
}
