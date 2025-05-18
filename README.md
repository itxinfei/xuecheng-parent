
<div align="center">

![Logo](docs/logo.png)


<h1>🎯 学成在线（Online Education Platform）</h1>


 一个基于互联网+的 IT 职业教育平台，提供课程学习、练习、考试等完整教学服务。

  <a href="https://gitee.com/itxinfei" target="_blank">
    <img alt="Author" src="https://img.shields.io/badge/心飞为你飞-https%3A%2F%2Fgitee.com%2Fitxinfei-green">
  </a>
  <a href="https://qm.qq.com/cgi-bin/qm/qr?k=9yLlyD1dRBL97xmBKw43zRt0-6xg8ohb&jump_from=webapi" target="_blank">
    <img alt="QQ群" src="https://img.shields.io/badge/QQ群-863662849-red">
  </a>
  <a href="mailto:747011882@qq.com">
    <img alt="Email" src="https://img.shields.io/badge/mail-747011882@qq.com-red">
  </a>
  <a href="https://xczx2-admin.itheima.net/#/login" target="_blank">
    <img alt="演示网站" src="https://img.shields.io/badge/演示网站-online-brightgreen">
  </a>

  <a href="#">
    <img alt="JDK" src="https://img.shields.io/badge/JDK-1.8%2B-brightgreen">
  </a>
  <a href="#">
    <img alt="Maven" src="https://img.shields.io/badge/Maven-3.6.3%2B-yellowgreen">
  </a>
  <a href="#">
    <img alt="License" src="https://img.shields.io/badge/license-Apache-green">
  </a>

</div>


### 一、项目背景

受互联网+概念的催化，当今中国在线教育市场的发展可谓是百花齐放、如火如荼。按照市场领域细分为：学前教育、K12教育、高等教育、留学教育、职业教育、语言教育、兴趣教育以及综合平台，其中，职业教育和语言教育的市场优势突出。

根据Analysys易观发布的数据显示，预计2019年中国互联网教育市场交易规模将达到3718亿元人民币，未来三年互联网教育市场规模保持高速增长。

学成在线借鉴了 MOOC（大型开放式网络课程）的设计思想，是一个提供 IT 职业课程在线学习的平台，为即将和已经加入 IT 领域的技术人才提供在线学习服务，用户通过在线学习、在线练习、在线考试等完成知识掌握，并能在工作中熟练应用。

![项目架构图](https://broadscope-dialogue-new.oss-cn-beijing.aliyuncs.com/output/20250518/14751ad9302816e9bcab3f869d019672.png?Expires=1779079743&OSSAccessKeyId=LTAI5tL97mBYzVcjkG1cUyin&Signature=ZDMfGTvBOP9jsumoQ1KBDhyiD0Q%3D)

---

### 二、功能模块

当前市场的在线教育模式多种多样，包括：B2C、C2C、B2B2C 等业务模式。学成在线采用 **B2B2C 模式**，即向企业或个人提供平台进行教学服务，老师和学生通过平台完成整个教学过程。

类似平台有网易云课堂、腾讯课堂等，而学成在线专注于 **IT 职业课程在线教学**。

主要功能模块如下：

- 门户系统
- 学习中心
- 教学管理系统
- 社交系统
- 系统管理后台

![功能模块图1](https://broadscope-dialogue-new.oss-cn-beijing.aliyuncs.com/output/20250518/3a0572520b7f92d1b667d6a8c1342171.png?Expires=1779079743&OSSAccessKeyId=LTAI5tL97mBYzVcjkG1cUyin&Signature=biws2wsQiYjPkWffsrbjkKbT3Nk%3D)  
![功能模块图2](https://broadscope-dialogue-new.oss-cn-beijing.aliyuncs.com/output/20250518/03c35096b788858e420acd1329c1966e.png?Expires=1779079743&OSSAccessKeyId=LTAI5tL97mBYzVcjkG1cUyin&Signature=iVvNgD50bP588IUzDHCsxyMuSKg%3D)

---

### 三、技术架构

学成在线采用当前流行的 **前后端分离架构** 开发，由用户层、UI 层、微服务层、数据层等组成，支持 PC、App、H5 等多终端访问。

![技术架构图](https://broadscope-dialogue-new.oss-cn-beijing.aliyuncs.com/output/20250518/d4a533458276d4beb9d03a4d1af83be3.png?Expires=1779079743&OSSAccessKeyId=LTAI5tL97mBYzVcjkG1cUyin&Signature=VvqEO%2FT%2BqpEt4R9fFMbW9q7suqQ%3D)

---

### 四、业务流程示例

1. 用户可通过 PC、手机等客户端访问系统进行在线学习。
2. 系统使用 CDN 技术加速静态资源（图片、CSS、视频等）加载。
3. 所有请求经过负载均衡器处理。
4. PC/H5 客户端请求 UI 层渲染界面。
5. UI 层调用服务层获取数据并执行具体业务逻辑。
6. 服务层将数据持久化至数据库。

---

### 五、技术栈概览

![技术栈简图](https://broadscope-dialogue-new.oss-cn-beijing.aliyuncs.com/output/20250518/a9c641c7bad475195c901262bdce0672.png?Expires=1779079744&OSSAccessKeyId=LTAI5tL97mBYzVcjkG1cUyin&Signature=krvL3T41EzfysLOMmpfzmABluPw%3D)

---

### 六、核心微服务技术栈

学成在线后端基于 **Spring Boot + Spring Cloud** 构建，采用微服务架构设计，具备良好的扩展性与维护性。

#### ✅ 持久层
- MySQL
- MongoDB
- Redis
- ElasticSearch

#### ✅ 数据访问层
- Spring Data JPA
- MyBatis
- Spring Data MongoDB

#### ✅ 业务层
- Spring IOC / AOP
- Spring Task 任务调度
- Feign / Ribbon 远程调用
- Spring AMQP 消息队列
- Spring Data Redis 缓存操作

#### ✅ 控制层
- Spring MVC
- FastJSON
- RestTemplate
- Spring Security OAuth2 + JWT 权限控制

#### ✅ 微服务治理
- Eureka 注册中心
- Zuul 网关路由
- Hystrix 熔断降级
- Spring Cloud Config 分布式配置

---

### 七、前端页面展示

![前端页面截图](https://broadscope-dialogue-new.oss-cn-beijing.aliyuncs.com/output/20250518/c0652550f4f205647c04c2f750cd72ab.png?Expires=1779079744&OSSAccessKeyId=LTAI5tL97mBYzVcjkG1cUyin&Signature=FI96a3fAlTxmybNqLOpdmkFjmtA%3D)

---
### 🔗 项目链接 & 社区支持  
![微信公众号二维码](docs/心飞为你飞.jpg)  
🚀 项目地址：[https://gitee.com/itxinfei/xuecheng-parent](https://gitee.com/itxinfei/xuecheng-parent)  
👥 QQ交流群：[661543188](https://qm.qq.com/cgi-bin/qm/qr?k=gNgch-wCkfUu-QbI7DZSudrax2BN7vY0&jump_from=webapi&authKey=QHSRnxQvu+h5S3AXGn/DSHrVPiFQAYEk6bSlCE1lS276SFjQAUagV4FG7bHf0OSM)  
📧 邮箱支持：[747011882@qq.com](http://mail.qq.com/cgi-bin/qm_share?t=qm_mailme&email=f0hLSE9OTkdHTT8ODlEcEBI)  

