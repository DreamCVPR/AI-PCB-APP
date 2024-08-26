package zjnu.huawei.pcb.dto.harmony;

public class HuaweiUserInfoDTO {
    private String idToken;

    private String iss;
    private String sub;
    private String aud;
    private Long exp;
    private Long iat;



    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

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
}
