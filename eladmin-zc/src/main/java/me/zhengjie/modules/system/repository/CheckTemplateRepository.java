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

import me.zhengjie.modules.system.domain.CheckTemplate;
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
 * @date 2021-02-23
 **/
public interface CheckTemplateRepository extends JpaRepository<CheckTemplate, Long>, JpaSpecificationExecutor<CheckTemplate> {

    @Query(value = "select a.id,a.pid,a.no,a.process,a.notice,a.ask,a.check_method as checkMethod,a.remark,b.project,b.product,b.type from zc_check_template a\n" +
            " left join  zc_check_card_template b  on a.pid=b.id  WHERE  IF (?1 is not null, b.type=?1, 1=1)and IF (?2!='', b.project_id=?2, 1=1) and IF (?3!='', b.product_id=?3, 1=1)",
            countQuery =  "select count(a.id) from zc_check_template a\n" +
                    " left join  zc_check_card_template b  on a.pid=b.id  WHERE  IF (?1 is not null, b.type=?1, 1=1)  and IF (?2!='', b.project_id=?2, 1=1) and IF (?3!='', b.product_id=?3, 1=1)",
            nativeQuery = true)
    Page<Map> findAlls(Long type,Long projectId,Long productId, Pageable pageable);


    @Query(value = "select id,name,project,product from  zc_check_card_template where type=?1",nativeQuery=true)
    List<Map> selectCheckCardTemplate(Long type);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO zc_accessory (pid,tablename,name,path,upload_time,upload_by)VALUES(?1,'zc_check_template',?2,?3,?4,?5)",nativeQuery=true)
    Integer InstAccessory(Long pid,String name,String path,Long uploadTime,String uploadBy);


    @Query(value = "select id,name,path,upload_time as uploadTime,upload_by as uploadBy from zc_accessory where tablename='zc_check_template'  and pid=?1",nativeQuery=true)
    List<Map> selectAccessory(Long pid);


    @Modifying
    @Transactional
    @Query(value = "delete from zc_accessory where id=?1",nativeQuery=true)
    void deleteCheckTemplate(Long id);

}