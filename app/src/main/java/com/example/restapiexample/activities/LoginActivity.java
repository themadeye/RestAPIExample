package com.example.restapiexample.activities;

import android.content.Intent;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.restapiexample.R;
import com.example.restapiexample.model.ResObj;
import com.example.restapiexample.model.Users;
import com.example.restapiexample.services.RSA;
import com.example.restapiexample.services.RestAuthenticationClient;
import com.example.restapiexample.services.UserService;
import com.example.restapiexample.sqlite.UserDBHelper;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONObject;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    LinearLayout linearLayout;
    private EditText username;
    private EditText password;
    private Button btnLogin;
    private Button btnExit;
    byte[] encodePass = null;
    private String encryptedPass;
    UserService userService;
    UserDBHelper dbHelp = new UserDBHelper(LoginActivity.this);
    //URL stuff
    private String baseUrl = "http://203.116.15.18/MobileSvc/api/Test/Login";
//    String URL = "https://jsonplaceholder.typicode.com/todos/1";
    String URL = "https://postman-echo.com/basic-auth";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        findViews();
        checkLogin();

        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
    }

    private void checkLogin(){
        if(dbHelp.checkCredential().equals("true")){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }else{
            findViews();
        }
    }

    private void findViews(){
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnExit = (Button)findViewById(R.id.btnExit);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Here to encrypt the password, call URL to execute rest authentication
                byte[] userPass = password.getText().toString().getBytes();
                try{
//                    encodePass = RSA.encryptByPublicKey(userPass, getString(R.string.pub_key));
                    encryptedPass = RSA.encryptByPublic(password.getText().toString(), getString(R.string.pub_key));
//                    encryptedPass = new BigInteger(1, encodePass).toString(16);

                    //Use the authentication to call REST api url, not passing encrypted password, because the Postman URL did not implement decryption
                    try{
                        RestAuthenticationClient rac =
                                new RestAuthenticationClient(URL, username.getText().toString(),
                                        password.getText().toString());

                        AsyncTask<Void, Void, String> execute = new ExecuteNetworkOperation(rac);
                        execute.execute();
                    }catch (Exception e){
                        e.printStackTrace();
                    }


                    //Use Retrofit to call REST API url
//                    validateLogin(username.getText().toString(), password.getText().toString());



                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
    }

    private void updateLogin(){
        dbHelp.updateCredential("true");
    }

    private void getProfileList(){
        //Here to use REQ sample URL to get users list as JSON array, should worked as well as Etiqa's url.
        String URL = "https://reqres.in/api/users?page=2";

        RequestQueue rq = Volley.newRequestQueue(LoginActivity.this);

        JsonObjectRequest ojr = new JsonObjectRequest(Request.Method.GET, URL, null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Rest Response", response.toString());// the response is JSON format
                        try{
                            JSONArray jary = response.getJSONArray("data");
                            for(int i = 0; i < jary.length(); i++){
                                JSONObject data = jary.getJSONObject(i);
                                Users users = new Users();
                                users.setID(Integer.valueOf(data.getString("id")));
                                users.setEmail(data.getString("email"));
                                users.setFirstName(data.getString("first_name"));
                                users.setLastName(data.getString("last_name"));
                                dbHelp.insertUsers(users);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Rest Response", error.toString());
                Toast.makeText(LoginActivity.this, getString(R.string.login_error), Toast.LENGTH_SHORT).show();
            }
        });
        rq.add(ojr);
    }

    public class ExecuteNetworkOperation extends AsyncTask<Void, Void, String> {

        private RestAuthenticationClient rac;
        private String isValidCredentials;

        public ExecuteNetworkOperation(RestAuthenticationClient rac) {
            this.rac = rac;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                isValidCredentials = rac.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // Login Success
            if (isValidCredentials.contains("true")) {//should be using equals instead of contains, but leave it for now, it is just custom testing url.

                //This is custom toast view
//                LayoutInflater inflater = getLayoutInflater();
//                View layout = inflater.inflate(R.layout.custom_toast_view,
//                        (ViewGroup) findViewById(R.id.custom_toast_container));
//
//                TextView text = (TextView) layout.findViewById(R.id.text);
//                text.setText(R.string.login_success);
//
//                Toast toast = new Toast(getApplicationContext());
//                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
//                toast.setDuration(Toast.LENGTH_LONG);
//                toast.setView(layout);
//                toast.show();

                Snackbar snackbar = Snackbar
                        .make(linearLayout, R.string.login_success, Snackbar.LENGTH_LONG)
                        .setAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Snackbar snackbar1 = Snackbar.make(linearLayout, "Message is restored!", Snackbar.LENGTH_SHORT);
                                snackbar1.show();
                            }
                        });

                snackbar.show();

//                Toast.makeText(getApplicationContext(), getString(R.string.login_success), Toast.LENGTH_LONG).show();
                updateLogin();
                getProfileList();
            }
            // Login Failure
            else {
                Toast.makeText(getApplicationContext(), getString(R.string.login_error), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void validateLogin(final String username, final String password){
        Call call = userService.login(username,password);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.isSuccessful()){
                    ResObj rob = (ResObj) response.body();
                    if(rob.getMessage().equals("true")){//Here is getting the return token, if true, then proceed to Main / download Profile List
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
