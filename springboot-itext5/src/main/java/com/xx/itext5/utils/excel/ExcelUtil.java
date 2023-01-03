package com.xx.itext5.utils.excel;

import com.xx.itext5.utils.pdf.Excel2Pdf;
import com.xx.itext5.utils.pdf.ExcelObject;
import com.google.common.collect.ImmutableList;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.Units;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.helpers.XSSFRowShifter;
import org.slf4j.Logger;
import java.io.*;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import static org.apache.commons.compress.utils.Lists.newArrayList;

/**
 * @author xx
 * @date 2023/1/3
 */
public class ExcelUtil {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ExcelUtil.class);
    private static final String SUPPLIER_OSS_PREFIX = "https://v2cs-oss.oss-cn-beijing.aliyuncs.com/";
    private static final String BUYER_OSS_PREFIX = "https://ggjc-oss.oss-cn-chengdu.aliyuncs.com/perfomance/";

    private static boolean test(Field field) {
        ExcelCell attr = field.getAnnotation(ExcelCell.class);
        return attr.format() == AbstractExcelFormat.UserSignPicFormat.class;
    }

    /**
     * Excel文件类型
     */
    public enum WorkbookType {
        /**
         * xls
         */
        xls,
        /**
         * xlsx
         */
        xlsx
    }

    /**
     * 填充Excel
     *
     * @param input
     * @param sheetName
     * @param form
     * @param output
     * @param <T>
     * @throws RuntimeException
     */
    public static <T> void fillExcel(InputStream input, String sheetName, T form, OutputStream output, boolean ignoreEmpty) {
        try {
            //定义工作簿
            Workbook workbook = new XSSFWorkbook(input);
            //定义工作表
            Sheet sheet = null;
            // 如果指定sheet名,则取指定sheet中的内容.
            if (null != sheetName && !"".equals(sheetName.trim())) {
                sheet = workbook.getSheet(sheetName);
            }
            // 如果传入的sheet名不存在则默认指向第1个sheet.
            if (sheet == null) {
                sheet = workbook.getSheetAt(0);
            }
            fill(workbook, sheet, form, ignoreEmpty);
            try {
                workbook.write(output);
            } catch (IOException e) {
                e.printStackTrace();
                log.error(e.getMessage());
                throw new RuntimeException(e.getMessage());
            } finally {
                try {
                    input.close();
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    log.error(e.getMessage());
                    throw new RuntimeException(e.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    /**
     * 填充Excel
     *
     * @param input
     * @param sheetName
     * @param form
     * @param output
     * @param <T>
     * @throws RuntimeException
     */
    public static <T> void fillExcels(InputStream input, String sheetName, List<T> form, OutputStream output, boolean ignoreEmpty) {
        try {
            //定义工作簿
            Workbook workbook = new XSSFWorkbook(input);
            form.forEach(r -> {
                //定义工作表
                Sheet sheet = null;
                // 如果指定sheet名,则取指定sheet中的内容.
                if (null != sheetName && !"".equals(sheetName.trim())) {
                    sheet = workbook.getSheet(sheetName);
                }
                // 如果传入的sheet名不存在则默认指向第1个sheet.
                if (sheet == null) {
                    sheet = workbook.getSheetAt(0);
                }
                try {
                    fill(workbook, sheet, r, ignoreEmpty);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });
            try {
                workbook.write(output);
            } catch (IOException e) {
                e.printStackTrace();
                log.error(e.getMessage());
                throw new RuntimeException(e.getMessage());
            } finally {
                try {
                    input.close();
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    log.error(e.getMessage());
                    throw new RuntimeException(e.getMessage());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }





    public static <T> void fillExcel2Pdf(InputStream input, String sheetName, T form, OutputStream output, boolean ignoreEmpty) {
        try {
//            log.info("PDF数据打印   ----{} ----",JsonUtils.toJsonString(form));
            //定义工作簿
            Workbook workbook = new XSSFWorkbook(input);
            Sheet sheet = null;
            if (null != sheetName && !"".equals(sheetName.trim())) {
                sheet = workbook.getSheet(sheetName);
            }
            if (sheet == null) {
                sheet = workbook.getSheetAt(0);
            }
            fill(workbook, sheet, form, false);
            try {
//                workbook.write(output);
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                workbook.write(os);
                ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
                os.flush();
                os.close();
                ZipSecureFile.setMinInflateRatio(-1.0d);
                Excel2Pdf excel2Pdf = new Excel2Pdf(
                        Collections.singletonList(new ExcelObject("sheet1",is )), output);
                excel2Pdf.convert();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
                log.error(e.getMessage());
                throw new RuntimeException(e.getMessage());
            } finally {
                try {
                    input.close();
                    workbook.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error(e.getMessage());
                    throw new RuntimeException(e.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    /**
     *
     * @param input
     * @param sheetName
     * @param form
     * @param output
     * @param ignoreEmpty
     * @param <T>
     */
    public static <T> void fillAllExcel2Pdf(InputStream input,String sheetName, List<T> form, OutputStream output, boolean ignoreEmpty) {
        try {
            //定义工作簿
            Workbook workbook = new XSSFWorkbook(input);
            Sheet sheet = null;
            if (null != sheetName && !"".equals(sheetName.trim())) {
                sheet = workbook.getSheet(sheetName);
            }
            if (sheet == null) {
                sheet = workbook.getSheetAt(0);
            }
            Sheet finalSheet = sheet;
            List<ExcelObject> excelObjects = newArrayList();
            AtomicReference<ByteArrayInputStream> is = new AtomicReference<>();
            try {
                form.forEach(f->{
                    try {
                        fill(workbook, finalSheet, f, false);
//                        workbook.write(output);
                        ByteArrayOutputStream os = new ByteArrayOutputStream();
                        workbook.write(os);
                        is.set(new ByteArrayInputStream(os.toByteArray()));
                        os.flush();
                        os.close();
                        ZipSecureFile.setMinInflateRatio(-1.0d);
                        excelObjects.add(new ExcelObject("sheet1", is.get()));
                    } catch (IllegalAccessException | IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                Excel2Pdf excel2Pdf = new Excel2Pdf(excelObjects
                        , output);
                excel2Pdf.convert();

            } catch (IOException e) {
                e.printStackTrace();
                log.error(e.getMessage());
                throw new RuntimeException(e.getMessage());
            } finally {
                try {
                    is.get().close();
                    input.close();
                    workbook.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error(e.getMessage());
                    throw new RuntimeException(e.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    /**
     * 填充Excel
     *
     * @param sheet
     * @param form
     * @param <T>
     * @throws IllegalAccessException
     */
    private static <T> void fill(Workbook workbook, Sheet sheet, T form, boolean ignoreEmpty) throws IllegalAccessException, FileNotFoundException {
        fill(workbook, sheet, form, null, ignoreEmpty);
    }

    /**
     * 填充Excel
     *
     * @param sheet
     * @param form
     */
    private static <T> void fill(Workbook workbook, Sheet sheet, T form, Integer fixedRow, boolean ignoreEmpty) throws IllegalAccessException, FileNotFoundException {
        List<Field> excelCellFiled = getMappedFiled(form.getClass(), ExcelCell.class, null);
        List<Field> excelRowFiled = getMappedFiled(form.getClass(), ExcelRow.class, null);
        if (null != form) {
            for (int i = 0; i < excelCellFiled.size(); i++) {
                Field field = excelCellFiled.get(i);
                field.setAccessible(true);
                ExcelCell excelCell = field.getAnnotation(ExcelCell.class);
                Row row = null;
                if (fixedRow == null) {
                    row = sheet.getRow(getExcelRow(excelCell.row()));
                    if (row == null) {
                        row = sheet.createRow(getExcelRow(excelCell.row()));
                    }
                } else {
                    row = sheet.getRow(getExcelRow(fixedRow));
                    if (row == null) {
                        row = sheet.createRow(getExcelRow(fixedRow));
                    }
                }

                Object obj = field.get(form);
                String cellValue = "";
                if (null != obj) {
                    Class<?> fieldType = field.getType();
                    if (fieldType != Date.class) {
                        cellValue = String.valueOf(obj);
                    } else {
                        try {
                            cellValue = DateFormatUtils.format((Date) obj, "yyyy-MM-dd HH:mm:ss");
                        } catch (Exception e) {
                            e.printStackTrace();
                            log.error(e.getMessage());
                            throw new RuntimeException(e.getMessage());
                        }
                    }
                    if (excelCell.format() != AbstractExcelFormat.AbstractNoneFormat.class) {
                        try {
                            cellValue = excelCell.format().newInstance().print(cellValue);
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                            log.error(e.getMessage());
                            throw new RuntimeException(e.getMessage());
                        }
                    }
                }
                if (StringUtils.isNotBlank(cellValue) &&
                        excelCell.format() != AbstractExcelFormat.UserSignPicFormat.class) {
                    Cell cell = row.getCell(getExcelCol(excelCell.column()));
                    if (cell == null) {
                        cell = row.createCell(getExcelCol(excelCell.column()));
                    }
                    cell.setCellValue(cellValue);
                }
                if (excelCell.width() != 0){
                    sheet.setColumnWidth(getExcelCol(excelCell.column()),excelCell.width());
                }else if (excelCell.autoSize()){
                    sheet.autoSizeColumn(getExcelCol(excelCell.column()));
                }
            }
            int moveNum = 0;
            if (null != excelRowFiled && !excelRowFiled.isEmpty()) {
                int pos = 0;
                for (int i = 0; i < excelRowFiled.size(); i++) {
                    Field field = excelRowFiled.get(i);
                    field.setAccessible(true);
                    ExcelRow attr = field.getAnnotation(ExcelRow.class);
                    List<Object> detailRows = (List<Object>) field.get(form);
                    if (null == detailRows || detailRows.isEmpty()) {
                        continue;
                    }
                    int rowNum = attr.row() + pos;
                    int lastRowNum = sheet.getLastRowNum();
                    if (detailRows.size() > 1) {
                        //移动rowNum以下的行整体向下移动
                        mvRows(sheet, rowNum, lastRowNum + 1, detailRows.size() - 1);
                        copyRow(sheet, rowNum, rowNum + 1, detailRows.size() - 1);
                        moveNum = detailRows.size() - 1;
                    }
                    for (int j = 0; j < detailRows.size(); j++) {
                        fill(workbook, sheet, detailRows.get(j), rowNum + j, ignoreEmpty);
                    }
                    pos += detailRows.size() - 1;
                }
            }

//            if ( form.getClass() == DeliveryExcel.class) {
//                //收货单位签字确认
//                sheet.addMergedRegion(new CellRangeAddress(6 + moveNum, 8 + moveNum, 0, 0));
//                //收货单位签字处
//                sheet.addMergedRegion(new CellRangeAddress(6 + moveNum, 8 + moveNum, 1, 2));
//                //发货单位签字确认
//                sheet.addMergedRegion(new CellRangeAddress(6 + moveNum, 8 + moveNum, 3, 3));
//                //发货单位签字处
//                sheet.addMergedRegion(new CellRangeAddress(6 + moveNum, 8 + moveNum, 4, 5));
//            }
            List<Field> picField = excelCellFiled.stream().filter(ExcelUtil::test).collect(Collectors.toList());
            for (Field field : picField) {
                ExcelCell attr = field.getAnnotation(ExcelCell.class);
                Object obj = field.get(form);
                if (null != obj) {
                    String cellValue = String.valueOf(obj);
                    if (StringUtils.isNotBlank(cellValue)) {
                        //获取行列数据
                        HashMap<String,Integer> anchorNum = getAnchorNum(attr,moveNum,form);
//                        System.out.println("锚点数据++++++++++++++++>" + JsonUtils.toJsonString(anchorNum));
                        //签名
                        try {
                            InputStream inStream;
                            try {
                                URL url = new URL(SUPPLIER_OSS_PREFIX+cellValue);
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setRequestMethod("GET");
                                conn.setConnectTimeout(5 * 1000);
                                inStream = conn.getInputStream();
                            }catch (Exception e){
                                e.printStackTrace();
                                URL url = new URL(BUYER_OSS_PREFIX+cellValue);
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setRequestMethod("GET");
                                conn.setConnectTimeout(5 * 1000);
                                inStream = conn.getInputStream();
                            }
                            int  picture = 0;
                            if (cellValue.contains("jpg")||cellValue.contains("jpeg")){
                                picture = workbook.addPicture(IOUtils.toByteArray(inStream), HSSFWorkbook.PICTURE_TYPE_JPEG);
                            }else if (cellValue.contains("png")){
                                picture = workbook.addPicture(IOUtils.toByteArray(inStream), HSSFWorkbook.PICTURE_TYPE_PNG);
                            }
                            inStream.close();
                            CreationHelper creationHelper = workbook.getCreationHelper();
                            ClientAnchor clientAnchor = creationHelper.createClientAnchor();
                            clientAnchor.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);
                            clientAnchor.setDx1(0);
                            clientAnchor.setDy1(0);
                            clientAnchor.setDx2(255* Units.EMU_PER_PIXEL);
                            clientAnchor.setDy2(255*Units.EMU_PER_PIXEL);
                            clientAnchor.setRow1(anchorNum.get("ro1"));
                            clientAnchor.setCol1(anchorNum.get("co1"));
                            clientAnchor.setRow2(anchorNum.get("ro2"));
                            clientAnchor.setCol2(anchorNum.get("co2"));
                            Drawing drawing = sheet.createDrawingPatriarch();
                            drawing.createPicture(clientAnchor, picture);
                            log.error("drawing.createPicture++++++++++++++++++++");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private static <T> HashMap<String, Integer>getAnchorNum(ExcelCell attr, int moveNum, T form) {
        Integer cellColumnNumber = getCellColumnNumber(attr.column());
        HashMap<String, Integer> result = new HashMap<>();
        result.put("co1",cellColumnNumber - 1);
        result.put("ro1",attr.row() + moveNum - 1);
        result.put("co2",cellColumnNumber);
        result.put("ro2",attr.row() + moveNum - 1);
//        if (form.getClass()==DeliveryExcel.class){
//            result.put("co1",cellColumnNumber - 1);
//            result.put("ro1",attr.row() + moveNum - 1);
//            result.put("co2",cellColumnNumber);
//            result.put("ro2",attr.row() + moveNum + 1);
//        }else {
//
//        }
        return result;
    }

    private static final ImmutableList<Character> xlsCellColumnList = ImmutableList.of('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');

    private static final Integer COLUMNS_LETTER_SIZE = 26;

    public static Integer getCellColumnNumber(String columnAddress) {
        //数据反转后才是数据正确的位子
        columnAddress = StringUtils.reverse(columnAddress);
        char[] chars = columnAddress.toCharArray();
        int result = 0;
        for (int i = 0; i < chars.length; i++) {
            if (i == 0) {
                result = result + xlsCellColumnList.indexOf(chars[i]);
            } else {
                result = result + (xlsCellColumnList.indexOf(chars[i]) + 1) * Double.valueOf(Math.pow(COLUMNS_LETTER_SIZE, i)).intValue();
            }
        }
        return result + 1;
    }

    /**
     * 将EXCEL中A, B, C, D, E列映射成0, 1, 2, 3
     *
     * @param col
     * @return
     */
    private static int getExcelCol(String col) {
        col = col.toUpperCase();
        // 从-1开始计算,字母重1开始运算。这种总数下来算数正好相同。
        int count = -1;
        char[] cs = col.toCharArray();
        for (int i = 0; i < cs.length; i++) {
            count += (cs[i] - 64) * Math.pow(26, cs.length - 1 - i);
        }
        return count;
    }

    /**
     * 映射row
     *
     * @param row
     * @return
     */
    private static int getExcelRow(int row) {
        return row - 1;
    }

    /**
     * 获取注解的属性包含所有父类中的属性
     *
     * @param clazz
     * @param target
     * @param fields
     * @return
     */
    private static List<Field> getMappedFiled(Class<?> clazz, Class target, List<Field> fields) {
        if (fields == null) {
            fields = new ArrayList<Field>();
        }
        // 得到所有定义的属性
        Field[] allFields = clazz.getDeclaredFields();
        // 获取ExcelColumn 注解的field
        for (Field field : allFields) {
            if (field.isAnnotationPresent(target)) {
                fields.add(field);
            }
        }
        // 获取父类中注解的属性
        if (clazz.getSuperclass() != null && !clazz.getSuperclass().equals(Object.class)) {
            getMappedFiled(clazz.getSuperclass(), target, fields);
        }
        return fields;
    }

    /**
     * copy row
     *
     * @param sheet
     * @param sourceRowNum
     * @param targetRowNum
     * @param number
     */
    private static void copyRow(Sheet sheet, int sourceRowNum, int targetRowNum, int number) {
        //复制模板行pos所在行
        for (int j = 0; j < number; j++) {
            if (j < number) {
                new XSSFRowShifter((XSSFSheet) sheet).shiftMergedRegions(getExcelRow(sourceRowNum), getExcelRow(sourceRowNum + j), 1);
            }
            XSSFRow xssfRow = null;
            Row row = sheet.getRow(getExcelRow(targetRowNum + j));
            if (null == row) {
                xssfRow = (XSSFRow) sheet.createRow(getExcelRow(targetRowNum + j));
            } else {
                xssfRow = (XSSFRow) row;
            }
            xssfRow.copyRowFrom(sheet.getRow(getExcelRow(sourceRowNum)), new CellCopyPolicy.Builder().condenseRows(true).build());
        }
    }

    /**
     * 移动行
     *
     * @param sheet      　页签
     * @param rowNum     移动区域启始行
     * @param lastRowNum 移动区域结束行
     * @param rows       位移量（行）
     */
    private static void mvRows(Sheet sheet, int rowNum, int lastRowNum, Integer rows) {
        for (int j = lastRowNum; j > rowNum; j--) {
            Row row = sheet.getRow(getExcelRow(j));
            if (null != row) {
                int targetRowNum = getExcelRow(j + rows);
                XSSFRow xssfRow = null;
                Row targetRow = sheet.getRow(targetRowNum);
                if (null == targetRow) {
                    targetRow = sheet.createRow(targetRowNum);
                }
                xssfRow = (XSSFRow) targetRow;

                xssfRow.copyRowFrom(row, new CellCopyPolicy());
                //移除合并单元格
                List<CellRangeAddress> cellRangeAddresses = sheet.getMergedRegions();
                CellRangeAddress cellAddresses = new CellRangeAddress(
                        row.getRowNum(),
                        row.getRowNum(),
                        row.getFirstCellNum(),
                        row.getLastCellNum()
                );
                for (int k = cellRangeAddresses.size() - 1; k >= 0; k--) {
                    if (cellRangeAddresses.get(k).intersects(cellAddresses)) {
                        sheet.removeMergedRegion(k);
                    }
                }
                //删除行内容
                sheet.removeRow(row);
            }
        }
    }

}
