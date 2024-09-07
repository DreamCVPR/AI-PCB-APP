package zjnu.huawei.pcb.mapper.basic;

import org.apache.ibatis.annotations.*;
import zjnu.huawei.pcb.entity.basic.HarmonyUserEntity;

import java.util.List;

public interface UserMapper {

    @Select({"<script>SELECT " +
            "harmony_user_id, gmt_create, open_id, union_id, user_img, user_name, login_name, user_phone, login_pwd " +
            "FROM harmony_user " +
            "WHERE TRUE " +
            "AND login_name = #{loginName} " +
            "LIMIT 1 " +
            "</script>"})
    HarmonyUserEntity query(@Param("loginName") String loginName) throws Exception;

    @Select({"<script>SELECT " +
            "harmony_user_id, gmt_create, open_id, union_id, user_img, user_name, login_name, user_phone, login_pwd " +
            "FROM harmony_user " +
            "WHERE TRUE " +
            "AND harmony_user_id = #{harmonyUserId} " +
            "LIMIT 1 " +
            "</script>"})
    HarmonyUserEntity queryById(@Param("harmonyUserId") Long harmonyUserId) throws Exception;

    @Insert({"<script>" +
            "INSERT INTO harmony_user (" +
            "gmt_create, login_name, login_pwd, open_id, union_id, user_img, user_name, user_phone) " +
            "VALUES " +
            "(#{entity.gmtCreate}, #{entity.loginName}, #{entity.loginPwd}, #{entity.openId}, #{entity.unionId}, #{entity.userImg}, #{entity.userName}, #{entity.userPhone}) " +
            "</script>"})
    @Options(useGeneratedKeys = true, keyProperty = "harmonyUserId", keyColumn = "harmony_user_id")
    Integer add(@Param("entity") HarmonyUserEntity entity) throws Exception;

    @Update("UPDATE harmony_user " +
            "SET user_name = #{entity.userName}, user_phone = #{entity.userPhone}, user_info = #{entity.userInfo} " +
            "WHERE harmony_user_id = #{entity.harmonyUserId}")
    Integer update(@Param("entity") HarmonyUserEntity entity) throws Exception;

    @Delete("DELETE FROM harmony_user WHERE harmony_user_id = #{harmonyUserId}")
    Integer remove(@Param("harmonyUserId") Long harmonyUserId) throws Exception;

    @Update("update harmony_user set user_img = #{imageUrl} where harmony_user_id = #{harmonyUserId}")
    Integer changeImage(@Param("harmonyUserId")Long harmonyUserId,
                        @Param("imageUrl")String imageUrl);

    @Update("UPDATE harmony_user SET login_pwd = #{loginPwd} WHERE harmony_user_id = #{harmonyUserId}")
    Integer resetPwd(@Param("harmonyUserId") Long harmonyUserId,
                     @Param("loginPwd") String loginPwd) throws Exception;
}
