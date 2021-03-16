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

import me.zhengjie.modules.system.domain.Unqualified;
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
* @date 2021-03-01
**/
public interface UnqualifiedRepository extends JpaRepository<Unqualified, Long>, JpaSpecificationExecutor<Unqualified> {

    @Query(value = "select  a.id,a.check_id as checkId,a.track_no as trackNo,a.fault_name as faultName,a.fault_level as faultLevel,a.create_by as createBy,a.update_time as updateTime,\n" +
            " a.`describe`,a.is_solve as isSolve,a.solve_by as solveBy,a.solve_time as solveTime,a.solve_describe as solveDescribe,e.name as projectName,d.`name` as productName,i.name as supplierName   from zc_unqualified   a \n" +
            "LEFT JOIN  zc_check b  on a.check_id =b.id\n" +
            "LEFT JOIN zc_check_card c  on b.pid=c.id\n" +
            "LEFT JOIN zc_product d  on c.product_id=d.id\n" +
            "LEFT JOIN zc_project e on c.project_id=e.id \n" +
            "LEFT JOIN zc_supplier i on a.supplier_id=i.id\n" +
            "where IF (?1!='', d.name=?1, 1=1)\n" +
            "and IF(?2='',a.create_by=?2,1=1)  and IF(?3!='',a.fault_name=?3,1=1) and IF(?4!='',a.fault_level= ?4,1=1) and IF(?5!='',d.project_id= ?5,1=1)",
            countQuery = "select  count(a.id)  from zc_unqualified   a \n" +
                    "LEFT JOIN  zc_check b  on a.check_id =b.id\n" +
                    "LEFT JOIN zc_check_card c  on b.pid=c.id \n" +
                    "LEFT JOIN zc_product d  on c.product_id=d.id\n" +
                    "LEFT JOIN zc_project e on c.project_id=e.id \n" +
                    "LEFT JOIN zc_supplier i on a.supplier_id=i.id\n"+
                    "where  IF (?1!='', d.name=?1, 1=1)\n" +
                    "and IF(?2='',a.create_by=?2,1=1)  and IF(?3!='',a.fault_name=?3,1=1) and IF(?4!='',a.fault_level= ?4,1=1) and IF(?5!='',d.project_id= ?5,1=1)",
            nativeQuery = true)
    Page<Map> findAlls(String productName, String createBy, String faultName, String faultLevel,Long projectId, Pageable pageable);

    @Query(value = "select id,create_by as createBy  from zc_unqualified GROUP BY create_by",nativeQuery = true)
    List<Map> findCreateBy();


    @Query(value = "select id,supplier_id as supplierId,check_id as checkId,track_no as trackNo,fault_name as fault_name,fault_level as faultLevel,\n" +
            "create_by as createBy,update_time as updateTime,`describe`,is_solve as isSolve,solve_by as solveBy,\n" +
            "solve_time as solveTime,solve_describe as solveDescribe from zc_unqualified  where check_id=?1",nativeQuery = true)
    List<Map> findByCheckId(Long checkId);

    @Query(value = "select id,name,path,upload_time as uploadTime,upload_by as uploadBy from zc_accessory where tablename='zc_unqualified'  and pid=?1",nativeQuery=true)
    List<Map> selectAccessory(Long pid);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO zc_accessory (pid,tablename,name,path,upload_time,upload_by)VALUES(?1,'zc_unqualified',?2,?3,?4,?5)",nativeQuery=true)
    Integer InstAccessory(Long pid,String name,String path,Long uploadTime,String uploadBy);


    @Query(value = "select a.dict_id,a.description,b.label,b.`value` from sys_dict a  left join  sys_dict_detail b  on  a.dict_id=b.dict_id  where  a.description=?1",nativeQuery=true)
    List<Map> selectDict(String  description);


}