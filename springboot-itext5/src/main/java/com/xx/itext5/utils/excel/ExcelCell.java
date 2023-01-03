package com.xx.itext5.utils.excel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Excel单元格
 * @author yema
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ExcelCell {
    /**
     * 列号
     * @return
     */
    String column();

    /**
     * 行号
     * @return
     */
    int row() default 0;
    int width() default 0;
    boolean autoSize() default false;

    /**
     * 格式化
     * @return
     */
    Class<? extends AbstractExcelFormat> format() default AbstractExcelFormat.AbstractNoneFormat.class;
}
