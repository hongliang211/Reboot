package Util;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import Been.Msg_Info;
import Been.Result;

/**
 * Created by admin on 2018/5/25.
 */

public class HttpUtils {
    /**
     * 发送消息到服务器
     *
     * @param message ：发送的消息
     * @return：消息对象
     */
    public static Msg_Info sendMessage(String message) {
        Msg_Info chatMessage = new Msg_Info();
        String gsonResult = doGet(message);
        Gson gson = new Gson();
        Result result = null;
        if (gsonResult != null) {
            try {
                result = gson.fromJson(gsonResult, Result.class);
                chatMessage.setText_msg(result.getText());
            } catch (Exception e) {
                chatMessage.setText_msg("网络情缘一线牵，看看是否还没连！");
            }
        }

        chatMessage.setMsgtype(Msg_Info.TYPE_RECEIVED);
        return chatMessage;
    }

    /**
     * get请求
     *
     * @param message ：发送的话
     * @return：数据
     */
    public static String doGet(String message) {
        String result = "";
        String url = setParmat(message);
        System.out.println("------------url = " + url);
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
            URL urls = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urls
                    .openConnection();
            connection.setReadTimeout(5 * 1000);
            connection.setConnectTimeout(5 * 1000);
            connection.setRequestMethod("GET");

            is = connection.getInputStream();
            baos = new ByteArrayOutputStream();
            int len = -1;
            byte[] buff = new byte[1024];
            while ((len = is.read(buff)) != -1) {
                baos.write(buff, 0, len);
            }
            baos.flush();
            result = new String(baos.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 设置参数
     *
     * @param message : 信息
     * @return ： url
     */
    private static String setParmat(String message) {
        String url = "";
        try {
            url = Config.URL_KEY + "?" + "key=" + Config.APP_KEY + "&info="
                    + URLEncoder.encode(message, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }
}
