package zjnu.huawei.pcb.dto.system;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HarmonyTokenDTO {

    private Long harmonyUserId;
    private String userName;
    private String userPhone;

    private String iss;
    private String sub;
    private String aud;
    private Long exp;
    private Long iat;
    private String given_name;

    public HarmonyTokenDTO(Long harmonyUserId, String userName, String userPhone) {
        this.harmonyUserId = harmonyUserId;
        this.userName = userName;
        this.userPhone = userPhone;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
