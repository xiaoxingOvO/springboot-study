package com.xx.itext5.utils.excel;

import com.xx.itext5.utils.CommonUtil;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 格式化
 *
 * @author yema
 */
public abstract class AbstractExcelFormat {
    private static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd";
    private static final String SIMPLE_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public AbstractExcelFormat() {
    }

    /**
     * 解析
     *
     * @param var1
     * @return
     */
    public abstract String parse(String var1);

    /**
     * 格式化
     *
     * @param var1
     * @return
     */
    public abstract String print(String var1);

    /**
     * None format
     */
    public abstract static class AbstractNoneFormat extends AbstractExcelFormat {
        public AbstractNoneFormat() {
        }
    }

    /**
     * ZonedDateTime format yyyy-MM-dd HH:mm:ss
     */
    public static class ZonedDateTimeFormatDateTime extends AbstractExcelFormat {
        @Override
        public String parse(String var1) {
            return null;
        }

        @Override
        public String print(String var1) {
            return ZonedDateTime.parse(var1).format(DateTimeFormatter.ofPattern(SIMPLE_DATE_FORMAT));
        }
    }

    /**
     * ZonedDateTime format yyyy-MM-dd
     */
    public static class ZonedDateTimeFormatDate extends AbstractExcelFormat {
        @Override
        public String parse(String var1) {
            return null;
        }

        @Override
        public String print(String var1) {
            return ZonedDateTime.parse(var1).format(DateTimeFormatter.ofPattern(SIMPLE_DATE_FORMAT));
        }
    }

    /**
     * ZonedDateTime format yyyy/MM/dd
     */
    public static class ZonedDateTimeFormatDate2 extends AbstractExcelFormat {
        @Override
        public String parse(String var1) {
            return null;
        }

        @Override
        public String print(String var1) {
            return var1.equals("null")?"":ZonedDateTime.parse(var1).format(DateTimeFormatter.ofPattern(SIMPLE_DATE_FORMAT)).replace("-","/");
        }
    }


    /**
     * ZonedDateTime format yyyy-MM-dd HH:mm:ss
     */
    public static class LocalDateTimeFormatDate extends AbstractExcelFormat {
        @Override
        public String parse(String var1) {
            return null;
        }

        @Override
        public String print(String var1) {
            return LocalDateTime.parse(var1).format(DateTimeFormatter.ofPattern(SIMPLE_DATE_FORMAT));
        }
    }

    /**
     * ZonedDateTime format yyyy-MM-dd
     */
    public static class LocalDateFormatDate extends AbstractExcelFormat {
        @Override
        public String parse(String var1) {
            return null;
        }

        @Override
        public String print(String var1) {
            return LocalDate.parse(var1).format(DateTimeFormatter.ofPattern(SIMPLE_DATE_FORMAT));
        }
    }

    /**
     * ZonedDateTime format
     */
    public static class ZonedDateTimeYyyyMmDdFormat extends AbstractExcelFormat {
        @Override
        public String parse(String var1) {
            return null;
        }

        @Override
        public String print(String var1) {
            return ZonedDateTime.parse(var1).format(DateTimeFormatter.ofPattern(SIMPLE_DATE_FORMAT));
        }
    }

    /**
     * BigDecimal format
     */
    public static class BigDecimalFormat extends AbstractExcelFormat {
        @Override
        public String parse(String var1) {
            return null;
        }

        @Override
        public String print(String var1) {
            return new BigDecimal(CommonUtil.initMathStr(var1)).stripTrailingZeros().toPlainString();
        }
    }

    /**
     * BigDecimal format
     */
    public static class BigDecimalFormatForRate extends AbstractExcelFormat {
        @Override
        public String parse(String var1) {
            return null;
        }

        @Override
        public String print(String var1) {
            return new BigDecimal(CommonUtil.initMathStr(var1))
                .multiply(new BigDecimal(100))
                .stripTrailingZeros()
                .toPlainString()
                .concat("%");
        }
    }

    /**
     * 字符串格式化
     */
    public static class StringFormat extends AbstractExcelFormat {
        @Override
        public String parse(String var1) {
            return null;
        }

        @Override
        public String print(String var1) {
            return StringUtils.isBlank(var1) ? "" : var1;
        }
    }

    /**
     * 签名图片格式化
     */
    public static class UserSignPicFormat extends AbstractExcelFormat {
        @Override
        public String parse(String var1) {
            return null;
        }

        @Override
        public String print(String var1) {
            return StringUtils.isBlank(var1) ? "" : var1;
        }
    }
}
