<template>
  <div>
    修改用户信息{{id}}，姓名{{name}}
  </div>
</template>
<script>
  export default {
    layout:'test',
    asyncData(){
      return{
        name:'黄礼缘'
      }
    },
    data(){
      return {
        id : ''
      }
    },
    mounted(){
      this.id = this.$route.params.id;
    }
  }
</script>
<style>

</style>
