package util;



import bean.JmBean.PostResponse;
import com.taobao.api.internal.util.StringUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.http.Consts;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;


import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.security.KeyStore;
import java.util.Map;

/**
 * HttpUtils
 *
 * @author Jonas, 5/11/2015. 7/8/2015. 2015-12-11.
 * @version 1.3.0
 * @since 1.0.0
 */
public class HttpUtils {

    private final static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    static {
        HttpsURLConnection.setDefaultHostnameVerifier((s, sslSession) -> true);
    }

    public static String post(String url, String param) {

        HttpURLConnection connection = null;
        try {
            connection = sendPost(url, param);

            try (InputStream inputStream = connection.getInputStream()) {
                return readConnection(inputStream);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (connection != null)
                connection.disconnect();
        }

        return null;
    }

    public static String postProxy(String url, String param, boolean isProxy) {

        HttpURLConnection connection = null;
        try {
            connection = sendPost(url, param, 60000, 120000, isProxy);

            try (InputStream inputStream = connection.getInputStream()) {
                return readConnection(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null)
                connection.disconnect();
        }

        return null;
    }

    public static String post(String url, String param, int connectTimeout, int readTimeout) {

        HttpURLConnection connection = null;
        try {
            connection = sendPost(url, param, connectTimeout, readTimeout);

            try (InputStream inputStream = connection.getInputStream()) {
                return readConnection(inputStream);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (connection != null)
                connection.disconnect();
        }

        return null;
    }

    public static String postWithException(String url, String param) throws IOException {

        HttpURLConnection connection = null;
        try {
            connection = sendPost(url, param);

            try (InputStream inputStream = connection.getInputStream()) {
                return readConnection(inputStream);
            }
        } finally {
            if (connection != null)
                connection.disconnect();
        }
    }

    public static String get(String url) {

        HttpURLConnection connection = null;
        try {
            connection = getConnection(url, "GET");

            connection.connect();

            try (InputStream inputStream = connection.getInputStream()) {
                return readConnection(inputStream);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (connection != null)
                connection.disconnect();
        }

        return null;
    }

    public static String get(String url, String param, String authorization) {
        if (!StringUtils.isEmpty(param)) url += "?" + param;
        HttpURLConnection connection = null;
        try {
            connection = getConnection(url, "GET");
            connection.setRequestProperty("Authorization", "Basic " + authorization);
            connection.connect();
            try (InputStream inputStream = connection.getInputStream()) {
                return readConnection(inputStream);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (connection != null)
                connection.disconnect();
        }

        return null;
    }


    public static String post(String url, String jsonBody, String accept, String token, HttpHost proxyhttpHost) throws Exception {
        HttpPost post = new HttpPost(new URI(url));

        // setHeader Accept
        post.setHeader("Accept", StringUtils.isEmpty(accept) ? "application/json" : accept);
        // setHeader Authorization
        if (!StringUtils.isEmpty(token)) post.setHeader("Authorization", "Bearer " + token);

        //setBody
        post.setEntity(new StringEntity(jsonBody, ContentType.APPLICATION_JSON));

        //测试启用代理
        if (proxyhttpHost != null) post.setConfig(RequestConfig.custom().setProxy(proxyhttpHost).build());

        //post request
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpResponse response = httpclient.execute(post);

        //从服务器获得输入流
        BufferedReader buffer = new BufferedReader(new InputStreamReader(response.getEntity().getContent()), 10 * 1024);
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = buffer.readLine()) != null) {
            sb.append(line);
        }

        //关闭流
        buffer.close();
        return sb.toString();
    }

    public static String targetGet(String url, String jsonBody, String accept, String token, HttpHost proxyhttpHost) throws Exception {
        HttpGet get = new HttpGet(new URI(url));
        // setHeader Accept
        get.setHeader("Accept", StringUtils.isEmpty(accept) ? "application/json" : accept);
        // setHeader Authorization
        if (!StringUtils.isEmpty(token)) get.setHeader("Authorization", "Bearer " + token);
        if (proxyhttpHost != null) get.setConfig(RequestConfig.custom().setProxy(proxyhttpHost).build());
        //post request
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpResponse response = httpclient.execute(get);
        //从服务器获得输入流
        BufferedReader buffer = new BufferedReader(new InputStreamReader(response.getEntity().getContent()), 10 * 1024);
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = buffer.readLine()) != null) {
            sb.append(line);
        }
        //关闭流
        buffer.close();
        return sb.toString();
    }

    public static String targetPut(String url, String jsonBody, String accept, String token, HttpHost proxyhttpHost) throws Exception {
        HttpPut put = new HttpPut(new URI(url));
        // setHeader Accept
        put.setHeader("Accept", StringUtils.isEmpty(accept) ? "application/json" : accept);
        //setBody
        put.setEntity(new StringEntity(jsonBody, ContentType.APPLICATION_JSON));
        // setHeader Authorization
        if (!StringUtils.isEmpty(token)) put.setHeader("Authorization", "Bearer " + token);
        if (proxyhttpHost != null) put.setConfig(RequestConfig.custom().setProxy(proxyhttpHost).build());
        //post request
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpResponse response = httpclient.execute(put);
        //从服务器获得输入流
        BufferedReader buffer = new BufferedReader(new InputStreamReader(response.getEntity().getContent()), 10 * 1024);
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = buffer.readLine()) != null) {
            sb.append(line);
        }
        //关闭流
        buffer.close();
        return sb.toString();
    }

    public static String targetDelete(String url, String accept, String token, HttpHost proxyhttpHost) throws Exception {
        HttpDelete delete = new HttpDelete(new URI(url));
        // setHeader Accept
        delete.setHeader("Accept", StringUtils.isEmpty(accept) ? "application/json" : accept);
        // setHeader Authorization
        if (!StringUtils.isEmpty(token)) delete.setHeader("Authorization", "Bearer " + token);
        if (proxyhttpHost != null) delete.setConfig(RequestConfig.custom().setProxy(proxyhttpHost).build());
        //post request
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpResponse response = httpclient.execute(delete);
        //从服务器获得输入流
        BufferedReader buffer = new BufferedReader(new InputStreamReader(response.getEntity().getContent()), 10 * 1024);
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = buffer.readLine()) != null) {
            sb.append(line);
        }
        //关闭流
        buffer.close();
        return sb.toString();
    }

    public static String put(String url, String jsonParam, String authorization) {

        InputStream input = null;//输入流
        InputStreamReader isr = null;
        BufferedReader buffer = null;
        StringBuilder sb;
        String line;

        try {
                /*post向服务器请求数据*/
            HttpPut request = new HttpPut(url);
            StringEntity se = new StringEntity(jsonParam);
            request.setEntity(se);
            se.setContentEncoding("UTF-8");
            se.setContentType("application/json");
            request.setHeader("Authorization", "Basic " + authorization);
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpResponse response = httpclient.execute(request);
            //int code = response.getStatusLine().getStatusCode();
            // System.out.println("postCode= " + code);

            //从服务器获得输入流
            input = response.getEntity().getContent();
            isr = new InputStreamReader(input);
            buffer = new BufferedReader(isr, 10 * 1024);

            sb = new StringBuilder();
            while ((line = buffer.readLine()) != null) {
                sb.append(line);
            }

            return sb.toString();

        } catch (Exception e) {
            //其他异常同样读取assets目录中的"local_stream.xml"文件
            System.out.println("请求异常数据异常");
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            try {
                if (buffer != null) {
                    buffer.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (input != null) {
                    input.close();
                }
            } catch (Exception ignored) {
            }
        }
    }

    public static String patch(String url, String jsonParam, String authorization) {

        InputStream input = null;//输入流
        InputStreamReader isr = null;
        BufferedReader buffer = null;
        StringBuilder sb;
        String line;

        try {
                /*post向服务器请求数据*/
            HttpPatch request = new HttpPatch(url);
            StringEntity se = new StringEntity(jsonParam);
            request.setEntity(se);
            se.setContentEncoding("UTF-8");
            se.setContentType("application/json");
            request.setHeader("Authorization", "Basic " + authorization);
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpResponse response = httpclient.execute(request);
            // int code = response.getStatusLine().getStatusCode();
            // System.out.println("postCode= " + code);

            //从服务器获得输入流
            input = response.getEntity().getContent();
            isr = new InputStreamReader(input);
            buffer = new BufferedReader(isr, 10 * 1024);

            sb = new StringBuilder();
            while ((line = buffer.readLine()) != null) {
                sb.append(line);
            }

            return sb.toString();
        } catch (Exception e) {
            //其他异常同样读取assets目录中的"local_stream.xml"文件
            System.out.println("请求异常数据异常");
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            try {
                if (buffer != null) {
                    buffer.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (input != null) {
                    input.close();
                }
            } catch (Exception ignored) {
            }
        }
    }


    public static String get(String url, String param) {

        if (!StringUtils.isEmpty(param)) url += "?" + param;

        HttpURLConnection connection = null;
        try {
            connection = getConnection(url, "GET");

            connection.connect();

            try (InputStream inputStream = connection.getInputStream()) {
                return readConnection(inputStream);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (connection != null)
                connection.disconnect();
        }

        return null;
    }

    /**
     * 使用 get 方式，获取输入流
     *
     * @param url   地址
     * @param param 参数
     * @return 输入流
     * @throws IOException
     */
    public static InputStream getInputStream(String url, String param) throws IOException {
        if (!StringUtils.isEmpty(param)) url += "?" + param;

        HttpURLConnection connection = getConnection(url, "GET");

        connection.connect();

        return connection.getInputStream();
    }

    /**
     * 使用 get 方式，获取输入流(Https)
     *
     * @param url   地址
     * @param param 参数
     * @return 输入流
     * @throws IOException
     */
    public static InputStream getHttpsInputStream(String url, String param) throws IOException {
        if (!StringUtils.isEmpty(param)) url += "?" + param;

        HttpsURLConnection connection = getHttpsConnection(url, "GET");

        connection.connect();

        return connection.getInputStream();
    }

    /**
     * 使用 post method 发送 http 请求
     *
     * @param url            请求地址
     * @param param          请求参数
     * @param connectTimeout 尝试打开连接的超时时间
     * @param readTimeout    读取连接内容的超时时间
     * @return http 连接
     * @throws IOException
     */
    private static HttpURLConnection sendPost(String url, String param, int connectTimeout, int readTimeout, boolean isProxy) throws IOException {

        // 打开和URL之间的连接
        HttpURLConnection connection = getConnection(url, "POST", connectTimeout, readTimeout, isProxy);

        // 发送POST请求必须设置如下两行
        connection.setDoOutput(true);
        connection.setDoInput(true);

        // 获取URLConnection对象对应的输出流
        try (OutputStream outputStream = connection.getOutputStream();
             PrintWriter printWriter = new PrintWriter(outputStream)) {

            // 发送请求参数
            printWriter.print(param);
            printWriter.flush();
        }

        connection.connect();

        return connection;
    }

    private static HttpURLConnection sendPost(String url, String param, int connectTimeout, int readTimeout) throws IOException {

        return sendPost(url, param, connectTimeout, readTimeout, false);
    }

    /**
     * 使用 post method 发送 http 请求, 超时配置默认为 60000, 120000
     *
     * @param url   请求地址
     * @param param 请求参数
     * @return http 连接
     * @throws IOException
     */
    private static HttpURLConnection sendPost(String url, String param) throws IOException {
        return sendPost(url, param, 60000, 120000);
    }

    /**
     * 以默认的 UTF－8 读取结果
     *
     * @param inputStream 连接的输入流
     * @return 解码的字符串
     * @throws IOException
     */
    private static String readConnection(InputStream inputStream) throws IOException {

        return readConnection(inputStream, "UTF-8");
    }

    /**
     * 从连接的输入流里读取结果
     *
     * @param inputStream 返回的输入流
     * @param encoding    编码方式
     * @return 解码的字符串
     * @throws IOException
     */
    private static String readConnection(InputStream inputStream, String encoding) throws IOException {

        try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream, encoding);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

            StringBuilder result = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) result.append(line);

            return result.toString();
        }
    }

    /**
     * 获取一个还没有打开的连接
     *
     * @param location 需要打开的地址
     * @param method   http 连接的方法 GET/POST
     * @return HttpURLConnection
     * @throws IOException
     */
    private static HttpURLConnection getConnection(String location, String method) throws IOException {

        return getConnection(location, method, 1000000, 1000000, false);
    }

    private static HttpURLConnection getConnection(String location, String method, int connectTimeout, int readTimeout) throws IOException {

        return getConnection(location, method, 1000000, 1000000, false);
    }

    private static HttpURLConnection getConnection(String location, String method, int connectTimeout, int readTimeout, boolean isProxy) throws IOException {

        URL url = new URL(location);

        // 打开和URL之间的连接
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // TEST CODE
//        HttpURLConnection connection;
//        // 打开和URL之间的连接
//        if(isProxy){
//            Proxy proxy = new Proxy(Proxy.Type.DIRECT.HTTP, new InetSocketAddress("121.41.58.229", 7656));
//            connection = (HttpURLConnection) url.openConnection(proxy);
//        }else{
//            connection = (HttpURLConnection) url.openConnection();
//        }

        connection.setRequestMethod(method);

        //设置连接超时
        connection.setConnectTimeout(connectTimeout);
        connection.setReadTimeout(readTimeout);
        // 设置通用的请求属性
        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

        return connection;
    }

    public static String PostSoap(String postUrl, String soap_action, String send_soap) throws Exception {
        String ret;
        URL url = new URL(postUrl);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setConnectTimeout(30000);
        // 设置从主机读取数据超时（单位：毫秒）
        http.setReadTimeout(30000);
        http.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows XP; DigExt)");
        http.setRequestProperty("Content-Length", String.valueOf(send_soap.length()));
        http.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
