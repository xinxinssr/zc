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

import me.zhengjie.modules.system.domain.Product;
import me.zhengjie.modules.system.domain.Project;
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
* @date 2021-02-19
**/
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {


    @Query(value = "select a.id,a.platform_name as platformName,b.name as projectName,a.name,a.product_code as productCode,\n" +
            "a.specification,a.type,a.create_by as createBy,a.create_time as createTime,a.update_by as updateBy,a.update_time as updateTime\n" +
            " from zc_product a LEFT JOIN zc_project b on a.project_id=b.id WHERE  IF (?1!='', a.type=?1, 1=1)  and IF(?2!='',a.platform_name=?2,1=1)\n" +
            "and IF(?3!='',b.id=?3,1=1) and IF(?4!='',a.name=?4,1=1) ",
            countQuery = "select COUNT(a.id)\n" +
                    " from zc_product a LEFT JOIN zc_project b on a.project_id=b.id where IF (?1!='', a.type=?1, 1=1)  and IF(?2!='',a.platform_name=?2,1=1)\n" +
                    "and IF(?3!='',b.id=?3,1=1) and IF(?4!='',a.name=?4,1=1)",
            nativeQuery = true)
    Page<Map> findAlls(String type,String platformName,String projectId,String name, Pageable pageable);


    @Modifying
    @Transactional
    @Query(value = "INSERT INTO zc_project(type,name,create_time,CREATE_by) VALUES (?1,?2,?3,?4)",nativeQuery=true)
    Integer InstProject(Long type,String name,Long creatTime,String creatBy);


    @Modifying
    @Transactional
    @Query(value = "update zc_product set project_id=?2,name=?3,platform_name=?4,product_code=?5,specification=?6,UPDATE_time=?7 ,update_by=?8 where id =?1",nativeQuery=true)
    Integer updateProduct(Long id,Long projectId,String name,String platformName,String productCode,String specification, Long updateTime,String updateBy);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO zc_product(type,project_id,name,platform_name,product_code,specification,create_time,create_by) VALUES (?1,?2,?3,?4,?5,?6,?7,?8)",nativeQuery=true)
    Integer InstProduct(Long type,Long projectId,String name,String platformName,String productCode,String specification, Long creatTime,String creatBy);

    @Query(value = "select * from zc_project where name=?1 and type=?2",nativeQuery=true)
    Map selectByname(String name,Long type);

    @Query(value = "select id,name from zc_project where IF(?1 is not null ,type=?1,1=1)",nativeQuery=true)
    List<Map> selectAllProject(Long type);
    @Query(value = "select id,name,product_code as productCode from zc_product where IF(?1!='',project_id=?1,1=1)",nativeQuery=true)
    List<Map> selectAllProduct(String projectId);
    @Query(value = "select id from zc_project where type=?1 and name=?2",nativeQuery=true)
    Long selectProjectByName(Long type,String name);
    @Query(value = "select id from zc_product where project_id=?1 and name=?2",nativeQuery=true)
    Long selectProductByName(Long id,String name);
    @Query(value = "select id,name,product_code as productCode from zc_product group by name",nativeQuery=true)
    List<Map> selectAllProduct2();
    @Query(value = "select id,name from zc_project group by name",nativeQuery=true)
    List<Map> selectAllProject2();
}