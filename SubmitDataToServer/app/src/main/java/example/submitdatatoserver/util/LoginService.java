package example.submitdatatoserver.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import static example.submitdatatoserver.util.StreamTools.readInputStream;

/**
 * 作者：国富小哥
 * 日期：2017/6/24
 * Created by Administrator
 */

public class LoginService {
    /**
     * 使用HttpURLConnectionGET方式提交
     * */
    public static String loginByGet(String username,String password){
        //拼接url
        //http://192.168.91.1:8080/Web/LoginServlet?username=zhangsan&password=123
        try {
            //为了防止乱码 这里需要将参数进行编码
            String path="http://192.168.91.1:8080/Web/LoginServlet?username="+ URLEncoder.encode(username,"UTF-8")+"&password="+URLEncoder.encode(password);
            //创建URL实例
            URL url=new URL(path);
            //获取HttpUrlConnection对象
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            int code=conn.getResponseCode();
            if (code==200){
                InputStream in=conn.getInputStream();
                //调用StreamTools的方法解析输入流
                String result= readInputStream(in);
                //返回字符串
                return result;
            }else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 使用HttpUrlConnection POST方式提交数据
     * */
    public static String loginByPost(String username,String password){
        try {
            //要访问的资源路径
            String path="http://192.168.91.1:8080/Web/LoginServlet";
            //创建URL的实例
            URL url=new URL(path);
            //获取HttpURLConnection对象
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置超时时间
            conn.setConnectTimeout(5000);
            //指定请求方式
            conn.setRequestMethod("POST");
            //准备数据 将参数编码
            String data = "username=" + URLEncoder.encode(username)
                    + "&password=" + URLEncoder.encode(password);
            //设置请求头
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", data.length() + "");
            //设置允许向外写入数据
            conn.setDoOutput(true);
            //得到输出流
            OutputStream out=conn.getOutputStream();
            //将数据写入输出流上
            out.write(data.getBytes());
            //获取状态码
            int code=conn.getResponseCode();
            if (code==200){
            //得到服务器返回的输入流
           InputStream in=conn.getInputStream();
                //调用StreamTools的方法解析输入流
                String result=StreamTools.readInputStream(in);
                return result;

            }else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * OkHttp GET的方式提交数据
     * */
    public static void loginByOkhttpGet(String username, String password, Callback callback){
        //为了防止乱码 这里需要将参数进行编码
        try {
            String path = "http://192.168.91.1:8080/Web/LoginServlet?username="+ URLEncoder.encode(username,"UTF-8")+"&password="+URLEncoder.encode(password);
            //创建URL实例
            URL url=new URL(path);
            //创建okhttpclient
            OkHttpClient client=new OkHttpClient();
            Request request=new Request.Builder().url(url).build();
            //异步方式
            client.newCall(request).enqueue(callback);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * OkHttp POST的方式提交数据
     * */
    public static void loginByOkhttpPost(String username, String password, Callback callback){
        try {
            //要访问的资源路径
            String path="192.168.91.1:8080/Web/LoginServlet";
            //创建URL实例
            URL url=new URL(path);
            //创建okhttpclient
            OkHttpClient client=new OkHttpClient();
            //先构建一个RequestBody
            RequestBody requestBody=new FormBody.Builder().add("username",URLEncoder.encode(username))
                    .add("password",URLEncoder.encode(password))
                    .build();
            Request request=new Request.Builder().url(url)
                    .post(requestBody)
                    .build();
            //异步方式
            client.newCall(request).enqueue(callback);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
