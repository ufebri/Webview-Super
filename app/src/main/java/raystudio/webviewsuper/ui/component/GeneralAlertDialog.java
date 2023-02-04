package raystudio.webviewsuper.ui.component;

import android.app.AlertDialog;
import android.content.Context;

import raystudio.webviewsuper.R;

public class GeneralAlertDialog {

    private final onClickButtonListener mOnClickListener;

    public interface onClickButtonListener {
        void onClick(boolean isPass);
    }

    public GeneralAlertDialog(Context context, String mTitle, String mMessage, onClickButtonListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(mTitle);
        builder.setMessage(mMessage);
        builder.setCancelable(true);
        mOnClickListener = onClickListener;

        builder.setPositiveButton(context.getString(R.string.text_yes), (dialogInterface, i) -> mOnClickListener.onClick(true));

        builder.setNegativeButton(context.getString(R.string.text_no), (dialogInterface, i) -> {
            mOnClickListener.onClick(false);
            dialogInterface.cancel();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public GeneralAlertDialog(Context context, String mTitle, String yesButton, String noButton, onClickButtonListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(mTitle);
        builder.setCancelable(true);
        mOnClickListener = onClickListener;

        builder.setPositiveButton(yesButton, (dialogInterface, i) -> {
            dialogInterface.dismiss();
            mOnClickListener.onClick(true);
        });

        builder.setNegativeButton(noButton, (dialogInterface, i) -> {
            mOnClickListener.onClick(false);
            dialogInterface.cancel();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


}
