package com.schoolInfo.bartosz.schoolinfo.Home.MembersList;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.schoolInfo.bartosz.schoolinfo.Home.TabFragment;
import com.schoolInfo.bartosz.schoolinfo.MainActivity.MainActivity;
import com.schoolInfo.bartosz.schoolinfo.R;
import com.schoolInfo.bartosz.schoolinfo.Rest.POJOClassInfo;
import com.hannesdorfmann.mosby.mvp.MvpFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MembersList extends MvpFragment<MembersView, MembersPresenter> implements MembersView {
    ArrayList<POJOClassInfo.Members> membersArrayList;
    ArrayList<POJOClassInfo.Requests> requestsArrayList;
    TabFragment tabFragment;
    boolean isCreate = false;


    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.faceImage) ImageView faceImage;
    @BindView(R.id.underIconText) TextView underIconText;
    @BindView(R.id.secondUnderIconText) TextView secondUnderIconText;
    @BindView(R.id.contentView) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.secondUnderLayout)
    LinearLayout underLayout;
    @BindView(R.id.retryUnderIconText) TextView retryUnderIconText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycle_layout, container, false);
        ButterKnife.bind(this, view);

        tabFragment = (TabFragment) getParentFragment();


        swipeRefreshLayout.setRefreshing(true);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.loadData(true);

            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        isCreate = true;

        if(tabFragment.getMainInformationAboutUserAndClass() != null)
            presenter.setMembersList(tabFragment.getMainInformationAboutUserAndClass());
        else
            networkIsNotAvailable();
//        getMembersList();
    }


    @Override
    public void onPause() {
        super.onPause();

        swipeRefreshLayout.setRefreshing(false);
    }

    @OnClick(R.id.retryUnderIconText)
    public void refreshButton(){

        refresh();

    }

    public boolean isCreate(){
        return isCreate;
    }


    @NonNull
    @Override
    public MembersPresenter createPresenter() {
        return new MembersPresenter();
    }


    @Override
    public void getMembersList() {

        try{
            MainActivity mainActivity = (MainActivity) getActivity();
            presenter.setMembersList(mainActivity.getPresenter().getMainInformationAboutUserAndClass());
        }catch (Exception ignore){}


    }

    @Override
    public void refresh() {

        swipeRefreshLayout.setRefreshing(true);

        MainActivity mainActivity = (MainActivity) getActivity();
//        mainActivity.refreshUBI();
        mainActivity.loadData(true);

    }

    @Override
    public void setMembersList(ArrayList<POJOClassInfo.Members> membersArrayList, ArrayList<POJOClassInfo.Requests> requestsArrayList) {
        this.membersArrayList = membersArrayList;
        this.requestsArrayList = requestsArrayList;



        recyclerView.setVisibility(View.VISIBLE);
        faceImage.setVisibility(View.GONE);
        underIconText.setVisibility(View.GONE);
        secondUnderIconText.setVisibility(View.GONE);
        underLayout.setVisibility(View.GONE);
        retryUnderIconText.setVisibility(View.GONE);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new MembersAdapter(getActivity()));


        swipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void networkIsNotAvailable() {


        recyclerView.setVisibility(View.GONE);

        faceImage.setImageResource(R.drawable.cloud_off);
        faceImage.setVisibility(View.VISIBLE);

        underIconText.setVisibility(View.VISIBLE);
        underIconText.setText("Jesteś w trybie offline.");


        underLayout.setVisibility(View.VISIBLE);

        secondUnderIconText.setVisibility(View.VISIBLE);
        secondUnderIconText.setText("Połącz się z internetem i ");

        retryUnderIconText.setVisibility(View.VISIBLE);
        retryUnderIconText.setText("spróbuj ponownie.");


        swipeRefreshLayout.setRefreshing(false);

    }

    public void setRefreshing(boolean b) {
        swipeRefreshLayout.setRefreshing(b);
    }

    class MembersAdapter extends RecyclerView.Adapter<MembersAdapter.ViewHolder> {
        LayoutInflater layoutInflater;


        MembersAdapter(Context context) {
            this.layoutInflater = LayoutInflater.from(context);
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.memberListUserImage) ImageView imageView;
            @BindView(R.id.memberListUserName) TextView name;
            @BindView(R.id.memberListUserStatus) TextView status;
            @BindView(R.id.acceptIcon) ImageView accept;
            @BindView(R.id.refuseIcon) ImageView refuse;

            ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }


            @OnClick(R.id.acceptIcon)
            void accept(){
                if(MainActivity.MANAGE_MEMBERS == 1)
                    presenter.acceptUser(requestsArrayList.get(getAdapterPosition()).getUser_id());
                else
                    Toast.makeText(getActivity(), "Nie posiadasz wystarczających uprawnień.", Toast.LENGTH_SHORT).show();

            }

            @OnClick(R.id.refuseIcon)
            void refuse(){

                if(MainActivity.MANAGE_MEMBERS == 1)
                    presenter.refuseUser(requestsArrayList.get(getAdapterPosition()).getUser_id());
                else
                    Toast.makeText(getActivity(), "Nie posiadasz wystarczających uprawnień.", Toast.LENGTH_SHORT).show();

            }

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = layoutInflater.inflate(R.layout.members_custom_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {


            if(position<requestsArrayList.size()){

                holder.accept.setVisibility(View.VISIBLE);
                holder.refuse.setVisibility(View.VISIBLE);
                holder.name.setText(requestsArrayList.get(position).getUser().getUsername());
                holder.status.setText(requestsArrayList.get(position).getUser().getName());

            }else {
                holder.accept.setVisibility(View.INVISIBLE);
                holder.refuse.setVisibility(View.INVISIBLE);
                holder.name.setText(membersArrayList.get(position - requestsArrayList.size()).getUsername());
                holder.status.setText(membersArrayList.get(position - requestsArrayList.size()).getName());
            }
        }

        @Override
        public int getItemCount() {
            return membersArrayList.size() +requestsArrayList.size();
        }
    }

}
