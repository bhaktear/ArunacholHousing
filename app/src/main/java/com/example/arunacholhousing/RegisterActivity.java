package com.example.arunacholhousing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arunacholhousing.libUtils.OkHttpUtils;
import com.example.arunacholhousing.libUtils.SpinnerItem;
import com.example.arunacholhousing.libUtils.Utils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    EditText name,mobile,email,address,userID,password,confirmPass;
    Spinner dist,thana;
    Button regBtn;
    TextView login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.name);
        mobile = findViewById(R.id.mobile);
        email = findViewById(R.id.email);
        address = findViewById(R.id.address);
        userID = findViewById(R.id.userID);
        password = findViewById(R.id.password);
        confirmPass = findViewById(R.id.confirmPass);
        dist = findViewById(R.id.district);
        thana = findViewById(R.id.thana);

        Utils.getDistrict(new Utils.ResponseCallback() {
            @Override
            public void onResponse(List<Map<String, String>> data) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.populateSpinner(dist,data);
                    }
                });
            }
        });
        dist.setOnItemSelectedListener(this);
        regBtn = findViewById(R.id.regbtn);
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addReg();
            }
        });


    }

    private void addReg() {
        String user_name = name.getText().toString();
        String mobileVal = mobile.getText().toString();
        String emailAddress = email.getText().toString();
        String addr = address.getText().toString();
        String user_id= userID.getText().toString();
        String pass = password.getText().toString();
        String confirm_pass = confirmPass.getText().toString();
        String district = ((SpinnerItem) dist.getSelectedItem()).getValue();
        String thanaVal = ((SpinnerItem) thana.getSelectedItem()).getValue();

        Map<String,String> obj = new HashMap<>();
        try{
            obj.put("user_name",user_name);
            obj.put("mobile",mobileVal);
            obj.put("address",addr);
            obj.put("email", emailAddress);
            obj.put("district",district);
            obj.put("thana",thanaVal);
            obj.put("user_id",user_id);
            obj.put("password",pass);
            obj.put("confirm_pass",confirm_pass);
            //Log.d("TAG","registerData:" + obj.toString());
            //Toast.makeText(RegisterActivity.this,obj.toString(),Toast.LENGTH_SHORT).show();
            OkHttpUtils okHttpUtils = new OkHttpUtils();
            String url = Utils.makeURL("api/reg.php");
            okHttpUtils.postRequest(url, obj, new OkHttpUtils.ResponseCallback() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String msg = response.getString("msg");
                        int err = response.getInt("err");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(err == 0){
                                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                    startActivity(intent);
                                }else{
                                    Utils.alert(RegisterActivity.this,msg,"Ok","");
                                }

                            }
                        });
                    }catch (Exception e){
                        Log.d("TAG","exp" + e.getMessage());
                    }

                }

                @Override
                public void onError(String error) {
                    Log.d("TAG","errorRegSubmit-" + error);
                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public void loginActivity(View view){
        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
        SpinnerItem selected = (SpinnerItem) adapterView.getItemAtPosition(pos);
        String name = selected.getName();
        String value = selected.getValue();
        String finalSelected = "Name-" + name + " value-" + value;
        if (adapterView == dist){
            Toast.makeText(this,finalSelected,Toast.LENGTH_SHORT).show();
            if(value != ""){
                Utils.getThana(value, new Utils.ResponseCallback() {
                    @Override
                    public void onResponse(List<Map<String, String>> data) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Utils.populateSpinner(thana,data);
                            }
                        });
                    }
                });

            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}