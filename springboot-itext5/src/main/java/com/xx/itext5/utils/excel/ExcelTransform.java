package com.xx.itext5.utils.excel;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTCol;
import org.slf4j.Logger;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Excel转换
 * @author yema
 */
public class ExcelTransform {

  private static final Logger log = org.slf4j.LoggerFactory.getLogger(ExcelTransform.class);
  private int lastColumn = 0;
    private short colorindex = 8;
    private static final int RGB_HEX_LENGTH = 6;
    private Map<Integer, HSSFCellStyle> styleMap = new HashMap<>(8);

    /**
     * xlsx to xls
     * @param workbookOld
     * @param workbookNew
     */
    public void transformXSSF(XSSFWorkbook workbookOld, HSSFWorkbook workbookNew) {
        HSSFSheet sheetNew;
        XSSFSheet sheetOld;

        workbookNew.setMissingCellPolicy(workbookOld.getMissingCellPolicy());

        for (int i = 0; i < workbookOld.getNumberOfSheets(); i++) {
            sheetOld = workbookOld.getSheetAt(i);
            sheetNew = workbookNew.createSheet(sheetOld.getSheetName());
            this.transform(workbookOld, workbookNew, sheetOld, sheetNew);
        }
    }

    /**
     * xlsx to xls,sheet
     * @param workbookOld
     * @param workbookNew
     * @param sheetOld
     * @param sheetNew
     */
    private void transform(XSSFWorkbook workbookOld, HSSFWorkbook workbookNew,
                           XSSFSheet sheetOld, HSSFSheet sheetNew) {

        sheetNew.setDisplayFormulas(sheetOld.isDisplayFormulas());
        sheetNew.setDisplayGridlines(sheetOld.isDisplayGridlines());
        sheetNew.setDisplayGuts(sheetOld.getDisplayGuts());
        sheetNew.setDisplayRowColHeadings(sheetOld.isDisplayRowColHeadings());
        sheetNew.setDisplayZeros(sheetOld.isDisplayZeros());
        sheetNew.setFitToPage(sheetOld.getFitToPage());

        sheetNew.setHorizontallyCenter(sheetOld.getHorizontallyCenter());
        sheetNew.setMargin(Sheet.BottomMargin,
                sheetOld.getMargin(Sheet.BottomMargin));
        sheetNew.setMargin(Sheet.FooterMargin,
                sheetOld.getMargin(Sheet.FooterMargin));
        sheetNew.setMargin(Sheet.HeaderMargin,
                sheetOld.getMargin(Sheet.HeaderMargin));
        sheetNew.setMargin(Sheet.LeftMargin,
                sheetOld.getMargin(Sheet.LeftMargin));
        sheetNew.setMargin(Sheet.RightMargin,
                sheetOld.getMargin(Sheet.RightMargin));
        sheetNew.setMargin(Sheet.TopMargin, sheetOld.getMargin(Sheet.TopMargin));
        sheetNew.setPrintGridlines(sheetNew.isPrintGridlines());
        sheetNew.setRightToLeft(sheetNew.isRightToLeft());
        sheetNew.setRowSumsBelow(sheetNew.getRowSumsBelow());
        sheetNew.setRowSumsRight(sheetNew.getRowSumsRight());
        sheetNew.setVerticallyCenter(sheetOld.getVerticallyCenter());

        HSSFRow rowNew;
        for (Row row : sheetOld) {
            rowNew = sheetNew.createRow(row.getRowNum());
            if (rowNew != null){
                this.transform(workbookOld, workbookNew, (XSSFRow) row, rowNew);
            }
        }

        for (int i = 0; i < this.lastColumn; i++) {
            CTCol col = sheetOld.getColumnHelper().getColumn((long)i, false);
            double width = col != null && col.isSetWidth() ? col.getWidth() : (double)sheetOld.getDefaultColumnWidth();
            sheetNew.setColumnWidth(i, Math.toIntExact(Math.round((width+1.8) * 256.0D)));
            sheetNew.setColumnHidden(i, sheetOld.isColumnHidden(i));
        }

        for (int i = 0; i < sheetOld.getNumMergedRegions(); i++) {
            CellRangeAddress merged = sheetOld.getMergedRegion(i);
            sheetNew.addMergedRegion(merged);
        }
    }

    /**
     * xlsx to xls,row
     * @param workbookOld
     * @param workbookNew
     * @param rowOld
     * @param rowNew
     */
    private void transform(XSSFWorkbook workbookOld, HSSFWorkbook workbookNew,
                           XSSFRow rowOld, HSSFRow rowNew) {
        HSSFCell cellNew;
        rowNew.setHeight(rowOld.getHeight());

        for (Cell cell : rowOld) {
            cellNew = rowNew.createCell(cell.getColumnIndex(),
                    cell.getCellType());
            if (cellNew != null){
                this.transform(workbookOld, workbookNew, (XSSFCell) cell,
                    cellNew);
            }
        }
        this.lastColumn = Math.max(this.lastColumn, rowOld.getLastCellNum());
    }

    /**
     * xlsx to xls,cell
     * @param workbookOld
     * @param workbookNew
     * @param cellOld
     * @param cellNew
     */
    private void transform(XSSFWorkbook workbookOld, HSSFWorkbook workbookNew,
                           XSSFCell cellOld, HSSFCell cellNew) {
        cellNew.setCellComment(cellOld.getCellComment());

        Integer hash = cellOld.getCellStyle().hashCode();
        if (this.styleMap != null && !this.styleMap.containsKey(hash)) {
            this.transform(workbookOld, workbookNew, hash,
                    cellOld.getCellStyle(),
                    (HSSFCellStyle) workbookNew.createCellStyle());
        }
        cellNew.setCellStyle(this.styleMap.get(hash));

        switch (cellOld.getCellType()) {
            case BLANK:
                break;
            case BOOLEAN:
                cellNew.setCellValue(cellOld.getBooleanCellValue());
                break;
            case ERROR:
                cellNew.setCellValue(cellOld.getErrorCellValue());
                break;
            case FORMULA:
                cellNew.setCellValue(cellOld.getCellFormula());
                break;
            case NUMERIC:
                cellNew.setCellValue(cellOld.getNumericCellValue());
                break;
            case STRING:
                cellNew.setCellValue(cellOld.getStringCellValue());
                break;
            default:
                log.info("transform: Unbekannter Zellentyp "
                        + cellOld.getCellType());
        }
    }

