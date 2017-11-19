package com.schoolInfo.bartosz.schoolinfo.Home.MainInformation.Detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.schoolInfo.bartosz.schoolinfo.MainActivity.MainActivity;
import com.schoolInfo.bartosz.schoolinfo.R;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class DetailActivity extends MvpActivity<DetailView, DetailPresenter> implements DetailView {
    @BindView(R.id.recycler_view_for_detail) RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayoutDetail) SwipeRefreshLayout swipeRefreshLayout;

    String subjectString, dateString, contentString, typeOfInfoString, typeOfInfoToSend, subjectStringSecond,contentStringSecond;
    boolean witchSave = false;
    Adapter adapter;
    boolean dateChange = false, isChange = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homework_exam_detail);
        ButterKnife.bind(this);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();

        presenter.getDetailInfo(getIntent().getIntExtra("id", -1),
                getIntent().getStringExtra("typeOfInfo"),
                getIntent().getStringExtra("subject"),
                getIntent().getStringExtra("date"),
                getIntent().getStringExtra("content"));


        setEditableEnable(true);

    }



    public void setEditableEnable(boolean enable){
        witchSave = enable;
        adapter.notifyItemChanged(0);
    }


//
//    Start: Presenter Methods
//
    @NonNull
    @Override
    public DetailPresenter createPresenter() {
        return new DetailPresenter();
    }


    @Override
    public void setDetailInfo(String subject, String date, String content, String typeOfInfo) {

        this.subjectString = subject;
        this.dateString = date;
        this.contentString = content;
        this.typeOfInfoString = typeOfInfo;

        adapter = new Adapter();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setNewDate(String s) {
        this.dateString = s;
        dateChange = true;

        adapter.notifyItemChanged(0);
    }


    @Override
    public void changesAreSave() {
        isChange = true;

        swipeRefreshLayout.setRefreshing(false);
        setEditableEnable(false);
        Toast.makeText(this, "Zmiany zostały wysłane.", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }

//
//    End: Presenter methods
//




    class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private static final int TYPE_HEADER = 0;
        private static final int TYPE_ITEM = 1;


        class HeaderViewHolder extends RecyclerView.ViewHolder {
//            @BindView(R.id.detailSubject) AutoCompleteTextView subject;
//            @BindView(R.id.detailDate) TextView date;
//            @BindView(R.id.detailContent) AutoCompleteTextView content;
////            @BindView(R.id.detailAcceptedIcon) ImageView acceptIcon;
//            @BindView(R.id.detailEditButton) ImageView edit;
//            @BindView(R.id.detailSave) ImageView save;
//            @BindView(R.id.detailTypeOfInfo) TextView typeOfInfo;
//            @BindView(R.id.detailArrow) ImageView detailArrow;

            @BindView(R.id.DetailHomeworkExamSubject) TextView subject;
            @BindView(R.id.DetailHomeworkExamContent) TextView content;
            @BindView(R.id.DetailHomeworkExamDate) TextView date;
            @BindView(R.id.DetailHomeworkExamTypeOfInfo) TextView typeOfInfo;


            HeaderViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);

//                itemView.clearFocus();
//                String[] subjects = getResources().getStringArray(R.array.subject_array);
//                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(DetailActivity.this,  android.R.layout.simple_list_item_1, subjects);
//                subject.setAdapter(arrayAdapter);

            }


//            @OnClick(R.id.DetailImageViewEdit)
//            void edit(){
//
//                if(MainActivity.MANAGE_DATA == 1) {
//
//                        setEditableEnable(true);
//                }else
//                    Toast.makeText(DetailActivity.this, "Nie posiadasz wystarczających uprawnień.", Toast.LENGTH_SHORT).show();
//            }


            @OnClick(R.id.DetailImageViewSave)
            void save(){
                subjectString = this.subject.getText().toString();
                dateString = this.date.getText().toString();
                contentString = this.content.getText().toString();


                swipeRefreshLayout.setRefreshing(true);
                presenter.onSaveButtonClick(typeOfInfoToSend,
                        subject.getText().toString(),
                        date.getText().toString(),
                        content.getText().toString());
            }

            @OnClick(R.id.DetailImageViewCancle)
            void cancle(){

//                if (witchSave)
//                    setEditableEnable(false);
//                else

                onResume();


            }



            @OnClick(R.id.DetailHomeworkExamDate)
            void setDate(){

                subjectStringSecond = subject.getText().toString();
                contentStringSecond = content.getText().toString();

                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "datePicker");
            }



            @OnClick(R.id.DetailHomeworkExamTypeOfInfo)
            void arrow(){

                if(witchSave) {

                    PopupMenu popup = new PopupMenu(DetailActivity.this, typeOfInfo);
                    popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            switch (item.getItemId()){

                                case R.id.detail_news:

                                    typeOfInfoString = "Inne";
                                    typeOfInfoToSend = "news";
                                    adapter.notifyDataSetChanged();

                                    break;
                                case R.id.detail_exam:

                                    typeOfInfoString = "Sprawdziany";
                                    typeOfInfoToSend = "exam";
                                    adapter.notifyDataSetChanged();

                                    break;
                                case R.id.detail_homework:

                                    typeOfInfoString = "Prace domowe";
                                    typeOfInfoToSend = "homework";
                                    adapter.notifyDataSetChanged();

                                    break;

                            }



                            return false;
                        }
                    });

                    popup.show();
                }
            }



