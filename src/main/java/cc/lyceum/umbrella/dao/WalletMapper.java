package cc.lyceum.umbrella.dao;

import cc.lyceum.umbrella.entity.Wallet;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author Lyceum Hewun
 * @date 2019-05-16 16:24
 */
@Mapper
@Component
public interface WalletMapper {

    @Insert("INSERT INTO `wallet` (user_id, money) VALUES " +
            "(#{w.userId}, #{w.money})")
    @ResultType(Boolean.class)
    @Options(useGeneratedKeys = true, keyProperty = "userId", keyColumn = "user_id")
    Boolean insert(@Param("w") Wallet wallet);

    @Select("SELECT * FROM `wallet` WHERE user_id=#{id}")
    Wallet getByUserId(@Param("id") Long userId);

    @Update("UPDATE `wallet` SET money = money + #{m} WHERE user_id=#{id}")
    Boolean addMoney(@Param("id") Long userId, @Param("m") BigDecimal money);

    @Update("UPDATE `wallet` SET money = money - #{m} WHERE user_id=#{id}")
    Boolean deductingMoney(@Param("id") Long userId, @Param("m") BigDecimal money);

}
