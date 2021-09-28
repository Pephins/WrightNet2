package example.com.right2wright;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {
    private EditText emails, passwords;
    private ProgressBar loading;
    private Button btn;
    private String URL_LOGIN = "https://lamp.ms.wits.ac.za/home/s2094705/Login.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emails = findViewById(R.id.email);
        passwords = findViewById(R.id.pass);
        loading = findViewById(R.id.progressBar);
        btn = findViewById(R.id.Login);

        configureLogin();
        configureSignUp();
    }

    private void configureSignUp() {
        Button nextButton = findViewById(R.id.signUp);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Registration.class));
            }
        });
    }

    private void configureLogin() {
        loading.setVisibility(View.GONE);
        btn.setVisibility(View.VISIBLE);
        Button nextButton = findViewById(R.id.Login);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emails.getText().toString().trim();
                String password = passwords.getText().toString();

                if (!email.isEmpty() || !password.isEmpty()) {
                    Login();
                } else {
                    emails.setError("Please enter your email");
                    passwords.setError("Please Enter Password");
                }
            }
        });

    }

    private void Login() {

        loading.setVisibility(View.VISIBLE);
        btn.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("response", "" + response);
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            //JSONArray jsonArray = jsonObject.getJSONArray("login");

                            if (success.equals("1")) {
                                loading.setVisibility(View.GONE);
                                Toast.makeText(Login.this, "Welcome", Toast.LENGTH_SHORT).show();
                                SharedPreferences.Editor myPref = getSharedPreferences( "Right2Wrightsharedpref", MODE_PRIVATE).edit();
                                myPref.putString("Email", emails.getText().toString());
                                myPref.apply();
                                //SharedPrefManager.userLogin(email);

                                startActivity(new Intent(Login.this, Home.class));
                                loading.setVisibility(View.GONE);

                            } else if (success.equals("0")) {
                                Toast.makeText(Login.this, "Wrong Email or Password", Toast.LENGTH_SHORT).show();
                                passwords.setError("check your password");
                                emails.setError("check your email");
                                passwords.setText("");
                                loading.setVisibility(View.GONE);
                                btn.setVisibility(View.VISIBLE);
                            }

                        } catch (JSONException e) {
                            loading.setVisibility(View.GONE);
                            btn.setVisibility(View.VISIBLE);
                            e.printStackTrace();
                            Toast.makeText(Login.this, "Error!" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.setVisibility(View.GONE);
                        btn.setVisibility(View.VISIBLE);
                        Toast.makeText(Login.this, "Error !!" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                String email = emails.getText().toString();
                String password = passwords.getText().toString().trim();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }
}