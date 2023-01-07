package kreasikode.ayonyolo;

import static kreasikode.ayonyolo.config.Constant.WEB_URL;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import java.util.List;

import kreasikode.ayonyolo.config.ChromeClient;
import kreasikode.ayonyolo.config.Constant;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {


    ProgressBar progressBar;
    private Context context;
    private Activity activity;
    private CoordinatorLayout coordinatorLayout;
    private WebView webView;
    private ImageView splash;
    private static final String[] REQUIRED_PERMISSION =
            {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final int FILECHOOSER_RESULTCODE = 1;
    private ChromeClient chromeClient;

    @SuppressLint({"SetJavaScriptEnabled", "ObsoleteSdkInt"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();
        activity = this;

        progressBar = findViewById(R.id.pb_webLoad);

        coordinatorLayout = findViewById(R.id.cl_webView);

        splash = findViewById(R.id.img_splash);

        webView = findViewById(R.id.wv_nyoloWeb);
        webView.setVisibility(View.GONE);

        if (hasCameraPermission())
            loadWeb();
        else
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.permission_message_denied_camera),
                    Constant.REQUEST_REQUIRED_PERMISSION,
                    REQUIRED_PERMISSION);

    }

    private void loadWeb() {
        final Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(1000);

        final Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setStartOffset(1000);
        fadeOut.setDuration(1000);

        final AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(fadeIn);
        animationSet.addAnimation(fadeOut);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                splash.setAnimation(fadeOut);
                splash.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        splash.setVisibility(View.GONE);
                        webView.setVisibility(View.VISIBLE);
                        webView.setAnimation(fadeIn);
                    }
                }, 1600);
            }
        });

        webView.loadUrl(WEB_URL);

        //Set Custom Chrome Client
        chromeClient = new ChromeClient(this, (intent, resultCode) ->
                startActivityForResult(intent, resultCode));
        webView.setWebChromeClient(chromeClient);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setDomStorageEnabled(true);
        settings.setMediaPlaybackRequiresUserGesture(false);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            settings.setDatabasePath("/data/data" + webView.getContext().getPackageName() + "/databases/");
        }
    }

    protected void exitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setTitle(getString(R.string.alert_quit_title));
        builder.setMessage(getString(R.string.alert_quit_message));
        builder.setCancelable(true);

        builder.setPositiveButton(getText(R.string.text_yes), (dialogInterface, i) -> MainActivity.super.onBackPressed());

        builder.setNegativeButton(getString(R.string.text_no), (dialogInterface, i) -> dialogInterface.cancel());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();

        } else {
            exitDialog();
        }
    }

    private boolean hasCameraPermission() {
        return EasyPermissions.hasPermissions(MainActivity.this, REQUIRED_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        loadWeb();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        EasyPermissions.requestPermissions(
                this,
                getString(R.string.permission_message_denied_camera),
                Constant.REQUEST_REQUIRED_PERMISSION,
                REQUIRED_PERMISSION);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == FILECHOOSER_RESULTCODE) {
            ValueCallback<Uri[]> mUploadMessages = chromeClient.getResultChooserImage().getValueCallback();
            Uri mCapturedImageURI = chromeClient.getResultChooserImage().getUri();
            if (mUploadMessages != null)
                handleUploadMessages(intent, mUploadMessages, mCapturedImageURI);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void handleUploadMessages(Intent intent, ValueCallback<Uri[]> mUploadMessages, Uri mCapturedImageURI) {
        Uri[] results = null;
        try {
            if (intent != null) {
                String dataString = intent.getDataString();
                ClipData clipData = intent.getClipData();
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }
                if (dataString != null) {
                    results = new Uri[]{Uri.parse(dataString)};
                }
            } else {
                results = new Uri[]{mCapturedImageURI};
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mUploadMessages.onReceiveValue(results);
    }
}