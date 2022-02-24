package com.example.tracexassesment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
        viewModel.init();
        viewModel.getData().observe(this, new Observer<List<Data>>() {
            @Override
            public void onChanged(List<Data> data) {
                adapter.setList(data);
            }
        });
        initAdapter();

        mDataBinding.btnAdd.setOnClickListener(view -> openDailogue());

    }

    private void openDailogue() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_form);

        EditText etName = (EditText) dialog.findViewById(R.id.name);
        EditText etEmail = (EditText) dialog.findViewById(R.id.email);
        EditText etPhone = (EditText) dialog.findViewById(R.id.contact);


        Button dialogButton = (Button) dialog.findViewById(R.id.save);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    storeData();
                }
            }

            private void storeData() {
                String name = "", email = "", phone = "";
                if (!etName.getText().toString().isEmpty()) {
                    name = etName.getText().toString();
                }
                if (!etEmail.getText().toString().isEmpty()) {
                    email = etEmail.getText().toString();
                }
                if (!etPhone.getText().toString().isEmpty()) {
                    phone = etPhone.getText().toString();
                }
                viewModel.addData(new Data(name, email, phone));
            }

            private boolean isValid() {
                if (etName.getText().toString().isEmpty() || etName.getText().toString() == null) {
                    return false;
                }
                return true;
            }
        });
        dialog.show();
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