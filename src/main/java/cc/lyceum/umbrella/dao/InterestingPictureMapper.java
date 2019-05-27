package cc.lyceum.umbrella.dao;

import cc.lyceum.umbrella.entity.InterestingPicture;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * @author Lyceum Hewun
 * @date 2019-05-17 20:07
 */
@Mapper
@Component
public interface InterestingPictureMapper {

    @Insert("INSERT INTO `interesting_picture` (tweets_id, picture, gallery_index, longitude, latitude, hidden_content, has_gift, gift_quantity, gift_total_amount, gift_balance) VALUES " +
            "(#{i.tweetsId}, #{i.picture}, #{i.galleryIndex}, #{i.longitude}, #{i.latitude}, #{i.hiddenContent}, #{i.hasGift}, #{i.giftQuantity}, #{i.giftTotalAmount}, #{i.giftBalance})")
    Boolean insert(@Param("i") InterestingPicture interestingPicture);

    @Select("SELECT * FROM `interesting_picture` WHERE tweets_id=#{id}")
    InterestingPicture get(@Param("id") Long tweetsId);
}
