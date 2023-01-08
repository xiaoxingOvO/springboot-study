package com.xx.itext5.utils.pdf;

import com.itextpdf.text.Font;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by cary on 6/15/17.
 */
public class PdfTableExcel {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    protected ExcelObject excelObject;
    protected Excel excel;
    protected boolean setting = false;

    /**
     * <p>Description: Constructor</p>
     *
     * @param excelObject
     */
    public PdfTableExcel(ExcelObject excelObject) {
        this.excelObject = excelObject;
        this.excel = excelObject.getExcel();
    }

    /**
     * <p>Description: 获取转换过的Excel内容Table</p>
     *
     * @return PdfPTable
     * @throws BadElementException
     * @throws MalformedURLException
     * @throws IOException
     */
    public PdfPTable getTable() throws DocumentException, MalformedURLException, IOException {
        Sheet sheet = this.excel.getSheet();
        return toParseContent(sheet);
    }

//    private PdfPCell createPdfPCell(int colIndex, int rowIndex, Cell cell) {
//
//    }

    public static <T> Stream<T> getStreamFromIterator(Iterator<T> iterator) {
        // Convert the iterator to Spliterator
        Spliterator<T>
            spliterator = Spliterators
            .spliteratorUnknownSize(iterator, 0);

        // Get a Sequential Stream from spliterator
        return StreamSupport.stream(spliterator, false);
    }

    protected PdfPTable toParseContent(Sheet sheet)
        throws DocumentException, MalformedURLException, IOException {
        int rows = sheet.getPhysicalNumberOfRows();

        List<Float> width = getStreamFromIterator(sheet.rowIterator())
            .filter(Objects::nonNull)
            .findFirst()
            .map(row -> getStreamFromIterator(row.cellIterator())
                .map(cell -> sheet.getColumnWidthInPixels(cell.getColumnIndex()))
                .collect(Collectors.toList()))
            .orElse(null);

        LinkedList<PdfPCell> cells = new LinkedList<>();
        ArrayList<String> list = new ArrayList<>();
//        Boolean g1Boolean = false;
        for (int i = 0; i < rows; i++) {
            Row row = sheet.getRow(i);
            if (row == null) {
                continue;
            }
            int columns = row.getLastCellNum();

            for (int j = 0; j < columns; j++) {

                //判断是否跳过
                if (passCell(i,j,list)){

                }else {
                    Cell cell = row.getCell(j);

                    if (cell == null) {
                        cell = row.createCell(j);
                    }
                    int cw = getPOIColumnWidth(cell);

                    PdfPCell pdfpCell = new PdfPCell();
                    pdfpCell.setRight(sheet.getColumnWidthInPixels(cell.getColumnIndex()));
                    pdfpCell.setBackgroundColor(new BaseColor(POIUtil.getRGB(cell.getCellStyle().getFillForegroundColorColor())));
                    pdfpCell.setVerticalAlignment(getVAlignByExcel(cell.getCellStyle().getVerticalAlignment().getCode()));
                    pdfpCell.setHorizontalAlignment(getHAlignByExcel(cell.getCellStyle().getAlignment().getCode()));
//                pdfpCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    //设置单元格内容的位置，前后左右等
//                pdfpCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    if (sheet.getDefaultRowHeightInPoints() != row.getHeightInPoints()) {
                        pdfpCell.setFixedHeight(this.getPixelHeight(row.getHeightInPoints()));
                    }

                    //设置第一行第二行字体大小
                    float size = (i == 0) ? 12 : 8;
                    pdfpCell.setPhrase(getPhrase(cell, size));
//                pdfpCell.setPhrase(getPhrase(cell, 8));
                    addImageByPOICell(pdfpCell, cell, cw);
                    addBorderByExcel(pdfpCell, cell);
                    CellRangeAddress range = getColspanRowspanByExcel(row.getRowNum(), cell.getColumnIndex());
                    int rowspan = 1;
                    int colspan = 1;
                    if (range != null) {
                        rowspan = range.getLastRow() - range.getFirstRow() + 1;
                        colspan = range.getLastColumn() - range.getFirstColumn() + 1;
                    }
                    pdfpCell.setColspan(colspan);
                    pdfpCell.setRowspan(rowspan);

                    savePassCell(rowspan,colspan,i,j,list);


                    cells.add(pdfpCell);
                    j += colspan - 1;
                }
            }
        }

