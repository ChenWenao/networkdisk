package cwa.controller;

import cwa.bean.NetFile;
import cwa.bean.NetShare;
import cwa.bean.NetUser;
import cwa.service.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RestController
public class ShareController {
    @Autowired
    private ShareService shareService;

    //增
    @GetMapping("/File/shareFile/{fileId}/{shareDay}")
    public String removeFile(HttpServletRequest request, @PathVariable(value = "fileId") int fileId, @PathVariable(value = "shareDay") int shareDay, HttpSession session) throws NoSuchAlgorithmException {
        NetShare netShare = new NetShare();
        Long cur = System.currentTimeMillis();
        netShare.setFileId(fileId);
        netShare.setUserId(((NetUser) session.getAttribute("currentUser")).getUserId());
        netShare.setShareLifeTime(shareDay);
        netShare.setShareCode(String.valueOf(cur / 1000));
        shareService.addNewShare(netShare);
        return netShare.getShareCode();
    }

    //查
    @GetMapping("/Share/shareFile/{shareCode}")
    public boolean getShareFile(@PathVariable(value = "shareCode") String shareCode) {
        if (shareService.getShareByCode(shareCode) == null)
            return false;
        else
            return true;
    }

    //查
    @GetMapping("/Share/sharePage/{shareCode}")
    public ModelAndView getSharePage(@PathVariable(value = "shareCode") String shareCode) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("sharePage");
        mav.addObject("shareFile", shareService.getShareByCode(shareCode));
        return mav;
    }
}
