package kreasikode.ayonyolo.config;

import static kreasikode.ayonyolo.config.Constant.REQUEST_CODE_CAMERA;
import static kreasikode.ayonyolo.config.Constant.REQUEST_CODE_LOCATION_GEOLOCATION;
import static kreasikode.ayonyolo.config.Constant.REQUEST_CODE_LOCATION_MAPS;
import static kreasikode.ayonyolo.config.Constant.REQUEST_CODE_READ_STORAGE_DEMO;
import static kreasikode.ayonyolo.config.Constant.WEB_URL;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import kreasikode.ayonyolo.MainActivity;
import kreasikode.ayonyolo.R;
import kreasikode.ayonyolo.util.ConnectionReceiver;
import kreasikode.ayonyolo.util.GeneralHelper;
import pub.devrel.easypermissions.EasyPermissions;

public class BaseApp extends AppCompatActivity implements EasyPermissions.PermissionCallbacks, ConnectionReceiver.ReceiverListener, SwipeRefreshLayout.OnRefreshListener {

    protected boolean isConnected;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //check connection
        isConnected = GeneralHelper.checkConnection(this);
    }

    @Override
    public void onNetworkChange(boolean isConnected) {
        this.isConnected = isConnected;
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.isConnected = GeneralHelper.checkConnection(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.isConnected = GeneralHelper.checkConnection(this);
    }

    @Override
    public void onRefresh() {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        switch (requestCode) {
            case REQUEST_CODE_LOCATION_GEOLOCATION:
                WEB_URL = getString(R.string.links_demo_geolocation);
                startActivity(new Intent(BaseApp.this, MainActivity.class));
                break;
            case REQUEST_CODE_LOCATION_MAPS:
                WEB_URL = getString(R.string.links_demo_maps);
                startActivity(new Intent(BaseApp.this, MainActivity.class));
                break;
            case REQUEST_CODE_READ_STORAGE_DEMO:
                WEB_URL = getString(R.string.links_demo_upload);
                startActivity(new Intent(BaseApp.this, MainActivity.class));
                break;
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        requestPermission(requestCode, perms.get(0));
    }


    public Animation fadeIn() {
        final Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(1000);

        final AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(fadeIn);

        return fadeIn;
    }

    public Animation fadeOut() {
        final Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setStartOffset(1000);
        fadeOut.setDuration(1000);

        final AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(fadeOut);

        return fadeOut;
    }

    public void DownloadManagerSetup(Context context, String url) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); //Notify client once download is completed!

        DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        dm.enqueue(request);

        Toast.makeText(context, getString(R.string.text_downloading_file), //To notify the Client that the file is being downloaded
                Toast.LENGTH_LONG).show();
    }

    public boolean isPermissionActive(String permission) {
        return EasyPermissions.hasPermissions(BaseApp.this, permission);
    }

    public void requestPermission(int requestCode, String requestPermission) {
        EasyPermissions.requestPermissions(this, getPermissionMessage(requestCode), requestCode, requestPermission);
    }

    private String getPermissionMessage(int requestCode) {
        String message = "";
        switch (requestCode) {
            case REQUEST_CODE_CAMERA:
                message = getString(R.string.permission_message_description_camera);
                break;
            case REQUEST_CODE_LOCATION_MAPS:
            case REQUEST_CODE_LOCATION_GEOLOCATION:
                message = getString(R.string.permission_message_description_location);
                break;
            case REQUEST_CODE_READ_STORAGE_DEMO:
                message = getString(R.string.permission_message_description_storage);
                break;
        }
        return message;
    }
}
