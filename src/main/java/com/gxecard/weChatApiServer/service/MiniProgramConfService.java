package com.gxecard.weChatApiServer.service;

import com.gxecard.weChatApiServer.dao.ContractConfDao;
import com.gxecard.weChatApiServer.entity.ContractConf;
import com.gxecard.weChatApiServer.vo.MiniProgramConf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MiniProgramConfService {
    @Autowired
    private ContractConfDao contractConfDao;

    public MiniProgramConf getConfs(){
        List<ContractConf> allConfs = contractConfDao.findAll();
        MiniProgramConf miniProgramConf=new MiniProgramConf();
        for (ContractConf conf:allConfs){
            determinConfBelongsTo(conf,miniProgramConf);
        }
        return miniProgramConf;
    }

    private void determinConfBelongsTo(ContractConf conf, MiniProgramConf miniProgramConf) {
        switch (conf.getConfName()){
            case "appId":
                miniProgramConf.setMainAppId(conf.getConfDesc());
                break;
            case "mch_id":
                miniProgramConf.setMchId(conf.getConfDesc());
                break;
            case "sub_mch_id":
                miniProgramConf.setSubMchId(conf.getConfDesc());
                break;
            case "appid":
                miniProgramConf.setAppId(conf.getConfDesc());
                break;
            case "sub_appid":
                miniProgramConf.setSubAppId(conf.getConfDesc());
                break;
            case "plan_id":
                miniProgramConf.setPlanId(conf.getConfDesc());
                break;
                default:
                    break;
        }
    }
}
