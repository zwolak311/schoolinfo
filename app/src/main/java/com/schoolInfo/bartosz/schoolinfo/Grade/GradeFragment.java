package com.schoolInfo.bartosz.schoolinfo.Grade;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hannesdorfmann.mosby.mvp.MvpFragment;
import com.schoolInfo.bartosz.schoolinfo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GradeFragment extends MvpFragment<GradeView, GradePresenter> implements GradeView {
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.contentView) SwipeRefreshLayout swipeRefreshLayout;
    public static final int GRADE_SCREEN = 2;

    float restOfWith;
    int numberOfItems;
    int iteration = 0;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycle_layout, container, false);
        ButterKnife.bind(this, view);

        swipeRefreshLayout.setEnabled(false);

        float dpWith = CalculateNumbersOfItem(getActivity());

        restOfWith = (int)dpWith%85;

        numberOfItems = (int)dpWith/85;

        GradeAdapter gradeAdapter = new GradeAdapter();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), numberOfItems);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(gradeAdapter);

        return view;
    }

    @Override
    public GradePresenter createPresenter() {
        return new GradePresenter();
    }




    class GradeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private final int TYPE_HEADER = 0;
        private final int TYPE_ITEM = 1;


        public GradeAdapter(){



        }


        class HeaderViewHolder extends RecyclerView.ViewHolder{

            public HeaderViewHolder(View itemView) {
                super(itemView);
            }
        }

        class ItemViewHolder extends RecyclerView.ViewHolder{
            @BindView(R.id.gradeLayout) ConstraintLayout gradeLayout;
            @BindView(R.id.gradeCardView) CardView gradeCard;


            public ItemViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);

            }

            @OnClick(R.id.gradeCardView)
            void GradeCardView(){


                AddGrade addGrade = new AddGrade();
                addGrade.show(getChildFragmentManager(), "Dodaj ocene");

            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            if(viewType == TYPE_HEADER){
//
//                return null;
//            }else if(viewType == TYPE_ITEM){
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.grade_card, parent, false);
                return new ItemViewHolder(view);
//            }else
//                return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

            int dp = (int)restOfWith/(numberOfItems*2);

            DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
            int x = Math.round((dp + 10)* (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));


            itemViewHolder.gradeLayout.setPadding(x, 8, x, 8);

        }


        @Override
        public int getItemViewType(int position) {
            if(position == 0)
                return TYPE_HEADER;
            else
                return TYPE_ITEM;
        }

        @Override
        public int getItemCount() {
            return 10;
        }
    }


    private static float CalculateNumbersOfItem(Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWith = displayMetrics.widthPixels / displayMetrics.density;

        return dpWith;
    }
}
