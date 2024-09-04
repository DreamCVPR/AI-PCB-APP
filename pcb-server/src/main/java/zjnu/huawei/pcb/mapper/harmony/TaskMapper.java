package zjnu.huawei.pcb.mapper.harmony;

import org.apache.ibatis.annotations.*;
import zjnu.huawei.pcb.dto.harmony.TaskDTO;
import zjnu.huawei.pcb.dto.harmony.TaskImgDTO;
import zjnu.huawei.pcb.entity.harmony.TaskEntity;

import java.util.Date;
import java.util.List;


public interface TaskMapper {
    @Select({"<script>SELECT " +
            "ht.task_id, task_name, task_desc, harmony_user_id, task_state, gmt_create, " +
            "(SELECT img_url FROM task_img ti WHERE ti.gmt_delete is NULL AND ti.task_id = ht.task_id LIMIT 1) as taskCoverImg " +
            "FROM harmony_task ht " +
            "WHERE ht.gmt_delete is NULL " +
            "AND harmony_user_id = #{queryParam.harmonyUserId} " +
            "<if test='queryParam.taskName != null'> AND task_name LIKE CONCAT('%', #{queryParam.taskName}, '%') </if>" +
            "ORDER BY " +
            "<if test='queryParam.taskState != null'>" +
            "Field(task_state, #{queryParam.taskState}) DESC, " +
            "</if>" +
            "gmt_create DESC " +
            "</script>"})
    List<TaskDTO> queryById(@Param("queryParam") TaskDTO taskDTO) throws Exception;

    @Select({"<script>SELECT " +
            "ti.task_id, is_detect, detection_classes, detection_boxes, detection_scores " +
            "FROM task_img ti LEFT JOIN harmony_task ht on ti.gmt_delete is NULL AND ti.task_id = ht.task_id " +
            "WHERE ht.gmt_delete is NULL " +
            "AND harmony_user_id = #{harmonyUserId} " +
            "</script>"})
    List<TaskImgDTO> queryImgByUserId(@Param("harmonyUserId") Long harmonyUserId) throws Exception;

    @Select({"<script>SELECT " +
            "ht.task_id, COALESCE(SUM(is_detect), 0) as countDetectImg, COUNT(is_detect) as countAllImg, " +
            "SUM(CASE WHEN CHAR_LENGTH(detection_classes) > 2 THEN 1 ELSE 0 END) as countDefectImg " +
            "FROM harmony_task ht LEFT JOIN task_img ti on ti.gmt_delete is NULL AND ti.task_id = ht.task_id " +
            "WHERE ht.gmt_delete is NULL " +
            "AND harmony_user_id = #{harmonyUserId} " +
            "GROUP BY " +
            "task_id " +
            "</script>"})
    List<TaskDTO> countDetectImg(@Param("harmonyUserId") Long harmonyUserId) throws Exception;

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

    @Delete({"<script>DELETE FROM harmony_task " +
            "WHERE gmt_delete &lt;= DATE_SUB(CURDATE(), INTERVAL #{expiredDays} DAY) " +
            "</script>"})
    Integer removeExpired(@Param("expiredDays") Integer expiredDays) throws Exception;

    @Update("UPDATE harmony_task " +
            "SET gmt_delete = #{gmtDelete} " +
            "WHERE task_id = #{taskId}")
    Integer softRemove(@Param("taskId") Long taskId, @Param("gmtDelete") Date gmtDelete) throws Exception;

    @Update("UPDATE harmony_task " +
            "SET gmt_delete = NULL " +
            "WHERE task_id = #{taskId}")
    Integer undoRemove(@Param("taskId") Long taskId) throws Exception;

    @Delete("DELETE FROM task_img WHERE task_id = #{taskId}")
    Integer removeImg(@Param("taskId") Long taskId) throws Exception;

    @Update("UPDATE task_img " +
            "SET gmt_delete = #{gmtDelete} " +
            "WHERE task_id = #{taskId}")
    Integer softRemoveImg(@Param("taskId") Long taskId, @Param("gmtDelete") Date gmtDelete) throws Exception;

    @Update("UPDATE task_img " +
            "SET gmt_delete = NULL " +
            "WHERE task_id = #{taskId}")
    Integer undoRemoveImg(@Param("taskId") Long taskId) throws Exception;

    @Select({"<script>SELECT " +
            "img_url " +
            "FROM task_img " +
            "WHERE TRUE " +
            "AND task_id = #{taskId} " +
            "</script>"})
    List<String> queryImgUrlById(@Param("taskId") Long taskId) throws Exception;
}
