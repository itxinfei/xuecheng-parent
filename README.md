### 项目背景

受互联网+概念的催化，当今中国在线教育市场的发展可谓是百花齐放、如火如荼。 按照市场领域细分为：学前教育、K12教育、高等教育、留学教育、职业教育、语言教育、兴趣教育以及综合平台，其中，职业教育和语言教育的市场优势突出。 根据Analysys易观发布的数据显示，预计2019年中国互联网教育市场交易规模将达到3718亿元人民币，未来三年互联网教育市场规模保持高速增长。

![输入图片说明](https://images.gitee.com/uploads/images/2020/0707/141836_8eb5bfe8_800553.png "472B78CD-699E-494f-BC84-5CFD43E7C6DB.png")

学成在线借鉴了MOOC（大型开放式网络课程，即MOOC（massive open online courses））的设计思想，是一个提供IT职业课程在线学习的平台，它为即将和已经加入IT领域的技术人才提供在线学习服务，用户通过在线学习、在线练习、在线考试等学习内容，最终掌握所学的IT技能，并能在工作中熟练应用。

### 功能模块

当前市场的在线教育模式多种多样，包括：B2C、C2C、B2B2C等业务模式，学成在线采用B2B2C业务模式，即向企业或个人提供在线教育平台提供教学服务，老师和学生通过平台完成整个教学和学习的过程，市场上类似的平台有：网易云课堂、腾讯课堂等，学成在线的特点是IT职业课程在线教学。学成在线包括门户、学习中心、教学管理中、社交系统、系统管理等功能模块。

![输入图片说明](https://images.gitee.com/uploads/images/2020/0617/161602_e1209ff3_800553.png "xcgn.png")
![输入图片说明](https://images.gitee.com/uploads/images/2020/0707/142020_40a517cb_800553.png "屏幕截图.png")

### 技术架构

学成在线采用当前流行的前后端分离架构开发，由用户层、UI层、微服务层、数据层等部分组成，为PC、App、H5等客户端用户提供服务。下图是系统的技术架构图：

![输入图片说明](https://images.gitee.com/uploads/images/2020/0617/161541_e0e61256_800553.png "xcjx.png")

#### 业务流程举例：

1. 用户可以通过pc、手机等客户端访问系统进行在线学习。
2. 系统应用CDN技术，对一些图片、CSS、视频等资源从CDN调度访问。
3. 所有的请求全部经过负载均衡器。
4. 对于PC、H5等客户端请求，首先请求UI层，渲染用户界面。
5. 客户端UI请求服务层获取进行具体的业务操作。
6. 服务层将数据持久化到数据库。

![输入图片说明](https://images.gitee.com/uploads/images/2020/0707/142642_9e56a1f0_800553.png "屏幕截图.png")

![输入图片说明](https://images.gitee.com/uploads/images/2020/0707/142716_3ad8536b_800553.png "屏幕截图.png")

![输入图片说明](https://images.gitee.com/uploads/images/2020/0707/142726_1ca66a9a_800553.png "屏幕截图.png")

![输入图片说明](https://images.gitee.com/uploads/images/2020/0707/142736_ea11423b_800553.png "屏幕截图.png")

### 技术栈
下图是项目技术架构的简图，通过简图了解项目所使用的技术栈。

![输入图片说明](https://images.gitee.com/uploads/images/2020/0707/142807_0aaf6264_800553.png "屏幕截图.png")

#### 重点了解微服务技术栈：
学成在线服务端基于Spring Boot构建，采用Spring Cloud微服务框架。

持久层：MySQL、MongoDB、Redis、ElasticSearch

数据访问层：使用Spring Data JPA 、Mybatis、Spring Data Mongodb等

业务层：Spring IOC、Aop事务控制、Spring Task任务调度、Feign、Ribbon、Spring AMQP、Spring Data Redis
等。

控制层：Spring MVC、FastJSON、RestTemplate、Spring Security Oauth2+JWT等

微服务治理：Eureka、Zuul、Hystrix、Spring Cloud Config等

### 开发步骤

项目是基于前后端分离的架构进行开发，前后端分离架构总体上包括前端和服务端，通常是多人协作并行开发，开发步骤如下：
1、需求分析
梳理用户的需求，分析业务流程

2、接口定义
根据需求分析定义接口

3、服务端和前端并行开发
依据接口进行服务端接口开发。
前端开发用户操作界面，并请求服务端接口完成业务处理。

4、前后端集成测试
最终前端调用服务端接口完成业务。

### 前端页面

![xc前端页面](https://images.gitee.com/uploads/images/2020/0707/143029_36e58658_800553.png "屏幕截图.png")


### 工程结构

CMS及其它服务端工程基于maven进行构建，首先需要创建如下基础工程：

parent工程：父工程，提供依赖管理。

common工程：通用工程，提供各层封装

model工程：模型工程，提供统一的模型类管理

utils工程：工具类工程，提供本项目所使用的工具类

Api工程：接口工程，统一管理本项目的服务接口。

![工程结构](https://images.gitee.com/uploads/images/2020/0707/143310_080aea5d_800553.png "屏幕截图.png")






## end