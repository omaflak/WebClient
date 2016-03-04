# WebClient
WebClient allows simple asynchronous requests to api's

# HOW

1) Don't forget to add the INTERNET permission in your Manifest file:

    <uses-permission android:name="android.permission.INTERNET"/>

2) Initilize the object and add the listener

    WebClient client = new WebClient();
        client.setOnRequestLoadedListener(new OnRequestLoadedListener() {
            @Override
            public void OnRequestLoaded(String response, int requestID) {
                Log.e(TAG, response);
            }

            @Override
            public void OnErrorOccurred(int error_code, String message) {
                Log.e(TAG, message);
            }
    });

3) Make the request
    
    //  requestAsynch(String url, String method, List<Pair<String, String>> postData, int requestID)
    client.requestAsynch("http://your-api.com", WebClient.GET, null, 0);
