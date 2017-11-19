package com.schoolInfo.bartosz.schoolinfo.MainActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenu;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hannesdorfmann.mosby.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.MvpLceViewStateActivity;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.data.RetainingLceViewState;
import com.schoolInfo.bartosz.schoolinfo.Home.MainInformation.AddedInfo.AddedDialog;
import com.schoolInfo.bartosz.schoolinfo.Grade.AddGrade;
import com.schoolInfo.bartosz.schoolinfo.Grade.GradeFragment;
import com.schoolInfo.bartosz.schoolinfo.GroupList.AddNewClassDialog;
import com.schoolInfo.bartosz.schoolinfo.GroupList.GroupMainFragment;
import com.schoolInfo.bartosz.schoolinfo.Home.TabFragment;
import com.schoolInfo.bartosz.schoolinfo.LogInAndRegister.LoginRegisterActivity;
import com.schoolInfo.bartosz.schoolinfo.R;
import com.schoolInfo.bartosz.schoolinfo.Rest.MainInformationAboutUserAndClass;
import com.schoolInfo.bartosz.schoolinfo.Rest.POJOClassInfo;
import com.schoolInfo.bartosz.schoolinfo.Rest.UserBaseInformation;
import com.schoolInfo.bartosz.schoolinfo.Subjects.AddSubject;
import com.schoolInfo.bartosz.schoolinfo.Subjects.SubjectsFragment;
import com.schoolInfo.bartosz.schoolinfo.Timetable.TimetableTabFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.yavski.fabspeeddial.FabSpeedDial;

public class MainActivity extends MvpLceViewStateActivity<DrawerLayout, MainInformationAboutUserAndClass,MainActivityView, MainActivityPresenter> implements
        NavigationView.OnNavigationItemSelectedListener, FabSpeedDial.MenuListener , FloatingActionButton.OnClickListener ,MainActivityView {
    boolean isMainGroupVisible = true;
    boolean witchCircle = false;
    boolean isCreateFirstTime = true;

    FragmentManager fragmentManager;


    public static String TOKEN;
    public static String ACTIVE_USER_CLASS;

    public static int MANAGE_INFO;
    public static int MANAGE_DATA;
    public static int MANAGE_MEMBERS;
    public static int MANAGE_TIMETABLE;



    public static final int NAV_ITEM_COUNT = 6;

    UserBaseInformation UBI;
    POJOClassInfo pojoClassInfo;
    MainInformationAboutUserAndClass mainInformationAboutUserAndClass = new MainInformationAboutUserAndClass();

    TextView email, username, activeClassText;

    @BindView(R.id.nav_view) NavigationView navigationView;
//    @BindView(R.id.swipeToRefreshMain) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.loadingView) ProgressBar progressBar;
    @BindView(R.id.fabSpeedDial) FabSpeedDial fabSpeedDial;
    @BindView(R.id.floatingActionButton) FloatingActionButton floatingActionButton;
    @BindView(R.id.contentView) DrawerLayout drawer;
    @BindView(R.id.toolbar) Toolbar toolbar;


//    @BindView(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;



    TabFragment tabFragment;
    GroupMainFragment groupMainFragment;
    GradeFragment gradeFragment;
    TimetableTabFragment timetableTabFragment;
    SubjectsFragment subjectsFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setRetainInstance(true);

        floatingActionButton.hide();


        fragmentManager = getSupportFragmentManager();
        timetableTabFragment = new TimetableTabFragment();
        groupMainFragment = new GroupMainFragment();
        gradeFragment = new GradeFragment();
        subjectsFragment = new SubjectsFragment();


//
//        Check if user is log in
//
        if(isLoggedIn()){
            SharedPreferences preferences = getSharedPreferences(getString(R.string.token_string), MODE_PRIVATE);
            TOKEN = preferences.getString(getString(R.string.token_string), null);
            ACTIVE_USER_CLASS = null;

        }else
            logOut();
//
//        Check if user is log in
//



        setSupportActionBar(toolbar);


        fabSpeedDial.setMenuListener(this);
        floatingActionButton.setOnClickListener(this);


        tabFragment = new TabFragment();




//
//        Start handle NavigationView
//

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(2).setEnabled(false);
        navigationView.getMenu().getItem(2).setVisible(false);


        View header = navigationView.getHeaderView(0);

        email = header.findViewById(R.id.nav_email_text);
        username = header.findViewById(R.id.nav_name_text);
        activeClassText =  header.findViewById(R.id.nav_active_class_text);
        final ImageView nav_arrow = header.findViewById(R.id.nav_arrow_icon);


        nav_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onArrowClick(navigationView, nav_arrow);
            }
        });



        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {


            }

            @Override
            public void onDrawerClosed(View drawerView) {
                if(!isMainGroupVisible){
                    onArrowClick(navigationView, nav_arrow);
                }
            }

            @Override
            public void onDrawerStateChanged(int newState) {



            }
        });


