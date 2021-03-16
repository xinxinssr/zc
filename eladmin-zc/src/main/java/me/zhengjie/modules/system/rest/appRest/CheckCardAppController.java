package me.zhengjie.modules.system.rest.appRest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.system.domain.Check;
import me.zhengjie.modules.system.domain.CheckCard;
import me.zhengjie.modules.system.domain.CheckTemplate;
import me.zhengjie.modules.system.service.CheckCardService;
import me.zhengjie.modules.system.service.CheckService;
import me.zhengjie.modules.system.service.dto.CheckCardDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Api(tags = "/checkCard管理")
@RequestMapping("/app/auth")
public class CheckCardAppController {

    private final CheckCardService checkCardService;
    private final CheckService checkService;

    /**
     * 新增卡片 或修改
     * @param source
     * @return
     */
    @RequestMapping("/insertCheckCard")
    public Map insertCheckCard(@RequestBody JSONObject source){
        Map map=new HashMap();
        JSONArray jsonArray=source.getJSONArray("checkList");
        if(source.getLong("id")==null) {
            CheckCard checkCard = new CheckCard();
            checkCard.setProjectId(source.getLong("projectId"));
            checkCard.setProductId(source.getLong("productId"));
            checkCard.setType(source.getInteger("type"));
            checkCard.setSri(source.getInteger("sri"));
            checkCard.setFactoryCode(source.getString("factoryCode"));
            checkCard.setCreateBy(source.getString("createBy"));
            checkCard.setCreateTime(source.getLong("createTime"));
            checkCard.setApproval(source.getInteger("approval"));
            CheckCardDto checkCardDto = checkCardService.create(checkCard);
        for(int i=0;i<jsonArray.size();i++) {
            Check check = JSON.toJavaObject(jsonArray.getJSONObject(i), Check.class);
            checkService.InstCheck(checkCardDto.getId(),check.getNo(),check.getProcess(),check.getNotice(),check.getAsk(),check.getCheckMethod(),check.getRemark(),check.getCheckBy(),check.getCheckTime(),check.getIsGood(),check.getIsUnqualified());
        }
        }else {
            CheckCard checkCard = new CheckCard();
            checkCard.setUpdateBy(source.getString("updateBy"));
            checkCard.setUpdateTime(source.getLong("updateTime"));
            checkCard.setId(source.getLong("id"));
            checkCardService.update(checkCard);
            for(int i=0;i<jsonArray.size();i++) {
                Check check = JSON.toJavaObject(jsonArray.getJSONObject(i), Check.class);
                checkService.UpdateCheck(check.getId(),source.getLong("id"),check.getNo(),check.getProcess(),check.getNotice(),check.getAsk(),check.getCheckMethod(),check.getRemark(),check.getCheckBy(),check.getCheckTime(),check.getIsGood(),check.getIsUnqualified());
            }
        }
        map.put("success","执行成功");
        return map;
    }

    /**
     * 查询该产品是否已检查
     * @param projectId
     * @param productId
     * @param sri
     * @param factoryCode
     * @param type
     * @return
     */
    @RequestMapping("/selectCheckCard")
    public List<Map> selectCheckCard(Long projectId, Long productId, Integer sri, String factoryCode, Long type){
        Map map=  checkCardService.selectCheckCard(projectId,productId,sri,factoryCode,type);
        return  checkService.selectCheck(Long.parseLong(map.get("id").toString()));
    }

    /**
     * 根据pid 查询检查项
     */
    @RequestMapping("/selectCheckByPid")
    public List<Map> selectCheckByPid(Long pid){
        return  checkService.selectCheck(pid);
    }
    /**
     * 查询待审批审批检查卡片
     */
    @RequestMapping("/selectCheckByApproval")
    public ResponseEntity<Object> query(Long type,Long approval, Pageable pageable) {
        return new ResponseEntity<>(checkCardService.selectByApproval(type,approval,pageable), HttpStatus.OK);
    }

    /**
     * 审批 检查卡片
     */
    @RequestMapping("/updateApproval")
    public Integer updateApproval(Long id, Integer approval, String approvalBy, Long approvalTime){
        return  checkCardService.updateApproval(id,approval,approvalBy,approvalTime);
    }

    @RequestMapping("/selectAllCheckCard")
    public ResponseEntity<Object> selectAllCheckCard(String type, String platformName, String projectId, String productId, String factoryCode, String sri, Pageable pageable) {
        return new ResponseEntity<>(checkCardService.findAlls(type,platformName,projectId,productId,factoryCode,sri,pageable),HttpStatus.OK);
    }
    @RequestMapping("/selectProductP")
    public List<Map> selectProductP(Long pid){
        return  checkService.selectProductP(pid);
    }
    @RequestMapping("/selectProductBatch")
    public List<Map> selectProductBatch(Long pid){
        return  checkService.selectProductBatch(pid);
    }

    @RequestMapping("/selectCheckCardByproductId")
    public List<Map> selectCheckCardByproductId(Long productId){
        return  checkCardService.selectCheckCardByproductId(productId);
    }
}
