package cc.lyceum.umbrella.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * @author Lyceum Hewun
 * @date 2019-05-13 23:47
 */
@Data
public class WalletLog {

    private Long id;
    private Long userId;
    private BigDecimal originalMoney;
    private BigDecimal laterMoney;
    private BigDecimal diffMoney;
    private Date createTime;
    private Date modifyTime;
}
