package me.zhengjie.modules.system.rest.appRest;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.AnonymousAccess;
import me.zhengjie.annotation.rest.AnonymousPostMapping;
import me.zhengjie.modules.system.service.SupplierService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/app/auth")
@RequiredArgsConstructor
public class SupplierAppController {

    private final SupplierService supplierService;
    @ApiOperation("查询所有供应商")
    @AnonymousPostMapping(value ="/selectAll")
    public List<Map> selectAll(Long type) {
        return supplierService.selectAll(type);

    }
}
