## CMS页面管理系统-31001

# 说明
CmsSite：站点模型
CmsTemplate：页面模板
CmsPage：页面信息

# 包结构
com.xuecheng.manage_cms.config：配置类目录，数据库配置、MQ配置等
com.xuecheng.manage_cms.dao：dao接口目录
com.xuecheng.manage_cms.service：service类目录
com.xuecheng.manage_cms.web.controller：controller类目录

Optional是jdk1.8引入的类型，Optional是一个容器对象，它包括了我们需要的对象，使用isPresent方法判断所包
含对象是否为空，isPresent方法返回false则表示Optional包含对象为空，否则可以使用get()取出对象进行操作。
Optional的优点是：
1、提醒你非空判断。
2、将对象非空检测标准化。

## Swagger常用注解
@Api：修饰整个类，描述Controller的作用 @ApiOperation：描述一个类的一个方法，或者说一个接口
@ApiParam：单个参数描述 @ApiModel：用对象来接收参数 @ApiModelProperty：用对象接收参数时，描述对
象的一个字段 @ApiResponse：HTTP响应其中1个描述 @ApiResponses：HTTP响应整体描述 @ApiIgnore：使用
该注解忽略这个API @ApiError ：发生错误返回的信息 @ApiImplicitParam：一个请求参数
@ApiImplicitParams：多个请求参数