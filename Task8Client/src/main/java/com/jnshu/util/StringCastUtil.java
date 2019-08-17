package com.jnshu.util;

import org.springframework.stereotype.Component;

/**这个工具类是把八进制和字符串进行转换
 */

@Component
public class StringCastUtil {
    /**这个方法将字符串（utf-8）转成八进制
     * @param s  （utf-8）
     * @result 八进制
     */
    public static String stringCast(String s)
    {
        String result = "";
        for(char c : s.toCharArray())
        {
            result += "\\" + (c / 64) % 8 +  "" + (c / 8) % 8 + "" + c % 8;
        }
        return result;
    }
    /**这个方法将把八进制转成字符串
     * @param s  八进制
     * @result 字符串（utf-8）
     */
    public static String getString(String s)
    {
        String result = "";
        for (String i : s.split("\\\\"))
        {
            int sum = 0;
            int base = 64;
            for (char c : i.toCharArray())
            {
                sum += base * ((int)c - '0');
                base /= 8;
            }
            result += Character.toString((char)sum);
        }
        return result;
    }
}
