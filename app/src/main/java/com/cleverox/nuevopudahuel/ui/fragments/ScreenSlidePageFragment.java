package com.cleverox.nuevopudahuel.ui.fragments;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.cleverox.nuevopudahuel.R;
import com.cleverox.nuevopudahuel.base.BaseFragment;
import com.cleverox.nuevopudahuel.ui.activities.DetailWebViewActivity;
import com.cleverox.nuevopudahuel.ui.activities.HomeActivity;

/**
 * Created by moddity on 2/3/16.
 */
public class ScreenSlidePageFragment extends BaseFragment implements View.OnClickListener{

    private String imageUrl;

    public static ScreenSlidePageFragment newInstance(String imageUrl) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        fragment.imageUrl = imageUrl;
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_screen_slide_page;
    }

    @Override
    protected void configView(View parentView) {
        //Declarar imageview
        ImageView img = $(R.id.slide_image);
        img.setOnClickListener(this);
        getBaseActivity().getPudahuelApplication().loadImageUrl(imageUrl, img, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.slide_image:
                Intent intent = new Intent(getBaseActivity(), DetailWebViewActivity.class);
                intent.putExtra(DetailWebViewActivity.EXTRA_URL, imageUrl);
                startActivity(intent);
        }
    }
}
