package zjnu.huawei.pcb.dto.harmony;

import com.alibaba.fastjson.JSONObject;

public class HarmonyTokenDTO {

    private String iss;
    private String sub;
    private String aud;
    private Long exp;
    private Long iat;
    private String given_name;

    public String getIss() {
        return iss;
    }

    public void setIss(String iss) {
        this.iss = iss;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getAud() {
        return aud;
    }

    public void setAud(String aud) {
        this.aud = aud;
    }

    public Long getExp() {
        return exp;
    }

    public void setExp(Long exp) {
        this.exp = exp;
    }

    public Long getIat() {
        return iat;
    }

    public void setIat(Long iat) {
        this.iat = iat;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
