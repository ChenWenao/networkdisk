package cwa.dao;

import cwa.bean.NetShare;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ShareRepository {
    @Autowired
    private JdbcTemplate shareTemplate;
    private ShareRowMapper shareRowMapper = new ShareRowMapper();

    //增
    public boolean insertNewShare(NetShare netShare) {
        try {
            shareTemplate.update("insert into networkdisk.share(share_fileId,share_userId,shareCode,shareLifeTime) values (?,?,?,?)",
                    netShare.getFileId(),
                    netShare.getUserId(),
                    netShare.getShareCode(),
                    netShare.getShareLifeTime());
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }


    //根据shareCode查文件分享
    public NetShare selectShareFileByCode(String shareCode) {
        try {
            List<NetShare> netShares = shareTemplate.query("select * from file,share,user where share_fileId=fileId and share_userId=userId and shareCode=?", shareRowMapper, shareCode);
            return netShares.get(0);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
