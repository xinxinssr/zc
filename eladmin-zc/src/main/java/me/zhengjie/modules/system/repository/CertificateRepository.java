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

import me.zhengjie.modules.system.domain.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
* @website https://el-admin.vip
* @author ly
* @date 2021-02-04
**/
public interface CertificateRepository extends JpaRepository<Certificate, Long>, JpaSpecificationExecutor<Certificate> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO zc_certificate(supplier_id,name,path,type_id,type_name,start_time,end_time,upload_time,create_time,create_by) VALUES (?1,?2,?3,?4,?5,?6,?7,?8,?9,?10)",nativeQuery=true)
    Integer InstCertificate(String supplierId,String name,String path,String typeId,String typeName,String startTime,String endTime,String uploadTime,String creatTime,String creatBy);

    @Modifying
    @Transactional
    @Query(value = "UPDATE  zc_certificate  set name=?2,path=?3,type_id=?4,type_name=?5,start_time=?6,end_time=?7,upload_time=?8,update_time=?9,update_By=?10 where id=?1",nativeQuery=true)
    Integer UpdateCertificate(String Id,String name,String path,String typeId,String typeName,String startTime,String endTime,String uploadTime,String updateTime,String updateBy);
}