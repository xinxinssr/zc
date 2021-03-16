<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <!-- 搜索 -->
        <label class="el-form-item-label">供应商名称</label>
        <el-input v-model="query.supplierName" clearable placeholder="供应商名称" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">产品类别</label>
        <el-input v-model="query.productType" clearable placeholder="产品类别" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <rrOperation :crud="crud" />
      </div>
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
          <el-form-item label="自增id">
            <el-input v-model="form.id" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="供应商id">
            <el-input v-model="form.supplierId" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="供应商名称" prop="supplierName">
            未设置字典，请手动设置 Select
          </el-form-item>
          <el-form-item label="供方范围">
            <el-input v-model="form.supplierScope" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="工序（电气事业部工序承接资质范围）">
            <el-input v-model="form.processScope" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="项目范围">
            <el-input v-model="form.projectScope" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="产品类别">
            <el-input v-model="form.productType" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="创建时间">
            <el-date-picker v-model="form.createTime" type="datetime" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="创建人">
            <el-input v-model="form.createBy" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="修改时间">
            <el-date-picker v-model="form.updateTime" type="datetime" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="修改人">
            <el-input v-model="form.updateBy" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="0正常 / 1 删除">
            <el-input v-model="form.state" style="width: 370px;" />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="crud.cancelCU">取消</el-button>
          <el-button :loading="crud.status.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
        </div>
      </el-dialog>
      <!--表格渲染-->
      <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="id" label="自增id" />
        <el-table-column prop="supplierId" label="供应商id" />
        <el-table-column prop="supplierName" label="供应商名称" />
        <el-table-column prop="supplierScope" label="供方范围" />
        <el-table-column prop="processScope" label="工序（电气事业部工序承接资质范围）" />
        <el-table-column prop="projectScope" label="项目范围" />
        <el-table-column prop="productType" label="产品类别" />
        <el-table-column prop="createTime" label="创建时间" />
        <el-table-column prop="createBy" label="创建人" />
        <el-table-column prop="updateTime" label="修改时间" />
        <el-table-column prop="updateBy" label="修改人" />
        <el-table-column prop="state" label="0正常 / 1 删除" />
        <el-table-column v-if="checkPer(['admin','subcontract:edit','subcontract:del'])" label="操作" width="150px" align="center">
          <template slot-scope="scope">
            <udOperation
              :data="scope.row"
              :permission="permission"
            />
          </template>
        </el-table-column>
      </el-table>
      <!--分页组件-->
      <pagination />
    </div>
  </div>
</template>

<script>
import crudSubcontract from '@/api/subcontract'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

const defaultForm = { id: null, supplierId: null, supplierName: null, supplierScope: null, processScope: null, projectScope: null, productType: null, createTime: null, createBy: null, updateTime: null, updateBy: null, state: null }
export default {
  name: 'Subcontract',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({ title: 'api/subcontract', url: 'api/subcontract', idField: 'id', sort: 'id,desc', crudMethod: { ...crudSubcontract }})
  },
  data() {
    return {
      permission: {
        add: ['admin', 'subcontract:add'],
        edit: ['admin', 'subcontract:edit'],
        del: ['admin', 'subcontract:del']
      },
      rules: {
        supplierName: [
          { required: true, message: '供应商名称不能为空', trigger: 'blur' }
        ]
      },
      queryTypeOptions: [
        { key: 'supplierName', display_name: '供应商名称' },
        { key: 'productType', display_name: '产品类别' }
      ]
    }
  },
  methods: {
    // 钩子：在获取表格数据之前执行，false 则代表不获取数据
    [CRUD.HOOK.beforeRefresh]() {
      return true
    }
  }
}
</script>

<style scoped>

</style>
