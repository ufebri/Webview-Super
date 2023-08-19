package semicolon.id.webviewsuper.ui.demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import semicolon.id.webviewsuper.model.DemoMenu;
import semicolon.id.webviewsuper.util.GeneralHelper;
import semicolon.id.webviewsuper.R;

public class DemoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<DemoMenu> menuList;

    private GeneralHelper.onClickItemListener onClickItemListener;

    public DemoAdapter(Context context, List<DemoMenu> menuList, GeneralHelper.onClickItemListener onClickItemListener) {
        this.context = context;
        this.menuList = menuList;
        this.onClickItemListener = onClickItemListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DemoItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_demo, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DemoItemViewHolder viewHolder = (DemoItemViewHolder) holder;
        viewHolder.setContent(menuList.get(position), onClickItemListener);
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }
}
