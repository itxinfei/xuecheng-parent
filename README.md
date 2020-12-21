![](Doc/logo.png)
<p align="center">
  <a href="https://gitee.com/itxinfei">
    <img alt="code style" src="https://img.shields.io/badge/心飞为你飞-https%3A%2F%2Fgitee.com%2Fitxinfei-green">
  </a> 
  <a href="https://qm.qq.com/cgi-bin/qm/qr?k=9yLlyD1dRBL97xmBKw43zRt0-6xg8ohb&jump_from=webapi">
    <img alt="code style" src="https://img.shields.io/badge/QQ群-863662849-red">
  </a> 
  <a href="http://mail.qq.com/cgi-bin/qm_share?t=qm_mailme&email=f0hLSE9OTkdHTT8ODlEcEBI">
    <img alt="code style" src="https://img.shields.io/badge/mail-747011882@qq.com-red">
  </a> 

  <a href=" ">
    <img alt="code style" src="https://img.shields.io/badge/JDK-1.8%2B-brightgreen">
  </a> 
  <a href=" ">
    <img alt="maven" src="https://img.shields.io/badge/maven-3.6.3%2B-yellowgreen">
  </a>
  <a href=" ">
    <img alt="code style" src="https://img.shields.io/badge/license-Apache-green">
  </a> 
</p>

### 静态页面

http://itxinfei.gitee.io/xuecheng-parent

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

### 前端页面

![xc前端页面](https://images.gitee.com/uploads/images/2020/0707/143029_36e58658_800553.png "屏幕截图.png")
