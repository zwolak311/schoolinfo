package com.schoolInfo.bartosz.schoolinfo.Home.HomeView;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.schoolInfo.bartosz.schoolinfo.MainActivity.MainActivity;
import com.schoolInfo.bartosz.schoolinfo.Rest.MainInformationAboutUserAndClass;
import com.schoolInfo.bartosz.schoolinfo.Rest.POJOClassInfo;
import com.schoolInfo.bartosz.schoolinfo.Rest.TimetableMainInformation;
import com.schoolInfo.bartosz.schoolinfo.Rest.UserBaseInformation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class HomePresenter extends MvpBasePresenter<HomeView> {
//    private POJOClassInfo pojoClassInfo;
//    private UserBaseInformation UBI;
    private MainInformationAboutUserAndClass mainInformationAboutUserAndClass;
    private TimetableMainInformation.Message group;
    private TimetableMainInformation tim;


    HomePresenter() {

//        pojoClassInfo = new POJOClassInfo();
//        UBI = new UserBaseInformation();


    }




    public void setDate(MainInformationAboutUserAndClass mainInformationAboutUserAndClass, TimetableMainInformation timetableMainInformation){
//        if(mainInformationAboutUserAndClass != null) {

        this.mainInformationAboutUserAndClass = mainInformationAboutUserAndClass;


        for (TimetableMainInformation.Message groupName: timetableMainInformation.getMessage()) {
            if(groupName.getGroupname().equals(MainActivity.ACTIVE_USER_CLASS))
                this.group = groupName;
        }


//        this.tim = timetableMainInformation;


        setTimetable();

    }

    private void setCorrectDateForView(int daysToAdd, String dayOfWeek, List<String> subjects, List<String> classList){
        if(!mainInformationAboutUserAndClass.isDateEmpty()) {

            ArrayList<POJOClassInfo.Information> informationArrayList = new ArrayList<>();

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            Calendar cal = Calendar.getInstance();

            cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + daysToAdd);

            for (int i = 0; i < mainInformationAboutUserAndClass.getPojoClassInfo().getInformations().size(); i++) {


                try {
                    calendar.setTime(df.parse(mainInformationAboutUserAndClass.getPojoClassInfo().getInformations().get(i).getDate()));
                } catch (ParseException e) {
                    if(getView()!= null)
                        getView().showToast(e.toString());
                }


                if (calendar.get(Calendar.YEAR) == cal.get(Calendar.YEAR) &&
                        calendar.get(Calendar.MONTH) == cal.get(Calendar.MONTH) &&
                        cal.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH) &&
                        mainInformationAboutUserAndClass.getPojoClassInfo().getInformations().get(i).getVisibility() == 1) {

                    informationArrayList.add(mainInformationAboutUserAndClass.getPojoClassInfo().getInformations().get(i));
                }
            }


            if(getView() != null)
                getView().setRecycleView(dayOfWeek, subjects, classList, informationArrayList);


        }else {

            if(getView() != null)
                getView().networkNotAvailable();

        }
    }

    private void searchDataForCurrentDay(int daysToAdd, String dayString, String dayName){

        ArrayList<String> subjects = new ArrayList<>();

        for (TimetableMainInformation.Message.Days day: group.getDays()) {
            if(day.getName().equals(dayString))
                for (TimetableMainInformation.Message.Days.Subject subject: day.getSubjects())
                    subjects.add(subject.getName());
        }

        setCorrectDateForView(daysToAdd, dayName, subjects, null);


    }


    private void setTimetable(){

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());


        if(getView() != null) {

            List<String> listDataHeader = new ArrayList<>();

            // Adding child data
            listDataHeader.add("Poniedziałek");
            listDataHeader.add("Wtorek");
            listDataHeader.add("Środa");
            listDataHeader.add("Czwartek");
            listDataHeader.add("Piątek");



            switch (cal.get(Calendar.DAY_OF_WEEK)) {

                case Calendar.MONDAY:

                    if(cal.get(Calendar.HOUR_OF_DAY) > 14)
                        searchDataForCurrentDay(1, "tuesday", listDataHeader.get(1));
                    else
                        searchDataForCurrentDay(0, "monday", listDataHeader.get(0));

                    break;
                case Calendar.TUESDAY:

                    if(cal.get(Calendar.HOUR_OF_DAY) > 14)
                        searchDataForCurrentDay(1, "wednesday", listDataHeader.get(2));

                    else
                        searchDataForCurrentDay(0, "tuesday", listDataHeader.get(1));

                    break;
                case Calendar.WEDNESDAY:

                    if(cal.get(Calendar.HOUR_OF_DAY) > 14)
                        searchDataForCurrentDay(1, "thursday", listDataHeader.get(3));
                    else
                        searchDataForCurrentDay(0, "wednesday", listDataHeader.get(2));


                    break;
                case Calendar.THURSDAY:


                    if(cal.get(Calendar.HOUR_OF_DAY) > 14)
                        searchDataForCurrentDay(1, "friday", listDataHeader.get(4));

                    else
                        searchDataForCurrentDay(0, "thursday", listDataHeader.get(3));


                    break;
                case Calendar.FRIDAY:

                    if(cal.get(Calendar.HOUR_OF_DAY) > 14)
                        searchDataForCurrentDay(1, "monday", listDataHeader.get(1));

                    else
                        searchDataForCurrentDay(0, "friday", listDataHeader.get(4));


                    break;
                case Calendar.SATURDAY:

                    searchDataForCurrentDay(2, "monday", listDataHeader.get(0));


                    break;
                case Calendar.SUNDAY:
                    searchDataForCurrentDay(1, "monday", listDataHeader.get(0));


                    break;
            }


        }


    }

}
