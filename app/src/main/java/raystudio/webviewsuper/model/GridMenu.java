package raystudio.webviewsuper.model;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import raystudio.webviewsuper.R;

public class GridMenu {

    public String menuName;
    public Drawable menuIcon;
    public String menuLink;

    public GridMenu(String menuName, Drawable menuIcon, String menuLink) {
        this.menuName = menuName;
        this.menuIcon = menuIcon;
        this.menuLink = menuLink;
    }

    public GridMenu() {
    }


    /**
     * Added Primary Menu on Here
     */
    public List<GridMenu> getListPrimaryMenu(Context context) {
        ArrayList<GridMenu> mListData = new ArrayList<>();

        //InitMenuName
        String menuNameLogin = context.getString(R.string.label_menu_login);
        String menuNameRegister = context.getString(R.string.label_menu_daftar);
        String menuNamePrediksiTogel = context.getString(R.string.label_menu_prediksi_togel);
        String menuNameResult = context.getString(R.string.label_menu_result);
        String menuNameBukuMimpi = context.getString(R.string.label_menu_buku_mimpi);

        //InitMenuIcon
        Drawable icLogin = ContextCompat.getDrawable(context, R.drawable.ic_login_person);
        Drawable icRegister = ContextCompat.getDrawable(context, R.drawable.ic_register_person);
        Drawable icPrediction = ContextCompat.getDrawable(context, R.drawable.ic_prediction);
        Drawable icResult = ContextCompat.getDrawable(context, R.drawable.ic_result);
        Drawable icBukuMimpi = ContextCompat.getDrawable(context, R.drawable.ic_buku_mimpi);

        //InitUrl
        String urlLogin = context.getString(R.string.links_grid_login);
        String urlRegister = context.getString(R.string.links_grid_register);
        String urlPrediction = context.getString(R.string.links_grid_prediksi_togel);
        String urlResult = context.getString(R.string.links_grid_result);
        String urlBukuMimpi = context.getString(R.string.links_grid_buku_mimpi);

        //Add Data Menu
        mListData.add(new GridMenu(menuNameLogin, icLogin, urlLogin));
        mListData.add(new GridMenu(menuNameRegister, icRegister, urlRegister));
        mListData.add(new GridMenu(menuNamePrediksiTogel, icPrediction, urlPrediction));
        mListData.add(new GridMenu(menuNameResult, icResult, urlResult));
        mListData.add(new GridMenu(menuNameBukuMimpi, icBukuMimpi, urlBukuMimpi));
        return mListData;
    }

    /**
     * Added Secondary Menu on Here
     */
    public List<GridMenu> getListSecondaryMenu(Context context) {
        ArrayList<GridMenu> mListData = new ArrayList<>();

        //Init Menu Name
        String menuNameWA = context.getString(R.string.label_menu_wa);

        //Init URL
        String urlWA = context.getString(R.string.links_grid_wa_number);

        //Init Icon
        Drawable icWa = ContextCompat.getDrawable(context, R.drawable.ic_wa);

        //Init Data Menu
        mListData.add(new GridMenu(menuNameWA, icWa, urlWA));
        return mListData;
    }
}
