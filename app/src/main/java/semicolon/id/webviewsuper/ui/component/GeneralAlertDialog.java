package semicolon.id.webviewsuper.ui.component;

import android.app.AlertDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;

import semicolon.id.webviewsuper.R;
import semicolon.id.webviewsuper.databinding.AlertDialogTextfieldBinding;

public class GeneralAlertDialog {

    private final onClickButtonListener mOnClickListener;

    public interface onClickButtonListener {
        void onClick(boolean isPass, String mValue);
    }

    public GeneralAlertDialog(Context context, String mTitle, String mMessage, onClickButtonListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(mTitle);
        builder.setMessage(mMessage);
        builder.setCancelable(true);
        mOnClickListener = onClickListener;

        builder.setPositiveButton(context.getString(R.string.text_yes), (dialogInterface, i) -> mOnClickListener.onClick(true, ""));

        builder.setNegativeButton(context.getString(R.string.text_no), (dialogInterface, i) -> {
            mOnClickListener.onClick(false, "");
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
            mOnClickListener.onClick(true, "");
        });

        builder.setNegativeButton(noButton, (dialogInterface, i) -> {
            mOnClickListener.onClick(false, "");
            dialogInterface.cancel();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public GeneralAlertDialog(Context context, String mTitle, onClickButtonListener onClickListener) {
        // Create an AlertDialog Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        AlertDialog dialog = builder.create();

        // Set up the layout inflater to inflate the custom view
        AlertDialogTextfieldBinding binding = AlertDialogTextfieldBinding.inflate(LayoutInflater.from(context));

        builder.setView(binding.getRoot());
        builder.setTitle(mTitle);
        mOnClickListener = onClickListener;


        // Set up the TextWatcher to detect text changes
        binding.teiUrl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Check if the entered text is not empty
                if (editable.length() > 0) {
                    binding.tilUrl.setError(null);

                    if (editable.toString().startsWith("https://")) {
                        binding.btnGo.setOnClickListener(v -> mOnClickListener.onClick(true, editable.toString()));
                        dialog.cancel();
                    } else {
                        binding.tilUrl.setError("Must be start with https://");
                    }
                } else {
                    binding.tilUrl.setError(null);
                }
            }
        });


        // Show the AlertDialog
        builder.show();

    }


}
