package zjnu.huawei.pcb.dto.harmony;

/**
 * @Author: dy
 * @Date: 2021/12/5
 * @Description: 钉钉用户基础信息实体类
*/

public class HarmonyDTO {
    private Long ddId;
    private Long enterpriseId;
    private String userName;
    private String cardNumber;
    private String ddUserId;
    private String token;

    public HarmonyDTO() {
    }

    public HarmonyDTO(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public HarmonyDTO(Long ddId, Long enterpriseId, String userName, String cardNumber, String ddUserId, String token) {
        this.ddId = ddId;
        this.enterpriseId = enterpriseId;
        this.userName = userName;
        this.cardNumber = cardNumber;
        this.ddUserId = ddUserId;
        this.token = token;
    }

    public Long getDdId() {
        return ddId;
    }

    public void setDdId(Long ddId) {
        this.ddId = ddId;
    }

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getDdUserId() {
        return ddUserId;
    }

    public void setDdUserId(String ddUserId) {
        this.ddUserId = ddUserId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
