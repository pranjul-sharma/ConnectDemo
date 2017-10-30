package com.test.connect.connectdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;


public class CreateContactFragment extends Fragment {

    TextInputLayout textLayoutName,textLayoutPhone,textLayoutEmail;
    EditText etName,etPhone,etEmail;
    Button btnSave;
    public CreateContactFragment(){
        //empty constructor required
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_contact,container,false);
        textLayoutName = view.findViewById(R.id.text_layout_name);
        textLayoutPhone = view.findViewById(R.id.text_layout_phone);
        textLayoutEmail = view.findViewById(R.id.text_layout_email);

        etName = view.findViewById(R.id.edit_text_name);
        etPhone = view.findViewById(R.id.edit_text_phone);
        etEmail = view.findViewById(R.id.edit_text_email);

        btnSave = view.findViewById(R.id.btn_save);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validateValues()){
                    Long val = saveToDb();
                    if (val == -1)
                        Snackbar.make(view,"This email is already saved. Please use another email. ",Snackbar.LENGTH_LONG).show();
                    else
                        Snackbar.make(view,"contact saved successfully. ",Snackbar.LENGTH_LONG).show();
                }else {
                    Snackbar.make(view,"Please fill all entries",Snackbar.LENGTH_LONG).show();
                }
            }

        });
        return view;
    }


    private boolean validateValues() {
        boolean val=false;
        if(!etName.getText().toString().equals("") && validPhone() && validateEmail())
            val = true;
        return val;
    }

    private boolean validPhone(){
        String phoneNo=etPhone.getText().toString().trim();
        if(phoneNo.isEmpty()|| !isValidPhone(phoneNo)){
            textLayoutPhone.setError("invalid phone number");
            requestFocus(etPhone);
            return false;
        }else if(phoneNo.toCharArray().length!=10){
            textLayoutPhone.setError("number must contain 10 digits");
            requestFocus(etPhone);
            return false;
        }else{
            textLayoutPhone.setErrorEnabled(false);
        }

        return true;
    }
    private boolean isValidPhone(String phone) {
        return !TextUtils.isEmpty(phone) && Patterns.PHONE.matcher(phone).matches();
    }

    private boolean validateEmail() {
        String emailStr=etEmail.getText().toString().trim();
        if(emailStr.isEmpty() || !isValidEmail(emailStr)){
            textLayoutEmail.setError("invalid email");
            requestFocus(etEmail);
            return false;
        }else {
            textLayoutEmail.setErrorEnabled(false);
        }
        return true;
    }

    private boolean isValidEmail(String email){
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view){
        if(view.requestFocus()){
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private long saveToDb(){
        ContactDBHelper contactDbHelper = new ContactDBHelper(getContext());
        Contact contact = new Contact(etName.getText().toString(),
             etEmail.getText().toString(),Long.parseLong(etPhone.getText().toString()));
        return contactDbHelper.insertRecord(contact);

    }
}
