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

import me.zhengjie.modules.system.domain.ProductProduct;
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
public interface ProductProductRepository extends JpaRepository<ProductProduct, Long>, JpaSpecificationExecutor<ProductProduct> {



    @Query(value = "select id,check_card_id as checkCardId,product,check_card_id2 as checkCardId2,product2 from zc_product_product where check_card_id=?1",nativeQuery=true)
    List<Map> selectProductP(Long checkCardId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO zc_product_product(check_card_id,product,check_card_id2,product2) VALUES (?1,?2,?3,?4)",nativeQuery=true)
    Integer InstProductP(Long checkCardId,String product,Long checkCardId2,String product2);


}