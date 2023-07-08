package raystudio.webviewsuper.ui.gridmenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import raystudio.webviewsuper.R;
import raystudio.webviewsuper.model.GridMenu;
import raystudio.webviewsuper.util.OnClickListenerHelper;

public class GridMenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<GridMenu> menuList;
    private OnClickListenerHelper.OnClickGridMenuListener menuListener;

    public GridMenuAdapter(Context context, List<GridMenu> menuList, OnClickListenerHelper.OnClickGridMenuListener menuListener) {
        this.context = context;
        this.menuList = menuList;
        this.menuListener = menuListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GridMenuViewHolder(LayoutInflater.from(context).inflate(R.layout.item_grid_menu, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        GridMenuViewHolder viewHolder = (GridMenuViewHolder) holder;
        viewHolder.setMenuContent(menuList.get(position), menuListener);
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }
}
