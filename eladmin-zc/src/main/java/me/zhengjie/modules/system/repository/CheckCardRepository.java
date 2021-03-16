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

import me.zhengjie.modules.system.domain.CheckCard;
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
public interface CheckCardRepository extends JpaRepository<CheckCard, Long>, JpaSpecificationExecutor<CheckCard> {

    @Query(value = "select a.id, a.name,a.project_id as projectId,a.product_id as productId,a.factory_code as factoryCode,a.sri,a.type,a.create_by as createBy,a.create_time as createTime,\n" +
            "a.update_by as updateBy,a.update_time as updateTime,a.approval,a.approval_by as approvalBy,a.approval_time as approvalTime,b.name as projectName,c.name as productName,c.platform_name as platformName,c.product_code as productCode  from zc_check_card a LEFT JOIN zc_project b on  a.project_id=b.id\n" +
            "LEFT JOIN zc_product c on a.product_id= c.id  where IF (?1!='', a.type=?1, 1=1)  and IF(?2!='',c.platform_name=?2,1=1)\n" +
            "and IF(?3!='',a.project_id=?3,1=1) and IF(?4!='',a.product_id=?4,1=1) and IF(?5!='',a.factory_code=?5,1=1) and IF(?6!='',a.sri=?6,1=1)",
            countQuery = "select count(a.id) from zc_check_card a LEFT JOIN zc_project b on  a.project_id=b.id \n" +
                    "LEFT JOIN zc_product c on a.product_id= c.id where IF (?1!='', a.type=?1, 1=1)  and IF(?2!='',c.platform_name=?2,1=1)\n" +
                    "and IF(?3!='',a.project_id=?3,1=1) and IF(?4!='',a.product_id=?4,1=1) and IF(?5!='',a.factory_code=?5,1=1) and IF(?6!='',a.sri=?6,1=1)",
            nativeQuery = true)
    Page<Map> findAlls(String type, String platformName, String projectId, String productId,String factoryCode,String sri, Pageable pageable);

    @Query(value = "select id from zc_check_card where project_id=?1 and product_id=?2 and sri=?3 and factory_code=?4 and type=?5" ,nativeQuery=true)
   Map selectCheckCard(Long projectId,Long productId,Integer sri,String factoryCode,Long type);


    @Query(value = "select a.id, a.name,a.project_id as projectId,a.product_id as productId,a.factory_code as factoryCode,a.sri,a.type,a.create_by as createBy,a.create_time as createTime,\n" +
            " a.update_by as updateBy,a.update_time as updateTime,a.approval,a.approval_by as approvalBy,a.approval_time as approvalTime,b.name as projectName,c.name as productName,c.platform_name as platformName,c.product_code as productCode  \n" +
            "from zc_check_card a LEFT JOIN zc_project b on  a.project_id=b.id   LEFT JOIN zc_product c on a.product_id= c.id where  IF (?1 is not null, a.type=?1, 1=1) and IF(?2 is not null,a.approval=?2,2=2)\n",
            countQuery ="select  count(a.id) from zc_check_card a LEFT JOIN zc_project b on  a.project_id=b.id \n" +
                    "  LEFT JOIN zc_product c on a.product_id= c.id where  IF (?1 is not null, a.type=?1, 1=1) and IF(?2 is not null,a.approval=?2,2=2)",
            nativeQuery=true)
    Page<Map> selectByApproval(Long type,Long approval,Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "UPDATE  zc_check_card  set approval=?2,approval_by=?3,approval_time=?4 where id=?1",nativeQuery=true)
    Integer updateApproval(Long id,Integer approval,String approvalBy,Long approvalTime);

    @Query(value = "select id,factory_code as factoryCode,sri from zc_check_card  where product_id=?1" ,nativeQuery=true)
    List<Map> selectCheckCardByproductId(Long productId);

}