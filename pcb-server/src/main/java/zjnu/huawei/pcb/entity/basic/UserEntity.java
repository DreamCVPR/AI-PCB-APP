package zjnu.huawei.pcb.entity.basic;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class UserEntity {
    private Long ddId;
    private Long enterpriseId;
    private String userName;
    private String cardNumber;
    private String ddUserId;
    private String unionId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreate;

    public UserEntity() {
    }

    public UserEntity(Long ddId, Long enterpriseId, String userName, String cardNumber, String ddUserId, String unionId, Date gmtCreate) {
        this.ddId = ddId;
        this.enterpriseId = enterpriseId;
        this.userName = userName;
        this.cardNumber = cardNumber;
        this.ddUserId = ddUserId;
        this.unionId = unionId;
        this.gmtCreate = gmtCreate;
    }

    public Long getddId() {
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

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}
