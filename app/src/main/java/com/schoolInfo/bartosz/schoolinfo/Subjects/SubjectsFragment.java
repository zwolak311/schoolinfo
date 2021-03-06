package com.schoolInfo.bartosz.schoolinfo.Subjects;

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
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.hannesdorfmann.mosby.mvp.MvpFragment;
import com.schoolInfo.bartosz.schoolinfo.MainActivity.MainActivity;
import com.schoolInfo.bartosz.schoolinfo.R;
import com.schoolInfo.bartosz.schoolinfo.Rest.SubjectList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SubjectsFragment extends MvpFragment<SubjectsView, SubjectsPresenter> implements  SubjectsView {
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.contentView) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.faceImage) ImageView faceImage;
    @BindView(R.id.underIconText) TextView underIconText;
    @BindView(R.id.secondUnderIconText) TextView secondUnderIconText;
    @BindView(R.id.secondUnderLayout) LinearLayout underLayout;
    @BindView(R.id.retryUnderIconText) TextView retryUnderIconText;
    SubjectList subjectList;
    public static final int SUBJECT_SCREEN = 2;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycle_layout, container, false);
        ButterKnife.bind(this, view);



        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.loadData(false);

            }
        });



        return view;
    }


    public void setRefreshing(boolean refreshing){

        swipeRefreshLayout.setRefreshing(refreshing);

    }

    @Override
    public void onResume() {
        super.onResume();

        final MainActivity m = (MainActivity) getActivity();
        presenter.setSubjectList(m.getPresenter().loadSubjectList());

    }

    @Override
    public SubjectsPresenter createPresenter() {
       return new SubjectsPresenter();
    }

    @NonNull
    @Override
    public SubjectsPresenter getPresenter() {
        return super.getPresenter();
    }

    @Override
    public void setViewOfSubjectList(SubjectList subjectList) {
        this.subjectList = subjectList;


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new SubjectAdapter());

        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void listIsEmpty() {

        recyclerView.setVisibility(View.GONE);
        faceImage.setImageResource(R.drawable.face_smile);
        faceImage.setVisibility(View.VISIBLE);

        underIconText.setVisibility(View.VISIBLE);
        underIconText.setText("Lista jest pusta.");

        secondUnderIconText.setVisibility(View.VISIBLE);
        secondUnderIconText.setText("Brak zdefiniowanych przedmiotów.");


        setRefreshing(false);

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

    class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder>{


        class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.subject_item_text) TextView subjectText;
            @BindView(R.id.subject_space) Space space;
//            @BindView(R.id.subject_space2) Space space2;


            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);

            }


            @OnClick(R.id.subjectCardView)
            void subjectCardView(){

                AddSubject addSubject = new AddSubject().newInstance(subjectList.getMessage().get(getAdapterPosition()).getName(),
                        subjectList.getMessage().get(getAdapterPosition()).getId());
                addSubject.show(getChildFragmentManager(), "Dodaj przdmiot");

            }

        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.subject_item, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            holder.subjectText.setText("Matematyka");


            holder.subjectText.setText(subjectList.getMessage().get(position).getName());
//            }else if(position == getItemCount() - 1){
//                holder.space2.setVisibility(View.VISIBLE);
//            }else {
//                holder.space.setVisibility(View.GONE);
//                holder.space2.setVisibility(View.GONE);
//
//            }

        }

        @Override
        public int getItemCount() {return subjectList.getMessage().size();}
    }


}