//
//        End handle NavigationView
//


    } /*End onCreate() method*/




    @Override
    protected void onResume() {
        super.onResume();

    }


//
//    Start presenter methods
//

    @NonNull
    @Override public LceViewState<MainInformationAboutUserAndClass, MainActivityView> createViewState() {
        Log.d("MVPMain", "createViewState");

        return new RetainingLceViewState<MainInformationAboutUserAndClass, MainActivityView>();
    }



    @Override
    public void loadData(boolean pullToRefresh) {
        progressBar.setVisibility(View.VISIBLE);

        presenter.getInfoAboutUserOnStart(TOKEN, ACTIVE_USER_CLASS);

        Log.d("MVPMain", "loadDate");

    }


    @Override
    public void setData(MainInformationAboutUserAndClass data) {
        UBI = data.getUBI();
        pojoClassInfo = data.getPojoClassInfo();
        mainInformationAboutUserAndClass = data;


        Log.d("MVPMain", "setDate");

    }


    @Override
    public MainInformationAboutUserAndClass getData() {

        return presenter.getMainInformationAboutUserAndClass();
    }

    @Override
    public void showContent() {
        super.showContent();



            switch (mainInformationAboutUserAndClass.getWithScreenOnTop()) {

                case TabFragment.MAIN_SCREEN:


                        onNavigationItemSelected(navigationView.getMenu().getItem(TabFragment.MAIN_SCREEN));
                        navigationView.getMenu().getItem(TabFragment.MAIN_SCREEN).setChecked(true);


                    break;
                case TimetableTabFragment.TIMETABLE_SCREEN:


                    onNavigationItemSelected(navigationView.getMenu().getItem(TimetableTabFragment.TIMETABLE_SCREEN));
                    navigationView.getMenu().getItem(TimetableTabFragment.TIMETABLE_SCREEN).setChecked(true);



                    break;
                case GroupMainFragment.GROUP_SCREEN:


                        onNavigationItemSelected(navigationView.getMenu().getItem(GroupMainFragment.GROUP_SCREEN));
                        navigationView.getMenu().getItem(GroupMainFragment.GROUP_SCREEN).setChecked(true);




                    break;

                case GradeFragment.GRADE_SCREEN:

                    onNavigationItemSelected(navigationView.getMenu().getItem(GradeFragment.GRADE_SCREEN));
                    navigationView.getMenu().getItem(GradeFragment.GRADE_SCREEN).setChecked(true);

                    break;

                case SubjectsFragment.SUBJECT_SCREEN:

                    onNavigationItemSelected(navigationView.getMenu().getItem(SubjectsFragment.SUBJECT_SCREEN));
                    navigationView.getMenu().getItem(SubjectsFragment.SUBJECT_SCREEN).setChecked(true);

                    break;
            }

            setActionbarName();

        if (isCreateFirstTime && !mainInformationAboutUserAndClass.isDateEmpty())
            setBaseInformation(UBI);




        isCreateFirstTime = false;
        Log.d("MVPMain", "showContent");

    }




    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
//        mainInformationAboutUserAndClass = null;


        Log.d("MVPMain", "error");


        showContent();

        setUserInfoFromFile();

        return "Sprawdż połączenie z internetem.";
    }






    @NonNull
    @Override
    public MainActivityPresenter createPresenter() {
        return new MainActivityPresenter();
    }


    @Override
    public void setBaseInformation(UserBaseInformation userInformation) {
        UBI = userInformation;
        saveUserToFile();

        setActiveUserClass();
        email.setText(UBI.getEmail());
        username.setText(UBI.getName());


        setupNavigationClassItems(UBI);

        setClassInformation(pojoClassInfo);
    }


    @Override
    public void setClassInformation(POJOClassInfo pojoClassInfo) {
        this.pojoClassInfo = pojoClassInfo;


        MANAGE_DATA = pojoClassInfo.getUser().getManage_data();
        MANAGE_INFO = pojoClassInfo.getUser().getManage_info();
        MANAGE_MEMBERS = pojoClassInfo.getUser().getManage_members();
        MANAGE_TIMETABLE = pojoClassInfo.getUser().getManage_timetable();

    }








    @Override
    public void getInfoForDelete() {
        presenter.deleteInfo(tabFragment.getInformationFragment().getPresenter().getHomeworkIdArrayListForDelete());

        refreshOptionMenu(false);
    }



