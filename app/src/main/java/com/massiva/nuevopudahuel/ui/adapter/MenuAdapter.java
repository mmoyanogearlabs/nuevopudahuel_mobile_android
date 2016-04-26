package com.massiva.nuevopudahuel.ui.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bzutils.BZUtils;
import com.massiva.nuevopudahuel.R;
import com.massiva.nuevopudahuel.base.BaseActivity;
import com.massiva.nuevopudahuel.model.MenuItem;

import java.util.List;

/**
 * Created by moddity on 29/2/16.
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private BaseActivity baseActivity;
    private List<MenuItem> items;
    private View.OnClickListener onItemListener;


    public MenuAdapter(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }


    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(baseActivity.getLayoutInflater().inflate(R.layout.activity_menu, null));
    }

    @Override
    public void onBindViewHolder(MenuAdapter.ViewHolder holder, int position) {
        MenuItem item = items.get(position);
        holder.image.setImageResource(item.getImgResource());
        holder.text.setText(item.getTitle());
        holder.cell.setTag(position);
        holder.cell.setOnClickListener(onItemListener);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(BZUtils.getScreenWidht(baseActivity) * 4 / 5, ViewGroup.LayoutParams.WRAP_CONTENT);
        holder.cell.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {
        if (items != null) return items.size();
        return 0;
    }

    public void setItems(List<MenuItem> items) {
        this.items = items;
    }

    public void setOnItemListener(View.OnClickListener onItemListener) {
        this.onItemListener = onItemListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private TextView text;
        private LinearLayout cell;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.menu_image);
            text = (TextView) itemView.findViewById(R.id.menu_text);
            cell = (LinearLayout) itemView.findViewById(R.id.menu_cell);
        }
    }
}
