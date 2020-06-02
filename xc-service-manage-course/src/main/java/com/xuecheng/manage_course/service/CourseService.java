package com.xuecheng.manage_course.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.cms.response.CmsPostPageResult;
import com.xuecheng.framework.domain.course.*;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.ext.CourseView;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.domain.course.response.AddCourseResult;
import com.xuecheng.framework.domain.course.response.CourseCode;
import com.xuecheng.framework.domain.course.response.CoursePublishResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.client.CmsPageClient;
import com.xuecheng.manage_course.dao.*;
import io.swagger.annotations.Example;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@SuppressWarnings("ALL")
@Service
public class CourseService {

    @Autowired
    TeachplanMapper teachplanMapper;

    @Autowired
    CourseBaseRepository courseBaseRepository;

    @Autowired
    TeachplanRepository teachplanRepository;

    @Autowired
    CourseMarketRepository courseMarketRepository;

    @Autowired
    CoursePicRepository coursePicRepository;

    @Autowired
    CoursePubRepository coursePubRepository;

    @Autowired
    TeachplanMediaRepository teachplanMediaRepository;

    @Autowired
    TeachplanMediaPubRepository teachplanMediaPubRepository;

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    CmsPageClient cmsPageClient;

    @Value("${course‐publish.dataUrlPre}")
    private String publish_dataUrlPre;
    @Value("${course‐publish.pagePhysicalPath}")
    private String publish_page_physicalpath;
    @Value("${course‐publish.pageWebPath}")
    private String publish_page_webpath;
    @Value("${course‐publish.siteId}")
    private String publish_siteId;
    @Value("${course‐publish.templateId}")
    private String publish_templateId;
    @Value("${course‐publish.previewUrl}")
    private String previewUrl;

    public TeachplanNode findTeachplanList(String courseId){
        TeachplanNode teachplanNode = teachplanMapper.selectList(courseId);
        return teachplanNode;
    }

