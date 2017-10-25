package com.schoolInfo.bartosz.schoolinfo.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenu;
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
import android.widget.TextView;
import android.widget.Toast;

import com.hannesdorfmann.mosby.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.MvpLceViewStateActivity;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.data.RetainingLceViewState;
import com.schoolInfo.bartosz.schoolinfo.AddedInfo.AddedDialog;
import com.schoolInfo.bartosz.schoolinfo.GroupList.GroupMainFragment;
import com.schoolInfo.bartosz.schoolinfo.Home.TabFragment;
import com.schoolInfo.bartosz.schoolinfo.LogInAndRegister.LoginRegisterActivity;
import com.schoolInfo.bartosz.schoolinfo.R;
import com.schoolInfo.bartosz.schoolinfo.Rest.MainInformationAboutUserAndClass;
import com.schoolInfo.bartosz.schoolinfo.Rest.POJOClassInfo;
import com.schoolInfo.bartosz.schoolinfo.Rest.UserBaseInformation;
import com.schoolInfo.bartosz.schoolinfo.Timetable.TimetableTabFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.yavski.fabspeeddial.FabSpeedDial;

public class MainActivity extends MvpLceViewStateActivity<DrawerLayout, MainInformationAboutUserAndClass,MainActivityView, MainActivityPresenter> implements
        NavigationView.OnNavigationItemSelectedListener, FabSpeedDial.MenuListener ,MainActivityView {
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



    public static final int NAV_ITEM_COUNT = 4;

    UserBaseInformation UBI;
    POJOClassInfo pojoClassInfo;
    MainInformationAboutUserAndClass mainInformationAboutUserAndClass;

    TextView email, username, activeClassText;

    @BindView(R.id.nav_view) NavigationView navigationView;
//    @BindView(R.id.swipeToRefreshMain) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.fabSpeedDial) FabSpeedDial fabSpeedDial;
    @BindView(R.id.contentView) DrawerLayout drawer;
    @BindView(R.id.toolbar) Toolbar toolbar;


//    @BindView(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;



    TabFragment tabFragment;
    GroupMainFragment groupMainFragment;
    TimetableTabFragment timetableTabFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setRetainInstance(true);


        fragmentManager = getSupportFragmentManager();


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


        tabFragment = new TabFragment();
        groupMainFragment = new GroupMainFragment();
        timetableTabFragment = new TimetableTabFragment();


        setSupportActionBar(toolbar);


        fabSpeedDial.setMenuListener(this);






//
//        Start handle NavigationView
//

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);


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
        presenter.getInfoAboutUserOnStart(TOKEN, ACTIVE_USER_CLASS);

        Log.d("MVPMain", "loadDate");

    }


    @Override
    public void setData(MainInformationAboutUserAndClass data) {

        if(data != null) {
            UBI = data.getUBI();
            pojoClassInfo = data.getPojoClassInfo();
            mainInformationAboutUserAndClass = data;

            if (isCreateFirstTime)
                setBaseInformation(UBI);
        }
        Log.d("MVPMain", "setDate");
    }


    @Override
    public MainInformationAboutUserAndClass getData() {

        return presenter.getMainInformationAboutUserAndClass();
    }

    @Override
    public void showContent() {
        super.showContent();
        Log.d("MVPMain", "showContent");

        if(mainInformationAboutUserAndClass != null) {

            switch (mainInformationAboutUserAndClass.getWithScreenOnTop()) {

                case TabFragment.MAIN_SCREEN:
                    if (getSupportActionBar() != null)
                        getSupportActionBar().setTitle("school info");


                    onNavigationItemSelected(navigationView.getMenu().getItem(TabFragment.MAIN_SCREEN));
                    navigationView.getMenu().getItem(TabFragment.MAIN_SCREEN).setChecked(true);


                    break;
                case TimetableTabFragment.TIMETABLE_SCREEN:
                    if (getSupportActionBar() != null)
                        getSupportActionBar().setTitle("Plan lekcji");


                    onNavigationItemSelected(navigationView.getMenu().getItem(TimetableTabFragment.TIMETABLE_SCREEN));
                    navigationView.getMenu().getItem(TimetableTabFragment.TIMETABLE_SCREEN).setChecked(true);


                    break;
                case GroupMainFragment.GROUP_SCREEN:
                    if (getSupportActionBar() != null)
                        getSupportActionBar().setTitle("Lista klas");


                    onNavigationItemSelected(navigationView.getMenu().getItem(GroupMainFragment.GROUP_SCREEN));
                    navigationView.getMenu().getItem(GroupMainFragment.GROUP_SCREEN).setChecked(true);

                    break;


            }

        }
        isCreateFirstTime = false;
    }




    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        mainInformationAboutUserAndClass = null;

        setUserInfoFromFile();



        tabFragment.setMainInformationAboutUserAndClass(null);


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
    public void networkNotAvailable(String s) {

        tabFragment.refreshPojoClass(null);
        setUserInfoFromFile();

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


            fabSpeedDial.show();


            if(!isCreateFirstTime)
                presenter.setTopScreen(TabFragment.MAIN_SCREEN);


            fragmentManager.beginTransaction().replace(R.id.content_main, tabFragment).commit();

            tabFragment.setMainInformationAboutUserAndClass(mainInformationAboutUserAndClass);


        } else if (id == R.id.nav_timetable) {


            fabSpeedDial.hide();


            presenter.setTopScreen(TimetableTabFragment.TIMETABLE_SCREEN);


            fragmentManager.beginTransaction().replace(R.id.content_main, timetableTabFragment).commit();


        }else if(id == R.id.nav_add_remove_membership_class){

            fabSpeedDial.hide();

            presenter.setTopScreen(GroupMainFragment.GROUP_SCREEN);


            fragmentManager.beginTransaction().replace(R.id.content_main, groupMainFragment).commit();


        } else if (id == R.id.nav_logout) {
            logOut();

        }else if (id >= NAV_ITEM_COUNT){

            ACTIVE_USER_CLASS = UBI.getGroups().get(id - NAV_ITEM_COUNT).getGroupname();

            loadData(true);
        }

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

            navigationView.getMenu().getItem(0).setEnabled(true);
            navigationView.getMenu().getItem(0).setChecked(true);
            onNavigationItemSelected(navigationView.getMenu().getItem(0));
        }else {

            ACTIVE_USER_CLASS = null;
            navigationView.getMenu().getItem(0).setEnabled(false);
            navigationView.getMenu().getItem(1).setChecked(true);
            onNavigationItemSelected(navigationView.getMenu().getItem(2));

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

        setActiveUserClass();
    }


//
//    End: setup user information
//




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
