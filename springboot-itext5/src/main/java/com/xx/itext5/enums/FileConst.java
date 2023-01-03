package com.xx.itext5.enums;

/**
 * 文件相关常量
 *
 * @author yema
 */
public class FileConst {
    /**
     * excel文件扩展名
     */
    public static final String EXCEL_EXTENSION = ".xlsx";

    /**
     * UTF_8
     */
    public static final String UTF_8 = "utf-8";

    /**
     * 设置数据存放工作表
     */
    public static final String SHEET_NAME = "sheet1";

    /**
     * B1 模板
     */
    public static final String Student = "Student";
    /**
     * B1 模板
     */
    public static final String Student_PATH = "Student".concat(EXCEL_EXTENSION);


    /**
     * PDF2文件扩展名
     */
    public static final String PDF_EXTENSION = ".pdf";
    public static final String ATTACHMENT = "attachment; filename*=utf-8'zh_cn'";
    public static final String FILE_NAME = "filename*=utf-8'zh_cn'";
    public static final String CONTENT_DISPOSITION = "Content-Disposition";
    public static final String APPLICATION_PDF = "application/pdf; charset=utf-8";
    public static final String APPLICATION_EXCEL = "application/vnd.ms-excel; charset=utf-8";

    public static final String SCRIPT_PREFIX = "<script>";
    public static final String SCRIPT_SUFFIX = "</script>";
    public static final String STRING_FORMAT = "('%s')";
    public static final String TABLE = "table";
    public static final String TABLE_TAG = "</table>";

    public static final String TRANSFER_FORM = "excel/调拨单.xlsx";
    public static final String STORAGE_FORM = "excel/入库单.xlsx";
    public static final String BACK_STORAGE_FORM = "excel/退库单.xlsx";
    public static final String OUT_STORAGE_FORM = "excel/出库单.xlsx";
    public static final String REJECT_FORM = "excel/退货单.xlsx";
    public static final String DELIVERY_FORM = "excel/送货单.xlsx";

    /**
     * PDF字体
     */
    public static final String PDF_STYLE = "body {font-family: 'SimSun' 'WenQuanYi Zen Hei Mono' 'WenQuanYi Micro Hei' 'WenQuanYi Micro Hei Mono' 'WenQuanYi Zen Hei';}\n@page {size: A1}\ntable {border-collapse: collapse;table-layout: fixed;word-break:break-all;width: 100%;text-align: center;b}\ntd {word-break:break-all;word-wrap : break-word;white-space:pre;}";

}
