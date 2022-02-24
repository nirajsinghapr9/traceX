package com.example.tracexassesment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.model.Data;
import com.example.tracexassesment.databinding.ActivityMainBinding;
import com.example.viewModel.MainActivityViewModel;

public class MainActivity extends AppCompatActivity implements MainAdapter.ClickListener {

    private ActivityMainBinding mDataBinding;
    private MainAdapter adapter;
    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        viewModel.init();
        viewModel.getData().observe(this, data -> adapter.setList(data));
        initAdapter();

        mDataBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDailogue();
            }
        });

    }

    private void openDailogue() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_form);

        EditText etName = (EditText) dialog.findViewById(R.id.name);
        EditText etEmail = (EditText) dialog.findViewById(R.id.email);
        EditText etPhone = (EditText) dialog.findViewById(R.id.contact);
        RelativeLayout layout= (RelativeLayout) dialog.findViewById(R.id.layout);

        Button dialogButton = (Button) dialog.findViewById(R.id.save);

        layout.setOnClickListener(view -> hideKeyboard(MainActivity.this));

        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!etEmail.getText().toString().isEmpty() && isValidEmail(etEmail.getText().toString()) && charSequence.length() > 0) {
                    dialogButton.setEnabled(true);
                    dialogButton.setBackground(getResources().getDrawable(R.drawable.button_enabled));
                } else if (etEmail.getText().toString().isEmpty()) {
                    dialogButton.setEnabled(true);
                    dialogButton.setBackground(getResources().getDrawable(R.drawable.button_enabled));
                } else {
                    dialogButton.setEnabled(false);
                    dialogButton.setBackground(getResources().getDrawable(R.drawable.button_disabled));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!etEmail.getText().toString().isEmpty() && isValidEmail(etEmail.getText().toString()) && !etName.getText().toString().isEmpty()) {
                    dialogButton.setEnabled(true);
                    dialogButton.setBackground(getResources().getDrawable(R.drawable.button_enabled));
                } else {
                    dialogButton.setEnabled(false);
                    dialogButton.setBackground(getResources().getDrawable(R.drawable.button_disabled));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    storeData();
                }
            }

            private void storeData() {
                String name = "", email = "", phone = "";
                name = etName.getText().toString();
                email = etEmail.getText().toString();

                if (!etPhone.getText().toString().isEmpty()) {
                    phone = etPhone.getText().toString();
                }

                viewModel.addData(new Data("", name, email, phone));

                Toast.makeText(MainActivity.this, R.string.saved, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }

            private boolean isValid() {
                if (etName.getText().toString().isEmpty() || etName.getText().toString() == null) {
                    return false;
                }
                return true;
            }
        });
        dialog.show();
        Display display = ((WindowManager) getSystemService(this.WINDOW_SERVICE)).getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        dialog.getWindow().setLayout((4 * width) / 4, (4 * height) / 7);
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (email.matches(emailPattern)) {
            return true;
        } else {
            return false;
        }
    }


    private void initAdapter() {
        adapter = new MainAdapter(this);
        adapter.setOnClickListener(this);
        mDataBinding.recyclerView.setAdapter(adapter);

    }

    @Override
    public void onClickViewOrder(int position, Data data) {

    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}