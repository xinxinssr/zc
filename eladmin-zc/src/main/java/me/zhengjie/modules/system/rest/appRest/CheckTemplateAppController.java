package me.zhengjie.modules.system.rest.appRest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.system.domain.CheckTemplate;
import me.zhengjie.modules.system.service.CheckCardTemplateService;
import me.zhengjie.modules.system.service.CheckTemplateService;
import me.zhengjie.modules.system.service.dto.CheckCardTemplateQueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Api(tags = "/checkTemplate管理")
@RequestMapping("/app/auth")
public class CheckTemplateAppController {

    private final CheckCardTemplateService checkCardTemplateService;
    private final CheckTemplateService checkTemplateService;

    /**
     * 查询检查模板
     * @param criteria
     * @param pageable
     * @return
     */
    @RequestMapping("/selectCheckCardTemplate")
    public ResponseEntity<Object> query(CheckCardTemplateQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(checkCardTemplateService.queryAll(criteria,pageable), HttpStatus.OK);
    }

    /**
     * 查看模板项
     * @param projectId
     * @param productId
     * @param type
     * @return
     */
    @RequestMapping("/selectCheckTemplate")
    public List<Map> selectCheckTemplate(Long projectId, Long productId, Long type){
        Long pid=checkCardTemplateService.selectCheckCardId(projectId,productId,type);
        List<Map> CheckTemplateList=checkCardTemplateService.selectTemplateByPid(pid);
        return  CheckTemplateList;
    }

    /**
     * 根据pid查询模板项
     * @param pid
     * @return
     */
    @RequestMapping("/selectCheckTemplateByPid")
    public List<Map> selectCheckTemplateByPid(Long pid){
        return  checkCardTemplateService.selectTemplateByPid(pid);
    }
    /**
     * 查看质量要求图片
     * @param pid
     * @return
     */
    @RequestMapping("/selectAccessory")
    public List<Map> selectAccessory(Long pid){
        return  checkTemplateService.selectAccessory(pid);
    }

}
