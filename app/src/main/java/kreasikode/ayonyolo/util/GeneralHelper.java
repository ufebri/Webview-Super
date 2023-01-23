package kreasikode.ayonyolo.util;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.fragment.app.FragmentActivity;

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
}
