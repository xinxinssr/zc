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

import me.zhengjie.modules.system.domain.CheckCardTemplate;
import me.zhengjie.modules.system.domain.CheckTemplate;
import me.zhengjie.modules.system.service.dto.CheckTemplateDto;
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
* @date 2021-02-22
**/
public interface CheckCardTemplateRepository extends JpaRepository<CheckCardTemplate, Long>, JpaSpecificationExecutor<CheckCardTemplate> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO zc_check_card_template(type,project_id,project,product_id,product,name,create_time,create_by) VALUES (?1,?2,?3,?4,?5,?6,?7,?8)",nativeQuery=true)
    Integer InstCheckCard(Long type,Long projectId,String project,Long productId,String product,String name, Long createTime,String createBy);

    @Modifying
    @Transactional
    @Query(value = "UPDATE  zc_check_card_template  set type=?2,project_id=?3,project=?4,product_id =?5,product=?6,name=?7,update_time=?8,update_By=?9 where id=?1",nativeQuery=true)
    Integer updateCheckCard(Long id,Long type,Long projectId,String project,Long productId,String product,String name, Long createTime,String createBy);
    //根据名字查询最新插入的卡片模板的id
    @Query(value = "select id from zc_check_card_template  where name = ?1 order by create_time desc limit 1",nativeQuery=true)
    Long selectCheckCard(String name);

    @Query(value = "select id,pid,no,process,notice,ask,check_method as checkMethod,remark from zc_check_template where pid=?1",nativeQuery=true)
    List<Map> selectTemplateByPid(Long pid);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO zc_check_template (pid,no,process,notice,ask,check_method,remark) VALUES (?1,?2,?3,?4,?5,?6,?7)",nativeQuery=true)
    Integer InstCheckTemplate(Long pid,Integer no,String process,String notice,String ask,String checkMethod, String remark);

    @Query(value = "select id from  zc_check_card_template where project_id=?1 and product_id=?2 and type=?3",nativeQuery=true)
    Long selectCheckCardId(Long projectId,Long productId,Long type);

    @Modifying
    @Transactional
    @Query(value = "delete from zc_check_template where id=?1",nativeQuery=true)
    void deleteCheckTemplate(Long id);
    @Query(value = "select a.id,a.pid,b.name,a.no,a.process,a.notice,a.ask,a.check_method as checkMethod,a.remark,b.project,b.product,b.type from zc_check_template a\n" +
            " left join  zc_check_card_template b  on a.pid=b.id  WHERE  a.id=?1",
            nativeQuery = true)
    Map findCheckTemplate(Long id);

}