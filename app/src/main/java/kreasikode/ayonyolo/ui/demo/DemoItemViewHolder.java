package kreasikode.ayonyolo.ui.demo;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import kreasikode.ayonyolo.R;
import kreasikode.ayonyolo.model.DemoMenu;
import kreasikode.ayonyolo.util.GeneralHelper;

public class DemoItemViewHolder extends RecyclerView.ViewHolder {

    private ImageView ivCover;
    private TextView tvTitle, tvCaption, tvActionPrimary, tvActionSecondary;

    private View line;
    private LinearLayout llAction;

    private DemoMenu mItem;
    private GeneralHelper.onClickItemListener listener;

    public DemoItemViewHolder(@NonNull View itemView) {
        super(itemView);

        ivCover = itemView.findViewById(R.id.iv_cover);
        tvTitle = itemView.findViewById(R.id.tv_title);
        tvCaption = itemView.findViewById(R.id.tv_message);
        line = itemView.findViewById(R.id.line);
        llAction = itemView.findViewById(R.id.ll_action);
        tvActionPrimary = itemView.findViewById(R.id.tv_action_primary);
        tvActionSecondary = itemView.findViewById(R.id.tv_action_secondary);

    }

    public void setContent(DemoMenu mItem, GeneralHelper.onClickItemListener itemListener) {
        this.mItem = mItem;
        this.listener = itemListener;

        setImage();

        tvTitle.setText(mItem.title);
        tvCaption.setText(mItem.caption);

        setPrimaryAction();
        setSecondaryAction();
        setLine();
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
            ivCover.setVisibility(View.GONE);
        }
    }

    private void setPrimaryAction() {
        if (isPrimaryActionAvailable()) {
            tvActionPrimary.setVisibility(View.VISIBLE);
            tvActionPrimary.setText(mItem.primaryAction);

            tvActionPrimary.setOnClickListener(view -> callToAction(mItem.actionPrimary));
        } else {
            tvActionPrimary.setVisibility(View.GONE);
        }
    }

    private void setSecondaryAction() {
        if (isSecondaryActionAvailable()) {
            tvActionSecondary.setVisibility(View.VISIBLE);
            tvActionSecondary.setText(mItem.secondaryAction);

            tvActionSecondary.setOnClickListener(view -> callToAction(mItem.actionSecondary));
        } else {
            tvActionSecondary.setVisibility(View.GONE);
        }
    }

    private void setLine() {
        llAction.setVisibility(isAllActionAvailable() ? View.VISIBLE : View.GONE);
        line.setVisibility(isAllActionAvailable() ? View.VISIBLE : View.GONE);
    }
}
