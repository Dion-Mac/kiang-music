<template>
  <div class="table">
    <div class="container">
      <div class="handle-box">
        <el-button type="primary" size="mini" @click="centerDialogVisable = true">添加歌手</el-button>
      </div>
    </div>
    <el-table size="mini" border style="width:100%" height="500px" :data="tableData">
      <el-table-column label="歌手图片" width="110" align="center">
        <template slot-scope="scope">
          <div class="singer-img">
            <img :src="getUrl(scope.row.pic)" style="width:100%"/>
          </div>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog title="添加歌手" :visible.sync="centerDialogVisable" width="400px" center>
      <el-form :model="registerForm" ref="registerForm" label-width="80px">
        <el-form-item prop="name" label="歌手名" size="mini">
          <el-input v-model="registerForm.name" placeholder="歌手名"></el-input>
        </el-form-item>
        <el-form-item prop="sex" label="性别" size="mini">
          <el-radio-group v-model="registerForm.sex">
            <el-radio :label="0">女</el-radio>
            <el-radio :label="1">男</el-radio>
            <el-radio :label="2">组合</el-radio>
            <el-radio :label="3">不明</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item prop="birth" label="生日" size="mini">
          <el-date-picker type="date" v-model="registerForm.birth" placeholder="选择日期" style="width: 100%"></el-date-picker>
        </el-form-item>
        <el-form-item prop="location" label="地区" size="mini">
          <el-input v-model="registerForm.location" placeholder="地区"></el-input>
        </el-form-item>
        <el-form-item prop="introduction" label="简介" size="mini">
          <el-input type="textarea" v-model="registerForm.introduction" placeholder="简介"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer">
          <el-button size="mini" @click="centerDialogVisable=false">取消</el-button>
          <el-button size="mini" @click="addSinger">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {setSinger} from '../api/index';
import {mixin} from '../mixins/index';
export default {
  mixins: [mixin],
  data () {
    return {
      centerDialogVisable: false,
      registerForm: {
        name: '',
        sex: '',
        birth: '',
        location: '',
        introduction: ''
      },
      tableData:[]
    }
  },
  created () {
    this.getData()
  },
  methods: {
    // 查询所有歌手
    getData () {
      this.tableData=[];
      getAllSinger().then(res => {
        this.tableData = res
      })
    },

    // 添加歌手
    addSinger() {
      let d = this.registerForm.birth
      let datetime = d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate();
      let params = new URLSearchParams();
      params.append('name', this.registerForm.name);
      params.append('sex', this.registerForm.sex);
      params.append('pic', '/img/singerPic/Tom.png');
      params.append('birth', datetime);
      params.append('location', this.registerForm.location);
      params.append('introduction', this.registerForm.introduction);
      setSinger(params).then((res)=>{
        if (res.code == 1) {
          this.notify('添加成功', 'success')
        } else {
          this.notify('添加失败', 'error')
        }
      }).catch(err => {
        console.log(err)
      })
      this.centerDialogVisable = false
    }
  }
}
</script>

<style scoped>
 .handle-box {
   margin-bottom: 20px;
 }
 .singer-img {
   width: 100%;
   height: 100%;
   border-radius: 5px;
   margin-bottom: 5px;
   overflow: hidden;
 }

</style>
