# WebClient
WebClient allows simple asynchronous requests to api's

# Install

Add to your dependencies:

    compile 'me.aflak.libraries:webclient:1.0'

# Use

1) Initilize the object and add the listener

    WebClient client = new WebClient();
    client.setOnRequestListener(new OnRequestListener() {
            @Override
            public void onRequest(String response, int requestID) {
                Log.e(TAG, response);
            }

            @Override
            public void onError(int error_code, String message) {
                Log.e(TAG, message);
            }
    });

2) Make the request
    
    //  requestAsync(String url, String method, List<Pair<String, String>> postData, int requestID)
    client.requestAsync("http://your-api.com", WebClient.GET, null, 0);
    
# Sample

https://github.com/omaflak/WebClient/blob/master/app/src/main/java/me/aflak/libraries/MainActivity.java
