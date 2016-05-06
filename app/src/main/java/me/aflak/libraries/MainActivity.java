package me.aflak.libraries;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Pair;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.aflak.webclient.WebClient;

public class MainActivity extends Activity implements WebClient.OnRequestLoadedListener{
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (TextView)findViewById(R.id.main_text);
        text.setMovementMethod(new ScrollingMovementMethod());

        WebClient client = new WebClient();
        client.setOnRequestLoadedListener(this);

        /*      POST REQUEST    */
        List<Pair<String, String>> post = new ArrayList<>();
        post.add(new Pair<>("field1", "value1"));
        post.add(new Pair<>("field2", "value2"));
        client.requestAsynch("http://your-api.com", WebClient.POST, post, 1);

        /*      GET REQUEST     */
        // You should build the URL with an Uri.Builder
        client.requestAsynch("http://your-api.com", WebClient.GET, null, 2);
    }

    @Override
    public void OnRequestLoaded(String response, int requestID) {
        System.out.println("Response: "+response);
    }

    @Override
    public void OnErrorOccurred(int error_code, String message) {
        System.out.println("Error "+error_code+": "+message);
    }
}
