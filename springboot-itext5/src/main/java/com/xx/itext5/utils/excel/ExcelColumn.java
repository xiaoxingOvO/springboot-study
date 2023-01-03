package com.xx.itext5.utils.excel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Excel列
 * @author yema
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ExcelColumn {
    /**
     * 列名称
     * @return
     */
    String name();

    /**
     * 列号
     * @return
     */
    String column();

    /**
     * 格式化
     * @return
     */
    Class<? extends AbstractExcelFormat> format() default AbstractExcelFormat.AbstractNoneFormat.class;
}
