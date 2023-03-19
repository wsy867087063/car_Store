<template>
	<div>
		<el-form :inline="true" :model="queryForm" style="text-align: left;">
			<el-form-item label="角色名称">
				<el-input v-model="queryForm.name" placeholder="请输入"></el-input>
			</el-form-item>
			<el-form-item>
				<el-button type="primary" icon="el-icon-search" @click="query">查询</el-button>
				<el-button type="primary" icon="el-icon-edit" @click="openAdd" node-key="id" ref="tree" plain>新增
				</el-button>
			</el-form-item>
		</el-form>
		<!-- 数据列表 -->
		<el-table :data="tableData" style="width: 100%" :header-cell-style="{background:'#f7f7f7',color:'#666666'}">
			<el-table-column prop="name" label="角色名称">
			</el-table-column>
			<el-table-column prop="mark" label="角色备注">
			</el-table-column>
			<el-table-column prop="authList" label="拥有权限">
				<template slot-scope="scope">
					<el-tag v-for="item in scope.row.authList" :key="item.id">{{item.name}}</el-tag>
				</template>
			</el-table-column>
			<el-table-column prop="createId" label="新增人员">
			</el-table-column>
			<el-table-column prop="createTime" label="新增时间">
			</el-table-column>
			<el-table-column prop="modifyId" label="修改人员">
			</el-table-column>
			<el-table-column prop="modifyTime" label="修改时间">
			</el-table-column>
			<el-table-column fixed="right" label="操作">
				<template slot-scope="scope">
					<el-button type="text" size="small" icon="el-icon-edit" node-key="id" ref="tree"
						@click="openEdit(scope.row)">编辑
					</el-button>
					<el-popconfirm title="确定删除吗?" @confirm="del(scope.row)">
						<el-button slot="reference" type="text" size="small">删除</el-button>
					</el-popconfirm>
				</template>
			</el-table-column>
		</el-table>
		<!-- 分页 -->
		<el-pagination v-show="total > 0" background @size-change="query" @current-change="query"
			:current-page.sync="queryForm.pageNum" :page-size.sync="queryForm.pageSize"
			layout="total,sizes,prev, pager, next,jumper" :total="total">
		</el-pagination>

		<!-- 新增/修改页面 -->
		<el-dialog :title="title" :visible.sync="show" width="500px">
			<el-input type="hidden" name="dialogType"></el-input>
			<el-form :model="form" :rules="rules" ref="form" label-width="80px">
				<el-form-item label="角色名称" prop="name">
					<el-input v-model="form.name" clearable></el-input>
				</el-form-item>
				<el-form-item label="角色备注" prop="mark">
					<el-input v-model="form.mark" clearable></el-input>
				</el-form-item>
				<el-form-item label="角色权限" prop="authList">
					<el-tree :data="data" show-checkbox default-expand-all node-key="id" ref="tree" empty-text="请输入"
						highlight-current props="defaultProps">
					</el-tree>
				</el-form-item>
			</el-form>
			<span slot="footer" class="dialog-footer">
				<el-button type="primary" icon="el-icon-check" @click="getCheckedKeys()">提交</el-button>
				<el-button @click="show = false">取消</el-button>
			</span>
		</el-dialog>
	</div>
</template>

<script>
	export default {
		name: 'Role',
		data() {
			return {
				//查询条件对象
				queryForm: {
					name: '',
					pageNum: 1,
					pageSize: 10
				},
				//数据表格数组
				tableData: [],
				//一共多少条数据
				total: 0,
				//对话框标题
				title: '',
				//是否显示对话框
				show: false,
				//初始化表单对象
				form: {
					name: '',
					mark: '',
					roleauthlist: [],
					roleauth: []
				},

				//初始化校验对象
				rules: {
					name: [{
						required: true,
						message: '请输入账号',
						trigger: 'blur'
					}]

				},
				//所有权限
				authList: [],
				defaultProps: {
					children: 'children',
					label: 'label'
				},
				data: [],
				//标识是新增还是修改
				dialogType: 'add',
				tree: []
			}

		},
		methods: {
			//进入页面直接查询数据
			query() {
				this.$axios({
					method: 'post',
					url: 'api/sys/role/list',
					data: this.queryForm
				}).then(res => {
					if (res.data.success) {
						this.tableData = res.data.obj.list;
						this.total = res.data.obj.total;
					} else {
						this.$message.error(res.data.msg);
					}
				}).catch(err => {
					console.info(err)
				});
			},
			//提交
			getCheckedKeys() {
				this.$refs['form'].validate((valid) => {
					if (valid) {
						this.form.roleauthlist = this.$refs.tree.getCheckedKeys()
						if (this.dialogType == 'add') {
							this.$axios({
								method: 'post',
								url: 'api/sys/role/add',
								data: this.form
							}).then(res => {
								//关闭对话框
								this.show = false;
								//刷新页面
								this.query();

							}).catch(err => {
								console.info(err)
							});
						} else {
							this.$axios({
								method: 'post',
								url: 'api/sys/role/edit',
								data: this.form
							}).then(res => {
								//关闭对话框
								this.show = false;
								//刷新页面
								this.query();

							}).catch(err => {
								console.info(err)
							});

						}
					}
				});


			},
			queryAuthList() {
				this.$axios({
					method: 'post',
					url: 'api/sys/auth/selectListTwo',
				}).then(res => {
					this.data = res.data.obj;

				}).catch(err => {
					console.info(err)
				});
			},
			openAdd() {
				this.$nextTick(function() {
					//设置标题
					this.title = "新增";
					//清空表单
					this.form = {},
						//打开对话框
						this.show = true;
					//更改标识为新增
					this.dialogType = 'add';

					//获取所有角色数组
					this.queryAuthList();
					//消除表单校验结果

					this.$nextTick(function() {
						this.$refs.tree.setCheckedKeys([]);
						this.$refs['form'].clearValidate();
					});
				});

			},
			openEdit(row) {
				this.$nextTick(function() {
					//设置标题
					this.title = "编辑";
					//清空表单
					this.form = {},
						//打开对话框
						this.show = true;
					//更改标识为新增
					this.dialogType = 'edit';
					//获取所有角色数组
					this.queryAuthList();

					//对列表行对象进行复制克隆，解决双向绑定问题
					this.form = Object.assign({}, row);
					//处理角色：row.authList=>form.roleauthlist
					this.form.m = [];
					for (let i = 0; i < row.authList.length; i++) {
						let auth = row.authList[i];
						this.form.m.push(auth.id);
					}
					console.log(this.form.m);

					this.$nextTick(function() {
						this.$refs.tree.setCheckedKeys(this.form.m);
					});
				});

			},
			del(row) {
				this.$axios({
					method: 'get',
					url: 'api/sys/role/del/' + row.id
				}).then(res => {
					//刷新页面
					this.query();

				}).catch(err => {
					console.info(err)
				});
			}

		},

		created() {
			//初始化页面时进行数据查询
			this.query();
		}
	}
</script>

<style>
</style>
