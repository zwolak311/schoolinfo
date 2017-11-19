package com.schoolInfo.bartosz.schoolinfo.Home.MainInformation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
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

import com.hannesdorfmann.mosby.mvp.MvpFragment;
import com.schoolInfo.bartosz.schoolinfo.Home.MainInformation.AddedInfo.AddedDialog;
import com.schoolInfo.bartosz.schoolinfo.Home.MainInformation.Detail.DetailActivity;
import com.schoolInfo.bartosz.schoolinfo.Home.TabFragment;
import com.schoolInfo.bartosz.schoolinfo.MainActivity.MainActivity;
import com.schoolInfo.bartosz.schoolinfo.R;
import com.schoolInfo.bartosz.schoolinfo.Rest.POJOClassInfo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnLongClick;


public class MainInformationFragment extends MvpFragment<MainInformationView, MainInformationPresenter>
        implements MainInformationView, SwipeRefreshLayout.OnRefreshListener {


    ArrayList<POJOClassInfo.Information> informationList;
    POJOClassInfo pojoClassInfo;
    MainInformationAdapter mainInformationAdapter;
    TabFragment tabFragment;
    LinearLayoutManager linear;
    DetailActivity detailActivity;
    boolean isCreate = false, isDetailCreated = false;
    boolean boolForHomework[];


    @BindView(R.id.faceImage) ImageView faceImage;
    @BindView(R.id.underIconText) TextView underIconText;
    @BindView(R.id.secondUnderIconText) TextView secondUnderIconText;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.contentView) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.secondUnderLayout) LinearLayout underLayout;
    @BindView(R.id.retryUnderIconText) TextView retryUnderIconText;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycle_layout, container, false);

        ButterKnife.bind(this, view);


        tabFragment = (TabFragment) getParentFragment();


        mainInformationAdapter = new MainInformationAdapter(getActivity());
        linear = new LinearLayoutManager(getActivity());



        detailActivity = new DetailActivity();



        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(this);




        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

        isCreate = true;
        if(tabFragment.getMainInformationAboutUserAndClass() != null)
            presenter.setRecycleView(tabFragment.getMainInformationAboutUserAndClass());
        else
            networkNotAvailable();

        if(isDetailCreated){
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.loadData(false);
            isDetailCreated = false;


        }
    }


    @Override
    public void onPause() {
        super.onPause();


        swipeRefreshLayout.setRefreshing(false);
        hideCircle();
    }


    @OnClick(R.id.retryUnderIconText)
    public void refreshButton(){

        swipeRefreshLayout.setRefreshing(true);

        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.loadData(true);

    }

    @Override
    public void onRefresh() {

        tabFragment.isCreateFirstTime = true;
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.loadData(true);

    }


