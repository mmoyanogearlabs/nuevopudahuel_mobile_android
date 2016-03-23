package com.cleverox.nuevopudahuel.ui.adapter;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.bzutils.BZScreenHelper;
import com.cleverox.nuevopudahuel.R;
import com.cleverox.nuevopudahuel.base.BaseActivity;
import com.cleverox.nuevopudahuel.model.ConfigCategory;

import java.util.List;

/**
 * Created by moddity on 15/3/16.
 */
public class ConfigurationAdapter extends RecyclerView.Adapter<ConfigurationAdapter.ViewHolder> {

    private BaseActivity baseActivity;
    private List<ConfigCategory> items;
    private CompoundButton.OnCheckedChangeListener checkedChangeListener;
    private CompoundButton.OnCheckedChangeListener allCategoriesCheckedListener;
    private View.OnClickListener clickListener;

    public ConfigurationAdapter(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }

    @Override
    public ConfigurationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(baseActivity.getLayoutInflater().inflate(R.layout.configuration_cell, null));
    }

    @Override
    public void onBindViewHolder(ConfigurationAdapter.ViewHolder holder, int position) {
        ConfigCategory item = items.get(position);
        if (position == 0 || position == 1) {
            holder.cell.setBackgroundColor(ContextCompat.getColor(baseActivity, R.color.hardGrey));
            holder.text.setTextColor(ContextCompat.getColor(baseActivity, R.color.white));
            holder.text.setTextSize(BZScreenHelper.dpFromPx(baseActivity.getResources().getDimension(R.dimen.textSize16), baseActivity));
        }
        else {
            holder.cell.setBackgroundColor(Color.WHITE);
            holder.text.setTextColor(ContextCompat.getColor(baseActivity, R.color.black));
            holder.text.setTextSize(BZScreenHelper.dpFromPx(baseActivity.getResources().getDimension(R.dimen.textSize14), baseActivity));
        }
        holder.text.setText(item.getText(baseActivity));
        holder.check.setChecked(item.isValue());
        holder.check.setTag(position);
        holder.check.setOnClickListener(clickListener);
    }

    @Override
    public int getItemCount() {
        if (items != null) return items.size();
        return 0;
    }

    public void setItems(List<ConfigCategory> items) {
        this.items = items;
    }

    public List<ConfigCategory> getItems() {
        return items;
    }

    public void setCheckedChangeListener(CompoundButton.OnCheckedChangeListener checkedChangeListener) {
        this.checkedChangeListener = checkedChangeListener;
    }

    public void setClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setAllCategoriesCheckedListener(CompoundButton.OnCheckedChangeListener allCategoriesCheckedListener) {
        this.allCategoriesCheckedListener = allCategoriesCheckedListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView text;
        private Switch check;
        private RelativeLayout cell;

        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.configuration_text);
            check = (Switch) itemView.findViewById(R.id.configuration_check);
            cell = (RelativeLayout) itemView.findViewById(R.id.configuration_cell);
        }
    }
}
