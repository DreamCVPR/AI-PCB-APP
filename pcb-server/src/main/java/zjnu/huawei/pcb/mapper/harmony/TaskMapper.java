package zjnu.huawei.pcb.mapper.harmony;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.*;
import zjnu.huawei.pcb.entity.basic.UserEntity;
import zjnu.huawei.pcb.entity.harmony.TaskEntity;
import zjnu.huawei.pcb.entity.system.HarmonyUserEntity;

import java.util.List;


public interface TaskMapper {
    @Select({"<script>SELECT " +
            "task_id, task_name, task_desc, harmony_user_id, task_state, gmt_create " +
            "FROM harmony_task " +
            "WHERE TRUE " +
            "AND harmony_user_id = #{harmony_user_id} " +
            "ORDER BY " +
            "gmt_create DESC " +
            "</script>"})
    List<TaskEntity> queryById(@Param("harmony_user_id") Long harmony_user_id) throws Exception;

    @Insert({"<script>" +
            "INSERT INTO harmony_task (" +
            "task_name, task_desc, harmony_user_id, task_state, gmt_create) " +
            "VALUES " +
            "<foreach collection=\"entityList\" index=\"index\" item=\"entity\" separator=\",\">" +
            "(#{entity.taskName}, #{entity.taskDesc}, #{entity.harmonyUserId}, #{entity.taskState}, #{entity.gmtCreate}) " +
            "</foreach>" +
            "</script>"})
    @Options(useGeneratedKeys = true, keyProperty = "taskId", keyColumn = "task_id")
    Integer addList(@Param("entityList") List<TaskEntity> entityList) throws Exception;

    @Insert({"<script>" +
            "INSERT INTO harmony_task (" +
            "task_name, task_desc, harmony_user_id, task_state, gmt_create) " +
            "VALUES " +
            "(#{entity.taskName}, #{entity.taskDesc}, #{entity.harmonyUserId}, #{entity.taskState}, #{entity.gmtCreate}) " +
            "</script>"})
    @Options(useGeneratedKeys = true, keyProperty = "taskId", keyColumn = "task_id")
    Integer add(@Param("entity") TaskEntity entity) throws Exception;

    @Update("UPDATE harmony_task " +
            "SET task_name = #{entity.taskName}, task_desc = #{entity.taskDesc}, task_state = #{entity.taskState}, harmony_user_id = #{entity.harmonyUserId} " +
            "WHERE task_id = #{entity.taskId}")
    Integer update(@Param("entity") TaskEntity entity) throws Exception;

    @Update("UPDATE harmony_task " +
            "SET task_state = #{entity.taskState} " +
            "WHERE task_id = #{entity.taskId}")
    Integer updateState(@Param("entity") TaskEntity entity) throws Exception;

    @Delete("DELETE FROM harmony_task WHERE task_id = #{taskId}")
    Integer remove(@Param("taskId") Long taskId) throws Exception;
}
