<template>
  <div>
    <!--    折叠 -->
    <el-collapse @change="handleChange">
      <el-collapse-item name="1">
        <template slot="title">
          <div style="font-size:18px;color: black;font-weight: bold;">权限列表
          </div>
        </template>


        <!-- 分割线 -->
        <el-divider></el-divider>

        <!--        按钮div -->
        <el-form>
          <el-form-item>
            <el-button type="primary" class="button1" icon="el-icon-edit" size="small" @click="openEdit">编辑
            </el-button>

            <el-popover placement="top" width="160" v-model="visible">
              <p>确定删除这个权限吗？</p>
              <div style="text-align: right; margin: 0">
                <el-button size="mini" type="text" @click=" visible = false">取消</el-button>
                <el-button type="primary" size="mini" @click="remove">确定</el-button>
              </div>
              <el-button slot="reference" class="botton2">删除</el-button>
            </el-popover>

            <el-button type="success" class="botton3" icon="el-icon-plus" size="small" @click="openAdd">新增
            </el-button>
          </el-form-item>
        </el-form>
        <!-- tree -->
        <div class="custom-tree-container">
          <div class="block">
            <el-tree :data="tTree" default-expand-all show-checkbox node-key="id" ref="tree" :default-checked-keys="cnk"
              :props="defaultProps">
            </el-tree>

          </div>
        </div>

      </el-collapse-item>
    </el-collapse>


    <!--  编辑dialog -->
    <el-dialog :title="title" :visible.sync="show" width="500px">
      <el-input type="hidden" :name="dialogType"></el-input>
      <el-form :model="form" :rules="rules" ref="form" label-width="110px">
        <el-form-item label="权限名称" prop="name">
          <el-input v-model="form.name" clearable></el-input>
        </el-form-item>
        <el-form-item label="图标样式" prop="icon">
          <el-input v-model="form.icon" clearable :disabled="disabled"></el-input>
        </el-form-item>
        <el-form-item label="访问地址" prop="url">
          <el-input v-model="form.url" clearable :disabled="disabled"></el-input>
        </el-form-item>
        <el-form-item label="排序序号" prop="seq">
          <el-input v-model="form.seq" clearable></el-input>
        </el-form-item>
        <el-form-item label="权限备注" prop="mark">
          <el-input v-model="form.mark" clearable></el-input>
        </el-form-item>
        <el-form-item label="上级菜单">
          <el-select v-model="form.pidText" placeholder="请选择" :disabled="disabled">
            <el-option v-for="item in preList" :key="item.id" :label="item.name" :value="item.name">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">提交</el-button>
        <el-button @click="show = false">取消</el-button>
      </span>

    </el-dialog>

  </div>
</template>

<div></div>
<script>
  let id = 1000;

  export default {
    name: "Auth",
    data() {
      return {
        tTree: [],
        defaultProps: {
          children: 'children',
          label: "name",
        },
        rules: {
          name: [{
            required: true,
            message: '请输入权限名',
            trigger: 'blur'
          }],
          seq: [{
            required: true,
            message: '请输入序号',
            trigger: 'blur'
          }],
          mark: [{
            required: true,
            message: "请输入权限备注",
            trigger: 'blur'
          }],
        },

        cnk: [],
        preList: [],
        //查询条件
        queryForm: {

        },
        //对话框标题
        title: '',
        //初始化表单对象
        form: {},
        //标识是新增还是修改
        dialogType: 'add',
        //主键是否可以被编辑:默认可以编辑
        disabled: false,
        show: false,

        visible: false,

        Pid: null
      }
    },
    methods: {
      handleChange(val) {
        this.query();
        console.log(val);
      },
      //查询数据
      query() {
        this.$axios({
          method: 'post',
          url: 'api/sys/auth/selectList',
          data: this.queryForm
        }).then(res => {
          if (res.data.success) {
            this.tTree = res.data.obj;
          } else {
            this.$message.error(res.data.msg);
          }
        }).catch(err => {
          console.info(err)
        });
      },
      open1() {
        const h = this.$createElement;
        this.$notify({
          title: '提示',
          message: h('i', {
            style: 'color: teal'
          }, '请选中再编辑')
        });
      },
      open2() {
        const h = this.$createElement;
        this.$notify({
          title: '提示',
          message: h('i', {
            style: 'color: teal'
          }, '请选中再删除')
        });
      },

      openEdit() {
        //设置对话框标题
        this.title = "编辑";
        //清空表单
        this.form = {};
        //主键不可以编辑
        this.disabled = true;
        //编辑之前看是否选中
        this.cnk = this.$refs.tree.getCheckedKeys();
        if (this.cnk == false) {
          this.open1();
        } else {
          //打开对话框
          this.show = true;
          //更改标识为编辑
          this.dialogType = 'edit';
          //获取所有preList
          this.querypreList();
          //清除表单校验结果
          this.$nextTick(function() {
            this.$refs['form'].clearValidate();
          });
          this.queryOne()
        }
      },

      queryOne() {
        this.$axios({
          method: 'post',
          url: 'api/sys/auth/authone',
          data: this.cnk
        }).then(res => {
          if (res.data.success) {
            this.form = res.data.obj;
          } else {
            this.$message.error(res.data.msg);
          }
        }).catch(err => {
          console.info(err)
        });
      },

      querypreList() {
        this.$axios({
          method: 'post',
          url: 'api/sys/auth/selectpreList',
        }).then(res => {
          if (res.data.success) {
            this.preList = res.data.obj;
          } else {
            this.$message.error(res.data.msg);
          }
        }).catch(err => {
          console.info(err)
        });
      },

      submitForm() {
        this.$refs['form'].validate((valid) => {
          if (valid) {
            if (this.dialogType == 'add') {
              this.$axios({
                method: 'post',
                url: 'api/sys/auth/add',
                data: this.form
              }).then(res => {
                //关闭对话框
                this.show = false;
                //刷新页面
                this.query()
              }).catch(err => {
                console.info(err)
              });
            } else {
              this.$axios({
                method: 'post',
                url: 'api/sys/auth/edit',
                data: this.form
              }).then(res => {
                //关闭对话框
                this.show = false;
                //刷新页面
                this.query()
              }).catch(err => {
                console.info(err)
              });
            }
          }
        });
      },

      openAdd() {
        //设置对话框标题
        this.title = "新增";
        //清空表单
        this.form = {};
        //主键可编辑
        this.disabled = false;
        //打开对话框
        this.show = true;
        //更改标识为新增
        this.dialogType = 'add';
        //获取所有preList
        this.querypreList();
        //清除表单校验结果
        this.$nextTick(function() {
          this.$refs['form'].clearValidate();
        });
      },
      remove() {
        this.cnk = this.$refs.tree.getCheckedKeys();
        if (this.cnk == false) {
          this.open2();
        } else {
          this.$axios({
            method: 'post',
            url: 'api/sys/auth/del',
            data: this.cnk
          }).then(res => {
            //刷新页面
            this.query()
            this.visible = false
          }).catch(err => {
            console.info(err)
            this.visible = false
          });
        }
      },
      created() {
        //初始化页面时进行数据查询
        this.query();
      }

    },
  };
</script>

<style>
  .el-button.button1 {
    float: left;
  }

  .el-button.botton2 {
    float: left;
    font-size: 10px;
    background-color: red;
    color: aliceblue;
    width: 80px;
    height: 34px;
  }

  .el-button.botton3 {
    float: right;
  }

  .custom-tree-node {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: space-between;
    font-size: 14px;
    padding-right: 8px;
  }

  .el-select {
    width: 100%;
  }
</style>
