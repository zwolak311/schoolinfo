package com.schoolInfo.bartosz.schoolinfo.Subjects;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.schoolInfo.bartosz.schoolinfo.Rest.SubjectList;


public interface SubjectsView extends MvpView {
    void setViewOfSubjectList(SubjectList subjectList);

    void listIsEmpty();


    void networkNotAvailable();
}
