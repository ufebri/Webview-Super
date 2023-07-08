package raystudio.webviewsuper.ui.gridmenu;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import raystudio.webviewsuper.R;
import raystudio.webviewsuper.model.GridMenu;
import raystudio.webviewsuper.util.OnClickListenerHelper;

public class GridMenuViewHolder extends RecyclerView.ViewHolder {

    private final ImageView ivIcon;
    private final TextView tvName;

    public GridMenuViewHolder(@NonNull View itemView) {
        super(itemView);

        ivIcon = itemView.findViewById(R.id.iv_menu_icon);
        tvName = itemView.findViewById(R.id.tv_menu_name);
    }

    public void setMenuContent(GridMenu mData, OnClickListenerHelper.OnClickGridMenuListener listener) {
        //set Data
        ivIcon.setImageDrawable(mData.menuIcon);
        tvName.setText(mData.menuName);


        //Handle OnClick
        itemView.setOnClickListener(view -> {
            OnClickListenerHelper.PassingData mClick = new OnClickListenerHelper.PassingData();
            mClick.f1s = mData.menuLink;
            listener.onClick(mClick);
        });
    }
}
