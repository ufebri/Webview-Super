package semicolon.id.webviewsuper.ui.demo;

import static semicolon.id.webviewsuper.config.Constant.REQUEST_CODE_LOCATION_GEOLOCATION;
import static semicolon.id.webviewsuper.config.Constant.REQUEST_CODE_LOCATION_MAPS;
import static semicolon.id.webviewsuper.config.Constant.REQUEST_CODE_READ_STORAGE_DEMO;
import static semicolon.id.webviewsuper.config.Constant.WEB_URL;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import semicolon.id.webviewsuper.MainActivity;
import semicolon.id.webviewsuper.R;
import semicolon.id.webviewsuper.config.BaseApp;
import semicolon.id.webviewsuper.model.DemoMenu;
import semicolon.id.webviewsuper.ui.component.GeneralAlertDialog;
import semicolon.id.webviewsuper.ui.settings.SettingsActivity;

public class DemoActivity extends BaseApp implements View.OnClickListener {

    private RecyclerView rvMenu;
    private DemoAdapter adapter;
    private List<DemoMenu> menuList;

    private FloatingActionButton fabMain;
    private ExtendedFloatingActionButton fabSettings, fabShare;
    private SwipeRefreshLayout refreshLayout;
    private Boolean isAllFabsVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        //Binding
        fabMain = findViewById(R.id.floating_action_button);
        fabSettings = findViewById(R.id.floating_action_settings);
        fabShare = findViewById(R.id.floating_action_share);
        rvMenu = findViewById(R.id.rv_menu);
        refreshLayout = findViewById(R.id.srl_webview);

        //onClick
        fabMain.setOnClickListener(this);
        fabSettings.setOnClickListener(this);
        fabShare.setOnClickListener(this);

        //listener
        refreshLayout.setOnRefreshListener(this);

        menuList = new DemoMenu().getListDemoMenu(this);

        adapter = new DemoAdapter(this, menuList, item -> {
            switch (item) {
                case DemoMenu.GO_TO_WEB_PAGE:
                    WEB_URL = getString(R.string.links_demo_homepage);
                    startActivity(new Intent(DemoActivity.this, MainActivity.class));
                    break;
                case DemoMenu.GO_TO_GOOGLE:
                    WEB_URL = getString(R.string.links_demo_google);
                    startActivity(new Intent(DemoActivity.this, MainActivity.class));
                    break;
                case DemoMenu.TRY_YOUR_WEB:
                    new GeneralAlertDialog(this, "Try your URL", (isPass, mValue) -> {
                        if (isPass) {
                            WEB_URL = mValue;
                            startActivity(new Intent(DemoActivity.this, MainActivity.class));
                        }
                    });
                    break;
                case DemoMenu.TRY_VIDEO:
                    WEB_URL = getString(R.string.links_demo_youtube);
                    startActivity(new Intent(DemoActivity.this, MainActivity.class));
                    break;
                case DemoMenu.TRY_DOWNLOAD:
                    DownloadManagerSetup(DemoActivity.this, getString(R.string.links_demo_download));
                    break;
                case DemoMenu.TRY_UPLOAD:
                    if (isPermissionActive(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        WEB_URL = getString(R.string.links_demo_upload);
                        startActivity(new Intent(DemoActivity.this, MainActivity.class));
                    } else {
                        requestPermission(REQUEST_CODE_READ_STORAGE_DEMO, Manifest.permission.READ_EXTERNAL_STORAGE);
                    }
                    break;
                case DemoMenu.TRY_GEOLOCATION:
                    if (isPermissionActive(Manifest.permission.ACCESS_FINE_LOCATION)) {
                        WEB_URL = getString(R.string.links_demo_geolocation);
                        startActivity(new Intent(DemoActivity.this, MainActivity.class));
                    } else {
                        requestPermission(REQUEST_CODE_LOCATION_GEOLOCATION, Manifest.permission.ACCESS_FINE_LOCATION);
                    }
                    break;
                case DemoMenu.TRY_MAPS:
                    if (isPermissionActive(Manifest.permission.ACCESS_FINE_LOCATION)) {
                        WEB_URL = getString(R.string.links_demo_maps);
                        startActivity(new Intent(DemoActivity.this, MainActivity.class));
                    } else {
                        requestPermission(REQUEST_CODE_LOCATION_MAPS, Manifest.permission.ACCESS_FINE_LOCATION);
                    }
                    break;
            }
        });

        rvMenu.setAdapter(adapter);
        rvMenu.setLayoutManager(new LinearLayoutManager(this));
        rvMenu.setAnimation(fadeIn());

        //addBanner
        parentView = findViewById(R.id.content);
        parentView.post(() -> setupAds());
    }

    @Override
    public void onBackPressed() {
        new GeneralAlertDialog(this, getString(R.string.alert_quit_title), getString(R.string.alert_quit_message), (isPass, mValue) -> {
            if (isPass) DemoActivity.super.onBackPressed();
        });
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        adapter.notifyDataSetChanged();
        refreshLayout.setRefreshing(false);
        rvMenu.setAnimation(fadeOut());
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
        String mUrlPage = getString(R.string.links_app_playstore);
        String mDescPage = getString(R.string.share_intent_message);
        String intentMessage = String.format("%s : \n %s", mDescPage, mUrlPage);

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, intentMessage);
        startActivity(shareIntent);
    }
}