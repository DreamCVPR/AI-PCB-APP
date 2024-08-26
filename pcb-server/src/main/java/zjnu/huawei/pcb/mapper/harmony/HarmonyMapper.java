package zjnu.huawei.pcb.mapper.harmony;

import zjnu.huawei.pcb.dto.harmony.HarmonyDTO;
import zjnu.huawei.pcb.entity.system.HarmonyUserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


public interface HarmonyMapper {
    /*
     * @Description 根据钉钉userId查询用户
     * @Author DY
     * @Date 2021/12/5
     **/
    @Select("SELECT dd_id, enterprise_id, user_name, card_number, dd_user_id FROM dd_user WHERE dd_user_id = #{ddUserId}")
    HarmonyDTO queryWxUser(@Param("ddUserId") String ddUserId) throws Exception;

    @Select("SELECT * FROM subscriber_user WHERE dd_user_id = #{ddUserId}")
    HarmonyDTO querySubscriberUser(@Param("ddUserId") String ddUserId) throws Exception;
    /**
     * @Author: dy
     * @Date: 2021/9/8
     * @Description: 新增dd用户
     */
    @Insert("Insert into dd_user (dd_user_id, gmt_create) Values (#{entity.ddUserId}, #{entity.gmtCreate})")
    @Options(useGeneratedKeys = true, keyProperty = "ddId", keyColumn = "dd_id")
    Long addDdUser(@Param("entity") HarmonyUserEntity entity) throws Exception;


}
