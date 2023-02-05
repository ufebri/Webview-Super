package raystudio.webviewsuper.ui.settings;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;

import raystudio.webviewsuper.R;
import raystudio.webviewsuper.config.BaseApp;

public class SettingsActivity extends BaseApp {

    private ConstraintLayout parentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportFragmentManager().beginTransaction().replace(R.id.fl_settings, new SettingsFragment()).commit();

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        parentView = findViewById(R.id.parent);

        parentView.post(() -> setupAds());
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}