//	        _httpConn.setRequestProperty("soapActionString",_soap_action_str);
        http.setRequestProperty("SOAPAction", soap_action);
        http.setRequestMethod("POST");
        http.setDoInput(true);
        http.setDoOutput(true);
        http.setUseCaches(false);

        http.connect();

        OutputStream out = http.getOutputStream();
        out.write(send_soap.getBytes());
        out.flush();
        out.close();

        int code = http.getResponseCode();
        if (code == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line).append("\n");
            }
            in.close();
            ret = sb.toString();
        } else {
            throw new Exception("Request 异常  Response Code: " + String.valueOf(code));
        }
        return ret;

    }

    /*
     * 带Session的HttpPost请求
     */
    public static PostResponse PostSoapFull(String postUrl, String soap_action, String send_soap, String session) throws Exception {

        URL url = new URL(postUrl);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setConnectTimeout(30000);

        // 设置从主机读取数据超时（单位：毫秒）
        http.setReadTimeout(30000);
        http.setRequestProperty("Content-Length", String.valueOf(send_soap.length()));
        http.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
        http.setRequestProperty("SOAPAction", soap_action);
        if (session != null && !"".equals(session)) {
            http.setRequestProperty("Cookie", session);
        }

        http.setRequestMethod("POST");
        http.setDoInput(true);
        http.setDoOutput(true);
        http.setUseCaches(false);

        PostResponse res = new PostResponse();

        OutputStreamWriter out = new OutputStreamWriter(http.getOutputStream(), "UTF-8");
        out.append(send_soap);
        out.flush();
        out.close();

        int code = http.getResponseCode();
        if (code == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream()));
            String line;
            StringBuilder buffer = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            reader.close();
            http.disconnect();

            //获取 sessionid
            String sessionId = http.getHeaderField("Set-Cookie");
            if (sessionId != null && !"".equals(sessionId)) {
                sessionId = sessionId.substring(0, sessionId.indexOf(";"));
                res.setSession(sessionId);
            }
            res.setResult(buffer.toString());
        } else {
            throw new Exception("Request 异常  Response Code: " + String.valueOf(code));
        }
        return res;

    }

    public static String get(String url, String clientTrustCerFile, String clientTrustCerPwd, String clientKeyPwd) {

        SSLSocketFactory ssf = getSsf(clientTrustCerFile, clientTrustCerPwd, clientKeyPwd);

        HttpsURLConnection connection = null;
        try {

            connection = getHttpsConnection(url, "GET");

            if (ssf != null) {
                connection.setSSLSocketFactory(ssf);
            }

            connection.connect();

            try (InputStream inputStream = connection.getInputStream()) {
                return readConnection(inputStream);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (connection != null)
                connection.disconnect();
        }

        return null;
    }

    public static String get(String url, String param, String clientTrustCerFile, String clientTrustCerPwd, String clientKeyPwd) {

        SSLSocketFactory ssf = getSsf(clientTrustCerFile, clientTrustCerPwd, clientKeyPwd);

        if (!StringUtils.isEmpty(param)) url += "?" + param;

        HttpsURLConnection connection = null;
        try {

            connection = getHttpsConnection(url, "GET");

            if (ssf != null) {
                connection.setSSLSocketFactory(ssf);
            }

            connection.connect();

            try (InputStream inputStream = connection.getInputStream()) {
                return readConnection(inputStream);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (connection != null)
                connection.disconnect();
        }

        return null;
    }

    /**
     * 获取一个还没有打开的连接
     *
     * @param location 需要打开的地址
     * @param method   http 连接的方法 GET/POST
     * @return HttpURLConnection
     * @throws IOException
     */
    private static HttpsURLConnection getHttpsConnection(String location, String method) throws IOException {

        return getHttpsConnection(location, method, 10000, 10000);
    }

    private static HttpsURLConnection getHttpsConnection(String location, String method, int connectTimeout, int readTimeout) throws IOException {

        URL url = new URL(location);

        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod(method);

        //设置连接超时
        connection.setConnectTimeout(connectTimeout);
        connection.setReadTimeout(readTimeout);
        // 设置通用的请求属性
        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("user-agent",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

        return connection;
    }

    public static SSLSocketFactory getSsf(String clientTrustCerFile, String clientTrustCerPwd, String clientKeyPwd) {

        SSLSocketFactory ssf;

        try {
            // 实例化 SSL 上下文  arg1:protocol arg2:provider
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");

            // 实例化一个 key store
            //Trust Key Store
            KeyStore keyStore = KeyStore.getInstance("JKS");
            // clientTrustCerFile 是证书路劲
            // clientTrustPwd 是证书的密码
            keyStore.load(new FileInputStream(new File(clientTrustCerFile)),
                    clientTrustCerPwd.toCharArray());

            //构建TrustManager
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
            trustManagerFactory.init(keyStore);
            TrustManager[] tms = trustManagerFactory.getTrustManagers();

            //构建Key Manager
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
            keyManagerFactory.init(keyStore, clientKeyPwd.toCharArray());
            KeyManager[] kms = keyManagerFactory.getKeyManagers();

            //初始化 SSL 上下文
            sslContext.init(kms, tms, null);

            //-----注入SSL Context
            ssf = sslContext.getSocketFactory();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
        return ssf;
    }

    public static String post(String url, String param, String clientTrustCerFile, String clientTrustCerPwd, String clientKeyPwd) throws IOException {

        String readConent = "";
        SSLSocketFactory ssf = getSsf(clientTrustCerFile, clientTrustCerPwd, clientKeyPwd);

        HttpsURLConnection connection = null;
        try {
            connection = sendHttpsPost(url, param, ssf);

            try (InputStream inputStream = connection.getInputStream()) {
                readConent = readConnection(inputStream);
            }

            connection.disconnect();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            if (connection != null) {
                connection.disconnect();
            }
            throw e;
        }

        return readConent;
    }

    /**
     * 使用 post method 发送 http 请求
     *
     * @param url   请求地址
     * @param param 请求参数
     * @return http 连接
     * @throws IOException
     */
    private static HttpsURLConnection sendHttpsPost(String url, String param, SSLSocketFactory ssf) throws IOException {

        // 打开和URL之间的连接
        HttpsURLConnection connection = getHttpsConnection(url, "POST");

        if (ssf != null) {
            connection.setSSLSocketFactory(ssf);
        }

        // 发送POST请求必须设置如下两行
        connection.setDoOutput(true);
        connection.setDoInput(true);

        // 获取URLConnection对象对应的输出流
        try (OutputStream outputStream = connection.getOutputStream();
             PrintWriter printWriter = new PrintWriter(outputStream)) {

            // 发送请求参数
            printWriter.print(param);
            printWriter.flush();
        }

        connection.connect();

        return connection;
    }

    /**
     * 根据url获取输入流
     *
     * @param urlString 地址
     * @return 输入流
     * @throws IOException
     */
    public static InputStream getInputStream(String urlString) throws IOException {
        URL url = new URL(urlString);

        return url.openStream();
    }


    /**
     * @param content
     * @param charset
     * @param url
     * @return
     * @throws Exception
     */
    public static String HttpPost(String content, String charset, String url) throws Exception {

        HttpURLConnection http = null;
        OutputStream output = null;
        BufferedReader in = null;
        String ret = null;

        try {
            URL u = new URL(url);
            http = (HttpURLConnection) u.openConnection();

            http.setDoOutput(true);
            http.setDoInput(true);
            // 设置连接主机超时（单位：毫秒）

            http.setConnectTimeout(10000);
            // 设置从主机读取数据超时（单位：毫秒）
            http.setReadTimeout(10000);
            // 设定请求的方法为"POST"，默认是GET
            http.setRequestMethod("POST");
            // 设定传送的内容类型
            http.setRequestProperty("Content-Type", "text/xml");
            http.connect();
            output = http.getOutputStream();
            if (charset != null) {
                output.write(content.getBytes(charset));
            } else {
                output.write(content.getBytes());
            }
            output.flush();
            output.close();

            StringBuilder sb = new StringBuilder();
            // 将内存缓冲区中封装好的完整的HTTP请求电文发送到服务端

            if (charset != null) {
                in = new BufferedReader(new InputStreamReader(http.getInputStream(), charset));
            } else {
                in = new BufferedReader(new InputStreamReader(http.getInputStream()));
            }

            String line = "";
            while ((line = in.readLine()) != null) {
                sb.append(line).append("\n");
            }
            in.close();
            http.disconnect();
            ret = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            if (output != null) {
                try {
                    output.close();
                } catch (IOException ex) {
                    e.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                    e.printStackTrace();
                }
            }
            if (http != null) {
                http.disconnect();
            }
            // 异常发生
            throw new Exception("HttpPost fail " + e.getMessage());
        }
        return ret;
    }

    /**
     * @param postUrl
     * @param soap_action
     * @param send_soap
     * @return
     * @throws Exception
     */
    public static String SOAPHttp(String postUrl, String soap_action, String send_soap) throws Exception {
        String ret = "";
        URL url = new URL(postUrl);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setConnectTimeout(30000);
        // 设置从主机读取数据超时（单位：毫秒）
        http.setReadTimeout(30000);
        http.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows XP; DigExt)");
        http.setRequestProperty("Content-Length", String.valueOf(send_soap.length()));
        http.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
//	        _httpConn.setRequestProperty("soapActionString",_soap_action_str);
        http.setRequestProperty("SOAPAction", soap_action);
        http.setRequestMethod("POST");
        http.setDoInput(true);
        http.setDoOutput(true);
        http.setUseCaches(false);

        http.connect();

        OutputStream out = http.getOutputStream();
        out.write(send_soap.getBytes());
        out.flush();
        out.close();

        int code = 0;
        code = http.getResponseCode();
        if (code == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = in.readLine()) != null) {
                sb.append(line).append("\n");
            }
            in.close();
            ret = sb.toString();


        } else {
            throw new Exception("Request 异常  Response Code: " + String.valueOf(code));
        }
        return ret;

    }

    public enum HttpMethod {
        GET, POST, PUT, PATCH, DELETE
    }

    /**
     * 通用http访问
     * 支持常见的http请求
     *
     * @param url           http地址
     * @param jsonBody      json内容
     * @param headers       header内容
     * @param proxyhttpHost 是否代理
     * @return 响应
     * @throws Exception (by aooer)
     */
    public static String execute(HttpMethod method, String url, String jsonBody, Map<String, String> headers, HttpHost proxyhttpHost) throws Exception {
        return execute(method, url, jsonBody, headers, proxyhttpHost, null);
    }

    /**
     * soap request (by aooer)
     */
    public static String execute(HttpMethod method, String url, String xmlBody, Map<String, String> headers) throws Exception {
        return execute(method, url, xmlBody, ContentType.create("text/xml", Consts.UTF_8), headers, null, null);
    }

    /**
     * 通用http访问
     * 支持常见的http请求
     *
     * @param url           http地址
     * @param jsonBody      json内容
     * @param headers       header内容
     * @param proxyhttpHost 是否代理
     * @return 响应
     * @throws Exception (by aooer)
     */
    public static String execute(HttpMethod method, String url, String jsonBody, Map<String, String> headers, HttpHost proxyhttpHost, org.apache.http.client.CookieStore cookieStore) throws Exception {
        return execute(method, url, jsonBody, ContentType.APPLICATION_JSON, headers, proxyhttpHost, cookieStore);
    }

    private static String execute(HttpMethod method, String url, String jsonBody, ContentType bodyType, Map<String, String> headers, HttpHost proxyhttpHost, org.apache.http.client.CookieStore cookieStore) throws Exception {
        HttpRequestBase request = null;

        // match method
        if (method.equals(HttpMethod.GET)) request = new HttpGet(new URI(url));
        if (method.equals(HttpMethod.DELETE)) request = new HttpDelete(new URI(url));
        if (method.equals(HttpMethod.POST)) request = new HttpPost(new URI(url));
        if (method.equals(HttpMethod.PATCH)) request = new HttpPatch(new URI(url));
        if (method.equals(HttpMethod.PUT)) request = new HttpPut(new URI(url));

        // assert
        Assert.notNull(request, "Http请求方法不合法");

        // set headers
        if (!MapUtils.isEmpty(headers))
            for (Map.Entry<String, String> e : headers.entrySet()) request.setHeader(e.getKey(), e.getValue());

        // set body
        if (!StringUtils.isEmpty(jsonBody) && request instanceof HttpEntityEnclosingRequestBase)
            ((HttpEntityEnclosingRequestBase) request).setEntity(new StringEntity(jsonBody, bodyType));

        // set proxy
        if (proxyhttpHost != null) request.setConfig(RequestConfig.custom().setProxy(proxyhttpHost).build());

        // execute
        CloseableHttpClient httpclient = HttpClients.createDefault();

        if (cookieStore != null) {
            httpclient = HttpClients.custom()
                    .setDefaultCookieStore(cookieStore).build();
        }

        HttpResponse response = httpclient.execute(request);

        // assert
        Assert.notNull(response.getEntity(), "Http响应内容为空");
        // build response
        BufferedReader buffer = new BufferedReader(new InputStreamReader(response.getEntity().getContent()), 10 * 1024);
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = buffer.readLine()) != null)
            sb.append(line);

        // close
        buffer.close();
        return sb.toString();
    }

}
