package zjnu.huawei.pcb.dto.harmony;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
    private String taskCoverImg;
    private Integer countDetectImg;
    private Integer countAllImg;
    private Integer countDefectImg;
    private JSONObject countDefect;
    private List<MultipartFile> files;
    private JSONObject base64Files;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreate;

    public void setBase64Files(JSONArray files, JSONArray fileNames) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("files", files);
        jsonObject.put("fileNames", fileNames);
        this.base64Files = jsonObject;
    }
}
