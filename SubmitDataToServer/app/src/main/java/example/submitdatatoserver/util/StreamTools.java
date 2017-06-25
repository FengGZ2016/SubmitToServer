package example.submitdatatoserver.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 作者：国富小哥
 * 日期：2017/6/24
 * Created by Administrator
 */

public class StreamTools {
    /**
     * 输入流转化为字符串
     * */
    public static String readInputStream(InputStream in){
        try {
            //字节数组流，可以捕获内存缓冲区的数据，转换成字节数组。
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            int len=0;
            byte[] buffer=new byte[1024];
            while ((len=in.read(buffer))!=-1){
                byteArrayOutputStream.write(buffer,0,len);
            }
            in.close();
            byteArrayOutputStream.close();
            //获取内存缓冲中的数据
            byte[] result=byteArrayOutputStream.toByteArray();
            //解析result中的字符串
            String temp=new String(result);
            //返回字符串
            return temp;

        } catch (IOException e) {
            e.printStackTrace();
            return "解析失败";
        }

    }
}
