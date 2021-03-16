<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <!-- 搜索 -->
        <label class="el-form-item-label">供应商名称</label>
        <el-input v-model="query.name" clearable placeholder="供应商名称" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">提供产品名称/范围（公司合格供方目录）</label>
        <el-input v-model="query.productScope" clearable placeholder="提供产品名称/范围（公司合格供方目录）" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">供应商联系人</label>
        <el-input v-model="query.supplierBy" clearable placeholder="供应商联系人" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <rrOperation :crud="crud" />
      </div>
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
          <el-form-item label="供应商名称" prop="name">
            <el-input v-model="form.name" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="提供产品名称/范围（公司合格供方目录）">
            <el-input v-model="form.productScope" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="供应商联系人">
            <el-input v-model="form.supplierBy" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="供应商联系电话">
            <el-input v-model="form.supplierPhone" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="供应商地址">
            <el-input v-model="form.supplierAddres" style="width: 370px;" />
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
        <el-table-column prop="name" label="供应商名称" />
        <el-table-column prop="productScope" label="提供产品名称/范围（公司合格供方目录）" />
        <el-table-column prop="supplierBy" label="供应商联系人" />
        <el-table-column prop="supplierPhone" label="供应商联系电话" />
        <el-table-column prop="supplierAddres" label="供应商地址" />
        <el-table-column prop="createTime" label="创建时间" />
        <el-table-column prop="createBy" label="创建人" />
        <el-table-column prop="updateTime" label="修改时间" />
        <el-table-column prop="updateBy" label="修改人" />
        <el-table-column v-if="checkPer(['admin','supplier:edit','supplier:del'])" label="操作" width="150px" align="center">
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
import crudSupplier from '@/api/supplier'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

const defaultForm = { id: null, name: null, productScope: null, supplierBy: null, supplierPhone: null, supplierAddres: null, createTime: null, createBy: null, updateTime: null, updateBy: null, state: null }
export default {
  name: 'Supplier',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({ title: 'api/supplier', url: 'api/supplier', idField: 'id', sort: 'id,desc', crudMethod: { ...crudSupplier }})
  },
  data() {
    return {
      permission: {
        add: ['admin', 'supplier:add'],
        edit: ['admin', 'supplier:edit'],
        del: ['admin', 'supplier:del']
      },
      rules: {
        name: [
          { required: true, message: '供应商名称不能为空', trigger: 'blur' }
        ]
      },
      queryTypeOptions: [
        { key: 'name', display_name: '供应商名称' },
        { key: 'productScope', display_name: '提供产品名称/范围（公司合格供方目录）' },
        { key: 'supplierBy', display_name: '供应商联系人' }
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
