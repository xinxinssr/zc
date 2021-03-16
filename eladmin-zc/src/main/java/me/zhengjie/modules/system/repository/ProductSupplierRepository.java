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

import me.zhengjie.modules.system.domain.ProductSupplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
* @website https://el-admin.vip
* @author ly
* @date 2021-03-15
**/
public interface ProductSupplierRepository extends JpaRepository<ProductSupplier, Long>, JpaSpecificationExecutor<ProductSupplier> {

    @Query(value = "select id,check_card_id as checkCardId,product,supplier_id as supplierId,supplier_name as supplierName from zc_product_supplier where check_card_id=?1",nativeQuery=true)
    List<Map> selectProductSupplier(Long checkCardId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO zc_product_supplier(check_card_id,product,supplier_id,supplier_name) VALUES (?1,?2,?3,?4)",nativeQuery=true)
    Integer InstProductSupplier(Long checkCardId,String product,Long supplierId,String supplierName);
}