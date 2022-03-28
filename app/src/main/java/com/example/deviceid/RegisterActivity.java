package com.example.deviceid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    TextInputEditText name_r,mobile_r,password_r;
    String strName,strMobile,strPassword,ID,strResponse;
    Button btRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name_r = findViewById(R.id.etname);
        mobile_r = findViewById(R.id.etMobile);
        password_r = findViewById(R.id.etPassword);
        btRegister = findViewById(R.id.register_btn);

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strName = name_r.getText().toString();
                strMobile = mobile_r.getText().toString();
                strPassword = password_r.getText().toString();

                if (strName.equals("")) {
                    name_r.setError("Enter Your Name!");
                } else if (strMobile.equals("")) {
                    mobile_r.setError("Enter your Phone Number!");
                } else if (strMobile.length() != 10) {
                    mobile_r.setError("Enter Valid Number");
                } else if (strPassword.equals("")) {
                    password_r.setError("Enter Your Password!");
                } else {
                    postData();

                    ID = Settings.Secure.getString(getContentResolver(),
                            Settings.Secure.ANDROID_ID);
                    Toast.makeText(RegisterActivity.this, "Device is:" + ID, Toast.LENGTH_SHORT).show();
                }
            }


        });

    }


    public void postData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONObject object = new JSONObject();
        try {
            //input your API parameters
            strMobile = mobile_r.getText().toString();
            strPassword = password_r.getText().toString();
            Toast.makeText(this, "Number is:"+strMobile, Toast.LENGTH_SHORT).show();
            //  object.put("parameter","value");
            object.put("mobileno",strMobile);
            object.put("password",strPassword);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        //  stsdl  =  object.tos


        HttpsTrustManager.allowAllSSL();
        // Enter the correct url for your api service site
        String url = "https://202.143.96.44:1831/api/Mob/URegister";//getResources().getString(R.string.url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG", "Details:" + response);
                      // responseTV.setText("String Response : " + response.toString());
                        Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(i);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                responseTV.setText(error.getMessage());
              //  Toast.makeText(RegisterActivity.this, "Error"+error, Toast.LENGTH_LONG).show();
                if (error instanceof NetworkError) {
                } else if (error instanceof ServerError) {
                } else if (error instanceof AuthFailureError) {
                } else if (error instanceof ParseError) {
                } else if (error instanceof NoConnectionError) {
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(getApplicationContext(),
                            "Oops. Timeout error!",
                            Toast.LENGTH_LONG).show();
                }
            }
            //This is for Headers If You Needed

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();

                headers.put("DeviceId", ID);
                return headers;
            }
        };


        requestQueue.add(jsonObjectRequest);

    }


}