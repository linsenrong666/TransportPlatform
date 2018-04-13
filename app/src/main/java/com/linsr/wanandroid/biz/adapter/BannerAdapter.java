package com.linsr.wanandroid.biz.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.linsr.wanandroid.R;
import com.linsr.wanandroid.data.model.BannerPojo;

import java.util.List;

/**
 * Description
 @author Linsr
 */

public class BannerAdapter extends AbstractViewPagerAdapter<BannerPojo> {

    public BannerAdapter(Context context, List<BannerPojo> data) {
        super(context, data);
    }

    @Override
    public View newView(int position) {
        View view = View.inflate(mContext, R.layout.item_banner, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.item_banner_iv);
        Glide.with(mContext).load(mData.get(position).getImagePath()).into(imageView);
        return view;
    }
}
