package com.schoolInfo.bartosz.schoolinfo.Home.HomeView;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.schoolInfo.bartosz.schoolinfo.Rest.MainInformationAboutUserAndClass;
import com.schoolInfo.bartosz.schoolinfo.Rest.POJOClassInfo;
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


    HomePresenter() {

//        pojoClassInfo = new POJOClassInfo();
//        UBI = new UserBaseInformation();


    }




    public void setDate(MainInformationAboutUserAndClass mainInformationAboutUserAndClass){
//        if(mainInformationAboutUserAndClass != null) {

        this.mainInformationAboutUserAndClass = mainInformationAboutUserAndClass;

//            this.UBI = mainInformationAboutUserAndClass.getUBI();
//            this.pojoClassInfo = mainInformationAboutUserAndClass.getPojoClassInfo();
//        }else {

//        }
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

            // Adding child data
            List<String> poniedzialek = new ArrayList<>();
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

            switch (cal.get(Calendar.DAY_OF_WEEK)) {

                case Calendar.MONDAY:

                    if(cal.get(Calendar.HOUR_OF_DAY) > 14)
                        setCorrectDateForView(1, listDataHeader.get(1), wtorek, wtorekClass);
                    else
                        setCorrectDateForView(0, listDataHeader.get(0), poniedzialek, poniedziałekClass);

                    break;
                case Calendar.TUESDAY:

                    if(cal.get(Calendar.HOUR_OF_DAY) > 14)
                        setCorrectDateForView(1, listDataHeader.get(2), sroda, srodaClass);
                    else
                        setCorrectDateForView(0, listDataHeader.get(1), wtorek, wtorekClass);

                    break;
                case Calendar.WEDNESDAY:

                    if(cal.get(Calendar.HOUR_OF_DAY) > 14)
                        setCorrectDateForView(1, listDataHeader.get(3), czwartek, czwartekClass);
                    else
                        setCorrectDateForView(0, listDataHeader.get(2), sroda, srodaClass);

                    break;
                case Calendar.THURSDAY:


                    if(cal.get(Calendar.HOUR_OF_DAY) > 14)
                        setCorrectDateForView(1, listDataHeader.get(4), piatek, piatekClass);
                    else
                        setCorrectDateForView(0, listDataHeader.get(3), czwartek, czwartekClass);

                    break;
                case Calendar.FRIDAY:

                    if(cal.get(Calendar.HOUR_OF_DAY) > 14)
                        setCorrectDateForView(3, listDataHeader.get(0), poniedzialek, poniedziałekClass);
                    else
                        setCorrectDateForView(0, listDataHeader.get(4), piatek, piatekClass);

                    break;
                case Calendar.SATURDAY:

                    setCorrectDateForView(2, listDataHeader.get(0), poniedzialek, poniedziałekClass);

                    break;
                case Calendar.SUNDAY:

                    setCorrectDateForView(1, listDataHeader.get(0), poniedzialek, poniedziałekClass);

                    break;
            }


        }


    }

}
