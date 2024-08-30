package zjnu.huawei.pcb.dto.harmony;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 
public class TaskDTO {
    private Long taskId; 
    private String taskName; 
    private String taskDesc; 
    private Long harmonyUserId;
    private Integer taskState;
    private List<MultipartFile> files;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreate;
}
