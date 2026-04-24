package co.id.pdamkotasmg.service;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class WebViewPostRequest {

    public static void sendPostRequest(WebView webView, String url, Map<String, String> postData) throws UnsupportedEncodingException {
        // Build the post data string
        StringBuilder postDataString = new StringBuilder();
        for (Map.Entry<String, String> entry : postData.entrySet()) {
            if (postDataString.length() > 0) {
                postDataString.append("&");
            }
            postDataString.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            postDataString.append("=");
            postDataString.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        // Create a WebViewClient to handle the response
        WebViewClient webViewClient = new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                // Handle the response from the server
            }
        };

        // Set the WebViewClient and load the URL with the post data
        webView.setWebViewClient(webViewClient);
        webView.postUrl(url, postDataString.toString().getBytes());
    }
}