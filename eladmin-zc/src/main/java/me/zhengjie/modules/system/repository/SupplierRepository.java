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

import me.zhengjie.modules.system.domain.Supplier;
import org.apache.poi.ss.formula.functions.T;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
* @website https://el-admin.vip
* @author ly
* @date 2021-02-01
**/
public interface SupplierRepository extends JpaRepository<Supplier, Long>, JpaSpecificationExecutor<Supplier> {
    @Query(value = "select * from zc_supplier where name = ?1", nativeQuery = true)
    Map findByName(String name);

    @Query(value = "SELECT a.id,a.name,a.type,a.product_scope as productScope,a.supplier_by as supplierBy,a.supplier_phone as supplierPhone,a.supplier_shengshiqu as supplierShengshiqu,a.supplier_addres as supplierAddres,a.create_by as createBy,a.create_time as createTime,a.update_by as updateBy,a.update_time as updateTime," +
            "a.state,b.id as zsid ,b.type_name as typeName,b.name as zhengshuname,b.start_time as startTime,b.end_time as endTime from " +
            "zc_supplier a LEFT JOIN zc_certificate b on a.id=b.supplier_id where IF (?1!='', a.name like %?1%, 1=1) and IF (?2!='', a.product_scope like %?2%, 1=1) and IF (?3!='', a.supplier_by like %?3%, 1=1) and IF(?4 is not null, a.type=?4, 1=1) ",
             countQuery = "select count(a.id) from zc_supplier a LEFT JOIN zc_certificate b on a.id=b.supplier_id where IF \n" +
                     "(?1!='', a.name like %?1%, 1=1) and IF (?2!='', a.product_scope like %?2%, 1=1) and IF \n" +
                     "(?3!='', a.supplier_by like %?3%, 1=1) and IF(?4 is not null, a.type=?4, 1=1) ",
            nativeQuery = true)
    Page<Map> findAlls(String name,String productScope,String  supplierBy,Long type, Pageable pageable);
    //Page<Map> findAlls(@Nullable Specification<T> var1, Pageable pageable);

    @Query(value = "select id,name,product_scope as productScope from zc_supplier where IF(?1 is not null, type=?1, 1=1)",nativeQuery = true)
    List<Map> selectAll(Long type);

    @Query(value = "select * from zc_supplier where name = ?1",nativeQuery = true)
    Supplier selectName(String name);

    @Query(value = "select * from zc_supplier where id = ?1", nativeQuery = true)
    Supplier selectById(String id);

    @Query(value = "select id,supplier_id as supplierId,name,path,type_id as typeId,type_name as typeName,start_time as startTime,end_time as endTime from zc_certificate where supplier_id=?1",
            countQuery ="select COUNT(*) from zc_certificate where supplier_id=?1",
            nativeQuery = true)
    Page<Map> findBySId(String id,  Pageable pageable);


    @Modifying
    @Transactional
    @Query(value = "delete from zc_certificate where id= ?1",nativeQuery=true)
     Integer delCertificate(String id);
}