package zjnu.huawei.pcb.entity.harmony;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor; 
import lombok.AllArgsConstructor;

import java.util.Date;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 
public class TaskEntity { 
    private Long taskId; 
    private String taskName; 
    private String taskDesc; 
    private Long harmonyUserId;
    private Integer taskState;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtDelete;

    public TaskEntity(Long taskId, Integer taskState) {
        this.taskId = taskId;
        this.taskState = taskState;
    }
}
