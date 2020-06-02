package com.xuecheng.ucenter.controller;

import com.xuecheng.api.ucenter.UcenterControllerApi;
import com.xuecheng.framework.domain.ucenter.ext.XcUserExt;
import com.xuecheng.ucenter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Administrator
 * @version 1.0
 **/
@RestController
@RequestMapping("/ucenter")
public class UcenterController implements UcenterControllerApi {
    
    @Resource
    UserService userService;

    /**
     * 查询用户
     *
     * @param username
     * @return
     */
    @Override
    @GetMapping("/getuserext")
    public XcUserExt getUserext(@RequestParam("username") String username) {
        return userService.getUserExt(username);
    }
}
