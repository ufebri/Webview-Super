package kreasikode.ayonyolo.config;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewKitClient extends WebViewClient {

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }
}
