package com.enlinkmob.test;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OauthTask {

//        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
//
//        connManager.setMaxTotal(200);
//        connManager.setDefaultMaxPerRoute(10);
//
//        CloseableHttpClient client = HttpClients.custom()
//                .setConnectionManager(connManager).build();	

    // 线程池
    private ExecutorService exe = null;
    // 线程池的容量
    private static final int POOL_SIZE = 20;
    private HttpClient client = null;
    String urls = null;

    public OauthTask(String urls) {
        this.urls = urls;
    }

    public void test() throws Exception {
        exe = Executors.newFixedThreadPool(POOL_SIZE);
        HttpParams params = new BasicHttpParams();
                /* 从连接池中取连接的超时时间 */
        ConnManagerParams.setTimeout(params, 1000);
                /* 连接超时 */
        HttpConnectionParams.setConnectionTimeout(params, 2000);
                /* 请求超时 */
        HttpConnectionParams.setSoTimeout(params, 4000);
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(
                new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));

        //ClientConnectionManager cm = new PoolingClientConnectionManager(schemeRegistry);
        PoolingClientConnectionManager cm = new PoolingClientConnectionManager(schemeRegistry);
        cm.setMaxTotal(10);
        final HttpClient httpClient = new DefaultHttpClient(cm, params);

        // URIs to perform GETs on
        final String urisToGet = urls;
//                // 有多少url创建多少线程，url多时机子撑不住
//                // create a thread for each URI
//                GetThread[] threads = new GetThread[urisToGet.length];
//                for (int i = 0; i < threads.length; i++) {
//                    HttpGet httpget = new HttpGet(urisToGet[i]);
//                    threads[i] = new GetThread(httpClient, httpget);            
//                }
//                // start the threads
//                for (int j = 0; j < threads.length; j++) {
//                    threads[j].start();
//                }
//
//                // join the threads，等待所有请求完成
//                for (int j = 0; j < threads.length; j++) {
//                    threads[j].join();
//                }
//                使用线程池
        int j = 0;
        System.out.println(j);
//                    HttpPost httpget = new HttpPost(urisToGet);

        exe.execute(new GetThread(httpClient));


        //创建线程池，每次调用POOL_SIZE
                /*
                for (int i = 0; i < urisToGet.length; i++) {
                    final int j=i;
                    System.out.println(j);
                    exe.execute(new Thread() {
                        @Override
                        pu-blic void run() {
                            this.setName("threadsPoolClient"+j);
                            
                                try {
                                    this.sleep(100);
                                    System.out.println(j);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                
                                HttpGet httpget = new HttpGet(urisToGet[j]);
                                new GetThread(httpClient, httpget).get();
                            }
                            
                            
                        
                    });
                }
                
                */
        //exe.shutdown();
        System.out.println("Done");
    }

    static class GetThread extends Thread {

        private final HttpClient httpClient;
        private final HttpContext context;

        public GetThread(HttpClient httpClient) {
            this.httpClient = httpClient;
            this.context = new BasicHttpContext();
        }

        @Override
        public void run() {
            this.setName("threadsPoolClient");
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                //  Auto-generated catch block
                e.printStackTrace();
            }
            get();
        }

        public void get() {
            String s = "http://localhost/oauth/token?client_id=ucenter-sys-client&client_secret=ucenter-sys&grant_type=password&scope=read,write&username=usersys&password=111111";
            String s1 = "http://localhost/login.do";

            HttpPost p = new HttpPost(s);
            HttpPost p1 = new HttpPost(s1);

            try {
                HttpResponse response = this.httpClient.execute(p, this.context);
                HttpEntity entity = response.getEntity();
//                        if (entity != null) {
//                            System.out.println(p.getURI()+": status"+response.getStatusLine().toString());
//                            Header[] hs= response.getHeaders("Location");
//                            System.out.println(hs[0].getValue());
//                            
//                        }

                List<NameValuePair> nvps = new ArrayList<NameValuePair>();

                nvps.add(new BasicNameValuePair("j_username", "usersys"));
                nvps.add(new BasicNameValuePair("j_password", "111111"));

                p1.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
                HttpResponse response1 = this.httpClient.execute(p1, this.context);
                Header[] hs = response1.getHeaders("Location");
                System.out.println("*****" + hs[0].getValue());

                HttpPost p2 = new HttpPost(hs[0].getValue());
                HttpResponse response2 = this.httpClient.execute(p2, this.context);
                Header[] hs1 = response2.getHeaders("Location");
                System.out.println("&&&&&&" + hs1[0].getValue());

                // ensure the connection gets released to the manager
                EntityUtils.consume(entity);
            } catch (Exception ex) {
                p.abort();
            } finally {
                p.releaseConnection();
            }
        }
    }


    public static void main(String[] args) throws Exception {
        String s = "http://localhost/oauth/authorize?client_id=ucenter-sys-client&redirect_uri=http://localhost&response_type=code&scope=read";
        new OauthTask(s).test();
    }
}
