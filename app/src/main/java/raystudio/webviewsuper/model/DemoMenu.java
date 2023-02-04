package raystudio.webviewsuper.model;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import raystudio.webviewsuper.R;

public class DemoMenu {

    public String title, caption, primaryAction, secondaryAction;
    public Drawable imageCover;
    public int style, actionPrimary, actionSecondary;

    public DemoMenu() {
    }

    public DemoMenu(String title, String caption, String primaryAction, String secondaryAction, Drawable imageCover, int style, int actionPrimary, int actionSecondary) {
        this.title = title;
        this.caption = caption;
        this.primaryAction = primaryAction;
        this.secondaryAction = secondaryAction;
        this.imageCover = imageCover;
        this.style = style;
        this.actionPrimary = actionPrimary;
        this.actionSecondary = actionSecondary;
    }

    public DemoMenu(String title, String caption, String primaryAction, Drawable imageCover, int style, int actionPrimary) {
        this.title = title;
        this.caption = caption;
        this.primaryAction = primaryAction;
        this.imageCover = imageCover;
        this.style = style;
        this.actionPrimary = actionPrimary;
    }

    public DemoMenu(String title, String caption, String primaryAction, int style, int actionPrimary) {
        this.title = title;
        this.caption = caption;
        this.primaryAction = primaryAction;
        this.style = style;
        this.actionPrimary = actionPrimary;
    }

    public DemoMenu(String title, String caption, String primaryAction, String secondaryAction, int style, int actionPrimary, int actionSecondary) {
        this.title = title;
        this.caption = caption;
        this.primaryAction = primaryAction;
        this.secondaryAction = secondaryAction;
        this.style = style;
        this.actionPrimary = actionPrimary;
        this.actionSecondary = actionSecondary;
    }

    public DemoMenu(String title, String caption, int style) {
        this.title = title;
        this.caption = caption;
        this.style = style;
    }


    //StyleMenu
    public static final int FULL_CONTENT_STYLE = 1;
    public static final int FULL_CONTENT_WITHOUT_SECONDARY_ACTION = 2;
    public static final int FULL_CONTENT_WITHOUT_COVER_AND_SECONDARY_ACTION = 3;
    public static final int FULL_CONTENT_WITHOUT_COVER = 4;
    public static final int FULL_CONTENT_WITHOUT_COVER_AND_ALL_ACTION = 5;

    //action
    public static final int GO_TO_WEB_PAGE = 1;
    public static final int GO_TO_GOOGLE = 2;
    public static final int TRY_DOWNLOAD = 3;
    public static final int TRY_UPLOAD = 4;
    public static final int TRY_VIDEO = 5;
    public static final int TRY_GEOLOCATION = 6;
    public static final int TRY_MAPS = 7;

    public List<DemoMenu> getListDemoMenu(Context context) {
        List<DemoMenu> mList = new ArrayList<>();

        //Introduction
        mList.add(new DemoMenu(context.getString(R.string.demo_introduction_title), context.getString(R.string.demo_introduction_caption), context.getString(R.string.demo_introduction_primaryAction), ContextCompat.getDrawable(context, R.drawable.img_icon_splash), FULL_CONTENT_WITHOUT_SECONDARY_ACTION, GO_TO_WEB_PAGE));

        //Check User Website
        mList.add(new DemoMenu(context.getString(R.string.demo_user_web_title), context.getString(R.string.demo_user_web_caption), context.getString(R.string.demo_user_web_primaryAction), FULL_CONTENT_WITHOUT_COVER_AND_SECONDARY_ACTION, GO_TO_GOOGLE));

        //Check Download & upload
        mList.add(new DemoMenu(context.getString(R.string.demo_download_upload_title), context.getString(R.string.demo_download_upload_caption), context.getString(R.string.demo_download_upload_primaryAction), context.getString(R.string.demo_download_upload_secondaryAction), FULL_CONTENT_WITHOUT_COVER, TRY_DOWNLOAD, TRY_UPLOAD));

        //Video
        mList.add(new DemoMenu(context.getString(R.string.demo_video_title), context.getString(R.string.demo_video_caption), context.getString(R.string.demo_video_primaryAction), FULL_CONTENT_WITHOUT_COVER_AND_SECONDARY_ACTION, TRY_VIDEO));

        //GeoLocation
        mList.add(new DemoMenu(context.getString(R.string.demo_geolocation_title), context.getString(R.string.demo_geolocation_caption), context.getString(R.string.demo_geolocation_primaryAction), context.getString(R.string.demo_geolocation_secondaryAction), FULL_CONTENT_WITHOUT_COVER, TRY_GEOLOCATION, TRY_MAPS));

        //Intent
        mList.add(new DemoMenu(context.getString(R.string.demo_intents_title), context.getString(R.string.demo_intents_caption), FULL_CONTENT_WITHOUT_COVER_AND_ALL_ACTION));

        //Links
        mList.add(new DemoMenu(context.getString(R.string.demo_links_title), context.getString(R.string.demo_links_caption), FULL_CONTENT_WITHOUT_COVER_AND_ALL_ACTION));

        return mList;
    }
}
