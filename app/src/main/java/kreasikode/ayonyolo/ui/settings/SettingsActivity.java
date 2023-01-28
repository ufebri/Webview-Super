package kreasikode.ayonyolo.ui.settings;

import android.os.Bundle;

import kreasikode.ayonyolo.R;
import kreasikode.ayonyolo.config.BaseApp;

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