//
//    Start Presenter method
//




    @NonNull
    @Override
    public MainInformationPresenter createPresenter() {
        return new MainInformationPresenter();
    }


    @Override
    public void setRecycleList(POJOClassInfo pojoClassInfo, ArrayList<POJOClassInfo.Information> informationArrayList) {
        informationList = informationArrayList;
        this.pojoClassInfo = pojoClassInfo;


        recyclerView.setVisibility(View.VISIBLE);
        faceImage.setVisibility(View.GONE);
        underIconText.setVisibility(View.GONE);
        secondUnderIconText.setVisibility(View.GONE);
        underLayout.setVisibility(View.GONE);
        retryUnderIconText.setVisibility(View.GONE);

        mainInformationAdapter.setWitchCircle(false, -1);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(linear);
        recyclerView.setAdapter(mainInformationAdapter);

//        setRefreshing(false);

        swipeRefreshLayout.setRefreshing(false);

    }




    @Override
    public void listIsEmpty(String name) {


        recyclerView.setVisibility(View.GONE);
        faceImage.setImageResource(R.drawable.face_smile);
        faceImage.setVisibility(View.VISIBLE);

        underIconText.setVisibility(View.VISIBLE);
        underIconText.setText("Lista jest pusta.");

        secondUnderIconText.setVisibility(View.VISIBLE);
        secondUnderIconText.setText("Brak zapowiedzianych prac domowych dla klasy " + name + ".");


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


        setRefreshing(false);

    }






    @Override
    public void acceptInfo() {
        Toast.makeText(getActivity(), "Dostajemy nową liste 'homework' i odświeżamy recycle view ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void doNotHavePermissionInfo() {

        Toast.makeText(getActivity(), "Nie posiadasz uprawnień do zatwierdzania informacji.", Toast.LENGTH_SHORT).show();
        Toast.makeText(getActivity(), "Odświeżamy recycle view aby wrócić do odpowiednich ikon 'akceptacji'", Toast.LENGTH_SHORT).show();

    }






    @Override
    public void showCircle(int position, boolean[] boolForHomework) {

        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.refreshOptionMenu(true);

        mainInformationAdapter.setWitchCircle(true, -1);

        this.boolForHomework = boolForHomework;
//        mainInformationAdapter.setBoolForHomework(boolForHomework);
//        mainInformationAdapter.setItemPosition(boolForHomework);
        mainInformationAdapter.notifyItemChanged(position);
    }

    @Override
    public void hideCircle() {
        mainInformationAdapter.setWitchCircle(false, -1);
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.refreshOptionMenu(false);
    }




    @Override
    public void showDetailInfo(int id, String typeOfinfo, String subject, String date, String content) {
        isDetailCreated = true;

//        Intent intent = new Intent(getActivity(), DetailActivity.class);
//        intent.putExtra("id", id);
//        intent.putExtra("typeOfInfo", typeOfinfo);
//        intent.putExtra("subject", subject);
//        intent.putExtra("date", date);
//        intent.putExtra("content", content);
//        intent.putExtra("token", MainActivity.TOKEN);

        String s = "" + id;

        AddedDialog addedDialog = new AddedDialog().newInstance(subject, content, date, typeOfinfo, s);
        addedDialog.show(getChildFragmentManager(), "editInfo");

//        startActivity(intent);


    }

    @Override
    public void setRefreshing(boolean refreshing) {
        swipeRefreshLayout.setRefreshing(refreshing);
    }


//
//      End Presenter method
//



    class MainInformationAdapter extends RecyclerView.Adapter<MainInformationAdapter.ViewHolder> {
        LayoutInflater layoutInflater;
        boolean witchCircle = false;
        int itemPosition;
//        boolean[] boolForHomework;


//        public void setBoolForHomework(boolean[] boolForHomework) {
//            this.boolForHomework = boolForHomework;
//        }

        MainInformationAdapter(Context context) {
            this.layoutInflater = LayoutInflater.from(context);
        }



        void setWitchCircle(boolean witchCircle, int itemPosition){
            this.itemPosition = itemPosition;
            this.witchCircle = witchCircle;
            notifyDataSetChanged();
        }

        void setItemPosition(int itemPosition) {
            this.itemPosition = itemPosition;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
//            @BindView(R.id.homeworkExamLine) TextView line;
            @BindView(R.id.homeworkExamSubject) TextView subject;
            @BindView(R.id.homeworkExamContent) TextView content;
            @BindView(R.id.homeworkExamDate) TextView date;
            @BindView(R.id.typeOfInfo) TextView typeOfInfo;
            @BindView(R.id.homeworkExamTextViewForOnClick) TextView textViewForOnClick;
            @BindView(R.id.homeworkExamSpace) Space space;
//            @BindView(R.id.homeworkExamAcceptIcon) ImageView acceptIcon;
            @BindView(R.id.homeworkExamCircleCheckbox) CheckBox checkBox;


            ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

//            @OnClick(R.id.homeworkExamAcceptIcon)
//            void acceptIconOnClick(){
//                if(informationList.get(getAdapterPosition()).getAccepted() <= 0) {
////                    acceptIcon.setImageResource(R.drawable.accepted_weight_);
//                    presenter.acceptInfo();
//                }else
//                    Toast.makeText(getActivity(), "Już zakceptowane.", Toast.LENGTH_SHORT).show();
//            }

            @OnClick(R.id.homeworkExamTextViewForOnClick)
            void textForOnClickOnClick(){

                if(witchCircle){
                    if(checkBox.isChecked())
                        checkBox.setChecked(false);
                    else
                        checkBox.setChecked(true);
                } else {
                    presenter.informationDetail(getAdapterPosition());
                }
            }


            @OnLongClick(R.id.homeworkExamTextViewForOnClick)
            boolean textForOnClickOnLongClick(){

                if(MainActivity.MANAGE_DATA == 1) {
                    if (!witchCircle)

                        presenter.showCircle(getAdapterPosition());
                }

                else
                    Toast.makeText(getActivity(), "Nie posiadasz wystarczających uprawnień.", Toast.LENGTH_LONG).show();
                    return true;
            }

            @OnCheckedChanged(R.id.homeworkExamCircleCheckbox)
            void onCheckedChanged(boolean isChecked) {

                if(isChecked) {
                    checkBox.setButtonDrawable(R.mipmap.checed_ok_try_1_);
                    boolForHomework[getAdapterPosition()] = true;
                    presenter.circleIsCheck(getAdapterPosition());
                }else {
                    checkBox.setButtonDrawable(R.mipmap.check_no_try_1_);
                    boolForHomework[getAdapterPosition()] = false;
                    presenter.circleIsUnCheck(getAdapterPosition());
                }
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = layoutInflater.inflate(R.layout.homework_exam_custom_item_second, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            final int fixedPosition = holder.getLayoutPosition();

//            Line
//            if(position == 0)
//                holder.line.setVisibility(View.GONE);
//            else
//                holder.line.setVisibility(View.VISIBLE);

//            Circle checkbox








            if(witchCircle) {
                holder.checkBox.setVisibility(View.VISIBLE);
//                if(fixedPosition == itemPosition)


                if(boolForHomework[fixedPosition])
                    holder.checkBox.setChecked(true);
                else
                    holder.checkBox.setChecked(false);
            }else
                holder.checkBox.setVisibility(View.GONE);

//            space
            if(fixedPosition == (informationList.size() - 1)){
                holder.space.setVisibility(View.VISIBLE);
            }else {
                holder.space.setVisibility(View.GONE);
            }










//            switch (informationList.get(fixedPosition).getAccepted()){
//                case 0:
//                    holder.acceptIcon.setImageResource(R.drawable.accepted_0_);
//                    break;
//                case 1:
//                    holder.acceptIcon.setImageResource(R.drawable.accepted_1_);
//                    break;
//                case 2:
//                    holder.acceptIcon.setImageResource(R.drawable.accepted_2_);
//                    break;
//            }



            switch (informationList.get(position).getType()){

                case "exam":

                    holder.subject.setBackgroundResource(R.color.colorExam);

                    break;

                case "news":

                    holder.subject.setBackgroundResource(R.color.colorNews);


                    break;

                case "homework":

                    holder.subject.setBackgroundResource(R.color.colorHomework);


                    break;


            }


//            Toast.makeText(detailActivity, "" + informationList.get(position).getType() + position, Toast.LENGTH_SHORT).show();

            holder.subject.setText(informationList.get(fixedPosition).getSubject());
            holder.content.setText(informationList.get(fixedPosition).getContent());
            holder.date.setText(informationList.get(fixedPosition).getDate());
            holder.typeOfInfo.setText(informationList.get(position).getType());
//            holder.typeOfInfo.setText(informationList.get(position).getType());

        }


        @Override
        public int getItemCount() {
            return informationList.size();
        }
    }



    public boolean isCreate() {
        return isCreate;
    }

}