package cc.lyceum.umbrella.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * 钱包表
 *
 * @author Lyceum Hewun
 * @date 2019-05-13 23:46
 */
@Data
public class Wallet {

    private Long userId;
    private BigDecimal money;
    private Date createTime;
    private Date modifyTime;
}
