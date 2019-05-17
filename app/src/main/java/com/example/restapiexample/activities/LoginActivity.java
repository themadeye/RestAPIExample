package com.example.restapiexample.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.restapiexample.R;
import com.example.restapiexample.model.ResObj;
import com.example.restapiexample.services.RSA;
import com.example.restapiexample.services.UserService;

import org.json.JSONObject;

import java.math.BigInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button btnLogin;
    byte[] encodePass = null;
    private String encryptedPass;
    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
    }

    private void findViews(){
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        btnLogin = (Button)findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] userPass = password.getText().toString().getBytes();
                try{
//                    encodePass = RSA.encryptByPublicKey(userPass, getString(R.string.pub_key));
                    encryptedPass = RSA.encryptByPublic(password.getText().toString(), getString(R.string.pub_key));
//                    encryptedPass = new BigInteger(1, encodePass).toString(16);
                    //Use Retrofit to call REST API
                    validateLogin(username.getText().toString(), encryptedPass);


                    //Use Volley to call REST API and return JSON
//                    String URL = "http://203.116.15.18/MobileSvc/api/Test/Login/"+ username.getText().toString() +"/" + encryptedPass;
//
//                    RequestQueue rq = Volley.newRequestQueue(LoginActivity.this);
//
//                    JsonObjectRequest ojr = new JsonObjectRequest(Request.Method.GET, URL, null,
//                            new com.android.volley.Response.Listener<JSONObject>() {
//                                @Override
//                                public void onResponse(JSONObject response) {
//                                    Log.e("Rest Response", response.toString());
//                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                                    startActivity(intent);
//                                }
//                            }, new com.android.volley.Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            Log.e("Rest Response", error.toString());
//                            Toast.makeText(LoginActivity.this, getString(R.string.login_error), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                    rq.add(ojr);

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }

    private void validateLogin(final String username, final String password){
        Call call = userService.login(username,password);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.isSuccessful()){
                    ResObj rob = (ResObj) response.body();
                    if(rob.getMessage().equals("true")){
                        //login start main activity
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(LoginActivity.this, getString(R.string.login_failed), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(LoginActivity.this, getString(R.string.login_error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
