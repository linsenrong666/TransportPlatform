package com.linsr.wanandroid.biz.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.linsr.wanandroid.R;
import com.linsr.wanandroid.data.model.ArticlePojo;

import java.util.List;

/**
 * Description
 @author Linsr
 */
public class HomeAdapter extends BaseQuickAdapter<ArticlePojo, BaseViewHolder> {

    public HomeAdapter(@LayoutRes int layoutResId, @Nullable List<ArticlePojo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ArticlePojo item) {
        helper.setText(R.id.item_article_user_name, item.getAuthor());

    }
}
