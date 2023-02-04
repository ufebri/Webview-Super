package raystudio.webviewsuper.ui.splash;

import static raystudio.webviewsuper.config.Constant.SPLASH_LOAD_TIME;
import static raystudio.webviewsuper.config.Constant.isDemoModeActivated;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

import raystudio.webviewsuper.MainActivity;
import raystudio.webviewsuper.R;
import raystudio.webviewsuper.config.BaseApp;
import raystudio.webviewsuper.ui.demo.DemoActivity;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends BaseApp {

    private ImageView splash;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //binding
        progressBar = findViewById(R.id.pb_webLoad);
        splash = findViewById(R.id.img_splash);

        if (isConnected) splash.postDelayed(() -> {
            //setAnimation
            splash.setAnimation(getAnimation());

            startActivity(new Intent(SplashScreenActivity.this, isDemoModeActivated ? DemoActivity.class : MainActivity.class));
            finish();
        }, SPLASH_LOAD_TIME);
    }

    private Animation getAnimation() {
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

        return fadeOut;
    }
}
