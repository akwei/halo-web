package halo.util.http.httpclient4_1;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

public class HttpClient4Util {

    private HttpClient httpclient;

    public static HttpClient4Util createDefault() {
        return new HttpClient4Util();
    }

    public HttpClient4Util(int timeout) {
        httpclient = new DefaultHttpClient();
        httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
                timeout);
    }

    public HttpClient4Util() {
        this(30000);
    }

    public void setHttpclient(HttpClient httpclient) {
        this.httpclient = httpclient;
    }

    public HttpClient getHttpclient() {
        return httpclient;
    }

    public HttpResp execute(HttpRequestBase request, boolean string,
            String charset) throws ClientProtocolException, IOException {
        HttpEntity entity = null;
        try {
            HttpResponse httpResponse = httpclient.execute(request);
            HttpResp httpResp = new HttpResp();
            httpResp.setStatusCode(httpResponse.getStatusLine().getStatusCode());
            entity = httpResponse.getEntity();
            if (string) {
                httpResp.setValue(EntityUtils.toString(entity, charset));
            }
            else {
                httpResp.setBytes(EntityUtils.toByteArray(entity));
            }
            return httpResp;
        }
        catch (ClientProtocolException e) {
            throw e;
        }
        catch (IOException e) {
            throw e;
        }
        finally {
            if (entity != null) {
                EntityUtils.consume(entity);
            }
        }
    }

    public HttpResp doGet(String url, boolean string, String charset)
            throws ClientProtocolException, IOException {
        HttpGet httpGet = new HttpGet(url);
        return this.execute(httpGet, string, charset);
    }

    public HttpResp doPostBody(String url, String value, boolean string,
            String charset) throws ClientProtocolException, IOException {
        HttpEntity entity = new StringEntity(value, charset);
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(entity);
        return this.execute(httpPost, string, charset);
    }

    public HttpResp doPost(String url, HttpParameter httpParameter,
            boolean string, String charset) throws ClientProtocolException,
            IOException {
        HttpPost httpPost = new HttpPost(url);
        if (httpParameter.isFileParameterEmpty()) {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            for (BasicParameter e : httpParameter.getBasicParameters()) {
                nameValuePairs.add(new BasicNameValuePair(e.getName(), e
                        .getValue()));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, charset));
        }
        else {
            MultipartEntity reqEntity = new MultipartEntity();
            for (FileParameter e : httpParameter.getFileParameters()) {
                reqEntity.addPart(e.getName(), new FileBody(e.getFile()));
            }
            for (BasicParameter e : httpParameter.getBasicParameters()) {
                reqEntity.addPart(e.getName(), new StringBody(e.getValue(),
                        Charset.forName(charset)));
            }
            httpPost.setEntity(reqEntity);
        }
        return this.execute(httpPost, string, charset);
    }

    public void close(HttpResponse httpResponse) throws IOException {
        HttpEntity entity = httpResponse.getEntity();
        if (entity != null) {
            EntityUtils.consume(entity);
        }
    }

    public void shutdown() {
        this.httpclient.getConnectionManager().shutdown();
    }

    public static void main(String[] args) throws ClientProtocolException,
            IOException {
        HttpClient4Util util = new HttpClient4Util(10000);
        HttpResp httpResp = util.doGet("http://www.yibao.com", true, "utf-8");
        System.out.println(httpResp.getValue());
    }
}