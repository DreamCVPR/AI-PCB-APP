package zjnu.huawei.pcb.dto.basic;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class IAMUserDTO {
    private Long ddId;
    private Long enterpriseId;
    private String userName;
    private String cardNumber;
    private String ddUserId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreate;

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

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}
