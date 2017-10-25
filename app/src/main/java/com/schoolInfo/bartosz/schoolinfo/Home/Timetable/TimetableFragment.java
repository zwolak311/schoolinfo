package com.schoolInfo.bartosz.schoolinfo.Home.Timetable;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.schoolInfo.bartosz.schoolinfo.R;
import com.hannesdorfmann.mosby.mvp.MvpFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TimetableFragment extends MvpFragment<TimetableView, TimetablePresenter> implements TimetableView {
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;
    private HashMap<Integer, List<String>> listDataClassNumber;
    ExpandableAdapter expandableAdapter;

    @BindView(R.id.exLV) ExpandableListView expandableListView;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_timetable_main, container, false);
        ButterKnife.bind(this, view);

        prepareListData();

        expandableAdapter = new ExpandableAdapter();
        expandableListView.setAdapter(expandableAdapter);

        return view;
    }

    @NonNull
    @Override
    public TimetablePresenter createPresenter() {
        return new TimetablePresenter();
    }


    @Override
    public void onResume() {
        super.onResume();
        presenter.recognizeDayOfWeek();
    }

    @Override
    public void toggleMonday() {
        expandableListView.expandGroup(0);
    }

    @Override
    public void toggleTuesday() {
        expandableListView.expandGroup(1);
    }

    @Override
    public void toggleWednesday() {
        expandableListView.expandGroup(2);
    }

    @Override
    public void toggleThursday() {
        expandableListView.expandGroup(3);
    }

    @Override
    public void toggleFriday() {
        expandableListView.expandGroup(4);
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();
        listDataClassNumber = new HashMap<>();

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
        czwartek.add("Zajęcia wych");
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

        listDataChild.put(listDataHeader.get(0), poniedzialek); // Header, Child data
        listDataChild.put(listDataHeader.get(1), wtorek);
        listDataChild.put(listDataHeader.get(2), sroda);
        listDataChild.put(listDataHeader.get(3), czwartek);
        listDataChild.put(listDataHeader.get(4), piatek);

        listDataClassNumber.put(0, poniedziałekClass);
        listDataClassNumber.put(1, wtorekClass);
        listDataClassNumber.put(2, srodaClass);
        listDataClassNumber.put(3, czwartekClass);
        listDataClassNumber.put(4, piatekClass);

    }



    private class ExpandableAdapter extends BaseExpandableListAdapter {

        @Override
        public Object getChild(int groupPosition, int childPosititon) {return listDataChild.get(listDataHeader.get(groupPosition)).get(childPosititon);}

        public Object getClassNumber(int groupPosition, int childPosititon) {return listDataClassNumber.get(groupPosition).get(childPosititon);}

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            final String childText = (String) getChild(groupPosition, childPosition);
            final String classNumberText = (String) getClassNumber(groupPosition, childPosition);

            if (convertView == null) {

                LayoutInflater infalInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.home_timetable_list_item, null);

            }


            TextView number = convertView.findViewById(R.id.timetableNumber);
            TextView subject = convertView.findViewById(R.id.timetableSubject);
            TextView roomNumber = convertView.findViewById(R.id.timetableRoomNumber);
            TextView space = convertView.findViewById(R.id.spaceForTimetable);

            number.setText("" + (childPosition + 1));
            subject.setText(childText);
            roomNumber.setText(classNumberText);

            if(groupPosition == 4 && childPosition == listDataChild.get(listDataHeader.get(groupPosition)).size() - 1)
                space.setVisibility(View.VISIBLE);
            else
                space.setVisibility(View.GONE);

            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {return listDataChild.get(listDataHeader.get(groupPosition)).size();}

        @Override
        public Object getGroup(int groupPosition) {
            return listDataHeader.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return listDataHeader.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            String headerTitle = (String) getGroup(groupPosition);

            if (convertView == null) {

                LayoutInflater infalInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.home_timetable_list_group, null);

            }

            TextView timetableGroup = (TextView) convertView.findViewById(R.id.timetableGroup);
            timetableGroup.setText(headerTitle);


            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
}
