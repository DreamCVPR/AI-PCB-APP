package zjnu.huawei.pcb.mapper.basic;

import zjnu.huawei.pcb.dto.basic.UserDTO;
import zjnu.huawei.pcb.entity.basic.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserMapper {
    /**
     * @Author: dy
     * @Date: 2021/10/27
     * @Description: 查询是否存在用户冲突
     */
    @Select("SELECT dd_Id FROM dd_user WHERE enterprise_id = #{userEntity.enterpriseId} " +
            "and card_number = #{userEntity.cardNumber}")
    List<UserDTO> judgeUser(@Param("userEntity") UserEntity userEntity) throws Exception;

    /**
     * @Author: dy
     * @Date: 2021/10/27
     * @Description: 查询用户信息是否准确
     */
    @Select("SELECT count(*) FROM basic_staff WHERE enterprise_id = #{userEntity.enterpriseId} " +
            "and staff_code = #{userEntity.cardNumber} and staff_name = #{userEntity.userName} ")
    Integer judgeUserInfo(@Param("userEntity") UserEntity userEntity) throws Exception;

    /**
     * @Author: dy
     * @Date: 2021/10/27
     * @Description: 更新用户信息
     */
    @Update("UPDATE dd_user SET enterprise_id = #{userEntity.enterpriseId}, user_name = #{userEntity.userName}, " +
            "card_number = #{userEntity.cardNumber}, gmt_create = #{userEntity.gmtCreate} " +
            "WHERE dd_Id = #{userEntity.ddId} ")
    Integer updateUser(@Param("userEntity") UserEntity userEntity) throws Exception;
}
