package com.example.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.model.Data;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivityRepo {
    private DatabaseReference databaseReference;

    private static MainActivityRepo instance;
    private ArrayList<Data> data= new ArrayList<>();

    public static MainActivityRepo getInstance(){
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

    public void setData(Data data) {
        databaseReference = FirebaseDatabase.getInstance().getReference("data");
        String key = databaseReference.push().getKey();
        data.setId(key);
        databaseReference.child(key).setValue(data);
    }
}
