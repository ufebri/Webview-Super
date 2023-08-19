package semicolon.id.webviewsuper.ui.demo;

import static android.view.View.GONE;

import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import semicolon.id.webviewsuper.model.DemoMenu;
import semicolon.id.webviewsuper.util.GeneralHelper;
import semicolon.id.webviewsuper.R;

public class DemoItemViewHolder extends RecyclerView.ViewHolder {

    private final ImageView ivCover;
    private final TextView tvTitle;
    private final TextView tvCaption;
    private final TextView tvActionPrimary;
    private final TextView tvActionSecondary;

    private final WebView wvTextMessage;

    private final View line;
    private final LinearLayout llAction;

    private DemoMenu mItem;
    private GeneralHelper.onClickItemListener listener;

    private final String mIntentContent;

    public DemoItemViewHolder(@NonNull View itemView) {
        super(itemView);

        ivCover = itemView.findViewById(R.id.iv_cover);
        tvTitle = itemView.findViewById(R.id.tv_title);
        tvCaption = itemView.findViewById(R.id.tv_message);
        line = itemView.findViewById(R.id.line);
        llAction = itemView.findViewById(R.id.ll_action);
        tvActionPrimary = itemView.findViewById(R.id.tv_action_primary);
        tvActionSecondary = itemView.findViewById(R.id.tv_action_secondary);
        wvTextMessage = itemView.findViewById(R.id.wv_textMessage);

        mIntentContent = itemView.getContext().getString(R.string.demo_intents_caption);
    }

    public void setContent(DemoMenu mItem, GeneralHelper.onClickItemListener itemListener) {
        this.mItem = mItem;
        this.listener = itemListener;

        setImage();

        tvTitle.setText(mItem.title);

        setCaption();
        setPrimaryAction();
        setSecondaryAction();
        setLine();
    }

    private void setCaption() {
        if (mItem.caption.contains(mIntentContent)) {
            wvTextMessage.setVisibility(View.VISIBLE);
            wvTextMessage.loadDataWithBaseURL(null, mItem.caption, "text/html", "utf-8", null);
            tvCaption.setVisibility(GONE);
        } else {
            wvTextMessage.setVisibility(GONE);
            tvCaption.setVisibility(View.VISIBLE);
            tvCaption.setText(mItem.caption);
        }
    }

    private void callToAction(int action) {
        listener.onClick(action);
    }

    private boolean isCoverVisible() {
        return mItem.imageCover != null;
    }

    private boolean isPrimaryActionAvailable() {
        return mItem.primaryAction != null;
    }

    private boolean isSecondaryActionAvailable() {
        return mItem.secondaryAction != null;
    }

    private boolean isAllActionAvailable() {
        return isPrimaryActionAvailable() || isSecondaryActionAvailable();
    }

    private void setImage() {
        if (isCoverVisible()) {
            ivCover.setVisibility(View.VISIBLE);
            ivCover.setImageDrawable(mItem.imageCover);
        } else {
            ivCover.setVisibility(GONE);
        }
    }

    private void setPrimaryAction() {
        if (isPrimaryActionAvailable()) {
            tvActionPrimary.setVisibility(View.VISIBLE);
            tvActionPrimary.setText(mItem.primaryAction);

            tvActionPrimary.setOnClickListener(view -> callToAction(mItem.actionPrimary));
        } else {
            tvActionPrimary.setVisibility(GONE);
        }
    }

    private void setSecondaryAction() {
        if (isSecondaryActionAvailable()) {
            tvActionSecondary.setVisibility(View.VISIBLE);
            tvActionSecondary.setText(mItem.secondaryAction);

            tvActionSecondary.setOnClickListener(view -> callToAction(mItem.actionSecondary));
        } else {
            tvActionSecondary.setVisibility(GONE);
        }
    }

    private void setLine() {
        llAction.setVisibility(isAllActionAvailable() ? View.VISIBLE : GONE);
        line.setVisibility(isAllActionAvailable() ? View.VISIBLE : GONE);
    }
}