//
//    End presenter methods
//





//
//    Start user login methods
//
    private boolean isLoggedIn(){
        SharedPreferences preferences = getSharedPreferences(getString(R.string.token_string), MODE_PRIVATE);


        return preferences.getBoolean("status", false);
    }

    private void logOut(){
        SharedPreferences preferences = getSharedPreferences(getString(R.string.token_string), MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean("status", false);
        editor.remove(getString(R.string.token_string));

        editor.apply();
        editor.commit();

        startActivity(new Intent(MainActivity.this, LoginRegisterActivity.class));
        finish();
    }
//
//    End user login methods
//





//
//    Start NavigationView methods
//
    private void onArrowClick(NavigationView navigationView, ImageView arrow){
        if(!isMainGroupVisible) {
            arrow.setImageResource(R.drawable.arrow_drop_down);
            navigationView.getMenu().setGroupVisible(R.id.nav_main_group, true);

            for (int i = 0; i < UBI.getGroups().size(); i++) {
                changeClassListVisible(false,(i + NAV_ITEM_COUNT));
            }

            isMainGroupVisible = true;
        }else{
            arrow.setImageResource(R.drawable.arrow_drop_up);
            navigationView.getMenu().setGroupVisible(R.id.nav_main_group, false);



            for (int i = 0; i < UBI.getGroups().size(); i++) {
                changeClassListVisible(true,(i + NAV_ITEM_COUNT));
            }

            isMainGroupVisible = false;
        }
    }


    private void changeClassListVisible(boolean visible, int id){
        navigationView.getMenu().getItem(id).setVisible(visible);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_home) {
            floatingActionButton.hide();


            fabSpeedDial.show();

            if(!isCreateFirstTime)
                presenter.setTopScreen(TabFragment.MAIN_SCREEN);



            if(tabFragment.isVisible()){
                tabFragment.setMainInformationAboutUserAndClass(mainInformationAboutUserAndClass, getPresenter().getTimetableMainInformation());
            }else {
                fragmentManager.beginTransaction().replace(R.id.content_main, tabFragment).commit();
            }


//            tabFragment.setMainInformationAboutUserAndClass(mainInformationAboutUserAndClass);

        } else if (id == R.id.nav_timetable) {


            floatingActionButton.hide();
            fabSpeedDial.hide();

            if(timetableTabFragment.isVisible()){
                timetableTabFragment.setDate(getPresenter().getTimetableMainInformation().getMessage());
            }else {
                fragmentManager.beginTransaction().replace(R.id.content_main, timetableTabFragment).commit();
            }

            presenter.setTopScreen(TimetableTabFragment.TIMETABLE_SCREEN);



        }else if(id == R.id.nav_grades_list){

            floatingActionButton.show();
            fabSpeedDial.hide();


            presenter.setTopScreen(GradeFragment.GRADE_SCREEN);


            if(gradeFragment.isVisible()){
//                gradeFragment.setMainInformationAboutUserAndClass(mainInformationAboutUserAndClass);
            }else {
                fragmentManager.beginTransaction().replace(R.id.content_main, gradeFragment).commit();
            }
//            groupMainFragment.getPresenter().setMainInformationAboutUserAndClass(mainInformationAboutUserAndClass);


        }else if(id == R.id.nav_subjects_list){

        floatingActionButton.show();
        fabSpeedDial.hide();


        presenter.setTopScreen(SubjectsFragment.SUBJECT_SCREEN);


        if(subjectsFragment.isVisible()){
            subjectsFragment.getPresenter().setSubjectList(presenter.loadSubjectList());
        }else {
            fragmentManager.beginTransaction().replace(R.id.content_main, subjectsFragment).commit();
        }
//            groupMainFragment.getPresenter().setMainInformationAboutUserAndClass(mainInformationAboutUserAndClass);


        }else if(id == R.id.nav_add_remove_membership_class){


            floatingActionButton.show();
            fabSpeedDial.hide();

            presenter.setTopScreen(GroupMainFragment.GROUP_SCREEN);


            if(groupMainFragment.isVisible()){
                groupMainFragment.setMainInformationAboutUserAndClass(mainInformationAboutUserAndClass);
            }else {
                fragmentManager.beginTransaction().replace(R.id.content_main, groupMainFragment).commit();
            }
//            groupMainFragment.getPresenter().setMainInformationAboutUserAndClass(mainInformationAboutUserAndClass);


        } else if (id == R.id.nav_logout) {
            logOut();

        }else if (id >= NAV_ITEM_COUNT){


            switch (mainInformationAboutUserAndClass.getWithScreenOnTop()) {

                case TabFragment.MAIN_SCREEN:

                    tabFragment.setRefreshing(true);

                    break;
                case TimetableTabFragment.TIMETABLE_SCREEN:


                    timetableTabFragment.setRefreshing(true);

                    break;
                case GroupMainFragment.GROUP_SCREEN:

                    groupMainFragment.setRefreshing(true);

                    break;

                case GradeFragment.GRADE_SCREEN:




                    break;

                case SubjectsFragment.SUBJECT_SCREEN:

                    subjectsFragment.setRefreshing(true);

                    break;
            }

            ACTIVE_USER_CLASS = UBI.getGroups().get(id - NAV_ITEM_COUNT).getGroupname();

            loadData(true);
        }



        setActionbarName();

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setActiveUserClass() {

        if (UBI.getGroups().size() > 0) {

            if (ACTIVE_USER_CLASS == null) {
                ACTIVE_USER_CLASS = UBI.getGroups().get(0).getGroupname();
                activeClassText.setText(UBI.getGroups().get(0).getName());
            } else
                for (int i = 0; i < UBI.getGroups().size(); i++)
                    if (UBI.getGroups().get(i).getGroupname().equals(ACTIVE_USER_CLASS))
                        activeClassText.setText(UBI.getGroups().get(i).getName());

        }else {

            ACTIVE_USER_CLASS = null;
            navigationView.getMenu().getItem(0).setEnabled(false);
            navigationView.getMenu().getItem(1).setChecked(true);
            onNavigationItemSelected(navigationView.getMenu().getItem(1));

        }
    }



    private void setupNavigationClassItems(UserBaseInformation UBI){


        for (int i = 0; i < UBI.getGroups().size(); i++)
            navigationView.getMenu().removeItem(i+NAV_ITEM_COUNT);



        for (int i = 0; i < UBI.getGroups().size(); i++) {

            navigationView.getMenu().add(Menu.NONE, (i + NAV_ITEM_COUNT), Menu.NONE, UBI.getGroups().get(i).getName());
            navigationView.getMenu().getItem(i + NAV_ITEM_COUNT).setVisible(false);
            navigationView.getMenu().getItem(i + NAV_ITEM_COUNT).setIcon(R.drawable.deflaut_user_picture);
        }


    }
//
//    End NavigationView methods
//


//
//      Start handle fabSpeedDial
//

    @Override
    public boolean onPrepareMenu(NavigationMenu navigationMenu) {
        return true;
    }

    @Override
    public boolean onMenuItemSelected(MenuItem menuItem) {

        switch (menuItem.getItemId()){

            case R.id.action_news:

                if(MANAGE_DATA == 1) {
                    AddedDialog addedDialogNews = new AddedDialog();
                    addedDialogNews.show(getSupportFragmentManager(), "Inne");
                }else
                    Toast.makeText(MainActivity.this, "Nie posiadasz wystarczających uprawnień.", Toast.LENGTH_SHORT).show();

                break;


            case R.id.action_exam:

                if(MANAGE_DATA == 1) {
                    AddedDialog addedDialogExam = new AddedDialog();
                    addedDialogExam.show(getSupportFragmentManager(), "Sprawdzian");
                }else
                    Toast.makeText(MainActivity.this, "Nie posiadasz wystarczających uprawnień.", Toast.LENGTH_SHORT).show();

                break;

            case R.id.action_homework:


                if(MANAGE_DATA == 1) {
                    AddedDialog addedDialogHomework = new AddedDialog();
                    addedDialogHomework.show(getSupportFragmentManager(), "Praca domowa");
                }else
                    Toast.makeText(MainActivity.this, "Nie posiadasz wystarczających uprawnień.", Toast.LENGTH_SHORT).show();

                break;


        }

        return true;
    }

    @Override
    public void onClick(View view) {

        switch (presenter.getMainInformationAboutUserAndClass().getWithScreenOnTop()){

            case GradeFragment.GRADE_SCREEN:


                AddGrade addGrade = new AddGrade();
                addGrade.show(getSupportFragmentManager(), "Dodaj ocene");


                break;
            case  SubjectsFragment.SUBJECT_SCREEN:

                AddSubject addSubject = new AddSubject().newInstance("", -1);
                addSubject.show(getSupportFragmentManager(), "Dodaj przdmiot");

                break;

            case GroupMainFragment.GROUP_SCREEN:

                AddNewClassDialog addNewClassDialog = new AddNewClassDialog();
                addNewClassDialog.show(getSupportFragmentManager(), "Dodaj klasę");

                break;

        }

    }

    @Override
    public void onMenuClosed() {

    }

//
//      End handle fabSpeedDial
//




//
//    Start: option menu methods
//

    public void refreshOptionMenu(boolean witchCircle){
        this.witchCircle = witchCircle;
        invalidateOptionsMenu();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(witchCircle)
            getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_delete:

                presenter.deleteButtonIsClick();

                break;
            case R.id.action_editTimetable:

                refreshOptionMenu(false);
//                timetableTabFragment.editTimetable();

                break;

            case R.id.action_editTimetableCancle:

                refreshOptionMenu(false);

                break;

        }

        return super.onOptionsItemSelected(item);
    }

