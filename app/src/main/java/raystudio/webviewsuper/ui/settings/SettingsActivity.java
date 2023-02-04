package raystudio.webviewsuper.ui.settings;

import android.os.Bundle;

import raystudio.webviewsuper.R;
import raystudio.webviewsuper.config.BaseApp;

public class SettingsActivity extends BaseApp {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportFragmentManager().beginTransaction().replace(R.id.fl_settings, new SettingsFragment()).commit();

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}