package com.example.aff02.secondtask.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.IdRes;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.aff02.secondtask.R;
import com.example.aff02.secondtask.database.DatabaseHelper;

public class SignInActivity extends AppCompatActivity {

    public EditText edtEmail, edtPass, edtUser;
    public TextInputLayout tipEmail, tipPass, tipUser;
    public RadioGroup radioGroupFirst;
    public RadioButton rbTeacher, rbStudent;
    public Button btnSignin, btnSignup;
    public DatabaseHelper databaseHelper;
    public static final String PREFS_NAME = "MyPrefs";
    public String identity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getSupportActionBar().setTitle("Sign In");



//        for (int i = 0; i <databaseHelper.getData().size() ; i++) {
//            Log.d("Identity: ",databaseHelper.getData().get(i).getIdentity());
//        }

        initViews();
        setListeners();

    }

    private void initViews() {

        edtEmail=(EditText)findViewById(R.id.edtEmail);
        tipEmail = (TextInputLayout)findViewById(R.id.tiplemail);
        edtEmail.addTextChangedListener(new MyTextWatcher(edtEmail));

        edtPass = (EditText)findViewById(R.id.edtPass);
        tipPass = (TextInputLayout)findViewById(R.id.tiplpass);
        edtPass.addTextChangedListener(new MyTextWatcher(edtPass));

        radioGroupFirst = (RadioGroup)findViewById(R.id.signinrgrp);

        rbTeacher = (RadioButton)findViewById(R.id.radioTeacher);
        rbStudent = (RadioButton)findViewById(R.id.radioStudent);

        btnSignin = (Button)findViewById(R.id.btnsignin);
        btnSignup = (Button)findViewById(R.id.btnsignup);

        databaseHelper = new DatabaseHelper(this);
    }

    private void setListeners()
    {
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });

        radioGroupFirst.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedID) {

                if (checkedID == R.id.radioTeacher)
                {
                    identity = "Teacher";
                    Toast.makeText(getApplicationContext(),"You Selected :"+identity,Toast.LENGTH_LONG).show();
                }
                if (checkedID == R.id.radioStudent)
                {
                    identity = "Student";
                    Toast.makeText(getApplicationContext(),"You Selected :"+identity,Toast.LENGTH_LONG).show();
                }
            }
        });

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyFromSqlite();
            }
        });

    }

    public void verifyFromSqlite() {

        String pass = edtPass.getText().toString();
        String email = edtEmail.getText().toString();


        if (email.isEmpty() && !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            edtEmail.setError("Email is Mandatory");
            edtEmail.requestFocus();
        }
        if (pass.isEmpty() && pass.length()>3 && pass.length()<=8)
        {
            edtPass.setError("Password is Mandatory");
            edtPass.requestFocus();
        }

        int radio = radioGroupFirst.getCheckedRadioButtonId();
        int selectedId = radioGroupFirst.getCheckedRadioButtonId();
        rbTeacher = (RadioButton)findViewById(selectedId);

        String teacher = rbTeacher.getText().toString();
        String student = rbStudent.getText().toString();

        if ( radio == -1)
        {
            Toast.makeText(getApplicationContext(),"Please Select Identity",Toast.LENGTH_LONG).show();
        }
        if (databaseHelper.checkUser(email,pass,identity))
        {
            userLoggedin();
            Intent intent = new Intent(SignInActivity.this,MainActivity.class);
            intent.putExtra("teacher",teacher);
            intent.putExtra("student",student);
//            String profilename = getIntent().getStringExtra("profilename");
//            Log.e("profilename",profilename);
            startActivity(intent);
        }
        else {
            Toast.makeText(getApplicationContext(),"Credentials Are Not Matched or Found",Toast.LENGTH_LONG).show();
        }
    }
    private void userLoggedin() {
        SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
        SharedPreferences.Editor edt = pref.edit();
        edt.putString("email",edtEmail.getText().toString());
//        Log.e("emmail",edtEmail.getText().toString());
        edt.putString("pass",edtPass.getText().toString());
//        Log.e("ppp",edtPass.getText().toString());
        edt.putString("teacher",rbTeacher.getText().toString());
        Log.e("TTTTT",rbTeacher.getText().toString());

        edt.putString("student",rbStudent.getText().toString());
        Log.e("SSSSS",rbStudent.getText().toString());

        edt.putBoolean("activity_executed", true);
        edt.commit();
    }

    static class MyTextWatcher implements TextWatcher
    {
        private View view;

        public MyTextWatcher(View view)
        {
            this.view = view;
        }



        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
    }
