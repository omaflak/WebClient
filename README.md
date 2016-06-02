# WebClient
WebClient allows simple asynchronous requests to api's

# Install

Add to your dependencies:

    compile 'me.aflak.libraries:webclient:1.2'

# Use

1) Initilize the object and add the listener

    WebClient client = new WebClient();
    client.setOnRequestListener(new OnRequestListener() {
            @Override
            public void onRequest(String response, int requestID, Objects... objects) {
                String my_string = (String) object[0];
                Log.e(TAG, response);
            }

            @Override
            public void onError(int error_code, String message) {
                Log.e(TAG, message);
            }
    });

2) Make the request
    
    //  requestAsync(String url, String method, List<Pair<String, String>> postData, int requestID, Object... objects)
    client.requestAsync("http://your-api.com", WebClient.GET, null, 0, "a string I want to get back when request is done");
    
# Sample

https://github.com/omaflak/WebClient/blob/master/app/src/main/java/me/aflak/libraries/MainActivity.java
