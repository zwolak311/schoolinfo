package com.schoolInfo.bartosz.schoolinfo.Timetable.OneDayInCalendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
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

import com.hannesdorfmann.mosby.mvp.MvpFragment;
import com.schoolInfo.bartosz.schoolinfo.MainActivity.MainActivity;
import com.schoolInfo.bartosz.schoolinfo.R;
import com.schoolInfo.bartosz.schoolinfo.Rest.SubjectList;
import com.schoolInfo.bartosz.schoolinfo.Rest.TimetableField;
import com.schoolInfo.bartosz.schoolinfo.Rest.TimetableMainInformation;
import com.schoolInfo.bartosz.schoolinfo.Timetable.OneDayInCalendar.AddEditDialog.TimetableAddEditDialog;
import com.schoolInfo.bartosz.schoolinfo.Timetable.TimetableTabFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class TimetableFragment extends MvpFragment<TimetableView, TimetablePresenter> implements TimetableView {
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.contentView) SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.faceImage) ImageView faceImage;
    @BindView(R.id.underIconText) TextView underIconText;
    @BindView(R.id.secondUnderIconText) TextView secondUnderIconText;
    @BindView(R.id.secondUnderLayout) LinearLayout underLayout;
    @BindView(R.id.retryUnderIconText) TextView retryUnderIconText;
//    @BindView(R.id.recycle_layout) RecyclerView recycleLayout;

    List<String> subjects;
    List<String> classNumb;
    List<String> listTime;

    public static TimetableFragment setDay(int dayId){
        TimetableFragment fragment = new TimetableFragment();

        Bundle args = new Bundle();
        args.putInt("dayId", dayId);
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycle_layout, container, false);
        ButterKnife.bind(this, view);

//        swipeRefreshLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorLightGray));

        subjects = new ArrayList<>();
        classNumb = new ArrayList<>();

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

        presenter.getDate(getArguments().getInt("dayId", -1));


    }

    @Override
    public TimetablePresenter createPresenter() {
        return new TimetablePresenter();
    }


    @Override
    public void showToast(String s) {

        Toast.makeText(getActivity(),""  + s , Toast.LENGTH_SHORT).show();

    }


    @Override
    public void getDate() {

        TimetableTabFragment tTF = (TimetableTabFragment) getParentFragment();
        tTF.setTimetable(getArguments().getInt("dayId", -1));

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
    public void setTimetableEditDate(int subject, int dayId, int sort, String classNum) {


        TimetableAddEditDialog timetableAddEdit = new TimetableAddEditDialog().newInstance(subject, dayId, sort, classNum);
        timetableAddEdit.show(getChildFragmentManager(), "edit timetable field");


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        Toast.makeText(getActivity(), "" + data.getIntExtra("id", -1), Toast.LENGTH_SHORT).show();


    }

    @Override
    public void setDate(List<String> subjects, List<String> classNumb,  List<String> lisTime) {
        this.subjects = subjects;
        this.classNumb = classNumb;
        this.listTime = lisTime;

        Adapter adapter = new Adapter();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setRefreshing(false);
    }

    class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private static final int TYPE_ITEM = 0;
        private static final int TYPE_ADD = 1;

        class HolderItem extends RecyclerView.ViewHolder {
            @BindView(R.id.mainTimetableLessonNumber) TextView mainTimetableLessonNumber;
            @BindView(R.id.mainTimetableSubject) TextView mainTimetableSubject;
            @BindView(R.id.mainTimetableClassNumber) TextView mainTimetableClassNumber;
            @BindView(R.id.mainTimetableTeacherName) TextView mainTimetableTeacherName;
            @BindView(R.id.mainTimetableTime) TextView mainTimetableTime;
//            @BindView(R.id.mainTimetableSpace) Space mainTimetableSpace;


            public HolderItem(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }


            @OnClick(R.id.cardView2)
            void onClick(){

                presenter.editTimetable(getAdapterPosition());


            }
        }


        class HolderAdd extends RecyclerView.ViewHolder {


            public HolderAdd(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            @OnClick(R.id.timetableAddCard)
            void add(){

                presenter.editTimetable(getAdapterPosition());

            }

        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            if(viewType == TYPE_ITEM){
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.timetable_item_mian, parent, false);
                view.clearFocus();
                return new TimetableFragment.Adapter.HolderItem(view);
            }else if (viewType == TYPE_ADD){
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.timetable_edit_add, parent, false);
                return new TimetableFragment.Adapter.HolderAdd(view);
            }else
                return null;


//            return new HolderItem(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


            if(holder instanceof HolderItem) {
                HolderItem holderItem = (HolderItem) holder;
                holderItem.mainTimetableLessonNumber.setText("" + (position + 1));
                holderItem.mainTimetableSubject.setText(subjects.get(position));
                holderItem.mainTimetableClassNumber.setText(classNumb.get(position));
                holderItem.mainTimetableTime.setText(listTime.get(position));
            }else if(holder instanceof HolderAdd) {
                HolderAdd holderAdd = (HolderAdd) holder;

            }
//            if(position != 6)
//                holder.mainTimetableSpace.setVisibility(View.GONE);


        }


        @Override
        public int getItemViewType(int position) {
            if (position < (getItemCount() - 1)) {
                return TYPE_ITEM;
            }
            return TYPE_ADD;
        }

        @Override
        public int getItemCount() {
            return subjects.size() + 1;
        }
    }
}