    /**
     * xlsx to xls,style
     * @param workbookOld
     * @param workbookNew
     * @param hash
     * @param styleOld
     * @param styleNew
     */
    private void transform(XSSFWorkbook workbookOld, HSSFWorkbook workbookNew,
                           Integer hash, XSSFCellStyle styleOld, HSSFCellStyle styleNew) {
        styleNew.setAlignment(styleOld.getAlignment());
        styleNew.setBorderBottom(styleOld.getBorderBottom());
        styleNew.setBorderLeft(styleOld.getBorderLeft());
        styleNew.setBorderRight(styleOld.getBorderRight());
        styleNew.setBorderTop(styleOld.getBorderTop());
        styleNew.setDataFormat(this.transform(workbookOld, workbookNew,
                styleOld.getDataFormat()));
        Color backgroundColor = getColor(styleOld.getFillBackgroundColorColor());
        try{
            HSSFPalette palette = workbookNew.getCustomPalette();
            HSSFColor hssfColor = palette.findColor((byte)backgroundColor.getRed(),(byte)backgroundColor.getGreen(),(byte)backgroundColor.getBlue());
            if(null==hssfColor){
                palette.setColorAtIndex(colorindex,(byte)backgroundColor.getRed(),(byte)backgroundColor.getGreen(),(byte)backgroundColor.getBlue());
            }
            hssfColor = palette.findColor((byte)backgroundColor.getRed(),(byte)backgroundColor.getGreen(),(byte)backgroundColor.getBlue());
            styleNew.setFillBackgroundColor(hssfColor.getIndex());
            colorindex++;
        }catch (Exception e){
            e.fillInStackTrace();
        }
        Color foregroundColor = getColor(styleOld.getFillForegroundColorColor());
        try{
            HSSFPalette palette = workbookNew.getCustomPalette();
            HSSFColor hssfColor = palette.findColor((byte)foregroundColor.getRed(),(byte)foregroundColor.getGreen(),(byte)foregroundColor.getBlue());
            if(null==hssfColor){
                palette.setColorAtIndex(colorindex,(byte)foregroundColor.getRed(),(byte)foregroundColor.getGreen(),(byte)foregroundColor.getBlue());
            }
            hssfColor = palette.findColor((byte)foregroundColor.getRed(),(byte)foregroundColor.getGreen(),(byte)foregroundColor.getBlue());
            styleNew.setFillForegroundColor(hssfColor.getIndex());
            colorindex++;
        }catch (Exception e){
            e.fillInStackTrace();
        }


        styleNew.setFillPattern(styleOld.getFillPattern());
        styleNew.setFont(this.transform(workbookNew,
                (XSSFFont) styleOld.getFont()));
        styleNew.setHidden(styleOld.getHidden());
        styleNew.setIndention(styleOld.getIndention());
        styleNew.setLocked(styleOld.getLocked());
        styleNew.setVerticalAlignment(styleOld.getVerticalAlignment());
        styleNew.setWrapText(styleOld.getWrapText());
        this.styleMap.put(hash, styleNew);
    }

    /**
     * 格式
     * @param workbookOld
     * @param workbookNew
     * @param index
     * @return
     */
    private short transform(XSSFWorkbook workbookOld, HSSFWorkbook workbookNew,
                            short index) {
        DataFormat formatOld = workbookOld.createDataFormat();
        DataFormat formatNew = workbookNew.createDataFormat();
        return formatNew.getFormat(formatOld.getFormat(index));
    }

    /**
     * 字体
     * @param workbookNew
     * @param fontOld
     * @return
     */
    private HSSFFont transform(HSSFWorkbook workbookNew, XSSFFont fontOld) {
        HSSFFont fontNew = workbookNew.createFont();
        fontNew.setBold(fontOld.getBold());
        fontNew.setCharSet(fontOld.getCharSet());
        fontNew.setColor(fontOld.getColor());
        fontNew.setFontName(fontOld.getFontName());
        fontNew.setFontHeight(fontOld.getFontHeight());
        fontNew.setItalic(fontOld.getItalic());
        fontNew.setStrikeout(fontOld.getStrikeout());
        fontNew.setTypeOffset(fontOld.getTypeOffset());
        fontNew.setUnderline(fontOld.getUnderline());
        return fontNew;
    }

    /**
     * 获取颜色
     * @param col
     * @return
     */
    private Color getColor(org.apache.poi.ss.usermodel.Color col) {
        Color color = null;
        try{
            if (col instanceof HSSFColor) {
                HSSFColor c = (HSSFColor) col;
                short[] triplet = c.getTriplet();
                color = new Color(triplet[0], triplet[1], triplet[2]);
            } else if (col instanceof XSSFColor) {
                XSSFColor c = (XSSFColor) col;
                String rgbHex = c.getCTColor().getDomNode().getAttributes().getNamedItem("rgb").getNodeValue();
                if (rgbHex.length() > RGB_HEX_LENGTH) {
                    rgbHex = rgbHex.substring(2);
                }
                color = Color.decode("#" + rgbHex);
            }
        }catch (Exception e){}
        return color;
    }

}
