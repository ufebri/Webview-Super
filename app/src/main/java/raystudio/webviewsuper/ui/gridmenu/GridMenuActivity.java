package raystudio.webviewsuper.ui.gridmenu;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import raystudio.webviewsuper.MainActivity;
import raystudio.webviewsuper.R;
import raystudio.webviewsuper.config.BaseApp;
import raystudio.webviewsuper.databinding.ActivityGridMenuBinding;
import raystudio.webviewsuper.model.GridMenu;

public class GridMenuActivity extends BaseApp {

    private ActivityGridMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Binding
        binding = ActivityGridMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Set Primary Title
        binding.tvTitlePrimary.setText(String.format("%s %s", getString(R.string.label_title_primary_menu), getString(R.string.app_name)));

        //Init List Menu
        GridMenu mListMenu = new GridMenu();

        //Setup Primary Recyclerview
        GridMenuAdapter primaryAdapter = new GridMenuAdapter(this, mListMenu.getListPrimaryMenu(this), mData -> {
            MainActivity.launchActivity(this, mData.f1s);
        });
        binding.rvPrimaryMenu.setAdapter(primaryAdapter);
        binding.rvPrimaryMenu.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
        binding.rvPrimaryMenu.setHasFixedSize(true);


        //Set Secondary Title
        binding.tvTitleSecondary.setText(getString(R.string.label_title_secondary));

        //Setup Secondary Recyclerview
        GridMenuAdapter secondaryAdapter = new GridMenuAdapter(this, mListMenu.getListSecondaryMenu(this), mData -> {
            MainActivity.launchActivity(this, mData.f1s);
        });
        binding.rvSecondaryMenu.setAdapter(secondaryAdapter);
        binding.rvSecondaryMenu.setLayoutManager(new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false));
        binding.rvSecondaryMenu.setHasFixedSize(true);
    }
}