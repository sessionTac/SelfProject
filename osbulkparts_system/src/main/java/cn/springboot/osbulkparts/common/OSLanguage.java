package cn.springboot.osbulkparts.common;

import java.util.Locale;

public class OSLanguage {

//    /**
//     * 本系统支持的语种（作为表后缀）
//     */
//    public final static String[] TABLE_SUFFIXS = {"CN", "EN"};
//
//    /**
//     * 默认题目语种（当用户要的语种不存在时选用此语种的题目信息）
//     */
//    public final static String DEFAULT_TABLE_SUFFIX_FOR_SUBJECTS = "CN";

    /**
     * 将前端的lang转为已知的表后缀，以防止sql注入
     * @param lang
     * @return
     */
    public static String localeToTableSuffix(String lang) {

        if (null == lang)
            lang = "zh";

        switch(lang) {
            case "en":
                return "EN";
            case "ru":
                return "RU";
            case "zh":
            default:
                return "CN";
        }
    }

    /**
     * 将前端的lang转为已知的多语言返回，这里是用于后台向前台返回数据用的
     * @param lang
     * @return
     */
    public static Locale localeToVueSuffix(String lang) {

        if (null == lang)
            lang = "zh";

        switch(lang) {
            case "en":
                return Locale.US;
            case "ru":
                return Locale.FRANCE;
            case "zh":
            default:
                return Locale.SIMPLIFIED_CHINESE;
        }
    }


}
