<template>
  <div>
    <!--编写页面静态部分，即view部分-->
    <!--查询表单-->
    <el-form :model="params">
      <el-select v-model="params.siteId" placeholder="请选择站点">
        <el-option
          v-for="item in siteList"
          :key="item.siteId"
          :label="item.siteName"
          :value="item.siteId">
        </el-option>
      </el-select>
      <el-select v-model="params.pageType" placeholder="请选择页面类型">
        <el-option
          v-for="item in pageTypeList"
          :key="item.pageTypeId"
          :label="item.pageTypeName"
          :value="item.pageTypeId">
        </el-option>
      </el-select>
      页面名称：<el-input v-model="params.pageName"  style="width: 100px"></el-input>
      页面别名：<el-input v-model="params.pageAliase"  style="width: 100px"></el-input>
      <el-button type="primary" size="small" v-on:click="query">查询</el-button>
      <router-link class="mui‐tab‐item" 
                   :to="{path:'/cms/page/add/',query:{
                      page:this.params.page,
                      siteId:this.params.siteId
                      }}">
        <el-button type="primary" size="small">新增页面</el-button>
      </router-link>
    </el-form>
    <el-table
      :data="list"
      stripe
      style="width: 100%">
      <el-table-column type="index" width="60">
      </el-table-column>
      <el-table-column prop="pageName" label="页面名称" width="100">
      </el-table-column>
      <el-table-column prop="pageAliase" label="别名" width="120">
      </el-table-column>
      <el-table-column prop="pageType" label="页面类型" width="100">
      </el-table-column>
      <el-table-column prop="pageWebPath" label="访问路径" width="250">
      </el-table-column>
      <el-table-column prop="pagePhysicalPath" label="物理路径" width="250">
      </el-table-column>
      <el-table-column label="操作" width="180">
        <template slot-scope="page">
          <el-button
            size="small"type="text"
            @click="edit(page.row.pageId)">编辑
          </el-button>
          <el-button
            size="small"type="text"
            @click="del(page.row.pageId)">删除
          </el-button>
          <el-button @click="preview(page.row.pageId)" type="text" size="small">页面预览</el-button>
          <el-button plain @click="postPage(page.row.pageId)" type="primary" size="small">发布</el-button>
        </template>

      </el-table-column>
    </el-table>
    <el-pagination
      layout="prev, pager, next"
      :total="total"
      :page-size="params.size"
      :current-page="params.page"
      @current-change="changePage"
      style="float: right">
    </el-pagination>
  </div>
</template>
<script>
  import * as cmsApi from '../api/cms'
  export default {
    data() {
      return {
        siteList:[],//站点列表
        pageTypeList:[],//页面类型列表
        list: [],
        total: 0,
        params:{
          page:1,//页码
          size:10,//每页显示个数
          siteId:'',//站点id
          pageAliase:'',//页面别名
          pageName:'',//页面名称
          pageType:''
        }
      }
    },
    methods:{
      query: function () {
        cmsApi.page_list(this.params.page,this.params.size,this.params).then((res) => {
          this.total = res.queryResult.total;
          this.list = res.queryResult.list;
        })
      },
      changePage: function (page) {
        this.params.page = page;
        this.query();
      },
      querySite: function () {
        cmsApi.site_list().then((res) => {
          this.siteList = res.queryResult.list;
        })
      },
      queryPageType: function(){
        this.pageTypeList = [
          {
            "pageTypeId": "0",
            "pageTypeName": "静态"
          },
          {
            "pageTypeId": "1",
            "pageTypeName": "动态"
          }
        ]
      },
      edit: function (pageId) {
        this.$router.push({path : '/cms/page/edit/'+pageId})
      },
      del: function (pageId) {
        this.$confirm('确认删除此页面吗?', '提示', {}).then(() => {
          cmsApi.page_del(pageId).then((res)=>{
            if(res.success){
              this.$message({
                type: 'success',
                message: '删除成功!'
              });
              //查询页面 
              this.query()
            }else{
              this.$message({
                type: 'error',
                message: '删除失败!'
              });
            }
          })
        })
      },
      preview:function (pageId) {
        window.open("http://www.xuecheng.com/cms/preview/"+pageId);
      },
      postPage:function (pageId) {
        this.$confirm('确认发布该页面吗?', '提示', {
        }).then(() => {
          cmsApi.page_postPage(pageId).then((res) => {
            if(res.success){
              console.log('发布页面id='+pageId);
              this.$message.success('发布成功，请稍后查看结果');
            }else{
              this.$message.error('发布失败');
            }
          });
        }).catch(() => {

        });
      }
    },
    created(){
      //   使用||符号表示前面条件没有值时显示后面的值
      this.params.page = Number.parseInt(this.$route.query.page || 1)
      this.params.siteId = this.$route.query.siteId || ''
      //初始化页面类型的内容
      this.params.pageTypeList = [
        {"pageTypeId":"0","pageTypeName":"静态"},
        {"pageTypeId":"1","pageTypeName":"动态"}
        ];
    },
    mounted() {//当DOM元素渲染完成后调用query
      //页面内容初始化查询
      this.query();
      //站点选择内容查询
      this.querySite();
      //页面类型内容
      this.queryPageType();
    },
  }
</script>
<style>
  /*编写页面样式，不是必须*/
</style>
