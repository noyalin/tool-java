package toolService.jumei.updateJm.utils;

import com.google.common.collect.ImmutableMap;
import com.jd.open.api.sdk.internal.util.codec.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.MD5;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by simin on 2017/10/25.
 */
public final class StringUtils {
    private static final Pattern special_symbol = Pattern.compile("[.]");

    private static Log logger = LogFactory.getLog(StringUtils.class);

    public static String null2Space(String input) {
        if (input == null) {
            return "";
        } else {
            return input;
        }
    }

    public static String null2Space2(String input) {
        if (input == null) {
            return "";
        } else if ("null".equalsIgnoreCase(input)) {
            return "";
        } else {
            return input;
        }
    }

    public static String formatNum(String number) {
        if (isEmpty(number))
            return number;

        return formatNum(new Double(number));
    }

    public static String formatNum(Number number) {
        DecimalFormat a = new DecimalFormat("0.00");
        return a.format(number);
    }

    /**
     * 空白Check
     *
     * @param chkParam
     * @return
     */
    public static boolean isEmpty(String chkParam) {
        boolean ret = false;

        if (chkParam == null || "".equals(chkParam)) {
            ret = true;
        }

        return ret;
    }

    /**
     * 数字Check
     *
     * @param chkParam
     * @return
     */
    public static boolean isDigit(String chkParam) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher match = pattern.matcher(chkParam);

