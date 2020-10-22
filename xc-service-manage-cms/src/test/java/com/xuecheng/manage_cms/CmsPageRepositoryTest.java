package com.xuecheng.manage_cms;

import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.manage_cms.service.PageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * CMS测试
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsPageRepositoryTest {

    @Resource
    private PageService pageService;

    /**
     *
     */
    @Test
    public void testCms() {
        QueryPageRequest queryPageRequest = new QueryPageRequest();
        queryPageRequest.setPageId("5a754adf6abb500ad05688d9");
        QueryResponseResult list = pageService.findList(1, 2, queryPageRequest);

        System.out.println(list.toString());
    }

}
