package zjnu.huawei.pcb.utils.harmony;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import zjnu.huawei.pcb.service.basic.impl.ServiceServiceImpl;

import javax.annotation.PostConstruct;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class HarmonyUtil {
    @Value("${application.jwt.appKey}")
    public String key;

    @Value("${application.jwt.appSecret}")
    public String secret;

    private static final int connectTimeout = 30000;
    private static final int socketTimeout = 10000;

    private static String appkey;
    private static String appSecret;

    @PostConstruct
    public void setSecret() {
        HarmonyUtil.appkey = this.key;
        HarmonyUtil.appSecret = this.secret;
    }


    public static JSONObject httpsRequest(String requestUrl,
                                          String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {
            // 创建SSLContext对象，并使用我们制定的信任管理器初始化
            TrustManager[] ttm = {new MyX509TrustManager()};
            SSLContext sslContent = SSLContext.getInstance("SSL", "SunJSSE");
            sslContent.init(null, ttm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContent.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection httpsUrlConn = (HttpsURLConnection) url
                    .openConnection();
            httpsUrlConn.setSSLSocketFactory(ssf);

            httpsUrlConn.setDoOutput(true);
            httpsUrlConn.setDoInput(true);
            httpsUrlConn.setUseCaches(false);
            // 设置请求方式(GET/POST)
            httpsUrlConn.setRequestMethod(requestMethod);
            if ("GET".equalsIgnoreCase(requestMethod)) {
                httpsUrlConn.connect();
            }

            // 当有数据需要提交时
            if (null != outputStr) {
                OutputStream outputStream = httpsUrlConn.getOutputStream();
                // 编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpsUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(
                    inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(
                    inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            httpsUrlConn.disconnect();
            System.out.println(buffer.toString());

            jsonObject = JSONObject.parseObject(buffer.toString());
        } catch (ConnectException e) {
            e.printStackTrace();
            System.out.println("weixin server connection timed out");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonObject;
    }


    public static JSONObject sendPostJson(String url, String json) throws IOException {

        HttpPost httpPost = new HttpPost(url);
        StringEntity postEntity = new StringEntity(json, "UTF-8");
        httpPost.addHeader("Content-Type", "application/json");
        httpPost.setEntity(postEntity);
        //设置请求器的配置
        RequestConfig requestConfig = RequestConfig.custom().
                setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
        httpPost.setConfig(requestConfig);

        HttpClient httpClient = HttpClients.createDefault();
        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity, "UTF-8");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("headers", response.getAllHeaders());
        jsonObject.put("body", JSONObject.parseObject(result));
        return jsonObject;
    }

    public static JSONObject sendPostFile(String url, MultipartFile file) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", "multipart/form-data");
        headers.set("x-auth-token", ServiceServiceImpl.serviceToken.getString("token"));
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        InputStream inputStream = file.getInputStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
        }
        byte[] byteArray = outputStream.toByteArray();
        String filename = file.getOriginalFilename();
        map.add("file", new ByteArrayResource(byteArray){
            @Override
            public String getFilename() {
                return filename; // 设置文件名
            }
        });
        org.springframework.http.HttpEntity<MultiValueMap<String, Object>> param = new org.springframework.http.HttpEntity<>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, param, String.class);
        String body = response.getBody();
        return JSONObject.parseObject(body);
    }

    public static JSONObject sendGet(String urlParam) throws IOException {
        // 创建httpClient实例对象
        HttpGet httpGet = new HttpGet(urlParam);
        // 设置httpClient连接主机服务器超时时间：15000毫秒
        RequestConfig requestConfig = RequestConfig.custom().
                setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
        httpGet.setConfig(requestConfig);

        HttpClient httpClient = HttpClients.createDefault();
        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity, "UTF-8");
        return JSONObject.parseObject(result);
    }

    public static String httpRequest(String reqUrl, String method, String content) throws Exception {
        URL url;
        // 打开和URL之间的连接
        url = new URL(reqUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestProperty("x-auth-token", ServiceServiceImpl.serviceToken.getString("token"));
        // Read from the connection. Default is true.
        connection.setDoInput(true);
        // Set the post method. Default is GET
        connection.setRequestMethod(method);
        // Post cannot use caches
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);
        connection.connect();
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        // The URL-encoded contend
        out.write(content.getBytes("UTF-8"));
        out.flush();
        out.close(); // flush and close
        // 定义 BufferedReader输入流来读取URL的响应
        BufferedReader reader = new BufferedReader
                (new InputStreamReader(connection.getInputStream(), "utf-8"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();
        connection.disconnect();

        return sb.toString();
    }

    public static JSONObject sendRequest(String url, String method, JSONObject jsonObject) throws Exception {
        String request = httpRequest(url, method, jsonObject.toJSONString());
        return JSONObject.parseObject(request);
    }

    public static JSONObject verifyIdToken(String idToken) throws Exception {
        String url = "https://oauth-login.cloud.huawei.com/oauth2/v3/tokeninfo";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id_token", idToken);
        JSONObject request = sendRequest(url, "post", jsonObject);
        return request;
    }
}