    //添加课程计划
    @Transactional
    public ResponseResult addTeachplan(Teachplan teachplan){
        //校验课程id和课程计划名称
        if (teachplan == null || StringUtils.isEmpty(teachplan.getCourseid())
                || StringUtils.isEmpty(teachplan.getPname())
                ){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        //取出课程id
        String courseid = teachplan.getCourseid();
        //取出父结点id
        String parentid = teachplan.getParentid();
        if (StringUtils.isEmpty(parentid)){
            //如果父结点为空则获取根结点
            parentid = this.getTeachplanRoot(courseid);
        }
        //查询根结点信息
        Optional<Teachplan> optional = teachplanRepository.findById(parentid);
        Teachplan teachplan1 = optional.get();
        //父结点的级别
        String parent_grade = teachplan1.getGrade();
        //创建一个新结点准备添加
        Teachplan teachplanNew = new Teachplan();
        //将teachplan的属性拷贝到teachplanNew中
        BeanUtils.copyProperties(teachplan,teachplanNew);
        //要设置必要的属性
        teachplanNew.setParentid(parentid);
        if (parent_grade.equals("1")){
            teachplanNew.setGrade("2");
        }else {
            teachplanNew.setGrade("3");
        }
        teachplanNew.setStatus("0");//未发布
        teachplanRepository.save(teachplanNew);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    //获取课程根结点，如果没有则添加根结点
    public  String getTeachplanRoot(String courseId){

        Optional<CourseBase> optional = courseBaseRepository.findById(courseId);
        if(!optional.isPresent()){
            return null;
        }
        CourseBase courseBase = optional.get();
        //取出课程计划根结点
        List<Teachplan> teachplanList = teachplanRepository.findByCourseidAndParentid(courseId,"0");
        if (teachplanList == null || teachplanList.size() <= 0){
            //新增一个根结点
            Teachplan teachplanRoot = new Teachplan();
            teachplanRoot.setCourseid(courseId);
            teachplanRoot.setPname(courseBase.getName());
            teachplanRoot.setParentid("0");
            teachplanRoot.setGrade("1");
            teachplanRoot.setStatus("0");
            teachplanRepository.save(teachplanRoot);
            return teachplanRoot.getId();
        }
        //返回根结点的id
        //System.out.println(teachplanList.get(0).getId());
        return teachplanList.get(0).getId();
    }


    /**
     * 分页查询我的课程
     * @param page
     * @param size
     * @param courseListRequest
     * @return
     */
    public QueryResponseResult findCourseList(int page, int size, CourseListRequest courseListRequest){

        if (courseListRequest == null){
            courseListRequest = new CourseListRequest();

        }

        if (page <=0){
            page=0;
        }
        if (size<=0){
            size = 20;
        }
        //设置分页参数
        PageHelper.startPage(page,size);
        //分页查询
        Page<CourseInfo> courseListPage = courseMapper.findCourseListPage(courseListRequest);
        //查询列表
        List<CourseInfo> list = courseListPage.getResult();
        //总记录数
        long total = courseListPage.getTotal();
        //查询结果集
        QueryResult<CourseInfo> courseInfoQueryResult = new QueryResult<CourseInfo>();
        courseInfoQueryResult.setList(list);
        courseInfoQueryResult.setTotal(total);
        return new QueryResponseResult(CommonCode.SUCCESS,courseInfoQueryResult);
    }

    /**
     * 添加课程
     * @param courseBase
     * @return
     */
    @Transactional
    public AddCourseResult addCourseBase(CourseBase courseBase) {
//课程状态默认为未发布
        courseBase.setStatus("202001");
        courseBaseRepository.save(courseBase);
        return new AddCourseResult(CommonCode.SUCCESS,courseBase.getId());
    }


    /**
     * 查询课程
     * @param courseid
     * @return
     */
    public CourseBase getCoursebaseById(String courseid){

        Optional<CourseBase> optional = courseBaseRepository.findById(courseid);
        if (optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    /**
     * 更新课程
     * @return
     */
    public ResponseResult updateCoursebase(String id,CourseBase courseBase){
        CourseBase one = this.getCoursebaseById(id);
        if (one == null){

        }
        //修改课程信息
        one.setName(courseBase.getName());
        one.setMt(courseBase.getMt());
        one.setSt(courseBase.getSt());
        one.setGrade(courseBase.getGrade());
        one.setStudymodel(courseBase.getStudymodel());
        one.setUsers(courseBase.getUsers());
        one.setDescription(courseBase.getDescription());
        CourseBase save = courseBaseRepository.save(one);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 获取课程的营销信息
     * @param courseid
     * @return
     */
    public CourseMarket getCourseMarketById(String courseid){
        Optional<CourseMarket> optional = courseMarketRepository.findById(courseid);
        if (optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    @Transactional
    public CourseMarket updateCourseMarket(String id, CourseMarket courseMarket) {
        CourseMarket one = this.getCourseMarketById(id);
        if(one!=null){
            one.setCharge(courseMarket.getCharge());
            one.setStartTime(courseMarket.getStartTime());//课程有效期，开始时间
            one.setEndTime(courseMarket.getEndTime());//课程有效期，结束时间
            one.setPrice(courseMarket.getPrice());
            one.setQq(courseMarket.getQq());
            one.setValid(courseMarket.getValid());
            courseMarketRepository.save(one);
        }else{
            //添加课程营销信息
            one = new CourseMarket();
            BeanUtils.copyProperties(courseMarket, one);
            //设置课程id
            one.setId(id);
            courseMarketRepository.save(one);
        }
        return one;
    }

    @Transactional
    public ResponseResult saveCoursePic(String courseId,String pic){
        //查询课程图片
         Optional<CoursePic> picOptional = coursePicRepository.findById(courseId);
         CoursePic coursePic = null;
         if (picOptional.isPresent()){
             coursePic= picOptional.get();
         }
         if (coursePic == null){
             coursePic = new CoursePic();
         }
         coursePic.setCourseid(courseId);
         coursePic.setPic(pic);
         coursePicRepository.save(coursePic);
         return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 查询课程图片
     * @param courseId
     * @return
     */
    public CoursePic findCoursepic(String courseId) {
        Optional<CoursePic> picOptional = coursePicRepository.findById(courseId);
        if (picOptional.isPresent()){
            return picOptional.get();
        }
        return null;
    }
    //删除课程图片
     @Transactional
     public ResponseResult deleteCoursePic(String courseId){
         //执行删除，返回1表示删除成功，返回0表示删除失败
          long result = coursePicRepository.deleteByCourseid(courseId);
          if(result>0){
              return new ResponseResult(CommonCode.SUCCESS);
          }
          return new ResponseResult(CommonCode.FAIL);
     }

     //课程数据
    public CourseView getCoruseView(String id){
         CourseView courseView = new CourseView();
         //查询课程基本信息
        Optional<CourseBase> optional = courseBaseRepository.findById(id);
        if (optional.isPresent()){
            courseView.setCourseBase(optional.get());
        }

        //查询课程营销信息
        Optional<CourseMarket> courseMarketOptional = courseMarketRepository.findById(id);
        if (courseMarketOptional.isPresent()){
            courseView.setCourseMarket(courseMarketOptional.get());
        }
         //查询课程图片
        Optional<CoursePic> picOptional = coursePicRepository.findById(id);
        if (picOptional.isPresent()){
              courseView.setCoursePic(picOptional.get());
        }

        //查询课程计划
        TeachplanNode teachplanNode = teachplanMapper.selectList(id);
        courseView.setTeachplanNode(teachplanNode);
        return courseView;
    }

    //课程预览
    public CoursePublishResult preview(String courseId){
        CourseBase one =this.getCoursebaseById(courseId);

        //发布课程预览页面
        CmsPage cmsPage = new CmsPage() ;
        //站点
        cmsPage.setSiteId(publish_siteId) ; //课程预览站点
        //模板
        cmsPage.setTemplateId(publish_templateId) ;
        //页面名称
        cmsPage.setPageName(courseId+".html");
        //页面别名
        cmsPage.setPageAliase(one.getName());
        //页面访问路径
        cmsPage.setPageWebPath(publish_page_webpath);
        //页面存储路径
        cmsPage.setPagePhysicalPath(publish_page_physicalpath);
        //数据url
        cmsPage.setDataUrl(publish_dataUrlPre+courseId);
        //远程请求cms保存页面信息
        CmsPageResult cmsPageResult = cmsPageClient.save(cmsPage);
        if(!cmsPageResult.isSuccess() ) {
            return new CoursePublishResult(CommonCode.FAIL, null) ;
        }
        //页面id
        String pageId = cmsPageResult.getCmsPage().getPageId();


        //页面url
        String pageUrl = previewUrl + pageId;
        return new CoursePublishResult(CommonCode.SUCCESS,pageUrl);
    }

    //保存课程计划媒资信息
    private void saveTeachplanMediaPub(String courseId){

        List<TeachplanMedia> teachplanMediaList = teachplanMediaRepository.findByCourseId(courseId);
        //将课程计划媒资信息存储待索引表
        teachplanMediaPubRepository.deleteByCourseId(courseId);
        List<TeachplanMediaPub> teachplanMediaPubList = new ArrayList<>();
         for (TeachplanMedia teachplanMedia:teachplanMediaList) {
            TeachplanMediaPub teachplanMediaPub = new TeachplanMediaPub();
            BeanUtils.copyProperties(teachplanMedia,teachplanMediaPub);
             //添加时间戳
             teachplanMediaPub.setTimestamp(new Date());
             teachplanMediaPubList.add(teachplanMediaPub);
        }
        teachplanMediaPubRepository.saveAll(teachplanMediaPubList);

    }

    //课程发布
    @Transactional
    public CoursePublishResult publish(String courseId){
        //获取课程信息
        CourseBase one  = this.getCoursebaseById(courseId);
        //发布课程详情页面
        CmsPostPageResult cmsPostPageResult = this.publish_page(courseId);
        if (!cmsPostPageResult.isSuccess()){
            ExceptionCast.cast(CommonCode.FAIL);
        }

        //更新课程状态
        CourseBase courseBase = this.saveCoursePubState(courseId);
        //课程索引. . .

        //创建课程索引信息
        CoursePub coursePub = this.createCoursePub(courseId);

        //向数据库保存课程索引信息
       CoursePub coursePubNew = this.saveCoursePub(courseId,coursePub);

        if (coursePubNew == null){
            //创建课程索引信息失败
          ExceptionCast.cast(CourseCode.COURSE_PUBLISH_CREATECOURSEPUB_ERROR) ;
       }
        //课程缓存. . .
        //向teachplanMediaPub中保存课程媒资信息
        this.saveTeachplanMediaPub(courseId);
        //页面url
        String pageUrl = cmsPostPageResult.getPageUrl();
        return new CoursePublishResult(CommonCode.SUCCESS,pageUrl);
    }

    //保存CoursePub
    public CoursePub saveCoursePub(String id,CoursePub coursePub){

        if (StringUtils.isEmpty(id)){
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_COURSEIDISNULL);
        }
        CoursePub coursePubNew = null;
        Optional<CoursePub> pubOptional = coursePubRepository.findById(id);
        if (pubOptional.isPresent()){
            coursePubNew = pubOptional.get();
        }
        if (coursePubNew == null){
            coursePubNew = new CoursePub();
        }
        BeanUtils.copyProperties(coursePub,coursePubNew);

        //设置主键
        coursePubNew.setId(id);

        //更新时间戳为最新时间
        coursePubNew.setTimestamp(new Date());
        //发布时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY‐MM‐dd HH:mm:ss") ;
        String date = simpleDateFormat. format(new Date()) ;
        coursePubNew.setPubTime(date) ;
        coursePubRepository.save(coursePubNew);
        return coursePubNew;

    }

    //创建coursePub对象
     private CoursePub createCoursePub(String id){
        CoursePub coursePub = new CoursePub();
        coursePub.setId(id);

         //基础信息
         Optional<CourseBase> baseOptional = courseBaseRepository.findById(id);
         if (baseOptional.isPresent()){
             CourseBase courseBase = baseOptional.get();
             BeanUtils.copyProperties(courseBase,coursePub);
         }
         //查询课程图片
         Optional<CoursePic> picOptional = coursePicRepository. findById(id) ;
         if(picOptional. isPresent() ) {
             CoursePic coursePic = picOptional. get() ;
             BeanUtils. copyProperties(coursePic, coursePub) ;
         }
         //课程营销信息
         Optional<CourseMarket> marketOptional = courseMarketRepository. findById(id) ;
         if(marketOptional. isPresent() ) {
             CourseMarket courseMarket = marketOptional. get() ;
             BeanUtils. copyProperties(courseMarket, coursePub) ;
         }

         //课程计划
         TeachplanNode teachplanNode = teachplanMapper.selectList(id);
         String teachplanString = JSON.toJSONString(teachplanNode);
         coursePub.setTeachplan(teachplanString);
         return coursePub;

     }

    //更新课程发布状态
    private CourseBase saveCoursePubState(String courseId){
        CourseBase courseBase = this.getCoursebaseById(courseId);
        //更新发布状态
        courseBase.setStatus("202002");
        CourseBase save = courseBaseRepository.save(courseBase);
        return save;
    }

    //发布课程正式页面
    public CmsPostPageResult publish_page(String courseId){
        CourseBase one = this.getCoursebaseById(courseId);

        //发布课程预览页面
        CmsPage cmsPage = new CmsPage() ;
        //站点
        cmsPage.setSiteId(publish_siteId) ; //课程预览站点
        //模板
        cmsPage.setTemplateId(publish_templateId) ;
        //页面名称
        cmsPage.setPageName(courseId+".html");
        //页面别名
        cmsPage.setPageAliase(one.getName());
        //页面访问路径
        cmsPage.setPageWebPath(publish_page_webpath);
        //页面存储路径
        cmsPage.setPagePhysicalPath(publish_page_physicalpath);
        //数据url
        cmsPage.setDataUrl(publish_dataUrlPre+courseId);
        //发布页面
        CmsPostPageResult cmsPostPageResult = cmsPageClient.postPageQuick(cmsPage);
        return cmsPostPageResult;

    }

    //保存媒资信息
    public ResponseResult savemedia(TeachplanMedia teachplanMedia){
        if(teachplanMedia == null) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        //课程计划
        String teachplanId = teachplanMedia.getTeachplanId();
        //查询课程计划
        Optional<Teachplan> optional = teachplanRepository. findById(teachplanId) ;
        if( !optional.isPresent() ) {
            ExceptionCast.cast(CourseCode.COURSE_MEDIA_NAMEISNULL) ;
        }
        Teachplan teachplan = optional.get() ;
        //只允许为叶子结点课程计划选择视频
        String grade = teachplan. getGrade() ;
        if(StringUtils.isEmpty(grade) || !grade.equals("3") ) {
            ExceptionCast.cast(CourseCode.COURSE_MEDIA_TEACHPLAN_GRADEERROR);
        }
        TeachplanMedia one = null;
        Optional<TeachplanMedia> teachplanMediaOptional =teachplanMediaRepository.findById(teachplanId) ;
        if(!teachplanMediaOptional.isPresent()) {
            one = new TeachplanMedia();
        }else{
            one = teachplanMediaOptional.get();
        }
        //保存媒资信息与课程计划信息
        one.setTeachplanId(teachplanId);
        one.setCourseId(teachplanMedia.getCourseId());
        one.setMediaFileOriginalName(teachplanMedia.getMediaFileOriginalName());
        one.setMediaId(teachplanMedia.getMediaId());
        one.setMediaUrl(teachplanMedia.getMediaUrl());
        teachplanMediaRepository.save(one) ;
        return new ResponseResult(CommonCode.SUCCESS) ;
    }
}
