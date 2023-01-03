package com.xx.itext5.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

/**
 * @author yema
 */
public class CommonUtil {

  private static final Logger log = org.slf4j.LoggerFactory.getLogger(CommonUtil.class);

  /**
     * bigDecimal 非空处理
     *
     * @param value value
     * @return result
     */
    public static String initMathStr(String value) {
        return StringUtils.isNotBlank(value) ? value : "0";
    }

}
