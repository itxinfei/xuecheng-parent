package com.xuecheng.manage_cms.dao;


import com.xuecheng.framework.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * 继承MongoRepository，并指定实体类型和主键类型
 */
public interface CmsPageRepository extends MongoRepository<CmsPage, String> {

    //根据页面名称查询
    CmsPage findByPageName(String pageName);

    //根据页面名称、站点Id、页面webpath查询
    CmsPage findByPageNameAndSiteIdAndPageWebPath(String pageName, String siteId, String pageWebPath);


}