        PdfPTable table = new PdfPTable(sheet.getRow(0).getLastCellNum()-sheet.getRow(0).getFirstCellNum());
        table.setWidthPercentage(100);
//        table.setLockedWidth(true);
        if (!CollectionUtils.isEmpty(width)){
            float[] floats = new float[width.size()];
            for (int i = 0; i < width.size(); i++) {
                floats[i] = width.get(i);
            }
            log.info("floats : {}",floats);
            table.setWidths(floats);
        }
        for (PdfPCell pdfpCell : cells) {
            table.addCell(pdfpCell);
        }
        return table;
    }

    protected Phrase getPhrase(Cell cell,float size) {
        if (this.setting || this.excelObject.getAnchorName() == null) {
            return new Phrase(String.valueOf(getCellValue(cell)), getFontByExcel(cell.getCellStyle(),size));
        }
        Anchor anchor = new Anchor(String.valueOf(getCellValue(cell)), getFontByExcel(cell.getCellStyle(),size));
        anchor.setName(this.excelObject.getAnchorName());
        this.setting = true;
        return anchor;
    }

    /**
     * 存储某个格子，因为行读取时，会读取到别多行合并的格子
     * @param rowspan 跨行数
     * @param colspan 跨列数
     * @param row 当前行
     * @param col 当前列
     * @return
     */
    public static void savePassCell(int rowspan, int colspan, int row, int col,ArrayList<String> list){
        if (rowspan > 1){
            for (int i = 0; i < rowspan-1; i++){
                for (int j = 0; j < colspan; j++){
                    list.add(String.valueOf(row+i+1)+String.valueOf(col+j));
                }
            }
        }
    }

    /**
     * 判断是否跳过某个格子，因为行读取时，，会读取到别多行合并的格子
     * @param row
     * @param col
     * @param list
     * @return
     */
    public boolean passCell(int row,int col,ArrayList<String> list){
        String r = String.valueOf(row)+String.valueOf(col);
        if (list.contains(r)){
            return true;
        }
        return false;
    }


    /**
     * 获取单元格值
     *
     * @return 单元格值
     */
    public Object getCellValue(Cell cell) {
        Object val = "";
        try {
            if (cell != null) {
                if (cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA) {
                    val = cell.getNumericCellValue();
                    if (DateUtil.isCellDateFormatted(cell)) {
                        // POI Excel 日期格式转换
                        val = DateUtil.getJavaDate((Double) val);
                    } else {
                        if ((Double) val % 1 > 0) {
                            val = new DecimalFormat("0.00").format(val);
                        } else {
                            val = new DecimalFormat("0").format(val);
                        }
                    }
                } else if (cell.getCellType() == CellType.STRING) {
                    val = cell.getStringCellValue();
                } else if (cell.getCellType() == CellType.BOOLEAN) {
                    val = cell.getBooleanCellValue();
                } else if (cell.getCellType() == CellType.ERROR) {
                    val = cell.getErrorCellValue();
                }

            }
        } catch (Exception e) {
            return val;
        }
        return val;
    }


    protected void addImageByPOICell(PdfPCell pdfpCell, Cell cell, float cellWidth) throws BadElementException, MalformedURLException, IOException {
        POIImage poiImage = new POIImage().getCellImage(cell);
        byte[] bytes = poiImage.getBytes();
        if (bytes != null) {

//           double cw = cellWidth;
//           double ch = pdfpCell.getFixedHeight();
//
//           double iw = poiImage.getDimension().getWidth();
//           double ih = poiImage.getDimension().getHeight();
//
//           double scale = cw / ch;
//
//           double nw = iw * scale;
//           double nh = ih - (iw - nw);
//
//           POIUtil.scale(bytes , nw  , nh);

            pdfpCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            pdfpCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            Image image = Image.getInstance(bytes);
            pdfpCell.setImage(image);
        }
    }

    protected float getPixelHeight(float poiHeight) {
        float pixel = poiHeight / 28.6f * 26f;
        return pixel;
    }

    /**
     * <p>Description: 此处获取Excel的列宽像素(无法精确实现,期待有能力的朋友进行改善此处)</p>
     *
     * @param cell
     * @return 像素宽
     */
    protected int getPOIColumnWidth(Cell cell) {
        int poiCWidth = excel.getSheet().getColumnWidth(cell.getColumnIndex());
        // com.itextpdf.text.pdf.PdfPTable.calculateWidths,此方法已经等比例转换了。不知道为什么还需要转换
        // int colWidthpoi = poiCWidth;
        // int widthPixel = 0;
        // if (colWidthpoi >= 416) {
        //     widthPixel = (int) (((colWidthpoi - 416.0) / 256.0) * 8.0 + 13.0 + 0.5);
        // } else {
        //     widthPixel = (int) (colWidthpoi / 416.0 * 13.0 + 0.5);
        // }
        return poiCWidth;
    }

    protected CellRangeAddress getColspanRowspanByExcel(int rowIndex, int colIndex) {
        CellRangeAddress result = null;
        Sheet sheet = excel.getSheet();
        int num = sheet.getNumMergedRegions();
        for (int i = 0; i < num; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            if (range.getFirstColumn() == colIndex && range.getFirstRow() == rowIndex) {
                result = range;
            }
        }
        return result;
    }

    protected boolean isUsed(int colIndex, int rowIndex) {
        boolean result = false;
        Sheet sheet = excel.getSheet();
        int num = sheet.getNumMergedRegions();
        for (int i = 0; i < num; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            if (firstRow < rowIndex && lastRow >= rowIndex) {
                if (firstColumn <= colIndex && lastColumn >= colIndex) {
                    result = true;
                }
            }
        }
        return result;
    }

    protected Font getFontByExcel(CellStyle style,float size) {
        Font result = new Font(Resource.BASE_FONT_CHINESE, size, Font.NORMAL);
        Workbook wb = excel.getWorkbook();

        short index = (short) style.getFontIndex();
        org.apache.poi.ss.usermodel.Font font = wb.getFontAt(index);

        if (font.getBold()) {
            result.setStyle(Font.BOLD);
        }

        HSSFColor color = HSSFColor.getIndexHash().get(font.getColor());

        if (color != null) {
            int rbg = POIUtil.getRGB(color);
            result.setColor(new BaseColor(rbg));
        }

        FontUnderline underline = FontUnderline.valueOf(font.getUnderline());
        if (underline == FontUnderline.SINGLE) {
            String ulString = Font.FontStyle.UNDERLINE.getValue();
            result.setStyle(ulString);
        }
        return result;
    }

    protected void addBorderByExcel(PdfPCell pdfCell, Cell cell) {
        Workbook wb = excel.getWorkbook();
//        pdfCell.setBorderWidthTop(0.5f);
//        pdfCell.setBorderWidthBottom(0.5f);
//        pdfCell.setBorderWidthLeft(0.5f);
//        pdfCell.setBorderWidthRight(0.5f);
        pdfCell.setBorderColorLeft(new BaseColor(POIUtil.getBorderRBG(wb, cell.getCellStyle().getLeftBorderColor())));
        pdfCell.setBorderColorRight(new BaseColor(POIUtil.getBorderRBG(wb, cell.getCellStyle().getRightBorderColor())));
        pdfCell.setBorderColorTop(new BaseColor(POIUtil.getBorderRBG(wb, cell.getCellStyle().getTopBorderColor())));
        pdfCell.setBorderColorBottom(new BaseColor(POIUtil.getBorderRBG(wb, cell.getCellStyle().getBottomBorderColor())));
    }

    protected int getVAlignByExcel(short align) {
        int result = 0;
        if (align == VerticalAlignment.BOTTOM.getCode()) {
            result = Element.ALIGN_BOTTOM;
        }
        if (align == VerticalAlignment.CENTER.getCode()) {
            result = Element.ALIGN_MIDDLE;
        }
        if (align == VerticalAlignment.JUSTIFY.getCode()) {
            result = Element.ALIGN_JUSTIFIED;
        }
        if (align == VerticalAlignment.TOP.getCode()) {
            result = Element.ALIGN_TOP;
        }
        return result;
    }

    protected int getHAlignByExcel(short align) {
        int result = 0;
        if (align == HorizontalAlignment.LEFT.getCode()) {
            result = Element.ALIGN_LEFT;
        }
        if (align == HorizontalAlignment.RIGHT.getCode()) {
            result = Element.ALIGN_RIGHT;
        }
        if (align == HorizontalAlignment.JUSTIFY.getCode()) {
            result = Element.ALIGN_JUSTIFIED;
        }
        if (align == HorizontalAlignment.CENTER.getCode()) {
            result = Element.ALIGN_CENTER;
        }
        return result;
    }
}
