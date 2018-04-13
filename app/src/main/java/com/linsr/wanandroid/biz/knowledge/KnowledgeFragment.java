package com.linsr.wanandroid.biz.knowledge;

import android.os.Bundle;

import com.linsr.linlibrary.utils.JLog;
import com.linsr.wanandroid.R;
import com.linsr.wanandroid.base.BaseFragment;

/**
 * Description
 @author Linsr
 */

public class KnowledgeFragment extends BaseFragment {

    public static KnowledgeFragment newInstance() {
        
        Bundle args = new Bundle();
        
        KnowledgeFragment fragment = new KnowledgeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        JLog.a("knowledge");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_service;
    }
}
