package me.zhengjie.modules.system.rest.appRest;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.rest.AnonymousPostMapping;
import me.zhengjie.modules.system.domain.ProductBatch;
import me.zhengjie.modules.system.domain.ProductProduct;
import me.zhengjie.modules.system.domain.ProductSupplier;
import me.zhengjie.modules.system.service.CheckService;
import me.zhengjie.modules.system.service.ProductBatchService;
import me.zhengjie.modules.system.service.ProductProductService;
import me.zhengjie.modules.system.service.ProductSupplierService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Api(tags = "/productRelation管理")
@RequestMapping("/app/auth")
public class ProductBarchAppController {

    private final ProductBatchService productBatchService;
    private final ProductProductService productProductService;
    private final ProductSupplierService productSupplierService;
    private final CheckService checkService;


    @RequestMapping(value ="/InstProductP")
    public Map InstProductP(@RequestBody List<ProductProduct> productProductList) {
    return productProductService.InstProductP(productProductList);
    }




    @RequestMapping(value ="/InstProductBatch")
    public Map InstProductBatch(@RequestBody  List<ProductBatch> productBatchList) {
        return productBatchService.InstProductBatch(productBatchList);
    }



    @RequestMapping(value ="/InstProductSupplier")
    public Map InstProductSupplier(@RequestBody  List<ProductSupplier> productSupplierList) {
        return productSupplierService.InstProductSupplier(productSupplierList);
    }
    @RequestMapping("/selectProductP")
    public List<Map> selectProductP(Long pid){
        return  checkService.selectProductP(pid);
    }
    @RequestMapping("/selectProductBatch")
    public List<Map> selectProductBatch(Long pid){

        return  checkService.selectProductBatch(pid);
    }

    @RequestMapping("/selectProductSupplier")
    public List<Map> selectProductSupplier(Long pid){
        return  checkService.selectProductSupplier(pid);
    }

}
