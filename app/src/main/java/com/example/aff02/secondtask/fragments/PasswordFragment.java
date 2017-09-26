package com.example.aff02.secondtask.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aff02.secondtask.R;
import com.example.aff02.secondtask.database.DatabaseHelper;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PasswordFragment extends Fragment {

    public EditText edtoldpass, edtnewpass, edtconpass;
    public TextInputLayout tiploldpass,tiplnewpass,tiplconpass;
    public Button btnsave;
    String oldpass, newpass, conpass;
    DatabaseHelper databaseHelper;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String EMAIL = "email";
    private static final String PASS = "pass";
    // TODO: Rename and change types of parameters
    private String email;
    private String pass;
    private Context context;


    public PasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @param email
     * @return A new instance of fragment PasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PasswordFragment newInstance(String pass, String email) {
        PasswordFragment fragment = new PasswordFragment();
        Bundle args = new Bundle();
        args.putString(PASS,pass);
        args.putString(EMAIL,email);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pass = getArguments().getString(PASS);
            email = getArguments().getString(EMAIL);

             databaseHelper = new DatabaseHelper(getContext());

            SharedPreferences preferences = getContext().getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            email = preferences.getString("email",EMAIL);
            Log.e("emem",preferences.getString("email",EMAIL));
            pass = preferences.getString("pass",PASS);
            Log.e("pfpf",preferences.getString("pass",PASS));
            editor.commit();
            editor.apply();


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_password, container, false);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Change Password");

//        tiploldpass = (TextInputLayout)view.findViewById(R.id.tiploldpass);
        edtoldpass = (EditText) view.findViewById(R.id.edtoldpass);
//        edtoldpass.addTextChangedListener(new MyTextWatcher(edtoldpass));

        edtnewpass = (EditText) view.findViewById(R.id.edtnewpass);
        edtconpass = (EditText) view.findViewById(R.id.edtconnewpass);

        btnsave = (Button) view.findViewById(R.id.btnSave);

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!validate())
                {
                    Toast.makeText(getContext(),"Updation Failed :-(",Toast.LENGTH_LONG).show();
                }
                else
                {
                    databaseHelper = new DatabaseHelper(getContext());
                    databaseHelper.updatePass(email,newpass);
                }
            }
        });
    }

    public boolean validate()
    {
        oldpass = edtoldpass.getText().toString().trim();
        newpass = edtnewpass.getText().toString().trim();
        conpass = edtconpass.getText().toString().trim();

        boolean valid = true;

        if (oldpass.isEmpty()) {
            edtoldpass.setError("Old Password is Mandatory");
            edtoldpass.requestFocus();
            valid = false;
        }

        if (newpass.isEmpty()) {
            edtnewpass.setError("New Password is Mandatory");
            edtnewpass.requestFocus();
            valid = false;
        }
        if (conpass.isEmpty()) {
            edtconpass.setError("Confirm your Password");
            edtconpass.requestFocus();
        }
        else if (!newpass.equals(conpass))
        {
            Toast.makeText(getContext(),"Password Not Matched",Toast.LENGTH_LONG).show();
        }
        else {
            Log.e("EE :",email);
            Log.e("PP :",pass);
            databaseHelper.updatePass(email,newpass);
            Toast.makeText(getContext(),"Password Changed Successfully",Toast.LENGTH_LONG).show();
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