//
//  End: option menu methods
//




//
//    Start: setup user information
//
    private void saveUserToFile(){
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        editor.putString("name", UBI.getName());
        editor.putString("email", UBI.getEmail());
        editor.putInt("size", UBI.getGroups().size());


        for (int i = 0; i < UBI.getGroups().size(); i++) {
            editor.putString("groupName_" + i, UBI.getGroups().get(i).getGroupname());
            editor.putString("name_" + i, UBI.getGroups().get(i).getName());
        }

        editor.apply();
        editor.commit();

    }



    private void setUserInfoFromFile(){
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);

        UBI = new UserBaseInformation();
        UBI.getUser().setName(sharedPreferences.getString("name", ""));
        UBI.getUser().setEmail(sharedPreferences.getString("email", ""));

        username.setText(UBI.getUser().getName());
        email.setText(UBI.getUser().getEmail());


        UBI.setClassesSize(sharedPreferences.getInt("size", 0));

        for (int i = 0; i < sharedPreferences.getInt("size", 0); i++) {

            UBI.getGroups().get(i).setGroupname(sharedPreferences.getString("groupName_" + i, ""));
            UBI.getGroups().get(i).setName(sharedPreferences.getString("name_" + i, "") );

        }

        setupNavigationClassItems(UBI);

        mainInformationAboutUserAndClass = new MainInformationAboutUserAndClass();
        mainInformationAboutUserAndClass.setDateEmpty(true);

        setActiveUserClass();
    }


