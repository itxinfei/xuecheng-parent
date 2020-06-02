package com.xuecheng.framework.domain.cms.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Administrator
 * @version 1.0
 * @create 2018-09-12 14:59
 * 定义请求模型QueryPageRequest，此模型作为查询条件类型
 * 为后期扩展需求，请求类型统一继承RequestData类型。
 **/

//接收页面查询的查询条件
@Data
public class QueryPageRequest {

    //站点id
    @ApiModelProperty("站点id")
    private String siteId;

    //页面ID
    private String pageId;

    //页面名称
    private String pageName;

    //页面类型
    private String pageType;

    //别名
    private String pageAliase;

    //模版id
    private String templateId;
}
