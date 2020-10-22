package com.xuecheng.manage_cms.controller;

import com.xuecheng.api.cms.CmsPageControllerApi;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.cms.response.CmsPostPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.service.PageService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * CMS页面服务
 */
@RestController
@RequestMapping("/cms/page")
public class CmsPageController implements CmsPageControllerApi {

    @Resource
    private PageService pageService;

    /**
     * 页面列表分页查询
     *
     * @param page
     * @param size
     * @param queryPageRequest
     * @return
     */
    @Override
    @RequestMapping("/list/{page}/{size}")
    public QueryResponseResult findList(@PathVariable("page") int page, @PathVariable("size") int size, QueryPageRequest queryPageRequest) {
        String pageId = queryPageRequest.getPageId();
        QueryResponseResult list = pageService.findList(page, size, queryPageRequest);
        //打印查询到的信息
        System.out.println(list.toString());
        return list;
    }

    /**
     * 新增页面
     *
     * @param cmsPage
     * @return
     */
    @Override
    @PostMapping("/add")
    public CmsPageResult add(@RequestBody CmsPage cmsPage) {
        return pageService.add(cmsPage);
    }

    /**
     * 根据id查询页面
     *
     * @param id
     * @return
     */
    @Override
    @GetMapping("/get/{id}")
    public CmsPage findById(@PathVariable("id") String id) {
        return pageService.getById(id);
    }

    /**
     * 更新页面
     *
     * @param id
     * @param cmsPage
     * @return
     */
    @Override
    @PutMapping("/edit/{id}")//这里使用put方法，http 方法中put表示更新
    public CmsPageResult edit(@PathVariable("id") String id, @RequestBody CmsPage cmsPage) {
        return pageService.update(id, cmsPage);
    }

    /**
     * 根据ID删除
     *
     * @param id
     * @return
     */
    @Override
    @DeleteMapping("/del/{id}")
    public ResponseResult delete(@PathVariable("id") String id) {
        return pageService.delete(id);
    }


    /**
     * 接收页面请求，调用service执行页面发布
     *
     * @param pageId
     * @return
     */
    @Override
    @PostMapping("/postPage/{pageId}")
    public ResponseResult post(@PathVariable("pageId") String pageId) {
        return pageService.postPage(pageId);
    }


    /**
     * 保存
     *
     * @param cmsPage
     * @return
     */
    @Override
    @PostMapping("/save")
    public CmsPageResult save(@RequestBody CmsPage cmsPage) {
        return pageService.save(cmsPage);
    }

    /**
     * 一键发布页面
     *
     * @param cmsPage
     * @return
     */
    @Override
    @PostMapping("/postPageQuick")
    public CmsPostPageResult postPageQuick(@RequestBody CmsPage cmsPage) {
        System.out.println("一键发布页面；" + cmsPage);
        return pageService.postPageQuick(cmsPage);
    }
}
