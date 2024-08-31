package zjnu.huawei.pcb.mapper.basic;

import org.apache.ibatis.annotations.*;
import zjnu.huawei.pcb.dto.basic.UserDTO;
import zjnu.huawei.pcb.entity.system.HarmonyUserEntity;

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
            "<foreach collection=\"entityList\" index=\"index\" item=\"entity\" separator=\",\">" +
            "(#{entity.gmtCreate}, #{entity.loginName}, #{entity.loginPwd}, #{entity.openId}, #{entity.unionId}, #{entity.userImg}, #{entity.userName}, #{entity.userPhone}) " +
            "</foreach>" +
            "</script>"})
    @Options(useGeneratedKeys = true, keyProperty = "harmonyUserId", keyColumn = "harmony_user_id")
    Integer addList(@Param("entityList") List<HarmonyUserEntity> entityList) throws Exception;

    @Insert({"<script>" +
            "INSERT INTO harmony_user (" +
            "gmt_create, login_name, login_pwd, open_id, union_id, user_img, user_name, user_phone) " +
            "VALUES " +
            "(#{entity.gmtCreate}, #{entity.loginName}, #{entity.loginPwd}, #{entity.openId}, #{entity.unionId}, #{entity.userImg}, #{entity.userName}, #{entity.userPhone}) " +
            "</script>"})
    @Options(useGeneratedKeys = true, keyProperty = "harmonyUserId", keyColumn = "harmony_user_id")
    Integer add(@Param("entity") HarmonyUserEntity entity) throws Exception;

    @Update("UPDATE harmony_user " +
            "SET gmt_create = #{entity.gmtCreate}, login_name = #{entity.loginName}, login_pwd = #{entity.loginPwd}, open_id = #{entity.openId}, union_id = #{entity.unionId}, user_img = #{entity.userImg}, user_name = #{entity.userName}, user_phone = #{entity.userPhone} " +
            "WHERE harmony_user_id = #{entity.harmonyUserId}")
    Integer update(@Param("entity") HarmonyUserEntity entity) throws Exception;

    @Delete("DELETE FROM harmony_user WHERE harmony_user_id = #{harmonyUserId}")
    Integer remove(@Param("harmonyUserId") Long harmonyUserId) throws Exception;
}
