<template>
  <div>
    user用户管理页面<nuxt-link :to="'/user/101'">修改用户</nuxt-link>
    <nuxt-child/>
  </div>
</template>
<script>
  export default {
    layout:'test'
  }
</script>
<style>

</style>
