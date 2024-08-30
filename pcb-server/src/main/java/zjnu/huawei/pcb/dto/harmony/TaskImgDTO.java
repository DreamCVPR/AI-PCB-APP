package zjnu.huawei.pcb.dto.harmony;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 
public class TaskImgDTO {
    private Long imgId; 
    private String imgName; 
    private String imgUrl; 
    private Integer isDetect;
    private String detectionClasses; 
    private String detectionBoxes; 
    private String detectionScores; 
    private Long taskId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8") 
    private Date gmtCreate;

    public TaskImgDTO(String imgName, String imgUrl, Integer isDetect, Long taskId, Date gmtCreate) {
        this.imgName = imgName;
        this.imgUrl = imgUrl;
        this.isDetect = isDetect;
        this.taskId = taskId;
        this.gmtCreate = gmtCreate;
    }
}
