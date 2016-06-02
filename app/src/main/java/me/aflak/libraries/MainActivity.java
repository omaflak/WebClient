package me.aflak.libraries;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import java.util.Arrays;

import me.aflak.webclient.WebClient;
import me.aflak.webclient.WebClient.*;


public class MainActivity extends Activity implements OnRequestListener {
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (TextView)findViewById(R.id.main_text);
        text.setMovementMethod(new ScrollingMovementMethod());

        WebClient client = new WebClient();
        client.setOnRequestListener(this);

        /*      POST REQUEST    */
        Pair p = new Pair("field1", "value1");
        Pair p2 = new Pair("field2", "value2");
        client.requestAsync("http://your-api.com", WebClient.POST, Arrays.asList(p, p2), 1);

        /*      GET REQUEST     */
        // You should build the URL with an Uri.Builder
        client.requestAsync("http://your-api.com", WebClient.GET, null, 2, "some text");
    }

    @Override
    public void onRequest(String response, int requestID, Object... objects) {
        String some_text = (String) objects[0];

        if(requestID==1){
            System.out.println("POST response: "+response);
        }
        else if(requestID==2){
            System.out.println("GET response: "+response);
        }
    }

    @Override
    public void onError(int error_code, String message) {
        System.out.println("Error "+error_code+": "+message);
    }
}
