package kreasikode.ayonyolo.ui.settings;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import kreasikode.ayonyolo.MainActivity;
import kreasikode.ayonyolo.R;

public class SettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceClickListener {


    private Preference mPermissionSection, mAboutUsSection, mPrivacyPolicy;

    public SettingsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPermissionSection = getPreferenceManager().findPreference(getString(R.string.preferences_key_permission));
        mAboutUsSection = getPreferenceManager().findPreference(getString(R.string.preference_key_about));
        mPrivacyPolicy = getPreferenceManager().findPreference(getString(R.string.preferences_key_privacy));

        mPermissionSection.setOnPreferenceClickListener(this);
        mAboutUsSection.setOnPreferenceClickListener(this);
        mPrivacyPolicy.setOnPreferenceClickListener(this);
    }

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.prefrences, rootKey);
    }

    @Override
    public boolean onPreferenceClick(@NonNull Preference preference) {
        if (getActivity() != null)
            switch (preference.getKey()) {
                case "permission_key_preferences":
                    final Intent i = new Intent();
                    i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    i.addCategory(Intent.CATEGORY_DEFAULT);
                    i.setData(Uri.parse("package:" + getActivity().getPackageName()));
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    getActivity().startActivity(i);
                    break;
                case "about_key_preferences":
                    MainActivity.launchActivity(getActivity(), getString(R.string.links_preferences_about));
                    break;
                case "privacy_key_preferences":
                    MainActivity.launchActivity(getActivity(), getString(R.string.links_preferences_privacy_policy));
                    break;
                default:
                    Toast.makeText(getActivity(), getString(R.string.general_error_system), Toast.LENGTH_LONG).show();
                    break;
            }
        return false;
    }
}