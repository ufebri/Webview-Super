package raystudio.webviewsuper;

import static raystudio.webviewsuper.config.Constant.FILECHOOSER_RESULTCODE;
import static raystudio.webviewsuper.config.Constant.REQUEST_REQUIRED_PERMISSION;
import static raystudio.webviewsuper.config.Constant.REQUIRED_PERMISSION;
import static raystudio.webviewsuper.config.Constant.SPLASH_LOAD_TIME;
import static raystudio.webviewsuper.config.Constant.WEB_URL;
import static raystudio.webviewsuper.config.Constant.isDemoModeActivated;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.ads.MobileAds;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import raystudio.webviewsuper.config.BaseApp;
import raystudio.webviewsuper.config.ChromeClient;
import raystudio.webviewsuper.config.WebViewKitClient;
import raystudio.webviewsuper.ui.component.CustomWebView;
import raystudio.webviewsuper.ui.component.GeneralAlertDialog;
import raystudio.webviewsuper.ui.settings.SettingsActivity;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends BaseApp implements View.OnClickListener {

    private ProgressBar progressBar;
    private CustomWebView webView;
    private ImageView splash;
    private FloatingActionButton fabMain;
    private ExtendedFloatingActionButton fabSettings, fabShare;
    private ConstraintLayout parentView;
    private ChromeClient chromeClient;
    private Boolean isAllFabsVisible = false;

    private SwipeRefreshLayout refreshLayout;
    public static String url;

    public static void launchActivity(Activity caller, String mURL) {
        url = mURL;
        caller.startActivity(new Intent(caller, MainActivity.class));
    }

    @SuppressLint({"SetJavaScriptEnabled", "ObsoleteSdkInt"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //binding
        parentView = findViewById(R.id.content);
        fabMain = findViewById(R.id.floating_action_button);
        fabSettings = findViewById(R.id.floating_action_settings);
        fabShare = findViewById(R.id.floating_action_share);
        progressBar = findViewById(R.id.pb_webLoad);
        splash = findViewById(R.id.img_splash);
        webView = findViewById(R.id.wv_nyoloWeb);
        refreshLayout = findViewById(R.id.srl_webview);

        //onClick
        fabMain.setOnClickListener(this);
        fabSettings.setOnClickListener(this);
        fabShare.setOnClickListener(this);

        //listener
        refreshLayout.setOnRefreshListener(this);

        isNeedToShutDownSplash();

        if (requiredPermission())
            loadWeb();
        else
            EasyPermissions.requestPermissions(this, getString(R.string.permission_message_description_camera), REQUEST_REQUIRED_PERMISSION, REQUIRED_PERMISSION);

        //setupAds
        parentView.post(() -> setupAds());
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void loadWeb() {

        webView.setWebViewClient(new WebViewKitClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                setToRefresh(chromeClient.isNeedToRefresh);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                refreshLayout.postDelayed(() -> {
                    progressBar.setVisibility(View.GONE);
                    splash.setVisibility(View.GONE);
                    refreshLayout.setVisibility(View.VISIBLE);
                    webView.setVisibility(View.VISIBLE);
                    fabMain.setVisibility(View.VISIBLE);
                    adViewBanner.setVisibility(View.VISIBLE);
                    setToRefresh(chromeClient.isNeedToRefresh);
                }, SPLASH_LOAD_TIME);
            }
        });

        MobileAds.registerWebView(webView);
        webView.loadUrl(getUrl());

        //Set Custom Chrome Client
        chromeClient = new ChromeClient(this, parentView, webView, (intent, resultCode) -> startActivityForResult(intent, resultCode));

        //Set on fullscreen
        chromeClient.setOnToggledFullscreen(fullscreen -> {
            WindowManager.LayoutParams attrs = getWindow().getAttributes();
            if (fullscreen) {
                attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
                attrs.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                getWindow().setAttributes(attrs);
                if (Build.VERSION.SDK_INT >= 14) {
                    //noinspection all
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                            | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                            | View.SYSTEM_UI_FLAG_IMMERSIVE);
                }

                //fab is gone
                fabMain.hide();
            } else {
                attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
                attrs.flags &= ~WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                getWindow().setAttributes(attrs);
                if (Build.VERSION.SDK_INT >= 14) {
                    //noinspection all
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
                }

                //fab is show
                fabMain.show();
            }
        });

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

        //Download Setup
        webView.setDownloadListener((url, userAgent, contentDisposition, mimetype, contentLength) -> DownloadManagerSetup(MainActivity.this, url));
    }

    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            if (!isDemoModeActivated)
                new GeneralAlertDialog(this, getString(R.string.alert_quit_title), getString(R.string.alert_quit_message), isPass -> {
                    if (isPass) MainActivity.super.onBackPressed();
                });
            else MainActivity.super.onBackPressed();
        }
    }

    private boolean requiredPermission() {
        return EasyPermissions.hasPermissions(MainActivity.this, REQUIRED_PERMISSION);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        loadWeb();
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.floating_action_button:
                mainFABOnClick();
                break;
            case R.id.floating_action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            case R.id.floating_action_share:
                shareIntent();
                break;
        }
    }

    private void mainFABOnClick() {
        fabSettings.setVisibility(!isAllFabsVisible ? View.VISIBLE : View.GONE);
        fabShare.setVisibility(!isAllFabsVisible ? View.VISIBLE : View.GONE);

        if (!isAllFabsVisible) {
            fabSettings.show();
            fabShare.show();
            isAllFabsVisible = true;
        } else {
            fabSettings.hide();
            fabShare.hide();
            isAllFabsVisible = false;
        }
    }

    private void shareIntent() {
        String mTitlePage = webView.getTitle();
        String mUrlPage = webView.getUrl();
        String mDescPage = getString(R.string.share_intent_message);
        String intentMessage = String.format("%s \n %s : \n %s", mDescPage, mTitlePage, mUrlPage);

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, intentMessage);
        startActivity(shareIntent);
    }

    @Override
    public void onRefresh() {
        webView.reload();
        refreshLayout.setRefreshing(false);
    }

    private void setToRefresh(boolean refresh) {
        refreshLayout.setEnabled(refresh);
        refreshLayout.setRefreshing(!refresh);
    }

    private void isNeedToShutDownSplash() {
        parentView.setBackgroundColor(Color.WHITE);

        refreshLayout.setVisibility(View.VISIBLE);
        refreshLayout.setRefreshing(true);

        splash.setVisibility(isDemoModeActivated ? View.GONE : View.VISIBLE);
        progressBar.setVisibility(isDemoModeActivated ? View.GONE : View.VISIBLE);
    }

    private String getUrl() {
        return url != null ? url : WEB_URL;
    }
}