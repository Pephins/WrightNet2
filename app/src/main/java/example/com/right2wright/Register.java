package example.com.right2wright;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import com.google.android.material.navigation.NavigationView;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
private Spinner spinnerArt, spinnerGender;
private EditText UserName;
private String Job, Gender, email;
private ProgressBar loading;
private Button btn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        spinnerArt = findViewById(R.id.spinner1);
        spinnerGender = findViewById(R.id.spinner2);
        email = getSharedPreferences("Right2Wrightsharedpref", Context.MODE_PRIVATE).getString("Email", "");
        loading = findViewById(R.id.progressBar2);

        //NavigationView navigationView = findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener(this);

        loading.setVisibility(View.GONE);

        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Gender = spinnerGender.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerArt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Job = spinnerArt.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        UserName = findViewById(R.id.Username);


        loading = findViewById(R.id.progressBar2);
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.setVisibility(View.VISIBLE);
                RegisterUser();
            }
        });


    }

    private boolean ValidateInput() {
        if (UserName.getText().toString().equals("")) {
            UserName.setError("Field Can't be empty");
            Toast.makeText(Register.this, "Fill in all fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (Gender.equals("")) {
            Toast.makeText(Register.this, "Please Choose your Gender", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (Job.equals("")) {
            Toast.makeText(Register.this, "Please Choose your Expertise", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private void RegisterUser() {
        if (ValidateInput()) {
            loading.setVisibility(View.VISIBLE);
            btn.setVisibility(View.VISIBLE);
            loading.setVisibility(View.GONE);
            final String Username = this.UserName.getText().toString().trim();
            final String Gender = this.Gender;
            final String Job = this.Job;

            StringRequest requestStr = new StringRequest(Request.Method.POST, "https://lamp.ms.wits.ac.za/home/s2094705/artisan.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {

                                response = response.replaceFirst("<font>.*?</font>", "");
                                int jsonStart = response.indexOf("{");
                                int jsonEnd = response.lastIndexOf("}");
                                if (jsonStart >= 0 && jsonEnd >= 0 && jsonEnd > jsonStart) {
                                    response = response.substring(jsonStart, jsonEnd + 1);
                                }
                                Log.i("", response);
                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.optString("success");

                                if (success.equals("1")) {
                                    Toast.makeText(Register.this, "That went well :)", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Register.this, Home.class));
                                    finish();

                                } else if (success.equals("2")) {
                                    UserName.setError(" Please Choose a different Username");
                                    Toast.makeText(Register.this, "Username Already Exists", Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(Register.this, "Register Error!" + e.toString(), Toast.LENGTH_SHORT).show();
                                loading.setVisibility(View.GONE);
                                btn.setVisibility(View.VISIBLE);

                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Register.this, "Register Error!" + error.toString(), Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.VISIBLE);
                            btn.setVisibility(View.VISIBLE);
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("Username", Username);
                    params.put("Gender", Gender);
                    params.put("Job", Job);
                    params.put("email", email);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(requestStr);
        }
    }
}