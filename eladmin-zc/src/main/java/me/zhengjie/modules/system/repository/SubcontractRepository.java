/*
*  Copyright 2019-2020 Zheng Jie
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package me.zhengjie.modules.system.repository;

import me.zhengjie.modules.system.domain.Subcontract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Map;

/**
* @website https://el-admin.vip
* @author ly
* @date 2021-02-05
**/
public interface SubcontractRepository extends JpaRepository<Subcontract, Long>, JpaSpecificationExecutor<Subcontract> {
    @Query(value = "select a.id,a.supplier_id as supplierId,a.supplier_scope as supplierScope,a.process_scope as processScope,a.product_type as productType,\n" +
            "a.project_scope as projectScope,a.product_type as projecType,a.create_by as createBy,a.create_time as createTime,a.update_by as updateBy,\n" +
            "a.update_time as updateTime,b.name as supplierName ,b.product_scope as productScope,b.supplier_by as supplierBy ,b.supplier_phone as supplierPhone from zc_subcontract a \n" +
            "LEFT JOIN zc_supplier  b on a.supplier_id=b.id where IF (?1!='', b.name like %?1%, 1=1) and IF (?2!='', a.product_type like %?2%, 1=1)",
            countQuery = "select count(a.id) from zc_subcontract a LEFT JOIN zc_supplier b on a.supplier_id=b.id" +
                        "where IF (?1!='', b.name like %?1%, 1=1) and IF (?2!='', a.product_type like %?2%, 1=1)",
            nativeQuery = true)
    Page<Map> findAlls(String supplierName,String productType, Pageable pageable);
}