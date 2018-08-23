package com.gxecard.weChatApiServer.dao;

import com.gxecard.weChatApiServer.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractDao extends JpaRepository<Contract,Integer> {
    public Contract findByPhoneEquals(String phone);
}
