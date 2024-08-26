package zjnu.huawei.pcb.dto.harmony;


public class HarmonyInfoDTO {

    private String encryptedData;//加密数据
    private String errMsg;
    private String iv;//加密算法初始向量
    private Object rawData;
    private String code;
    private String sessionKey;

    public HarmonyInfoDTO(String encryptedData, String errMsg, String iv, Object rawData, String code, String sessionKey) {
        this.encryptedData = encryptedData;
        this.errMsg = errMsg;
        this.iv = iv;
        this.rawData = rawData;
        this.code = code;
        this.sessionKey = sessionKey;
    }

    public HarmonyInfoDTO() {
    }

    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public Object getRawData() {
        return rawData;
    }

    public void setRawData(Object rawData) {
        this.rawData = rawData;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }
}
