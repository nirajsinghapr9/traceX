package com.example.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.model.Data;

import java.util.ArrayList;
import java.util.List;

public class MainActivityRepo {

    private static MainActivityRepo instance;
    private ArrayList<Data> data= new ArrayList<>();

    private static MainActivityRepo getInstance(){
        if(instance==null){
            instance=new MainActivityRepo();
        }
        return instance;
    }

    public MutableLiveData<List<Data>> getData(){
        fetchData();
        MutableLiveData<List<Data>> mutableLiveData= new MutableLiveData<>();
        mutableLiveData.setValue(data);
        return mutableLiveData;
    }

    private void fetchData() {
    }
}
