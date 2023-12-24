package semicolon.id.webviewsuper.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.DrawableRes;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import semicolon.id.webviewsuper.R;

public class CustomTIL extends TextInputLayout {

    @DrawableRes
    private int focusedDrawable;

    @DrawableRes
    private int unfocusedDrawable;

    @DrawableRes
    private int disabledDrawable;

    @DrawableRes
    private int errorDrawable;

    private final List<OnFocusChangeListener> editTextFocusChangeListeners = new ArrayList<>();

    public CustomTIL(Context context) {
        this(context, null);
    }

    public CustomTIL(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTIL(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        focusedDrawable = R.drawable.text_input_white_background_focused_shape;
        unfocusedDrawable = R.drawable.text_input_white_background_unfocused_shape;
        disabledDrawable = R.drawable.text_input_white_background_disabled_shape;
        errorDrawable = R.drawable.text_input_white_background_error_shape;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        final EditText editText = getEditText();
        if (editText != null) {
            editText.setOnFocusChangeListener((view, hasFocus) -> {
                for (OnFocusChangeListener listener : editTextFocusChangeListeners) {
                    listener.onFocusChange(view, hasFocus);
                }
                setEditTextDrawable();
            });
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        setEditTextDrawable();
    }

    @Override
    public void setError(CharSequence errorText) {
        super.setError(errorText);
        setEditTextDrawable();
    }

    /**
     * Adds focus change listeners.
     *
     * @param l Focus change listener.
     */
    public void addEditTextFocusChangeListener(OnFocusChangeListener l) {
        editTextFocusChangeListeners.add(l);
    }

    /**
     * Removes focus change listeners.
     *
     * @param l Focus change listener.
     */
    public void removeEditTextFocusChangeListener(OnFocusChangeListener l) {
        editTextFocusChangeListeners.remove(l);
    }

    private void setEditTextDrawable() {
        EditText editText = getEditText();
        if (editText != null) {
            if (getError() == null || getError().toString().isEmpty()) {
                if (editText.isFocused()) {
                    editText.setBackgroundResource(focusedDrawable);
                } else {
                    editText.setBackgroundResource(unfocusedDrawable);
                }
            } else {
                editText.setBackgroundResource(errorDrawable);
            }
            if (!isEnabled()) {
                editText.setBackgroundResource(disabledDrawable);
            }
        }
    }
}
