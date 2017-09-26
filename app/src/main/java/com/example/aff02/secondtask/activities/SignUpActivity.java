package com.example.aff02.secondtask.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aff02.secondtask.model.DetailModel;
import com.example.aff02.secondtask.R;
import com.example.aff02.secondtask.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class SignUpActivity extends AppCompatActivity {

    public EditText edtUser,edtPass,edtconpass,edtContact,edtEmail;
    public TextInputLayout tilUser,tilPass,tilconpass,tilcontact,tilEmail;
    public Spinner batchspinner;
    public Button btnSignUp;
    public String name,email,pass,conpass,contact;
    public RadioButton rbTeacher,rbStudent,rbMale,rbFemale;
    public RadioGroup rgrpfirst,rgrpsecond;
    public DatabaseHelper databasehelper;
    public TextView txtbatch;
    private String identity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().setTitle("Sign Up");

        initViews();
        setListener();

        //For Spinner...
        List<String> spinnerItems = new ArrayList<String>();
        spinnerItems.add("Batch A");
        spinnerItems.add("Batch B");
        spinnerItems.add("Batch C");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,spinnerItems);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        batchspinner.setAdapter(arrayAdapter);

        edtPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                    String password = editable.toString();
                    edtconpass.setText(password);
            }
        });
    }

    private void initViews() {

        edtUser = (EditText)findViewById(R.id.edtUser);
        tilUser = (TextInputLayout)findViewById(R.id.tiplname);
        edtUser.addTextChangedListener(new MyTextWatcher(edtUser));


        edtPass = (EditText)findViewById(R.id.edtPass);
        tilPass = (TextInputLayout)findViewById(R.id.tiplpass);
        edtPass.addTextChangedListener(new MyTextWatcher(edtPass));

        edtconpass = (EditText)findViewById(R.id.edtConpass);
        tilconpass = (TextInputLayout) findViewById(R.id.tiplconpass);
        edtconpass.addTextChangedListener(new MyTextWatcher(edtconpass));

        edtContact = (EditText)findViewById(R.id.edtMobile);
        tilcontact = (TextInputLayout)findViewById(R.id.tiplmobile);
        edtContact.addTextChangedListener(new MyTextWatcher(edtContact));

        edtEmail = (EditText)findViewById(R.id.edtEmail);
        tilEmail = (TextInputLayout)findViewById(R.id.tiplemail);
        edtEmail.addTextChangedListener(new MyTextWatcher(edtEmail));

        batchspinner = (Spinner)findViewById(R.id.batchSpinner);

        btnSignUp = (Button)findViewById(R.id.btnsignup);

        rbTeacher = (RadioButton)findViewById(R.id.radioTeacher);
        rbStudent = (RadioButton)findViewById(R.id.radioStudent);
        rbMale = (RadioButton)findViewById(R.id.radioMale);
        rbFemale = (RadioButton)findViewById(R.id.radioFemale);

        rgrpfirst = (RadioGroup)findViewById(R.id.radioGrp);
        rgrpsecond = (RadioGroup)findViewById(R.id.radioGrptwo);

        txtbatch = (TextView)findViewById(R.id.txtbatch);

    }

    private void setListener() {

        batchspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String item = batchspinner.getItemAtPosition(i).toString();
                Snackbar.make(batchspinner,"Selected "+item,Snackbar.LENGTH_SHORT).show();
}

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!validate())
                {
                    Toast.makeText(getApplicationContext(),"Sign-Up Failed :-(",Toast.LENGTH_LONG).show();
                }
                else {
                    databasehelper = new DatabaseHelper(SignUpActivity.this);
                    DetailModel modeldata = new DetailModel();

                    modeldata.setName(name);
                    modeldata.setEmail(email);
                    modeldata.setPass(pass);
                    modeldata.setConpass(conpass);
                    modeldata.setIdentity(identity);
//                    modeldata.setContact(Integer.parseInt(String.valueOf(contact)));

//
//                    int selectedIDGender = rgrpsecond.getCheckedRadioButtonId();
//
//                    rbMale = (RadioButton)findViewById(selectedIDGender);
//                    String male = rbMale.getText().toString();
//
//                    rbFemale = (RadioButton)findViewById(selectedIDGender);
//                    String female = rbFemale.getText().toString();
//
//
//                    int selectedIDIdentity = rgrpfirst.getCheckedRadioButtonId();
//
//                    rbTeacher = (RadioButton)findViewById(selectedIDIdentity);
//                    String teacher = rbTeacher.getText().toString();
//
//
//
//                    rbStudent = (RadioButton)findViewById(selectedIDIdentity);
//                    String student = rbStudent.getText().toString();

                    databasehelper.insertData(modeldata);
                    edtUser.setText("");
                    edtEmail.setText("");
                    edtPass.setText("");
                    edtconpass.setText("");
                    edtContact.setText("");
                    rgrpfirst.clearCheck();
                    rgrpsecond.clearCheck();


                    Toast.makeText(getApplicationContext(),"Data Saved SuccessFully",Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(SignUpActivity.this,SignInActivity.class);
                    //intent.putExtra("profilename",name);
                    startActivity(intent);
                }
            }
        });

        rgrpfirst.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {


                if (checkedId == R.id.radioTeacher)
                {
                    batchspinner.setVisibility(View.INVISIBLE);
                    txtbatch.setVisibility(View.INVISIBLE);
                }
                if(checkedId == R.id.radioStudent)
                {
                    batchspinner.setVisibility(View.VISIBLE);
                    txtbatch.setVisibility(View.VISIBLE);
                }
            }
        });

    }
    public void RadioButtonClicked(View view)
    {
        identity="";
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.radioTeacher:
                if (checked)
                    identity = "Teacher";
                Toast.makeText(getApplicationContext(),"You Selected :"+identity,Toast.LENGTH_LONG).show();
                break;
            case R.id.radioStudent:
                if (checked)
                    identity = "Student";
                Toast.makeText(getApplicationContext(),"You Selected :"+identity,Toast.LENGTH_LONG).show();
                break;
        }
    }

    private boolean validate() {

        name = edtUser.getText().toString().trim();
        email = edtEmail.getText().toString().trim();
        pass = edtPass.getText().toString().trim();
        conpass = edtconpass.getText().toString().trim();
        contact = edtContact.getText().toString().trim();

        boolean valid = true;

        if (name.isEmpty() && name.length()>=3 && name.length()<32)
        {
            edtUser.setError("Enter Name");
            edtUser.requestFocus();
            valid = false;
        }
        if (email.isEmpty())
        {
            edtEmail.setError("Enter Valid Email");
            edtEmail.requestFocus();
            valid = false;
        }

        if (pass.isEmpty() && pass.length()>=3 && pass.length()<=8) {
            edtPass.setError("Enter Proper Password");
            edtPass.requestFocus();
            valid = false;
        }
          else if (!pass.equals(conpass)) {
                Toast.makeText(getApplicationContext(),"Passwords not Matched",Toast.LENGTH_LONG).show();
        }

        if (contact.isEmpty() && contact.length()==10)
        {
            edtContact.setError("Enter Contact No.");
            edtContact.requestFocus();
            valid = false;

        }
        if (rgrpfirst.getCheckedRadioButtonId() == -1)
        {
            Toast.makeText(getApplicationContext(),"Please Select Identity",Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"You Selected :"+identity,Toast.LENGTH_LONG).show();
        }
        return valid;
    }

    static class MyTextWatcher implements TextWatcher {

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
