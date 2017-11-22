package com.schoolInfo.bartosz.schoolinfo.Home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.schoolInfo.bartosz.schoolinfo.Home.HomeView.HomeFragment;
import com.schoolInfo.bartosz.schoolinfo.Home.MembersList.MembersList;
import com.schoolInfo.bartosz.schoolinfo.Home.MainInformation.MainInformationFragment;
import com.schoolInfo.bartosz.schoolinfo.MainActivity.MainActivity;
import com.schoolInfo.bartosz.schoolinfo.R;
import com.schoolInfo.bartosz.schoolinfo.Rest.MainInformationAboutUserAndClass;
import com.schoolInfo.bartosz.schoolinfo.Rest.POJOClassInfo;
import com.schoolInfo.bartosz.schoolinfo.Rest.TimetableMainInformation;
import com.schoolInfo.bartosz.schoolinfo.Rest.UserBaseInformation;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;



public class TabFragment extends Fragment {

    public static final int MAIN_SCREEN = 0;

    HomeFragment homeFragment;
    MainInformationFragment informationFragment;
//    TimetableFragment timetableFragment;
    MembersList membersList;

    POJOClassInfo pojoClassInfo;
    UserBaseInformation UBI;
    MainInformationAboutUserAndClass mainInformationAboutUserAndClass;
    TimetableMainInformation timetableMainInformation;



    public final static String HOMEWORK = "homework";
    public final static String EXAM = "exam";
    public boolean isCreateFirstTime = true;


    @BindView(R.id.viewPager) ViewPager viewPager;
    @BindView(R.id.tabLayout) TabLayout tabLayout;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);


        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.refreshOptionMenu(false);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homework_exam_conteiner, container, false);
        ButterKnife.bind(this, view);

        homeFragment = new HomeFragment();
        informationFragment = new MainInformationFragment();
//        timetableFragment = new TimetableFragment();
        membersList = new MembersList();


        isCreateFirstTime = true;

        setupViewPager(viewPager);


        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.mipmap.home_image_third);
        tabLayout.getTabAt(1).setIcon(R.mipmap.book_first_try);
        tabLayout.getTabAt(2).setIcon(R.mipmap.people_image_second);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        MainActivity mainActivity = (MainActivity) getActivity();
        setMainInformationAboutUserAndClass(mainActivity.getPresenter().getMainInformationAboutUserAndClass(), mainActivity.getPresenter().getTimetableMainInformation());
    }





    public void refreshPojoClass(MainInformationAboutUserAndClass mainInformationAboutUserAndClass, TimetableMainInformation timetableMainInformation) {

        if(homeFragment != null ) {


//            this.pojoClassInfo = mainInformationAboutUserAndClass.getPojoClassInfo();
//            this.UBI = mainInformationAboutUserAndClass.getUBI();

//            if (pojoClassInfo != null && UBI != null) {
                if (homeFragment.isVisible())
                    homeFragment.getPresenter().setDate(mainInformationAboutUserAndClass, timetableMainInformation);

                if (informationFragment.isVisible())
                    informationFragment.getPresenter().setRecycleView(mainInformationAboutUserAndClass);

                if (membersList.isCreate())
                    membersList.getPresenter().setMembersList(mainInformationAboutUserAndClass);
//            }
        }
    }


    public void hideCircleByOnBackPressed(){
        informationFragment.getPresenter().hideCircle();
    }


    public MainInformationFragment getInformationFragment() {
        return informationFragment;
    }


    public MainInformationAboutUserAndClass getMainInformationAboutUserAndClass() {
        return mainInformationAboutUserAndClass;
    }

    public TimetableMainInformation getTimetableMainInformation() {
        return timetableMainInformation;
    }

    public void setMainInformationAboutUserAndClass(MainInformationAboutUserAndClass mainInformationAboutUserAndClass, TimetableMainInformation timetableMainInformation) {
        this.mainInformationAboutUserAndClass = mainInformationAboutUserAndClass;
        this.timetableMainInformation = timetableMainInformation;
        refreshPojoClass(mainInformationAboutUserAndClass, timetableMainInformation);
    }


    private void setupViewPager(ViewPager viewPager) {

        TabFragment.Adapter adapter = new TabFragment.Adapter(getChildFragmentManager());
        adapter.addFragment(homeFragment, "");
        adapter.addFragment(informationFragment, "");
//        adapter.addFragment(timetableFragment, "");
        adapter.addFragment(membersList, "");


        viewPager.setAdapter(adapter);
    }

    public void setRefreshing(boolean refreshing) {


        if(homeFragment != null) {

//            this.pojoClassInfo = mainInformationAboutUserAndClass.getPojoClassInfo();
//            this.UBI = mainInformationAboutUserAndClass.getUBI();

//            if (pojoClassInfo != null && UBI != null) {
            if (homeFragment.isVisible())
                homeFragment.setRefreshing(refreshing);

            if (informationFragment.isVisible())
                informationFragment.setRefreshing(refreshing);;

            if (membersList.isVisible())
                membersList.setRefreshing(refreshing);
//            }
        }

    }


    private static class Adapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }





    }

}
