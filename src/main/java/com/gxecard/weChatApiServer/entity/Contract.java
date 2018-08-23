package com.gxecard.weChatApiServer.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户签约信息实体类
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@Table(name = "contract")
@DynamicUpdate
public class Contract {
    @Id
    @GeneratedValue
    private int id;
    private String contractId;
    private String requestSerial;
    private String contractCode;
    private String contractDisplayAccount;
    private Integer contractState;
    private String contractSignedTime;
    private String contractExpiredTime;
    private String contractTerminatedTime;
    private Integer contractTerminationMode;
    private String contractTerminationRemark;
    private String openid;
    private String subOpenid;
    private String userNo;
    private String phone;
    private String field1;
    private String field2;
    private String field3;
    private String field4;
    private String field5;
}
