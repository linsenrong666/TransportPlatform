package com.linsr.wanandroid.biz.home;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.linsr.wanandroid.R;
import com.linsr.wanandroid.base.BaseFragment;
import com.linsr.wanandroid.biz.adapter.BannerAdapter;
import com.linsr.wanandroid.biz.adapter.HomeAdapter;
import com.linsr.wanandroid.data.model.ArticlePojo;
import com.linsr.wanandroid.data.model.BannerPojo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description
 @author Linsr
 */
public class HomeFragment extends BaseFragment implements HomeContact.View, ViewPager.OnPageChangeListener {

    @BindView(R.id.home_article_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.home_srl)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private HomeAdapter mHomeAdapter;
    private BannerAdapter mBannerAdapter;
    private HomePresenter mHomePresenter;
    private List<ArticlePojo> mArticlePojos;
    private List<BannerPojo> mBannerPojos;
    private HomeHeaderView mHomeHeaderView;

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initView() {
        mArticlePojos = new ArrayList<>();
        mHomeAdapter = new HomeAdapter(R.layout.item_article, mArticlePojos);
        addHeader();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.setAdapter(mHomeAdapter);
        mHomeAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mHomePresenter.loadMore();
            }
        }, mRecyclerView);
        mHomeAdapter.setEmptyView(R.layout.view_recycler_empty);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHomePresenter.refreshList();
            }
        });
    }

    @Override
    protected void initData() {
        mHomePresenter = new HomePresenter(mActivity, this);
        mHomePresenter.getArticles(true);
        mHomePresenter.getBanner();
    }

    private void addHeader() {
        View header = LayoutInflater.from(mActivity).inflate(R.layout.header_home, null);
        mBannerPojos = new ArrayList<>();
        mBannerAdapter = new BannerAdapter(mActivity, mBannerPojos);
        mHomeHeaderView = new HomeHeaderView(header);
        mHomeHeaderView.viewPager.setAdapter(mBannerAdapter);
        mHomeHeaderView.viewPager.addOnPageChangeListener(this);
        mHomeAdapter.addHeaderView(header);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mHomeHeaderView.bannerTitle.setText(mBannerPojos.get(position).getTitle());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class HomeHeaderView {
        @BindView(R.id.header_home_tv_title)
        TextView bannerTitle;
        @BindView(R.id.header_home_vp)
        ViewPager viewPager;

        HomeHeaderView(View headerRootView) {
            ButterKnife.bind(this, headerRootView);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void showArticles(List<ArticlePojo> data, boolean isRefresh) {
        if (isRefresh) {
            mHomeAdapter.replaceData(data);
            mSwipeRefreshLayout.setRefreshing(false);
        } else {
            mHomeAdapter.addData(data);
            mHomeAdapter.loadMoreComplete();
        }
    }

    @Override
    public void showBanners(List<BannerPojo> data) {
        mBannerPojos.clear();
        mBannerPojos.addAll(data);
        mBannerAdapter.notifyDataSetChanged();
        onPageSelected(0);
    }

    @Override
    public void onLoadFailed(boolean isRefresh) {
        if (isRefresh) {
            mSwipeRefreshLayout.setRefreshing(false);
        } else {
            mHomeAdapter.loadMoreComplete();
        }
    }
}
