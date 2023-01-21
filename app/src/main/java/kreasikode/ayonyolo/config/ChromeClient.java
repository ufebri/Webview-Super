package kreasikode.ayonyolo.config;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

import kreasikode.ayonyolo.R;
import kreasikode.ayonyolo.ui.component.CustomWebView;
import kreasikode.ayonyolo.ui.component.GeneralAlertDialog;
import kreasikode.ayonyolo.util.ImageChooser;
import kreasikode.ayonyolo.util.VideoEnabledWebChromeClient;

public class ChromeClient extends VideoEnabledWebChromeClient {

    private final String TAG = "TEST";
    private PermissionRequest mPermissionRequest;
    private final Context context;

    private final ImageChooser imageChooser = new ImageChooser();
    private ResultChooserImage resultChooserImage;
    private final CallBackFileChooser callBackFileChooser;

    public ChromeClient(Context context, View parentView, CustomWebView webView, CallBackFileChooser callBackFileChooser) {
        this.context = context;
        this.callBackFileChooser = callBackFileChooser;
        this.activityNonVideoView = parentView;
        this.activityVideoView = (ViewGroup) parentView;
        this.webView = webView;
    }

    public interface CallBackFileChooser {
        void onSuccessChooser(Intent intent, int resultCode);
    }

    @Override
    public void onPermissionRequest(PermissionRequest request) {
        Log.i(TAG, "onPermissionRequest");
        mPermissionRequest = request;
        final String[] requestedResources = request.getResources();
        for (String r : requestedResources) {
            if (r.equals(PermissionRequest.RESOURCE_VIDEO_CAPTURE)) {
                // In this sample, we only accept video capture request.
                new GeneralAlertDialog(context, context.getString(R.string.permission_title_camera), context.getString(R.string.text_accept), context.getString(R.string.text_deny), isPass -> {
                    if (isPass)
                        mPermissionRequest.grant(new String[]{PermissionRequest.RESOURCE_VIDEO_CAPTURE});
                    else mPermissionRequest.deny();
                });
                break;
            }
        }
    }

    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {

        //Run ChooserImage
        imageChooser.chooseImage();

        //SetResult
        resultChooserImage = new ResultChooserImage(imageChooser.getResultData().getUri(), filePathCallback);

        callBackFileChooser.onSuccessChooser(imageChooser.getResultData().getIntent(), imageChooser.getResultData().getResultCode());

        return true;
    }


    @Override
    public void onPermissionRequestCanceled(PermissionRequest request) {
        super.onPermissionRequestCanceled(request);
        Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show();
    }

    public static class ResultChooserImage {

        private final Uri uri;
        private final ValueCallback<Uri[]> valueCallback;

        public ResultChooserImage(Uri uri, ValueCallback<Uri[]> valueCallback) {
            this.uri = uri;
            this.valueCallback = valueCallback;
        }

        public Uri getUri() {
            return uri;
        }

        public ValueCallback<Uri[]> getValueCallback() {
            return valueCallback;
        }
    }

    public ResultChooserImage getResultChooserImage() {
        return resultChooserImage;
    }
}
