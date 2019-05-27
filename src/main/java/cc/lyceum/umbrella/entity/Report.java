package cc.lyceum.umbrella.entity;

import lombok.Data;

import java.sql.Date;

/**
 * 举报表
 *
 * @author Lyceum Hewun
 * @date 2019-05-13 23:33
 */
@Data
public class Report {

    private Long id;
    private Long tweetsId;
    private Long commentId;
    /**
     * 举报人id
     */
    private Long reportUserId;
    /**
     * 举报类型
     * TODO
     */
    private Integer type;
    private String message;
    private Date createTime;
    private Date modifyTime;
}
