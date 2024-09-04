package zjnu.huawei.pcb.dto.basic;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HarmonyUserDTO {
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
    private String token;

    public HarmonyUserDTO(String userName, String userPhone, String token) {
        this.userName = userName;
        this.userPhone = userPhone;
        this.token = token;
    }
}
