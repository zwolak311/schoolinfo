package com.schoolInfo.bartosz.schoolinfo.Timetable;

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

import com.schoolInfo.bartosz.schoolinfo.R;
import com.schoolInfo.bartosz.schoolinfo.Timetable.OneDayInCalendar.TimetableFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TimetableTabFragment extends Fragment {

    TimetableFragment timetableFragmentMon, timetableFragmentTue, timetableFragmentWed, timetableFragmentThu, timetableFragmentFri;

    public boolean isCreateFirstTime = true;
    List<String> poniedzialek;

    public static final int TIMETABLE_SCREEN = 1;



    public static final int MON = 0;
    public static final int TUE = 1;
    public static final int WAD = 2;
    public static final int THU = 3;
    public static final int FRI = 4;

    @BindView(R.id.viewPager) ViewPager viewPager;
    @BindView(R.id.tabLayout) TabLayout tabLayout;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
//
//
////        try{
////            MainActivity mainActivity = (MainActivity) getActivity();
////            mainActivity.refreshUBI();
////        }catch (Exception ignored){}
//
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homework_exam_conteiner, container, false);
        ButterKnife.bind(this, view);


        timetableFragmentMon = TimetableFragment.setDay(MON);
        timetableFragmentTue = TimetableFragment.setDay(TUE);
        timetableFragmentWed = TimetableFragment.setDay(WAD);
        timetableFragmentThu = TimetableFragment.setDay(THU);
        timetableFragmentFri = TimetableFragment.setDay(FRI);


        isCreateFirstTime = true;

        setupViewPager(viewPager);



        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());

        switch (cal.get(Calendar.DAY_OF_WEEK)) {

            case Calendar.MONDAY:

                if(cal.get(Calendar.HOUR_OF_DAY) > 15)
                    viewPager.setCurrentItem(1);
                else
                    viewPager.setCurrentItem(0);

                break;
            case Calendar.TUESDAY:

                if(cal.get(Calendar.HOUR_OF_DAY) > 15)
                    viewPager.setCurrentItem(2);
                else
                    viewPager.setCurrentItem(1);


                break;
            case Calendar.WEDNESDAY:

                if(cal.get(Calendar.HOUR_OF_DAY) > 15)
                    viewPager.setCurrentItem(3);

                else
                    viewPager.setCurrentItem(2);


                break;
            case Calendar.THURSDAY:


                if(cal.get(Calendar.HOUR_OF_DAY) > 15)
                    viewPager.setCurrentItem(4);

                else
                    viewPager.setCurrentItem(3);

                break;
            case Calendar.FRIDAY:

                if(cal.get(Calendar.HOUR_OF_DAY) > 15)
                    viewPager.setCurrentItem(0);

                else
                    viewPager.setCurrentItem(4);


                break;
            case Calendar.SATURDAY:

                viewPager.setCurrentItem(0);


                break;
            case Calendar.SUNDAY:

                viewPager.setCurrentItem(0);


                break;
        }


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();


    }


    public void setTimetable(int day){


        List<String> listTime = new ArrayList<>();
        listTime.add("8:00-8:45");
        listTime.add("8:50-9:35");
        listTime.add("9:45-10:30");
        listTime.add("10:35-11:20");
        listTime.add("11:35-12:20");
        listTime.add("12:25-13:10");
        listTime.add("13:15-14:00");
        listTime.add("14:05-14:50");
        listTime.add("14:55-15:40");


        switch (day) {

            case MON:
            // Adding child data
            poniedzialek = new ArrayList<>();
            poniedzialek.add("Polski");
            poniedzialek.add("Polski");
            poniedzialek.add("Religia");
            poniedzialek.add("Angielski");
            poniedzialek.add("Angielski");
            poniedzialek.add("Proj. sieci");
            poniedzialek.add("Matematyka");

            List<String> poniedziałekClass = new ArrayList<>();
            poniedziałekClass.add("9");
            poniedziałekClass.add("9");
            poniedziałekClass.add("7");
            poniedziałekClass.add("103");
            poniedziałekClass.add("103");
            poniedziałekClass.add("208");
            poniedziałekClass.add("8");



            timetableFragmentMon.getPresenter().setDate(poniedzialek, poniedziałekClass, listTime);

                break;

            case TUE:
            List<String> wtorek = new ArrayList<>();
            wtorek.add("Witryny");
            wtorek.add("Witryny");
            wtorek.add("Admin.");
            wtorek.add("Admin.");
            wtorek.add("Matematyka");
            wtorek.add("Angielski");
            wtorek.add("Polski");


            List<String> wtorekClass = new ArrayList<>();
            wtorekClass.add("108");
            wtorekClass.add("108");
            wtorekClass.add("208");
            wtorekClass.add("208");
            wtorekClass.add("8");
            wtorekClass.add("103");
            wtorekClass.add("9");

            timetableFragmentTue.getPresenter().setDate(wtorek, wtorekClass, listTime);

                break;

            case WAD:
            List<String> sroda = new ArrayList<>();
            sroda.add("Historia i społeczeństwo");
            sroda.add("Matematyka");
            sroda.add("Progr.");
            sroda.add("Progr.");
            sroda.add("WF");
            sroda.add("Systemy");
            sroda.add("Progr.");


            List<String> srodaClass = new ArrayList<>();
            srodaClass.add("102");
            srodaClass.add("8");
            srodaClass.add("108");
            srodaClass.add("108");
            srodaClass.add("");
            srodaClass.add("208");
            srodaClass.add("108");


            timetableFragmentWed.getPresenter().setDate(sroda, srodaClass, listTime);

                break;

            case THU:

            List<String> czwartek = new ArrayList<>();
            czwartek.add("Historia i społeczeństwo");
            czwartek.add("Matematyka");
            czwartek.add("Matematyka");
            czwartek.add("Progr.");
            czwartek.add("Zajencia wych");
            czwartek.add("Angielski");
            czwartek.add("Angielski");


            List<String> czwartekClass = new ArrayList<>();
            czwartekClass.add("107");
            czwartekClass.add("8");
            czwartekClass.add("8");
            czwartekClass.add("108");
            czwartekClass.add("105");
            czwartekClass.add("103");
            czwartekClass.add("103");

            timetableFragmentThu.getPresenter().setDate(czwartek, czwartekClass, listTime);

                break;

            case FRI:

            List<String> piatek = new ArrayList<>();
            piatek.add("Sieci");
            piatek.add("Projektowanie");
            piatek.add("Niemiecki");
            piatek.add("Religia");
            piatek.add("Sieci");
            piatek.add("Dział");
            piatek.add("WF");
            piatek.add("WF");


            List<String> piatekClass = new ArrayList<>();
            piatekClass.add("208");
            piatekClass.add("208");
            piatekClass.add("105");
            piatekClass.add("17");
            piatekClass.add("208");
            piatekClass.add("204");
            piatekClass.add("");
            piatekClass.add("");

                timetableFragmentFri.getPresenter().setDate(piatek, piatekClass, listTime);

                break;

        }






//        }


    }



    private void setupViewPager(ViewPager viewPager) {

        Adapter adapter = new Adapter(getChildFragmentManager());
        adapter.addFragment(timetableFragmentMon, "Poniedziałek");
        adapter.addFragment(timetableFragmentTue, "Wtorek");
        adapter.addFragment(timetableFragmentWed, "Środa");
        adapter.addFragment(timetableFragmentThu, "Czwartek");
        adapter.addFragment(timetableFragmentFri, "Piątek");

        viewPager.setAdapter(adapter);


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
