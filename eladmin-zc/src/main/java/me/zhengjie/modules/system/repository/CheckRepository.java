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

import me.zhengjie.modules.system.domain.Check;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
* @date 2021-02-24
**/
public interface CheckRepository extends JpaRepository<Check, Long>, JpaSpecificationExecutor<Check> {
    @Query(value = "select * from zc_check  where IF (?1!='', pid=?1, 1=1)",
            countQuery = "select count(id) from zc_check  where IF (?1!='', pid=?1, 1=1)",
            nativeQuery = true)
    Page<Map> findAlls(String pid, Pageable pageable);

    @Query(value = " select a.id,check_card_id as checkCardId,a.product,a.check_card_id2 as checkCardId2,a.product2,b.type,b.product_id as productId,b.sri,b.factory_code as factoryCode  from zc_product_product a\n" +
            "    left JOIN zc_check_card b  on a.check_card_id2=b.id where a.check_card_id=?1",nativeQuery=true)
    List<Map>  selectProductP(Long pid);


    @Query(value = " select a.id,check_card_id as checkCardId,a.product,a.materia_batch_id as materiaBarchId,a.materia_name as materiaName,b.batch_number as batchNumber,number from zc_product_batch a\n" +
            "left JOIN zc_materia_batch b  on a.materia_batch_id=b.id  where a.check_card_id=?1",nativeQuery=true)
    List<Map>  selectProductBatch(Long pid);

    @Query(value = " select  a.id,check_card_id as checkCardId,a.product,a.supplier_id as supplierId,a.supplier_name as supplierName,b.type ,b.supplier_phone as supplierPhone from zc_product_supplier a LEFT JOIN zc_supplier b  on a.supplier_id=b.id\n" +
            "where a.check_card_id=?1",nativeQuery=true)
    List<Map>  selectProductSupplier(Long pid);

    @Query(value = "select  COUNT(is_good ) as 'value',is_good as name from  zc_check a LEFT JOIN zc_check_card b  on a.pid=b.id\n" +
            "where IF (?1!='', b.project_id=?1, 1=1)  and  IF (?2!='',b.product_id=?2, 1=1)  \n" +
            "GROUP BY is_good\n",nativeQuery = true)
   List<Map> selectIsGood(String projectId,String productId);

    @Query(value = "select a.fault_level as name,COUNT(a.fault_level) as 'value'  from zc_unqualified a  LEFT JOIN zc_check b on a.check_id=b.id\n" +
            "LEFT JOIN zc_check_card c on b.pid=c.id\n" +
            "where IF (?1!='', project_id=?1, 1=1)  and  IF (?2!='', product_id=?2, 1=1)\n" +
            "GROUP BY a.fault_level ",nativeQuery = true)
    List<Map> selectUnqualified(String projectId,String productId);


    @Modifying
    @Transactional
    @Query(value = "INSERT INTO zc_check(pid,no,process,notice,ask,check_method,remark,check_by,check_time,is_good,is_unqualified) VALUES (?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11)",nativeQuery=true)
    Integer InstCheck(Long pid,Integer no,String process,String notice,String ask,String checkMethod, String remark,String checkBy,Long checkTime,Integer isGood,Integer isUnqualified);

    @Modifying
    @Transactional
    @Query(value = "UPDATE  zc_check  set pid=?2,no=?3,process=?4,notice=?5,ask=?6,check_method=?7,remark=?8,check_by=?9,check_time=?10,is_good=?11,is_unqualified=?12 where id=?1",nativeQuery=true)
    Integer UpdateCheck(Long id,Long pid,Integer no,String process,String notice,String ask,String checkMethod,String remark,String checkBy,Long checkTime,Integer isGood,Integer isUnqualified);

    @Query(value = "select id,pid,no,process,notice,ask,check_method as checkMethod,remark ,check_by as checkBy,check_time as checkTime,is_good as isGood,is_unqualified as isUnqualified from zc_check where pid=?1 ",nativeQuery=true)
    List<Map>  selectCheck(Long pid);
}