package com.cleverox.nuevopudahuel.ui.fragments;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bzutils.BZScreenHelper;
import com.cleverox.nuevopudahuel.R;
import com.cleverox.nuevopudahuel.base.HomeFragment;
import com.cleverox.nuevopudahuel.controllers.ConfigurationCategoriesController;
import com.cleverox.nuevopudahuel.model.ConfigCategory;
import com.cleverox.nuevopudahuel.ui.custom.NPSwitch;

import java.util.List;

/**
 * Created by moddity on 15/3/16.
 */
public class ConfigurationFragment extends HomeFragment implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private List<ConfigCategory> items;

    public static ConfigurationFragment newInstance() {
        ConfigurationFragment fragment = new ConfigurationFragment();
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_configuration;
    }

    @Override
    protected void configView(View parentView) {
        configList();

        TextView info = $(R.id.configuration_info);
        info.setTextColor(Color.WHITE);
    }

    private void configList() {
        LinearLayout list = $(R.id.configuration_list);
        if (items == null)
            items = ConfigurationCategoriesController.getInstance().getStoredCategories(getBaseActivity().getPudahuelApplication());
        if (items != null) {
            for (int i = 0; i < items.size() + 1; i ++) {
                if (i < items.size()) {
                    ConfigCategory category = items.get(i);

                    View itemView = null;
                    boolean needToAdd = false;
                    if (list.getChildCount() < items.size()) {
                        itemView = getBaseActivity().getLayoutInflater().inflate(R.layout.configuration_cell, null);
                        needToAdd = true;
                    } else {
                        itemView = list.getChildAt(i);
                    }

                    TextView text = (TextView) itemView.findViewById(R.id.configuration_text);
                    NPSwitch check = (NPSwitch) itemView.findViewById(R.id.configuration_check);
                    RelativeLayout cell = (RelativeLayout) itemView.findViewById(R.id.configuration_cell);

                    if (i == 0 || i == 1) {
                        cell.setBackgroundColor(ContextCompat.getColor(getBaseActivity(), R.color.hardGrey));
                        text.setTextColor(ContextCompat.getColor(getBaseActivity(), R.color.white));
                        text.setTextSize(BZScreenHelper.dpFromPx(getBaseActivity().getResources().getDimension(R.dimen.textSize16), getBaseActivity()));
                    } else {
                        cell.setBackgroundColor(Color.WHITE);
                        text.setTextColor(ContextCompat.getColor(getBaseActivity(), R.color.black));
                        text.setTextSize(BZScreenHelper.dpFromPx(getBaseActivity().getResources().getDimension(R.dimen.textSize14), getBaseActivity()));
                    }
                    text.setText(category.getText(getBaseActivity()));
                    check.setChecked(category.isValue());
                    check.setTag(i);
                    check.setCheckedChangeListener(this);

                    if (needToAdd)
                        list.addView(itemView);
                } else {
                    View aboutView = null;
                    boolean needToAdd = false;
                    if (list.getChildCount() < items.size() +1) {
                        aboutView = getBaseActivity().getLayoutInflater().inflate(R.layout.about_cell, null);
                        needToAdd = true;
                    } else {
                        aboutView = list.getChildAt(i);
                    }

                    TextView textView = (TextView) aboutView.findViewById(R.id.about_info);
                    textView.setTextColor(ContextCompat.getColor(getBaseActivity(), R.color.white));
                    textView.setLinkTextColor(ContextCompat.getColor(getBaseActivity(), R.color.white));

                    String text = getString(R.string.aboutTextKey);

                    text = text.replaceAll("\\n", "<br>");

                    text = text.replace("Cleverox.com", "<a href=\"http://www.cleverox.com\">Cleverox.com</a>");
                    text = text.replace("Moddity.net", "<a href=\"http://www.moddity.net\">Moddity.net</a>");
                    text = text.replace("http://www.nuevopudahuel.cl/pol-ticas-de-privacidad", "<a href=\"http://www.nuevopudahuel.cl/pol-ticas-de-privacidad\">http://www.nuevopudahuel.cl/pol-ticas-de-privacidad</a>");

                    textView.setMovementMethod(LinkMovementMethod.getInstance());
                    textView.setText(Html.fromHtml(text));

                    if (needToAdd)
                        list.addView(aboutView);
                }
            }
        }
    }

    private void updateSwitches(View view) {
        Integer position = (Integer) view.getTag();
        boolean isChecked = !items.get(position).isValue();
        items.get(position).setValue(isChecked);
        switch (position) {
            case 0:
                break;
            case 1:
                for (int i = 2; i < items.size(); i ++) {
                    items.get(i).setValue(isChecked);
                }
                break;
            default:
                if (!isChecked)
                    items.get(1).setValue(false);
                else {
                    boolean allChecked = true;
                    for (int i = 2; i < items.size(); i ++) {
                        if (!items.get(i).isValue()) {
                            allChecked = false;
                            break;
                        }
                    }
                    if (allChecked)
                        items.get(1).setValue(true);
                }
                break;
        }
        configList();
    }

    @Override
    public void onClick(View v) {
//        updateSwitches(v);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        updateSwitches(buttonView);
    }

    @Override
    public void onPause() {
        ConfigurationCategoriesController.getInstance().uploadCategoriesToMOCA(getBaseActivity().getPudahuelApplication(), items);
        super.onPause();
    }
}
