package com.gxecard.weChatApiServer.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;

/**
 * 小程序配置实体类
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@Table(name = "contract_conf")
@DynamicUpdate
public class ContractConf {
    @Id
    @GeneratedValue
    private int confId;
    private String confName;
    private String confDesc;
}
