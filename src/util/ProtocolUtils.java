package util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;



import javassist.expr.NewArray;

public class ProtocolUtils
{
    public static int NodeID = 0;
    //请求协议tag
    public static final byte REQ = 0x00;
    //响应协议tag
    public static final byte RES = 0x01;
    private static byte[] DeviceCrc = new byte[]{(byte) 0xff, (byte) 0xff};
    private static byte[] CharTemp = new byte[2];
    private static Gson gson = new Gson();
    private static Config Config_;

    public static void setConfg(Config C)
    {
        Config_ = C;
    }

    public static Config GetConfg()
    {
        synchronized (Config_)
        {
            return Config_;
        }
    }

    public static String getProjectPath()
    {
        java.net.URL url = ProtocolUtils.class.getProtectionDomain().getCodeSource().getLocation();
        String filePath = null;
        try
        {
            filePath = java.net.URLDecoder.decode(url.getPath(), "utf-8");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        if (filePath.endsWith(".jar"))
        {
            filePath = filePath.substring(0, filePath.lastIndexOf("/") + 1);
        }
        java.io.File file = new java.io.File(filePath);
        filePath = file.getAbsolutePath();
        return filePath;
    }

    public static String StringForm(int Num, String value)
    {
        return String.format("%0" + Num + "d", Integer.parseInt(value));
    }

    static Object GetTime_ = new Object();

    public static String GetTime(String timeByte)
    {
        synchronized (GetTime_)
        {
            try
            {
                String Dt = String.valueOf(2000 + Integer.parseInt(timeByte.substring(0, 2).toString(), 16)) + "-" + StringForm(2, Integer.toString(Integer.parseInt(timeByte.subSequence(2, 4).toString()), 16)) + "-" + StringForm(2, Integer.toString(Integer.parseInt(timeByte.subSequence(4, 6).toString(), 16))) + " " + StringForm(2, Integer.toString(Integer.parseInt(timeByte.subSequence(6, 8).toString(), 16))) + ":" + StringForm(2, Integer.toString(Integer.parseInt(timeByte.subSequence(8, 10).toString(), 16))) + ":" + StringForm(2, Integer.toString(Integer.parseInt(timeByte.subSequence(10, 12).toString(), 16)));
                return Dt;

            }
            catch (Exception e)
            {
                // TODO: handle exception
            }
            return "";
        }

    }

    public static String GetByteTime(byte[] timeByte)
    {

        try
        {
            //  String temp_=ProtocolUtils.byteToString(timeByte);
            String Dt = String.valueOf(2000 + timeByte[0]) + "-" + String.valueOf(timeByte[1]) + "-" + String.valueOf(timeByte[2]) + " " + String.valueOf(timeByte[3]) + ":" + String.valueOf(timeByte[4]) + ":" + String.valueOf(timeByte[5]);
            return Dt;

        }
        catch (Exception e)
        {
            // TODO: handle exception
        }
        return "";


    }

    public static int byte1ToInt(byte[] res)
    {
        // 一个byte数据左移24位变成0x??000000，再右移8位变成0x00??0000

        int targets = (res[0] & 0xff)// | 表示安位或
                ;
        return targets;


    }

    public static int byteToInt(byte[] res)
    {
        // 一个byte数据左移24位变成0x??000000，再右移8位变成0x00??0000

        int targets = (res[0] & 0xff) | ((res[1] << 8) & 0xff00) // | 表示安位或
                ;
        return targets;


    }

    public static int byteToIntresever(byte[] res)
    {
        // 一个byte数据左移24位变成0x??000000，再右移8位变成0x00??0000

        int targets = (res[1] & 0xff) | ((res[0] << 8) & 0xff00) // | 表示安位或
                ;
        return targets;


    }

    public static int byte4int(byte[] res)
    {
        // 一个byte数据左移24位变成0x??000000，再右移8位变成0x00??0000
        int targets = (res[0] & 0xff) | ((res[1] << 8) & 0xff00) // | 表示安位或
                | ((res[2] << 24) >>> 8) | (res[3] << 24);
        return targets;


    }

    public static byte[] int4byte(int res)
    {

        byte[] targets = new byte[4];
        targets[0] = (byte) (res & 0xff);// 最低位
        targets[1] = (byte) ((res >> 8) & 0xff);// 次低位
        targets[2] = (byte) ((res >> 16) & 0xff);// 次高位
        targets[3] = (byte) (res >>> 24);// 最高位,无符号右移。
        return targets;

    }

    public static byte[] int2byte(int res)
    {

        byte[] targets = new byte[2];
        targets[0] = (byte) (res & 0xFF);
        targets[1] = (byte) (res >> 8 & 0xFF);
        return targets;


    }

    public static int int2byte_re(byte[] res)
    {

        int targets = (res[1] & 0xff) | ((res[0] << 8) & 0xff00) // | 表示安位或
                ;
        return targets;


    }

    static byte[] targetG = new byte[1];

    public static byte[] int1byte(int res)
    {


        byte[] targetG = new byte[1];
        targetG[0] = (byte) (res & 0xff);// 最低位


        return targetG;


    }

    public static byte[] int1byte123(int res)
    {

        byte[] targetsU = new byte[1];
        targetsU[0] = (byte) Integer.parseInt(Integer.toHexString(res));// 最低位
        return targetsU;


    }


    /** */
    /**
     * 把字节数组转换成16进制字符串
     *
     * @param bArray
     * @return
     */

    public static final String bytesToHexString(byte[] bArray)
    {

        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++)
        {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
            {
                sb.append(0);
            }
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();


    }

    static Object O_ = new Object();

    public static byte[] hexStringToByte(String hex)
    {
        synchronized (O_)
        {
            int len = (hex.length() / 2);
            byte[] result = new byte[len];
            char[] achar = hex.toCharArray();
            for (int i = 0; i < len; i++)
            {
                int pos = i * 2;
                result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
            }
            return result;
        }
    }

    private static byte toByte(char c)
    {
        byte b = (byte) "0123456789ABCDEF".indexOf(c);
        return b;
    }


   
  




}
