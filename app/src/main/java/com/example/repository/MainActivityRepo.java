package com.example.repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.example.model.Data;
import com.example.tracexassesment.DataLoadListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivityRepo {
    private DatabaseReference databaseReference;

    private static MainActivityRepo instance;
    private ArrayList<Data> data= new ArrayList<>();
    private static Context mContext;
    private static DataLoadListener dataLoadListener;

    public static MainActivityRepo getInstance(Context context){
        mContext=context;
        if(instance==null){
            instance=new MainActivityRepo();
        }
        dataLoadListener= (DataLoadListener) mContext;
        return instance;
    }

    public MutableLiveData<List<Data>> getData(){
        fetchData();
        MutableLiveData<List<Data>> mutableLiveData= new MutableLiveData<>();
        mutableLiveData.setValue(data);
        return mutableLiveData;
    }

    private void fetchData() {
        databaseReference = FirebaseDatabase.getInstance().getReference("data");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                data.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    data.add(dataSnapshot.getValue(Data.class));
                }
                dataLoadListener.onDataLoaded();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void setData(Data data) {
        databaseReference = FirebaseDatabase.getInstance().getReference("data");
        String key = databaseReference.push().getKey();
        data.setId(key);
        databaseReference.child(key).setValue(data);
    }
}
