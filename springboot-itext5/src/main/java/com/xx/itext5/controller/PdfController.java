package com.xx.itext5.controller;

import com.xx.itext5.utils.StudentUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xx
 * @date 2022/12/30
 */
@Api(value = "pdf打印预览")
@RestController
public class PdfController {

    /**
     * 打印excel模版
     * @return result
     */
    @ApiOperation(value = "打印excel模版", notes = "打印excel模版")
    @GetMapping(value = "/print", produces = "text/html; charset=utf-8")
    public String supplierStoragePdf(HttpServletResponse response,
                                     @PathVariable("studentId") String studentId) {
        return StudentUtil.printStorageOrSupplyPdf(response, studentId);
    }

}
