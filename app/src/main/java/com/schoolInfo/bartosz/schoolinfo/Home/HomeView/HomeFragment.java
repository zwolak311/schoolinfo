package com.schoolInfo.bartosz.schoolinfo.Home.HomeView;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.schoolInfo.bartosz.schoolinfo.Home.TabFragment;
import com.schoolInfo.bartosz.schoolinfo.MainActivity.MainActivity;
import com.schoolInfo.bartosz.schoolinfo.R;
import com.schoolInfo.bartosz.schoolinfo.Rest.POJOClassInfo;
import com.hannesdorfmann.mosby.mvp.MvpFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HomeFragment extends MvpFragment<HomeView, HomePresenter> implements HomeView {
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.contentView) SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.faceImage) ImageView faceImage;
    @BindView(R.id.underIconText) TextView underIconText;
    @BindView(R.id.secondUnderIconText) TextView secondUnderIconText;
    @BindView(R.id.secondUnderLayout) LinearLayout underLayout;
    @BindView(R.id.retryUnderIconText) TextView retryUnderIconText;

    TabFragment tabFragment;
    String dayOfWeek;
    List<String> subjectList = new ArrayList<>();
    List<String> classList = new ArrayList<>();
    ArrayList<POJOClassInfo.Information> information = new ArrayList<>();
    HomeAdapter homeAdapter;
    boolean isCreated = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycle_layout, container, false);
        ButterKnife.bind(this, view);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.loadData(true);


            }
        });

        tabFragment = (TabFragment) getParentFragment();
        homeAdapter = new HomeAdapter();
        swipeRefreshLayout.setRefreshing(true);

        return view;
    }

    public boolean isCreated() {
        return isCreated;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(!tabFragment.getMainInformationAboutUserAndClass().isDateEmpty())
            presenter.setDate(tabFragment.getMainInformationAboutUserAndClass(), tabFragment.getTimetableMainInformation());
        else
            networkNotAvailable();
        isCreated = true;


    }


    @Override
    public void onPause() {

        swipeRefreshLayout.setRefreshing(false);

        super.onPause();
    }

    @OnClick(R.id.retryUnderIconText)
    public void refreshButton(){

        swipeRefreshLayout.setRefreshing(true);

        MainActivity mainActivity = (MainActivity) getActivity();
//        mainActivity.refreshUBI();
        mainActivity.loadData(true);

    }



    @NonNull
    @Override
    public HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    public void showToast(String s) {

        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();

    }


    @Override
    public void setRecycleView(String dayOfWeek, List<String> subjectList, List<String> classList, ArrayList<POJOClassInfo.Information> information) {
        this.dayOfWeek = dayOfWeek;
        this.subjectList = subjectList;
        this.classList = classList;
        this.information = information;


        faceImage.setVisibility(View.GONE);
        underIconText.setVisibility(View.GONE);
        secondUnderIconText.setVisibility(View.GONE);
        underLayout.setVisibility(View.GONE);
        retryUnderIconText.setVisibility(View.GONE);


        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(homeAdapter);

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

    public void setRefreshing(boolean b) {
        swipeRefreshLayout.setRefreshing(b);
    }


    public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private static final int TYPE_TIMETABLE_HEADER = 0;
        private static final int TYPE_TIMETABLE_ITEM = 1;
        private static final int TYPE_INFO_HEADER = 2;
        private static final int TYPE_INFO_ITEM = 3;


        class ViewHolderTimetableHeader extends RecyclerView.ViewHolder {
            @BindView(R.id.homeHeader) TextView homeHeader;


            public ViewHolderTimetableHeader(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }


        class ViewHolderTimetable extends RecyclerView.ViewHolder {
            @BindView(R.id.timetableNumber) TextView number;
            @BindView(R.id.timetableSubject) TextView subject;
            @BindView(R.id.timetableRoomNumber) TextView roomNumber;
            @BindView(R.id.spaceForTimetable) TextView space;


            public ViewHolderTimetable(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }



        class ViewHolderInfoHeader extends RecyclerView.ViewHolder {
            @BindView(R.id.homeHeader) TextView homeHeader;

            public ViewHolderInfoHeader(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);

            }
        }



        class ViewHolderInfo extends RecyclerView.ViewHolder {
            @BindView(R.id.customItem) ConstraintLayout customItem;
            @BindView(R.id.cardView) CardView cardView;
            @BindView(R.id.secondLayout) ConstraintLayout secondLayout;
            @BindView(R.id.homeworkExamSubject) TextView subject;
            @BindView(R.id.homeworkExamContent) TextView content;
            @BindView(R.id.homeworkExamDate) TextView date;
            @BindView(R.id.typeOfInfo) TextView typeOfInfo;
            @BindView(R.id.homeworkExamTextViewForOnClick) TextView textViewForOnClick;
            @BindView(R.id.homeworkExamSpace) Space space;
//            @BindView(R.id.homeworkExamAcceptIcon) ImageView acceptIcon;
            @BindView(R.id.homeworkExamCircleCheckbox) CheckBox checkBox;
            @BindView(R.id.nothingNewText) TextView nothingNewText;
            @BindView(R.id.nothingNewIcon) ImageView nothingNewIcon;

            public ViewHolderInfo(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);

            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if(viewType == TYPE_TIMETABLE_HEADER){
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.home_info_header, parent, false);
                return new ViewHolderTimetableHeader(view);
            }else if(viewType == TYPE_TIMETABLE_ITEM){
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.home_timetable_list_item, parent, false);
                return new ViewHolderTimetable(view);

            }else if(viewType == TYPE_INFO_HEADER){
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.home_info_header, parent, false);
                return new ViewHolderInfoHeader(view);

            }else if(viewType == TYPE_INFO_ITEM){
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.homework_exam_custom_item_second, parent, false);
                return new ViewHolderInfo(view);
            }

            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof ViewHolderTimetableHeader) {
                ViewHolderTimetableHeader viewHolderTimetableHeader = (ViewHolderTimetableHeader) holder;

                viewHolderTimetableHeader.homeHeader.setText(dayOfWeek);

            } else if (holder instanceof ViewHolderTimetable) {
                ViewHolderTimetable viewHolderTimetable = (ViewHolderTimetable) holder;

                viewHolderTimetable.number.setText("" + (position));
//                viewHolderTimetable.roomNumber.setText(classList.get(position - 1));
                viewHolderTimetable.subject.setText(subjectList.get(position - 1));

            } else if (holder instanceof ViewHolderInfoHeader) {
                ViewHolderInfoHeader viewHolderInfoHeader = (ViewHolderInfoHeader) holder;

                viewHolderInfoHeader.homeHeader.setText("Najbliższe info");


            } else if (holder instanceof ViewHolderInfo) {
                ViewHolderInfo viewHolderInfo = (ViewHolderInfo) holder;

                if (information.size() == 0) {
                    viewHolderInfo.nothingNewText.setVisibility(View.VISIBLE);
                    viewHolderInfo.nothingNewIcon.setVisibility(View.VISIBLE);

                    viewHolderInfo.nothingNewText.setText("Świetnie! \nŻadnych nowości na " + dayOfWeek + "!");


                    viewHolderInfo.cardView.setVisibility(View.GONE);
                    viewHolderInfo.secondLayout.setVisibility(View.GONE);
                    viewHolderInfo.subject.setVisibility(View.GONE);
                    viewHolderInfo.date.setVisibility(View.GONE);
                    viewHolderInfo.content.setVisibility(View.GONE);
                    viewHolderInfo.textViewForOnClick.setVisibility(View.GONE);
                    viewHolderInfo.space.setVisibility(View.GONE);
//                    viewHolderInfo.typeOfInfo.setVisibility(View.GONE);


//                    if (position == subjectList.size() + information.size() + 2)
//                        viewHolderInfo.space.setVisibility(View.VISIBLE);

                } else {

                    switch (information.get((position - 2 - subjectList.size())).getType()){

                        case "exam":

                            viewHolderInfo.subject.setBackgroundResource(R.color.colorExam);

                            break;

                        case "news":

                            viewHolderInfo.subject.setBackgroundResource(R.color.colorNews);


                            break;

                        case "homework":

                            viewHolderInfo.subject.setBackgroundResource(R.color.colorHomework);


                            break;


                    }


                    viewHolderInfo.subject.setText(information.get((position - 2 - subjectList.size())).getSubject());
                    viewHolderInfo.date.setText(information.get((position - 2 - subjectList.size())).getDate());
                    viewHolderInfo.content.setText(information.get((position - 2 - subjectList.size())).getContent());
                    viewHolderInfo.typeOfInfo.setText(information.get(position - 2 - subjectList.size()).getType());
//                    viewHolderInfo.typeOfInfo.setText(information.get((position - 2 - subjectList.size())).getType());

                    if (position == subjectList.size() + information.size() + 1)
                        viewHolderInfo.space.setVisibility(View.VISIBLE);
                    else
                        viewHolderInfo.space.setVisibility(View.GONE);


//             if(position == subjectList.size() + 2)
//                 viewHolderInfo.line.setVisibility(View.GONE);

                }



            }
        }


        @Override
        public int getItemViewType(int position) {
            if (position == 0)
                return TYPE_TIMETABLE_HEADER;
            else if(position > 0 && position <= subjectList.size())
                return TYPE_TIMETABLE_ITEM;

            else if(position == subjectList.size() + 1)
                return TYPE_INFO_HEADER;
            else if (position >= subjectList.size() + 2)
                return TYPE_INFO_ITEM;
            return 0;
        }


        @Override
        public int getItemCount() {
            int infoSize;

            if(information.size() == 0)
                infoSize = 1;
            else
                infoSize = information.size();

            return subjectList.size() + infoSize + 2;
        }
    }


}
