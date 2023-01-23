package kreasikode.ayonyolo.config;

import static kreasikode.ayonyolo.config.Constant.REQUEST_REQUIRED_PERMISSION;
import static kreasikode.ayonyolo.config.Constant.REQUIRED_PERMISSION;

import android.os.Bundle;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

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
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        EasyPermissions.requestPermissions(this, getString(R.string.permission_message_denied_camera), REQUEST_REQUIRED_PERMISSION, REQUIRED_PERMISSION);
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
}
