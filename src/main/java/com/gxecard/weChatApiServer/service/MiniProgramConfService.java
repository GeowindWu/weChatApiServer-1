package com.gxecard.weChatApiServer.service;

import com.gxecard.weChatApiServer.dao.ContractConfDao;
import com.gxecard.weChatApiServer.entity.ContractConf;
import com.gxecard.weChatApiServer.vo.MiniProgramConfVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MiniProgramConfService {
    @Autowired
    private ContractConfDao contractConfDao;

    public MiniProgramConfVo getConfs(){
        List<ContractConf> allConfs = contractConfDao.findAll();
        MiniProgramConfVo miniProgramConfVo =new MiniProgramConfVo();
        for (ContractConf conf:allConfs){
            determinConfBelongsTo(conf, miniProgramConfVo);
        }
        return miniProgramConfVo;
    }

    private void determinConfBelongsTo(ContractConf conf, MiniProgramConfVo miniProgramConfVo) {
        switch (conf.getConfName()){
            case "appId":
                miniProgramConfVo.setMainAppId(conf.getConfDesc());
                break;
            case "mch_id":
                miniProgramConfVo.setMchId(conf.getConfDesc());
                break;
            case "sub_mch_id":
                miniProgramConfVo.setSubMchId(conf.getConfDesc());
                break;
            case "appid":
                miniProgramConfVo.setAppId(conf.getConfDesc());
                break;
            case "sub_appid":
                miniProgramConfVo.setSubAppId(conf.getConfDesc());
                break;
            case "plan_id":
                miniProgramConfVo.setPlanId(conf.getConfDesc());
                break;
                default:
                    break;
        }
    }
}
