package com.schoolInfo.bartosz.schoolinfo.GroupList;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.schoolInfo.bartosz.schoolinfo.MainActivity.MainActivity;
import com.schoolInfo.bartosz.schoolinfo.R;
import com.schoolInfo.bartosz.schoolinfo.Rest.Requests;
import com.schoolInfo.bartosz.schoolinfo.Rest.Status;
import com.hannesdorfmann.mosby.mvp.MvpFragment;
import com.schoolInfo.bartosz.schoolinfo.Rest.UserBaseInformation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class GroupMainFragment extends MvpFragment<GroupListView, GroupListPresenter> implements GroupListView {
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.contentView) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.faceImage) ImageView faceImage;
    @BindView(R.id.underIconText) TextView underIconText;
    @BindView(R.id.secondUnderIconText) TextView secondUnderIconText;
    @BindView(R.id.secondUnderLayout) LinearLayout underLayout;
    @BindView(R.id.retryUnderIconText) TextView retryUnderIconText;


    public static final int GROUP_SCREEN = 2;



    LinearLayoutManager linear;
    GroupListAdapter groupListAdapter;
    Status status;
    Requests requests;
    UserBaseInformation UBI;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycle_layout, container, false);
        ButterKnife.bind(this, view);



        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.refreshOptionMenu(false);


        groupListAdapter = new GroupListAdapter(getActivity());


        linear = new LinearLayoutManager(getActivity());

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linear.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        swipeRefreshLayout.setRefreshing(true);

        final MainActivity m = (MainActivity) getActivity();
        UBI = m.getPresenter().getUBI();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                MainActivity mainActivity = (MainActivity) getActivity();
//                mainActivity.refreshUBI();
                mainActivity.loadData(true);

                presenter.getGroupList();

            }
        });

        return view;
    }




    @Override
    public void onResume() {

        presenter.getGroupList();

        super.onResume();
    }


    @OnClick(R.id.retryUnderIconText)
    void retry(){

        swipeRefreshLayout.setRefreshing(true);
        presenter.getGroupList();

    }

    @NonNull
    @Override
    public GroupListPresenter createPresenter() {
        return new GroupListPresenter();
    }


    @Override
    public void setGroupList(Status status, Requests requests) {
        this.status = status;
        this.requests = requests;

        faceImage.setVisibility(View.GONE);
        underIconText.setVisibility(View.GONE);
        secondUnderIconText.setVisibility(View.GONE);
        underLayout.setVisibility(View.GONE);
        retryUnderIconText.setVisibility(View.GONE);


        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linear);
        recyclerView.setAdapter(groupListAdapter);

        swipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void networkNotAvailable() {
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

    @Override
    public void getUBI() {

        MainActivity main = (MainActivity) getActivity();
//        main.refreshUBI();
        main.loadData(true);

    }

    class GroupListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        LayoutInflater layoutInflater;
        private static final int TYPE_HEADER = 0;
        private static final int TYPE_ITEM = 1;


        public GroupListAdapter(Context context) {
            layoutInflater = LayoutInflater.from(context);
        }

        class HeaderViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.addNewGroup) Button addNewGroup;

            public HeaderViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }


            @OnClick(R.id.addNewGroup)
            void addToGroup(){

                Toast.makeText(getActivity(), "Funkcja dostepna wkrótce.", Toast.LENGTH_SHORT).show();


//                AddNewClassDialog addedDialogNews = new AddNewClassDialog();
//                addedDialogNews.show(getChildFragmentManager(), "Stwórz nową klasę");;

            }


        }

        class ItemViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.groupName) TextView groupName;
            @BindView(R.id.addToGroup) TextView addToGroup;
            @BindView(R.id.requestInfo) LinearLayout requestInfo;
            @BindView(R.id.requestInfoText) TextView requestInfoText;

            public ItemViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }


            @OnClick(R.id.addToGroup)
            void addToGroup(){

                swipeRefreshLayout.setRefreshing(true);
                presenter.addToGroup(getAdapterPosition() - 1);

            }


        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if(viewType == TYPE_HEADER){
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.group_list_item_0, parent, false);
                return new HeaderViewHolder(view);
            }else if (viewType == TYPE_ITEM){
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.group_list_item, parent, false);
                return new ItemViewHolder(view);
            }else
                return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            if(holder instanceof  HeaderViewHolder){

                HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;


            }else if(holder instanceof ItemViewHolder){
                ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

                itemViewHolder.groupName.setText(status.getMessage().get(position - 1).getName());

                for (UserBaseInformation.Group group:UBI.getGroups() ) {
                    if(group.getId() == status.getMessage().get(position - 1).getId()){
                        itemViewHolder.addToGroup.setBackgroundColor(getResources().getColor(R.color.colorMediumGray));
                        itemViewHolder.addToGroup.setEnabled(false);
                        itemViewHolder.requestInfo.setVisibility(View.VISIBLE);
                        itemViewHolder.requestInfoText.setText("Należysz do tej klasy.");
                    }
                }

                for (Requests.Message request: requests.getMessage()) {

                    if(request.getGroup() != null) {
                        if (request.getGroup().getId() == status.getMessage().get(position - 1).getId()) {
                            itemViewHolder.addToGroup.setBackgroundColor(getResources().getColor(R.color.colorMediumGray));
                            itemViewHolder.addToGroup.setEnabled(false);
                            itemViewHolder.requestInfo.setVisibility(View.VISIBLE);
                            itemViewHolder.requestInfoText.setText("Wysłano prośbę o dodanie do klasy.");
                        }
                    }
                }



            }



        }


        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return TYPE_HEADER;
            }
            return TYPE_ITEM;
        }

        @Override
        public int getItemCount() {
            return status.getMessage().size() + 1;
        }
    }




}
