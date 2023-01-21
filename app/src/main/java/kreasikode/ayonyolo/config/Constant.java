package kreasikode.ayonyolo.config;

import android.Manifest;

public class Constant {

    public static final int REQUEST_REQUIRED_PERMISSION = 442;
    public static final int FILECHOOSER_RESULTCODE = 1;

    public static final long SPLASH_LOAD_TIME = 1600; //its 1600ms mean 1.6s

    public static final String[] REQUIRED_PERMISSION =
            {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};

    //TODO 01: Change the URL to yours web
    public static final String WEB_URL = "https://raytalktech.com";
}
