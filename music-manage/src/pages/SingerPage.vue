<template>
  <div class="table">
    <!--按钮所在的框-->
    <div class="container">
      <div class="handle-box">
        <el-button type="primary" size="mini" @click="delAll">批量删除</el-button>
        <el-input v-model="select_word" size="mini" placeholder="请输入歌手名" class="handle-input"></el-input>
        <el-button type="primary" size="mini" @click="centerDialogVisable = true">添加歌手</el-button>
      </div>
    </div>
    <!-- 歌手列表-->
    <el-table size="mini" border style="width:100%" height="750px" :data="data" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="40"></el-table-column>
      <!-- 列表第一列-->
      <el-table-column label="歌手图片" width="110" align="center">
        <template slot-scope="scope">
          <div class="singer-img">
            <img :src="getUrl(scope.row.pic)" style="width:100%"/>
          </div>
          <el-upload :action="uploadUrl(scope.row.id)" :before-upload="beforeAvatorUpload" :on-success="handleAvatorSuccess" >
            <el-button size="mini">更新图片</el-button>
          </el-upload>
        </template>
      </el-table-column>
      <el-table-column prop="name" label="歌手" width="120" align="center"></el-table-column>
      <el-table-column label="性别" width="110" align="center">
        <template slot-scope="scope">
          {{ changeSex(scope.row.sex) }}
        </template>
      </el-table-column>
      <el-table-column label="生日" width="120" align="center">
        <template slot-scope="scope">
          {{attachBirth(scope.row.birth)}}
        </template>
      </el-table-column>
      <el-table-column prop="location" label="地区" width="100" align="center"></el-table-column>
      <el-table-column prop="introduction" label="简介" align="center">
        <template slot-scope="scope">
          <p style="height: 100px;overflow: scroll">
            {{scope.row.introduction}}
          </p>
        </template>
      </el-table-column>
      <el-table-column label="歌曲管理" width="110" align="center">
        <template slot-scope="scope">
          <el-button size="mini" @click="songEdit(scope.row.id, scope.row.name)">歌曲管理</el-button>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="150" align="center">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.row.id)">删除</el-button>
        </template>

      </el-table-column>
    </el-table>
    <div class="pagination">
      <el-pagination background layout="total,prev,pager,next" :page-size="pageSize" :total="tableData.length" @current-change="handleCurrentChange"></el-pagination>
    </div>
<!-- 添加歌手弹窗-->
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
    <el-dialog title="修改歌手" :visible.sync="editVisible" width="400px" center>
      <el-form :model="form" ref="form" label-width="80px">
        <el-form-item prop="name" label="歌手名" size="mini">
          <el-input v-model="form.name" placeholder="歌手名"></el-input>
        </el-form-item>
        <el-form-item prop="sex" label="性别" size="mini">
          <el-radio-group v-model="form.sex">
            <el-radio :label="0">女</el-radio>
            <el-radio :label="1">男</el-radio>
            <el-radio :label="2">组合</el-radio>
            <el-radio :label="3">不明</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item prop="birth" label="生日" size="mini">
          <el-date-picker type="date" v-model="form.birth" placeholder="选择日期" style="width: 100%"></el-date-picker>
        </el-form-item>
        <el-form-item prop="location" label="地区" size="mini">
          <el-input v-model="form.location" placeholder="地区"></el-input>
        </el-form-item>
        <el-form-item prop="introduction" label="简介" size="mini">
          <el-input type="textarea" v-model="form.introduction" placeholder="简介"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer">
          <el-button size="mini" @click="editVisible=false">取消</el-button>
          <el-button size="mini" @click="editSave">确定</el-button>
      </span>
    </el-dialog>
    <el-dialog title="删除歌手" :visible.sync="delVisible" width="400px" center>
      <div align="center">删除不可恢复，是否确定删除？</div>
      <span slot="footer">
          <el-button size="mini" @click="delVisible=false">取消</el-button>
          <el-button size="mini" @click="deleteRow">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {setSinger, getAllSinger, updateSinger, deleteSinger} from '../api/index'
import {mixin} from '../mixins/index';
export default {
  mixins: [mixin],
  data () {
    return {
      centerDialogVisable: false, // 添加功能弹窗是否显示
      editVisible: false, // 编辑功能弹窗是否显示
      delVisible: false, // 删除功能弹窗是否显示
      registerForm: {  // 添加框
        name: '',
        sex: '',
        birth: '',
        location: '',
        introduction: ''
      },
      form: { // 编辑框
        id: '',
        name: '',
        sex: '',
        birth: '',
        location: '',
        introduction: ''
      },
      tableData: [],
      tempData: [],
      select_word: '',
      pageSize: 5,
      currentPage: 1,
      idx: -1, // 当前所选项
      multipleSelection: [] //哪些项已经打勾
    }
  },
  watch: {
    // 搜索框里面的内容发生变化时，搜索结果table列表的内容跟着它的内容变化
    select_word: function () {
      if (this.select_word == '') {
        this.tableData = this.tempData
      } else {
        this.tableData=[]
        for (let item of this.tempData) {
          if (item.name.includes(this.select_word)) {
            this.tableData.push(item)
          }
        }
      }
    }
  },
  computed: {
    data () {
      return this.tableData.slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize)
    }
  },
  created () {
    this.getData()
  },
  methods: {
    // 获取当前页
    handleCurrentChange (val) {
      this.currentPage = val
    },
    // 查询所有歌手
    getData () {
      this.tempData = []
      this.tableData = []
      getAllSinger().then(res => {
        this.tempData = res
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
      this.registerForm = {}
      setSinger(params).then((res)=>{
        if (res.code == 1) {
          this.getData()
          this.notify('添加成功', 'success')
        } else {
          this.notify('添加失败', 'error')
        }
      }).catch(err => {
        console.log(err)
      })
      this.centerDialogVisable = false
    },
    // 更新歌手图片
    uploadUrl(id){
      return `${this.$store.state.HOST}/singer/updateSingerPic?id=${id}`
    },
    // this.idx=id使之已经被传参数，deleteRow方法不用再传值
    deleteRow () {
      deleteSinger(this.idx).then((res)=>{
        if (res) {
          this.getData()
          this.notify('删除成功', 'success')
        } else {
          this.notify('删除失败', 'error')
        }
      }).catch(err => {
        console.log(err)
      })
      this.delVisible = false

    },
    // 弹出编辑页面
    handleEdit (row) {
      this.editVisible = true
      this.form={
        id: row.id,
        name: row.name,
        sex: row.sex,
        birth: row.birth,
        location: row.location,
        introduction: row.introduction
      }
    },
    // 保存编辑页面修改的数据
    editSave () {
      let d = this.form.birth
      let datetime = d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate();
      let params = new URLSearchParams();
      params.append('id', this.form.id);
      params.append('name', this.form.name);
      params.append('sex', this.form.sex);
      params.append('birth', datetime);
      params.append('location', this.form.location);
      params.append('introduction', this.form.introduction);
      updateSinger(params).then((res)=>{
        if (res.code == 1) {
          this.getData()
          this.notify('修改成功', 'success')
        } else {
          this.notify('修改失败', 'error')
        }
      }).catch(err => {
        console.log(err)
      })
      this.editVisible = false
    },
    // 转向歌曲管理页面
    songEdit (id, name) {
      this.$router.push({path:`/Song`,query:{id, name}})
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

 .handle-input {
   width: 300px;
   display: inline-block;
 }

 .pagination {
   display: flex;
   justify-content: center;

 }

 .el-table_header {
   width: 100% !important;
 }

 .el-table_body {
   width: 100% !important;
 }

</style>