        return match.matches() != false;
    }

    /**
     * 数值Check
     *
     * @param chkParam
     * @return
     */
    public static boolean isNumeric(String chkParam) {
        boolean ret = true;
        try {
            Float.valueOf(chkParam);
        } catch (Exception e) {
            ret = false;
        }

        return ret;
    }

    /**
     * Web Service 用
     *
     * @param str
     * @return
     */
    public static boolean isNullOrBlank2(String str) {
        if (str == null || "".equals(str)) {
            return true;
        } else return "null".equals(str);
    }

    /**
     * ArrayList 转为 页面输出字符串
     *
     * @param errorList
     * @return
     */
    public static String arrayListToString(ArrayList<String> errorList) {
        // 异常信息输出
        String errorOutput = "";
        StringBuffer outputBuff = new StringBuffer();
        for (int i = 0; i < errorList.size(); i++) {
            if (i == 0) {
                outputBuff.append(errorList.get(i));
            } else {
                outputBuff.append("\\n ");
                outputBuff.append(errorList.get(i));
            }
        }
        errorOutput = outputBuff.toString();

        return errorOutput;
    }

    /**
     * ArrayList 转为 页面输出字符串
     *
     * @param errorList
     * @return
     */
    public static String arrayListToString2(ArrayList<String> errorList) {
        // 异常信息输出
        String errorOutput = "";
        StringBuffer outputBuff = new StringBuffer();
        for (int i = 0; i < errorList.size(); i++) {
            if (i == 0) {
                outputBuff.append(errorList.get(i));
            } else {
                outputBuff.append("\n");
                outputBuff.append(errorList.get(i));
            }
        }
        errorOutput = outputBuff.toString();

        return errorOutput;
    }

    /**
     * 小数点2位精度取得
     *
     * @param value
     * @return
     */
    public static String getNumPrecision2(double value) {

        BigDecimal b = new BigDecimal(value);

        return String.valueOf(b.setScale(2, BigDecimal.ROUND_HALF_UP));
    }

    public static boolean isPhoneNum(String phone) {

        // 电话号码以1开头的11位数字
        Pattern pattern = Pattern.compile("^1[\\d]{10}$");

        Matcher match = pattern.matcher(phone);

        return match.matches();

    }

    /**
     * DB 日期时间去除
     *
     * @param dbDate DB中日期值（含时间）
     * @return
     */
    public static String trimDBDateTime(String dbDate) {
        String ret = "";

        if (dbDate != null) {
            // 数据库日期后缀
            String dbDateSuf = " 00:00:00.0";

            ret = dbDate.replace(dbDateSuf, "");
        }

        return ret;
    }

    /**
     * DB 日期时间毫秒去除
     *
     * @param dbDate DB中日期值（含时间）
     * @return
     */
    public static String trimDBDateTimeMs(String dbDate) {
        String ret = "";

        if (dbDate != null) {
            // 毫秒后缀
            String dbMsSuf = ".0";

            ret = dbDate.replace(dbMsSuf, "");
        }

        return ret;
    }

    /**
     * DB 日期取得
     *
     * @param dbDate DB中日期值（含时间）
     * @return
     */
    public static String getDate(String dbDate) {
        String ret = "";

        if (dbDate != null) {
            String[] dateTime = dbDate.split(" ");
            if (dateTime.length == 2) {
                ret = dateTime[0];
            }
        }

        return ret;
    }

    /**
     * DB 时间取得
     *
     * @param dbDate DB中日期值（含时间）
     * @return
     */
    public static String getTime(String dbDate) {
        String ret = "";

        if (dbDate != null) {
            String[] dateTime = dbDate.split(" ");
            if (dateTime.length == 2) {
                ret = dateTime[1];
            }
        }

        return ret;
    }

    /**
     * DB 日期时间取得
     *
     * @param dbDate DB中日期值（含时间）
     * @param dbTime DB中时间值（含时间）
     * @return
     */
    public static String getDateTime(String dbDate, String dbTime) {
        String ret = "";

        if (dbDate != null && dbTime != null) {
            ret = getDate(dbDate) + " " + getTime(dbTime);
        }

        return ret;
    }

    /**
     * DB 金额取得
     *
     * @param dbMoney DB中金额（0.0000）
     * @return
     */
    public static String getFormatedMoney(String dbMoney) {
        String ret = "";

        if (dbMoney != null) {
            double retD = Double.parseDouble(dbMoney);
            BigDecimal b = new BigDecimal(retD);

            ret = String.valueOf(b.setScale(2, BigDecimal.ROUND_HALF_UP));
        }

        return ret;
    }

    public static String join(final List<?> list, final String separator) {
        return join(list.iterator(), separator);
    }

    public static String join(final Iterator<?> iterator, final String separator) {
        if (iterator == null) return null;

        if (!iterator.hasNext()) return "";

        final Object first = iterator.next();

        if (!iterator.hasNext()) {
            @SuppressWarnings("deprecation") // ObjectUtils.toString(Object) has been deprecated in 3.2
            final String result = String.valueOf(first);
            return result;
        }

        // two or more elements
        final StringBuilder buf = new StringBuilder(256); // java default is 16, probably too small

        if (first != null) {
            buf.append(first);
        }

        while (iterator.hasNext()) {
            if (separator != null) buf.append(separator);

            final Object obj = iterator.next();

            if (obj != null) buf.append(obj);
        }

        return buf.toString();
    }

    /**
     * 解码 Unicode
     *
     * @param str 一串 Unicode 编码
     * @return String
     */
    public static String decodeUnicode(String str) {
        String[] buf = str.split("//u");
        StringBuilder sb = new StringBuilder();
        for (String string : buf) {
            if (string.length() > 0) {
                sb.append((char) Integer.parseInt(string, 16));
            }
        }
        return sb.toString();
    }

    /**
     * 判断字符串中是否包含某些字符串
     *
     * @param str
     * @param s
     * @return
     */
    public static boolean containstr(String str, String[]... comp) {
        boolean flag = false;
        for (String[] s : comp) {
            for (int i = 0; i < s.length; i++) {
                if (str.contains(s[i])) {
                    return true;
                }
            }
        }
        return flag;
    }

    /**
     * 字符串首字母大写
     *
     * @param name
     * @return
     */
    public static String uppercaseFirst(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    /**
     * 转换数据中的特殊字符
     *
     * @param data
     * @return
     */
    public static String transferStr(String data) {
        if (StringUtils.isNullOrBlank2(data)) {
            return "";
        } else {
            return data.replace("'", "''").replace("\\", "\\\\").replace("\r\n", " ").replace("\n", " ").replace("\r", " ");
        }
    }

    /**
     * <p>
     * Discription: 指定的字符串累加
     * </p>
     *
     * @param chr
     * @param len
     * @return
     * @author :
     * @update :
     */
    public static String strAdd(String chr, int len) {
        if (len > 0) {
            StringBuffer ret = new StringBuffer(len);
            for (int i = 0; i < len; i++) {
                ret.append(chr);
            }
            return (ret.toString());
        } else {
            return "";
        }
    }

    /**
     * <p>
     * Discription: 给字符串补足到指定的长度，从左边补足chr指定的字符
     * </p>
     *
     * @param source
     * @param chr
     * @param len
     * @return
     * @author :
     * @update :
     */
    public static String lPad(String source, String chr, int len) {
        int lenleft = len - source.length();
        if (lenleft < 0) {
            lenleft = 0;
        }
        return (strAdd(chr, lenleft) + source);
    }

    /**
     * <p>
     * Discription: 给字符串补足到指定的长度，从右边补足chr指定的字符
     * </p>
     *
     * @param source
     * @param chr
     * @param len
     * @return
     * @author :
     * @update :
     */
    public static String rPad(String source, String chr, int len) {
        int lenleft = len - source.length();
        if (lenleft < 0) {
            lenleft = 0;
        }
        return (source + strAdd(chr, lenleft));
    }

    /**
     * <p>
     * Discription: 取得字符的Byte长度
     * </p>
     *
     * @param content
     * @param charsetName
     * @return
     * @author :
     * @update :
     */
    public static int getByteLength(String content, String charsetName) {
        int byteLength = 0;

        try {
            byte[] byteContent = content.getBytes(charsetName);
            byteLength = byteContent.length;
        } catch (Exception e) {

        }

        return byteLength;
    }

    /**
     * <p>
     * Discription: <img></> 元素删除
     * </p>
     *
     * @param content
     * @param
     * @return
     * @author :
     * @update :
     */
    public static String trimImgElement(String content) {
        String ret = content;

        int imgBeginIndex = content.indexOf("<img");
        while (imgBeginIndex != -1) {
            int imgEndIndex = ret.indexOf(">", imgBeginIndex);

            // 正常匹配的场合
            if (imgEndIndex != -1) {
                imgEndIndex = imgEndIndex + 1;
                ret = ret.substring(0, imgBeginIndex) + ret.substring(imgEndIndex, ret.length());
            } else {
                // 异常数据的场合
                break;
            }

            imgBeginIndex = ret.indexOf("<img");
        }

        return ret;
    }


    /**
     * 把str中的【.】替换成【->】
     *
     * @param str
     * @return
     */
    public static String replaceDot(String str) {
        return special_symbol.matcher(str).replaceAll("->");
    }

    /**
     * 把str中的【->】替换成【.】
     */
    public static String replaceToDot(String str) {
        return str.replaceAll("->", ".");
    }

    /**
     * BASE64字符串二进制数据编码为
     */
    public static String decodeBase64(String input) {
        String result = null;
        if (input != null) {
            result = new String(Base64.decodeBase64(input.getBytes()));
        }
        return result;
    }

    /**
     * 二进制数据编码为BASE64字符串
     */
    public static String encodeBase64(String input) {
        String result = null;
        if (input != null) {
            result = new String(Base64.encodeBase64(input.getBytes()));
        }
        return result;
    }

    public static String generCatId(String catPath) {
        return MD5.getMD5(catPath);
    }

    /**
     * 取得金额的小数点后两位为止
     *
     * @param calValue
     * @return
     */
    public static String getPriceValue(int newScale, double calValue) {

        BigDecimal b = new BigDecimal(calValue);

        return String.valueOf(b.setScale(newScale, BigDecimal.ROUND_HALF_UP));
    }

    /**
     * 如果字符串包含连续两个及两个以上的char相同,返回true
     *
     * @param words
     * @return
     */
    public static boolean hasContinuousSameChar(String words) {
        if (words == null || words.length() == 0) {
            return false;
        }
        for (int i = 0; i < words.length() - 1; i++) {
            if (words.charAt(i) == words.charAt(i + 1)) {
                return true;
            }
        }
        return false;
    }

    private static final ImmutableMap<String, String> PROVINCE_MAP = ImmutableMap.<String, String>builder()
            .put("北京","北京市")
            .put("天津","天津市")
            .put("上海","上海市")
            .put("重庆","重庆市")
            .put("广西","广西壮族自治区")
            .put("内蒙古","内蒙古自治区")
            .put("西藏","西藏自治区")
            .put("宁夏","宁夏回族自治区")
            .put("新疆","新疆维吾尔自治区")
            .build();

    /**
     * 将省字段正规化
     * @param province
     * @return
     */
    public static String normalizProvince(String province) {
        if (province == null) {
            return province;
        }
        for (Map.Entry<String, String> entry : PROVINCE_MAP.entrySet()) {
            if (province.contains(entry.getKey())) {
                //直接返回value
                return entry.getValue();
            }
        }
        if (!province.trim().endsWith("省")) {
            return province.trim() + "省";
        }
        return province.trim();

    }

    /**
     * <p>
     * Discription:定义字符串替换时需要用到的转义字符串
     * </p>
     */
    private static String REPLACE_OUT="[	,`''/<>‘”“’ &\"·—…]";

    /**
     * <p>
     * Discription: 用于字符串转换显示。将XML、json敏感的尖括号、引号、连接号等用转义符替代。
     * </p>
     *
     * @param str
     *            需要检查的字符串
     * @return 转化后的字串
     * @author :
     * @update :
     */
    public static String StringFilter(String   str)   throws PatternSyntaxException {

        // 清除掉所有特殊字符
        Pattern   p   =   Pattern.compile(REPLACE_OUT);
        Matcher   m   =   p.matcher(str);
        return   m.replaceAll(" ").trim().replaceAll("\r\n", " ").replaceAll("\r", "").replaceAll("\n", "");
    }

    public static boolean isInteger(String string) {
        if (isEmpty(string)) return false;
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


//    public static void main(String[] args) {
//        String content = "<img src=https://img.alicdn.com/imgextra/i3/2694857307/TB2GprUjVXXXXbjXpXXXXXXXXXX_!!2694857307.jpg> <p>The envelope <img src=https://img.alicdn.com/imgextra/i3/2694857307/TB2GprUjVXXXXbjXpXXXXXXXXXX_!!2694857307.jpg>clutch is reimagined in smooth cork with splashes of vivid colors for an artistic finish.</p><ul><li>Magnetic snap closure.</li><li>Material: Cork.</li><li>Lining: Rayon.</li><li>Imported.</li></ul><br /><p>这款信封式手拿包的灵感来源于鲜艳的色彩飞溅在光滑的软木上，体现了现代艺术与自然的结合.</p><ul><li>磁扣闭合.</li><li>材质: 软木.</li><li>内衬: 人造丝.</li><li>进口原厂制作.</li></ul>  <img src=https://img.alicdn.com/imgextra/i3/2694857307/TB2GprUjVXXXXbjXpXXXXXXXXXX_!!2694857307.jpg> <br>";
//
//        String ret = trimImgElement(content);
//        System.out.println(content);
//        System.out.println(ret);
//    }
}
