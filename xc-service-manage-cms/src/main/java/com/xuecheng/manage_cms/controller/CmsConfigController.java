package com.xuecheng.manage_cms.controller;

import com.xuecheng.api.cms.CmsConfigControllerApi;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.manage_cms.service.PageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 配置管理
 */
@RestController
@RequestMapping("/cms/config")
public class CmsConfigController implements CmsConfigControllerApi {

    @Resource
    private PageService pageService;

    /**
     * 轮播图DataUrl接口
     *
     * @param id
     * @return
     */
    @Override
    @GetMapping("/getmodel/{id}")
    public CmsPage getmodel(@PathVariable("id") String id) {
        CmsPage byId = pageService.getById(id);
        System.out.println("byId:" + byId);
        return pageService.getById(id);
    }
}
