package com.example.projectbus;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    public String StartStopHolder , EndStopHolder ;
    public MainViewModel() {

    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
