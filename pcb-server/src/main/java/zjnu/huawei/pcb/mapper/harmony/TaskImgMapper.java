package zjnu.huawei.pcb.mapper.harmony;

import org.apache.ibatis.annotations.*;
import zjnu.huawei.pcb.entity.harmony.TaskEntity;
import zjnu.huawei.pcb.entity.harmony.TaskImgEntity;

import java.util.List;


public interface TaskImgMapper {
    @Select({"<script>SELECT " +
            "img_id, img_name, img_url, is_detect, detection_classes, detection_boxes, detection_scores, task_id, gmt_create " +
            "FROM task_img " +
            "WHERE TRUE " +
            "AND task_id = #{task_id} " +
            "ORDER BY " +
            "gmt_create DESC " +
            "</script>"})
    List<TaskImgEntity> queryById(@Param("task_id") Long task_id) throws Exception;

    @Select({"<script>SELECT " +
            "img_id, img_name, img_url, is_detect, detection_classes, detection_boxes, detection_scores, task_id, gmt_create " +
            "FROM task_img " +
            "WHERE TRUE " +
            "AND task_id = #{task_id} " +
            "AND is_detect = 0 " +
            "ORDER BY " +
            "gmt_create DESC " +
            "</script>"})
    List<TaskImgEntity> queryNotDetectById(@Param("task_id") Long task_id) throws Exception;

    @Insert({"<script>" +
            "INSERT INTO task_img (" +
            "img_name, img_url, is_detect, detection_classes, detection_boxes, detection_scores, task_id, gmt_create) " +
            "VALUES " +
            "<foreach collection=\"entityList\" index=\"index\" item=\"entity\" separator=\",\">" +
            "(#{entity.imgName}, #{entity.imgUrl}, #{entity.isDetect}, #{entity.detectionClasses}, #{entity.detectionBoxes}, #{entity.detectionScores}, #{entity.taskId}, #{entity.gmtCreate}) " +
            "</foreach>" +
            "</script>"})
    @Options(useGeneratedKeys = true, keyProperty = "imgId", keyColumn = "img_id")
    Integer addList(@Param("entityList") List<TaskImgEntity> entityList) throws Exception;

    @Insert({"<script>" +
            "INSERT INTO task_img (" +
            "img_name, img_url, is_detect, detection_classes, detection_boxes, detection_scores, task_id, gmt_create) " +
            "VALUES " +
            "(#{entity.imgName}, #{entity.imgUrl}, #{entity.isDetect}, #{entity.detectionClasses}, #{entity.detectionBoxes}, #{entity.detectionScores}, #{entity.taskId}, #{entity.gmtCreate}) " +
            "</script>"})
    @Options(useGeneratedKeys = true, keyProperty = "imgId", keyColumn = "img_id")
    Integer add(@Param("entity") TaskImgEntity entity) throws Exception;

    @Update("UPDATE task_img " +
            "SET img_name = #{entity.imgName}, img_url = #{entity.imgUrl}, is_detect = #{entity.isDetect}, detection_classes = #{entity.detectionClasses}, detection_boxes = #{entity.detectionBoxes}, detection_scores = #{entity.detectionScores}, task_id = #{entity.taskId} " +
            "WHERE img_id = #{entity.imgId}")
    Integer update(@Param("entity") TaskImgEntity entity) throws Exception;

    @Delete("DELETE FROM task_img WHERE img_id = #{imgId}")
    Integer remove(@Param("imgId") Long imgId) throws Exception;
}
