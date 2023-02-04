package raystudio.webviewsuper.util;

import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.ViewGroup;
import android.view.WindowMetrics;
import android.widget.FrameLayout;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.ads.AdSize;

public class GeneralHelper {

    public interface onClickItemListener {
        void onClick(int item);
    }

    public static boolean checkConnection(FragmentActivity context) {

        // initialize intent filter
        IntentFilter intentFilter = new IntentFilter();

        // add action
        intentFilter.addAction("android.new.conn.CONNECTIVITY_CHANGE");

        // register receiver
        context.registerReceiver(new ConnectionReceiver(), intentFilter);

        // Initialize listener
        ConnectionReceiver.Listener = (ConnectionReceiver.ReceiverListener) context;

        // Initialize connectivity manager
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        // Initialize network info
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        // get connection status
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    public static AdSize getAdSize(FragmentActivity context, ViewGroup adContainerView) {
        int adWidth;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            WindowMetrics windowMetrics = context.getWindowManager().getCurrentWindowMetrics();
            Rect bounds = windowMetrics.getBounds();

            float adWidthPixels = adContainerView.getWidth();

            // If the ad hasn't been laid out, default to the full screen width.
            if (adWidthPixels == 0f) {
                adWidthPixels = bounds.width();
            }

            float density = context.getResources().getDisplayMetrics().density;
            adWidth = (int) (adWidthPixels / density);
        } else {
            // Determine the screen width (less decorations) to use for the ad width.
            Display display = context.getWindowManager().getDefaultDisplay();
            DisplayMetrics outMetrics = new DisplayMetrics();
            display.getMetrics(outMetrics);

            float density = outMetrics.density;

            float adWidthPixels = adContainerView.getWidth();

            // If the ad hasn't been laid out, default to the full screen width.
            if (adWidthPixels == 0) {
                adWidthPixels = outMetrics.widthPixels;
            }

            adWidth = (int) (adWidthPixels / density);
        }

        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth);
    }
}
