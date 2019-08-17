package com.jnshu.util;

import org.springframework.stereotype.Component;

/**
 * 将byte数组转换为表示16进制值的字符串，如：byte[]{8,18}转换为：0813， 和public static byte[]
 * hexStrToByteArr(String strIn) 互为可逆的转换过程
 * @return 转换后的字符串
 */
@Component
public class StrConvertUtil {
    public static String byteArrToHexStr(byte[] arrB) {
        int iLen = arrB.length;
        //计量输入的字符数组的长度
        // 每个byte(8位)用两个(16进制)字符才能表示，一个16进制可以表示4位2进制，所以字符串的长度是数组长度的两倍，2的4次方16只能表示4位，而一个字节有8位，需要两个连在一起表示
        StringBuffer sb = new StringBuffer(iLen * 2);//stringbuffer和stringbuilder修饰可变的字符串
        for (byte anArrB : arrB) {           //将字节数组转换成字符串
            int intTmp = anArrB;
            while (intTmp < 0) {// 把负数转换为正数
                intTmp = intTmp + 256;//范围0-255，所以负数转换成正数就是+256（二进制八位）
            }
            if (intTmp < 16) {// 小于0F的数需要在前面补0，二进制数不满4位，在前面补零（1000）
                sb.append("0");//先出0，后面再加上二进制数
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }
    /**
     * 将表示16进制值的字符串转换为byte数组，和public static String byteArrToHexStr(byte[] arrB)
     * 互为可逆的转换过程
     *
     * @param strIn
     *            需要转换的字符串
     * @return 转换后的byte数组
     */
    static byte[] hexStrToByteArr(String strIn) {
        byte[] arrB = strIn.getBytes();
        //string字符串转换成字符数组
        int iLen = arrB.length;
        // 两个(16进制)字符表示一个字节(8位)，所以字节数组长度是字符串长度除以2
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            //取连续两个字符（4位2进制数）给字符串strtmp
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
            //输出8位的2进制数组
        }
        return arrOut;
    }
}