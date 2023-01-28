package kreasikode.ayonyolo.config;

import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewKitClient extends WebViewClient {

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (url.startsWith("tel:")) {
            Intent tel = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
            view.getContext().startActivity(tel);
            return true;
        } else if (url.contains("mailto:")) {
            view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            return true;

        } else if (url.contains("geo:") || url.contains("maps.app.goo.gl")) {
            Uri gmmIntentUri = Uri.parse(url);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            view.getContext().startActivity(mapIntent);
            return true;
        } else {
            view.loadUrl(url);
            return true;
        }
    }
}
