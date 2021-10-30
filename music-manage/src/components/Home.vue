<template>
  <div>
    <the-header></the-header>
    <the-aside></the-aside>
    <router-view></router-view>
  </div>
</template>

<script>
import TheHeader from './TheHeader'
import TheAside from './TheAside'
export default {
  components: {
    TheHeader,
    TheAside
  }
}
</script>

<style scoped>

</style>
