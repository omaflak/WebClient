package me.aflak.webclient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by Omar on 01/12/2015.
 */
public class WebClient {
    private OnRequestListener listener=null;
    public static final String GET="GET";
    public static final String POST="POST";

    public void requestAsync(String url, String method, List<Pair> postData, int requestID, Object... objects) {
        new REQUEST(url, method, postData, requestID, objects).start();
    }

    private class REQUEST extends Thread implements Runnable{
        private String strURL;
        private String method;
        private List<Pair> postData;
        private int requestID;
        private Object[] objects;

        public REQUEST(String url, String method, List<Pair> postData, int requestID, Object... objects){
            this.strURL=url;
            this.method=method;
            this.postData=postData;
            this.requestID=requestID;
            this.objects=objects;
        }

        public void run(){
            try {
                InputStream inputStream;
                URL url = new URL(strURL);
                URLConnection conn = url.openConnection();
                HttpURLConnection httpConn = (HttpURLConnection) conn;
                httpConn.setRequestMethod(method);

                if(method.equals(WebClient.POST) && postData!=null)
                {
                    httpConn.setDoInput(true);
                    httpConn.setDoOutput(true);

                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write(getQuery(postData));
                    writer.flush();
                    writer.close();
                    os.close();
                }

                httpConn.connect();
                if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    inputStream = httpConn.getInputStream();
                    BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder total = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        total.append(line);
                    }

                    if(listener!=null)
                        listener.onRequest(total.toString(), requestID, objects);
                }
                else{
                    if(listener!=null)
                        listener.onError(httpConn.getResponseCode(), httpConn.getResponseMessage());
                }
            } catch (IOException e) {
                if(listener!=null)
                    listener.onError(-1, e.getMessage());
            }
        }
    }

    private String getQuery(List<Pair> params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (Pair pair : params)
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.first, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.second, "UTF-8"));
        }

        return result.toString();
    }

    public static class Pair{
        public String first;
        public String second;

        public Pair(String first, String second){
            this.first=first;
            this.second=second;
        }
    }

    public interface OnRequestListener{
        void onRequest(String response, int requestID, Object... objects);
        void onError(int error_code, String message);
    }

    public void setOnRequestListener(OnRequestListener listener){
        this.listener=listener;
    }

    public void removeRequestListener(){
        this.listener=null;
    }

}
