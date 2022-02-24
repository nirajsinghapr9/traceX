package com.example.tracexassesment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.model.Data;
import com.example.tracexassesment.databinding.ActivityMainBinding;
import com.example.viewModel.MainActivityViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainAdapter.ClickListener {

    private ActivityMainBinding mDataBinding;
    private MainAdapter adapter;
    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(MainActivity.this).get(MainActivityViewModel.class);
        viewModel.getData().observe(this, new Observer<List<Data>>() {
            @Override
            public void onChanged(List<Data> data) {
                adapter.setList(data);
            }
        });
        initAdapter();


    }

    private void initAdapter() {
        adapter = new MainAdapter(this);
        adapter.setOnClickListener(this);
        mDataBinding.recyclerView.setAdapter(adapter);

    }

    @Override
    public void onClickViewOrder(int position, Data data) {

    }
}