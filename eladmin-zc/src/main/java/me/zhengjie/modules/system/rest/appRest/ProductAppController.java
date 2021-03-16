package me.zhengjie.modules.system.rest.appRest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.system.service.ProductService;
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
@Api(tags = "/product管理")
@RequestMapping("/app/auth")
public class ProductAppController {
    private final ProductService productService;

    @RequestMapping("/selectAllProject")
    public List<Map> selectAllProject(Long type){
        return  productService.selectAllProject(type);
    }
    @RequestMapping("/selectAllProduct")
    public  List<Map>  selectAllProduct(String projectId){
        return  productService.selectAllProduct(projectId);
    }

    @GetMapping("/selectAllProduct")
    @Log("查询/product")
    @ApiOperation("查询/product")
    public ResponseEntity<Object> query(String type, String platformName, String projectId, String name, Pageable pageable){
        if(type==null){
            return new ResponseEntity<>(productService.queryAlls("0", platformName, projectId, name,pageable), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(productService.queryAlls(type, platformName, projectId, name,pageable), HttpStatus.OK);
        }
    }
}
