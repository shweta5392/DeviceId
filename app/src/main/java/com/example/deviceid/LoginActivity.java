package com.example.deviceid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText login_number,login_password;
    String strLogin,strPass;
    Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_number = findViewById(R.id.lMobile);
        login_password = findViewById(R.id.lPassword);
        btnLogin = findViewById(R.id.login_btn);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             strLogin = login_number.getText().toString();
             strPass = login_password.getText().toString();

             if (strLogin.equals("")){
                 login_number.setError("Enter the Number!");
             }else if (strPass.equals("")){
                 login_password.setError("Enter Your Password!");
             }else {
                 /*Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                 startActivity(intent);*/
                 // calling a method to post the data and passing our name and job.
                 postData();
             }
            }
        });


    }

    public void postData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONObject object = new JSONObject();
        try {
            //input your API parameters
            strLogin =  login_number.getText().toString();
            strPass = login_password.getText().toString();
            Toast.makeText(this, "Number is:"+strLogin, Toast.LENGTH_SHORT).show();
            //  object.put("parameter","value");
            object.put("mobilno",strLogin);
            object.put("passwd",strPass);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //  stsdl  =  object.tos
        HttpsTrustManager.allowAllSSL();
        // Enter the correct url for your api service site
        String url = "https://202.143.96.44:1831/api/Mob/Login";//getResources().getString(R.string.url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG", "Login:" + response);
//                        responseTV.setText("String Response : " + response.toString());
                        

                      /*  try {

                           *//* strLogin = response.getString("mobileno");
                            strPass = response.getString("passwd");*//*

                            // response_url.setText(strResponse);

                        } catch (JSONException e) {
                            Log.d("TAG", "profile: " + e);
                        }*/
                        Intent i = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(i);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                responseTV.setText(error.getMessage());
            }
        });
        requestQueue.add(jsonObjectRequest);

    }

}