//
//    End: setup user information
//


    void setActionbarName(){

        if(mainInformationAboutUserAndClass!= null) {


            if (getSupportActionBar() != null) {
                switch (mainInformationAboutUserAndClass.getWithScreenOnTop()) {

                    case TabFragment.MAIN_SCREEN:

                            getSupportActionBar().setTitle("school info");

                        break;

                    case TimetableTabFragment.TIMETABLE_SCREEN:

                            getSupportActionBar().setTitle("Plan lekcji");

                        break;

                    case GradeFragment.GRADE_SCREEN:

                            getSupportActionBar().setTitle("Oceny");

                        break;

                    case SubjectsFragment.SUBJECT_SCREEN:

                            getSupportActionBar().setTitle("Przedmioty");

                        break;

                    case GroupMainFragment.GROUP_SCREEN:

                            getSupportActionBar().setTitle("Lista klas");

                        break;

                }
            } else {
                    getSupportActionBar().setTitle("school info");
            }

        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else if(witchCircle){
            refreshOptionMenu(false);
            tabFragment.hideCircleByOnBackPressed();
        }

        else {
            super.onBackPressed();
        }
    }

    public void setRefreshing(boolean b) {

            switch (mainInformationAboutUserAndClass.getWithScreenOnTop()) {

                case TabFragment.MAIN_SCREEN:

                    tabFragment.setRefreshing(b);

                    break;

                case TimetableTabFragment.TIMETABLE_SCREEN:


                    timetableTabFragment.setRefreshing(b);


                    break;

                case GradeFragment.GRADE_SCREEN:

//                    gradeFragment.setRefreshing(b);

                    break;

                case SubjectsFragment.SUBJECT_SCREEN:

                    subjectsFragment.setRefreshing(b);

                    break;

                case GroupMainFragment.GROUP_SCREEN:

                    groupMainFragment.setRefreshing(b);

                    break;

            }

    }


//    private void noInternetSnackBar(){
//        Snackbar.make(coordinatorLayout, "Brak połączenia", Snackbar.LENGTH_LONG)
//                .setActionTextColor(getResources().getColor(R.color.colorDirtyWhite))
//                .setAction("PONÓW PRUBĘ", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        presenter.getInfoAboutUserOnStart(TOKEN);
//                    }
//                }).show();
//    }


}
