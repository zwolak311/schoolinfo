package com.schoolInfo.bartosz.schoolinfo.MainActivity.Try;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.mosby.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.MvpLceViewStateFragment;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.data.RetainingLceViewState;
import com.schoolInfo.bartosz.schoolinfo.R;

import java.util.List;

import butterknife.ButterKnife;


public class TryFragment extends MvpLceViewStateFragment<SwipeRefreshLayout, List<String>,
        TryView, TryPresenter>
        implements TryView, SwipeRefreshLayout.OnRefreshListener {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.try_layout, container, false);



        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        contentView.setOnRefreshListener(this);
        setRetainInstance(true);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public LceViewState<List<String>, TryView> createViewState() {
        return new RetainingLceViewState<List<String>, TryView>();
    }

    @Override
    public List<String> getData() {
        return null;
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return null;
    }

    @Override
    public TryPresenter createPresenter() {
        return new TryPresenter();
    }

    @Override
    public void setData(List<String> data) {

    }

    @Override
    public void loadData(boolean pullToRefresh) {

    }
}
