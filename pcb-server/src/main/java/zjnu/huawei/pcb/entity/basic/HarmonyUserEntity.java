package zjnu.huawei.pcb.entity.basic;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HarmonyUserEntity {
    private Long harmonyUserId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreate;
    private String openId;
    private String unionId;
    private String userImg;
    private String userName;
    private String userPhone;
    private String loginName;
    private String loginPwd;
}
