package com.example.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.model.Data;

import java.util.List;

public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<List<Data>> mutableLiveData;

    public LiveData<List<Data>> getData() {
        return mutableLiveData;
    }
}
