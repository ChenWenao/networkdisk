package cwa.service;

import cwa.bean.NetShare;
import cwa.dao.ShareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShareService {
    @Autowired
    private ShareRepository shareRepository;

    public boolean addNewShare(NetShare netShare){
        return shareRepository.insertNewShare(netShare);
    }

    public NetShare getShareByCode(String shareCode){
        return shareRepository.selectShareFileByCode(shareCode);
    }
}
