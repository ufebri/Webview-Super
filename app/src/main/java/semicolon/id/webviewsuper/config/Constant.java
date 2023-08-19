package semicolon.id.webviewsuper.config;

import android.Manifest;

public class Constant {

    public static final int REQUEST_REQUIRED_PERMISSION = 442;

    public static final int REQUEST_CODE_CAMERA = 1;
    public static final int REQUEST_CODE_READ_STORAGE_DEMO = 2;
    public static final int REQUEST_CODE_LOCATION_GEOLOCATION = 3;
    public static final int REQUEST_CODE_LOCATION_MAPS = 4;

    public static final int FILECHOOSER_RESULTCODE = 1;

    public static final long SPLASH_LOAD_TIME = 1600; //its 1600ms mean 1.6s

    public static final boolean isDemoModeActivated = false; //Demo Mode is for selling

    public static final boolean isNeedToShowAds = true;

    public static final boolean isNeedToShowFAB = true;

    public static final String[] REQUIRED_PERMISSION = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION};

    //TODO: Change the URL to yours web
    public static String WEB_URL = "https://webview-super.web.app";

    //TODO: Set appConfig Mode
    public static MAIN_APP_CONFIG appConfig = MAIN_APP_CONFIG.DEMO_MODE;

    public enum MAIN_APP_CONFIG {
        DEMO_MODE,
        DIRECT_WEBVIEW_MODE,
        GRID_MENU_WEBVIEW_MODE
    }
}
