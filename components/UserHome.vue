<template>
  <div class="demo-type">
    <el-avatar :size="60" src="https://empty" :alt="title" @error="errorHandler">
      <img src="https://cube.elemecdn.com/e/fd/0fc7d20532fdaf769a25683617711png.png" />
    </el-avatar>
    <div>欢迎光临</div>
    <el-steps :active='act' align-center>
      <el-step title="选车"></el-step>
      <el-step title="门店/时间"></el-step>
      <el-step title="增值服务"></el-step>
      <el-step title="提交"></el-step>
    </el-steps>
    <el-tabs v-model="act" tab-position="left" style="height: 400px;">
      <el-tab-pane label="选车" name="1">
        <el-carousel :interval="4000" type="card" height="400px">
          <el-carousel-item v-for="item in 6" :key="item">
            <h3 class="medium">{{ item }}</h3>
          </el-carousel-item>
        </el-carousel>
      </el-tab-pane>
      <el-tab-pane label="门店/时间" name="2"></el-tab-pane>
      <el-tab-pane label="增值服务" name="3"></el-tab-pane>
      <el-tab-pane label="提交" name="4"></el-tab-pane>
    </el-tabs>

    <div>

      <el-button-group class="group">
        <el-button type="primary" icon="el-icon-arrow-right" @click="prestep">上一步</el-button>
        <el-button type="primary" @click="nextstep">下一步<i class="el-icon-arrow-left el-icon--left"></i></el-button>
      </el-button-group>
      <el-button type="danger" class='bottom4' :disabled="disabled">提交</el-button>
    </div>
  </div>
</template>
<script>
  export default {
    name: "UserHome",
    data() {
      return {
        title: '顾客',
        act: 1,
        disabled: true,
      }
    },
    methods: {
      errorHandler() {
        return true
      },

      prestep() {
        if (this.act > 1) {
          --this.act;
        }
        this.disabled = true
      },

      nextstep() {
        if (this.act < 4) {
          ++this.act;
        }
        if (this.act == 4)
          this.disabled = false;
    }

  }
  }
</script>

<style>
  .el-avatar {
    margin: 0 auto;
  }

  .el-steps {
    margin: 0 auto;
    margin-top: 3%;
    /* 垂直居中 */
  }

  .group {
    float: right;
  }

  .bottom4 {
    float: right;
  }

  .el-carousel__item h3 {
    color: #475669;
    font-size: 14px;
    opacity: 0.75;
    line-height: 200px;
    margin: 0;
  }

  .el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
  }

  .el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
  }
</style>
