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
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.schoolInfo.bartosz.schoolinfo.MainActivity.MainActivity;
import com.schoolInfo.bartosz.schoolinfo.R;
import com.schoolInfo.bartosz.schoolinfo.Rest.RestInterface;
import com.schoolInfo.bartosz.schoolinfo.Rest.SubjectList;
import com.schoolInfo.bartosz.schoolinfo.Rest.TimetableMainInformation;
import com.schoolInfo.bartosz.schoolinfo.Rest.Token;
import com.schoolInfo.bartosz.schoolinfo.Rest.TokenAndGroupName;
import com.schoolInfo.bartosz.schoolinfo.Timetable.OneDayInCalendar.TimetableFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class TimetableTabFragment extends Fragment {

    TimetableFragment timetableFragmentMon, timetableFragmentTue, timetableFragmentWed, timetableFragmentThu, timetableFragmentFri;

    public boolean isCreateFirstTime = true;
    public static final int TIMETABLE_SCREEN = 1;


    public static final int MON = 0;
    public static final int TUE = 1;
    public static final int WAD = 2;
    public static final int THU = 3;
    public static final int FRI = 4;

    @BindView(R.id.viewPager) ViewPager viewPager;
    @BindView(R.id.tabLayout) TabLayout tabLayout;
    TimetableMainInformation.Message currentGroup;
    TimetableMainInformation.Message.Days currentDay;
    int day;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homework_exam_conteiner, container, false);
        ButterKnife.bind(this, view);

        isCreateFirstTime = true;






        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        MainActivity mainActivity = (MainActivity) getActivity();

        if(mainActivity.getPresenter().getTimetableMainInformation() != null) {
            for (TimetableMainInformation.Message messageLoop : mainActivity.getPresenter().getTimetableMainInformation().getMessage()) {
                if (messageLoop.getGroupname().equals(MainActivity.ACTIVE_USER_CLASS))
                    currentGroup = messageLoop;
            }

            timetableFragmentMon = TimetableFragment.setDay(MON);
            timetableFragmentTue = TimetableFragment.setDay(TUE);
            timetableFragmentWed = TimetableFragment.setDay(WAD);
            timetableFragmentThu = TimetableFragment.setDay(THU);
            timetableFragmentFri = TimetableFragment.setDay(FRI);

        }else {


            timetableFragmentMon = TimetableFragment.setDay(-1);
            timetableFragmentTue = TimetableFragment.setDay(-1);
            timetableFragmentWed = TimetableFragment.setDay(-1);
            timetableFragmentThu = TimetableFragment.setDay(-1);
            timetableFragmentFri = TimetableFragment.setDay(-1);


        }




        setupViewPager(viewPager);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());


        switch (cal.get(Calendar.DAY_OF_WEEK)) {

            case Calendar.MONDAY:

                if(cal.get(Calendar.HOUR_OF_DAY) > 14)
                    viewPager.setCurrentItem(1);
                else
                    viewPager.setCurrentItem(0);

                break;
            case Calendar.TUESDAY:

                if(cal.get(Calendar.HOUR_OF_DAY) > 14)
                    viewPager.setCurrentItem(2);
                else
                    viewPager.setCurrentItem(1);


                break;
            case Calendar.WEDNESDAY:

                if(cal.get(Calendar.HOUR_OF_DAY) > 14)
                    viewPager.setCurrentItem(3);

                else
                    viewPager.setCurrentItem(2);


                break;
            case Calendar.THURSDAY:


                if(cal.get(Calendar.HOUR_OF_DAY) > 14)
                    viewPager.setCurrentItem(4);

                else
                    viewPager.setCurrentItem(3);

                break;
            case Calendar.FRIDAY:

                if(cal.get(Calendar.HOUR_OF_DAY) > 14)
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
    }

    public void setDate(TimetableMainInformation timetableMainInfo) {

        if (timetableMainInfo != null) {
            for (TimetableMainInformation.Message messageLoop : timetableMainInfo.getMessage()) {
                if (messageLoop.getGroupname().equals(MainActivity.ACTIVE_USER_CLASS))
                    this.currentGroup = messageLoop;
            }


            if (timetableFragmentMon.isVisible())
                setTimetable(0);


            if (timetableFragmentTue.isVisible())
                setTimetable(1);


            if (timetableFragmentWed.isVisible())
                setTimetable(2);


            if (timetableFragmentThu.isVisible())
                setTimetable(3);


            if (timetableFragmentFri.isVisible())
                setTimetable(4);

        }else {

            if (timetableFragmentMon.isVisible())
                timetableFragmentMon.networkNotAvailable();


            if (timetableFragmentTue.isVisible())
                timetableFragmentTue.networkNotAvailable();

            if (timetableFragmentWed.isVisible())
                timetableFragmentWed.networkNotAvailable();

            if (timetableFragmentThu.isVisible())
                timetableFragmentThu.networkNotAvailable();

            if (timetableFragmentFri.isVisible())
                timetableFragmentFri.networkNotAvailable();

        }




    }


    public void setTimetable(int today){
        this.day = today;


//        if(currentGroup!=null) {

            switch (today) {


                case MON:


                    for (TimetableMainInformation.Message.Days day : currentGroup.getDays()) {
                        if (day.getName().equals("monday")) {
                            currentDay = day;
                        }
                    }


                    timetableFragmentMon.getPresenter().setDate(currentDay);

                    break;

                case TUE:


                    for (TimetableMainInformation.Message.Days day : currentGroup.getDays()) {
                        if (day.getName().equals("tuesday"))
                            currentDay = day;
                    }


                    timetableFragmentTue.getPresenter().setDate(currentDay);

                    break;

                case WAD:


                    for (TimetableMainInformation.Message.Days day : currentGroup.getDays()) {
                        if (day.getName().equals("wednesday"))
                            currentDay = day;
                    }


                    timetableFragmentWed.getPresenter().setDate(currentDay);

                    break;

                case THU:


                    for (TimetableMainInformation.Message.Days day : currentGroup.getDays()) {
                        if (day.getName().equals("thursday"))
                            currentDay = day;
                    }


                    timetableFragmentThu.getPresenter().setDate(currentDay);

                    break;

                case FRI:


                    for (TimetableMainInformation.Message.Days day : currentGroup.getDays()) {
                        if (day.getName().equals("friday"))
                            currentDay = day;
                    }


                    timetableFragmentFri.getPresenter().setDate(currentDay);

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

    public void setRefreshing(boolean b) {

        if(timetableFragmentMon.isVisible())
            timetableFragmentMon.setRefreshing(b);


        if(timetableFragmentTue.isVisible())
            timetableFragmentTue.setRefreshing(b);


        if(timetableFragmentWed.isVisible())
            timetableFragmentWed.setRefreshing(b);


        if(timetableFragmentThu.isVisible())
            timetableFragmentThu.setRefreshing(b);

        if(timetableFragmentFri.isVisible())
            timetableFragmentFri.setRefreshing(b);


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
