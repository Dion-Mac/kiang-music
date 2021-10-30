<template>
  <div class="header">
    <!--折叠图片-->
    <div class="collapse-btn">
      <i class="el-icon-menu"></i>
    </div>
  </div>
</template>

<script>
export default {
  name: 'TheHeader'
}
</script>

<style scoped>
.header {
  position: relative;
  background-color: #253041;
  box-sizing: border-box;
  width: 100%;
  height: 70px;
  font-size: 22px;
  color: white;
}

.collapse-btn {
  float: left;
  padding: 0 21px;
  curse: pointer;
  line-height: 70px;
}

</style>
