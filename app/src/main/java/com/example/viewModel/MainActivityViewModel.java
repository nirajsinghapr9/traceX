package com.example.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.model.Data;
import com.example.repository.MainActivityRepo;

import java.util.List;

public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<List<Data>> mutableLiveData;
    private MainActivityRepo mainActivityRepo;

    public void init() {
        if (mutableLiveData != null) {
            return;
        }
        mainActivityRepo = MainActivityRepo.getInstance();
        mutableLiveData = mainActivityRepo.getData();
    }

    public void addData(final Data data) {
        mainActivityRepo.setData(data);
    }

    public LiveData<List<Data>> getData() {
        return mutableLiveData;
    }
}