//

        }

        class ItemViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.comment_user_img) ImageView commentUserImg;
            @BindView(R.id.comment_user_name) TextView commentUserName;
            @BindView(R.id.comment_date) TextView commentDate;
            @BindView(R.id.comment_content) TextView commentContent;


            ItemViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if(viewType == TYPE_HEADER){
//                View view = LayoutInflater.from(DetailActivity.this).inflate(R.layout.homework_exam_detail_item_0_position, parent, false);
                View view = LayoutInflater.from(DetailActivity.this).inflate(R.layout.homework_exam_detail_item_0_second, parent, false);
//                view.clearFocus();
                return new HeaderViewHolder(view);
            }else if (viewType == TYPE_ITEM){
                View view = LayoutInflater.from(DetailActivity.this).inflate(R.layout.comment, parent, false);
                return new ItemViewHolder(view);
            }else
                return null;
        }




        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            if(holder instanceof HeaderViewHolder){

                HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;



                if(dateChange){
                    headerViewHolder.subject.setText(subjectStringSecond);
                    headerViewHolder.content.setText(contentStringSecond);

                }else {
                    headerViewHolder.subject.setText(subjectString);
                    headerViewHolder.content.setText(contentString);

                }


                headerViewHolder.date.setText(dateString);
                headerViewHolder.typeOfInfo.setText(typeOfInfoString);

                if(witchSave) {
//                    headerViewHolder.edit.setImageResource(R.drawable.detail_cancle);
//                    headerViewHolder.save.setVisibility(View.VISIBLE);
//                    headerViewHolder.detailArrow.setImageResource(R.drawable.arrow_drop_down);
                }else {
//                    headerViewHolder.edit.setImageResource(R.drawable.detail_edit_info);
//                    headerViewHolder.save.setVisibility(View.GONE);
//                    headerViewHolder.detailArrow.setImageResource(R.drawable.detail_arrow_right);
                }
                headerViewHolder.subject.setEnabled(witchSave);
                headerViewHolder.date.setEnabled(witchSave);
                headerViewHolder.content.setEnabled(witchSave);
                headerViewHolder.typeOfInfo.setEnabled(witchSave);


            } else if(holder instanceof ItemViewHolder){

                ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                itemViewHolder.commentUserName.setText("Nickname");
                itemViewHolder.commentDate.setText("Date");
                itemViewHolder.commentContent.setText("Funkcja komentowania dostepna wkrótce.");

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
            return 1;
        }




    }

}
