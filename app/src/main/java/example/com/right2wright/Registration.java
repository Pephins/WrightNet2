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


import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Registration extends AppCompatActivity {
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=!?])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{6,}" +               //at least 8 characters
                    "$");
    private EditText FName, LName, Emails, phone, pass1, pass2;
    Button btn_sign;
    private ProgressBar loading;

    private static final String REGISTER_URL = "https://lamp.ms.wits.ac.za/home/s2094705/Users.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        FName = findViewById(R.id.FName);
        LName = findViewById(R.id.LName);
        Emails = findViewById(R.id.email2);
        phone = findViewById(R.id.Phone1);
        pass1 = findViewById(R.id.Pss1);
        pass2 = findViewById(R.id.Pss2);
        loading = findViewById(R.id.prog1);
        btn_sign = findViewById(R.id.signUp2);
        loading.setVisibility(View.GONE);
        btn_sign.setOnClickListener(new View.OnClickListener() {
            int x = 0;
            @Override
            public void onClick(View v) {
                RegisterUser();
            }
        });

    }

    private boolean validateEmail() {
        String emailInput = Emails.getText().toString().trim();

        if (emailInput.isEmpty()) {
            Emails.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            Emails.setError("Please enter a valid email address");
            return false;
        } else {
            Emails.setError(null);
            return true;

        }

    }

    private boolean validateFname() {
        String FnameInput = FName.getText().toString().trim();

        if (FnameInput.isEmpty()) {
            FName.setError("Field can't be empty");
            return false;
        } else {
            FName.setError(null);
            return true;

        }
    }

    private boolean validateLName() {
        String LNameInput = LName.getText().toString().trim();

        if (LNameInput.isEmpty()) {
            LName.setError("Field can't be empty");
            return false;
        } else {
            LName.setError(null);
            return true;

        }
    }

    private boolean validatePhone() {
        String phoneInput = phone.getText().toString().trim();

        if (phoneInput.isEmpty()) {
            phone.setError("Field can't be empty");
            return false;
        } else {
            phone.setError(null);
            return true;

        }
    }

    private boolean validatePassword() {
        String passwordInput = pass1.getText().toString().trim();
        String passInput = pass2.getText().toString().trim();

        if (passwordInput.isEmpty()) {
            pass1.setError("Field can't be empty");
            if (passInput.isEmpty()) {
                pass2.setError("Field can't be empty");
            } else {
                pass2.setError(null);
            }
            return false;
        } else if (passInput.isEmpty()) {
            pass2.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            pass1.setError("Password must contain a special character(@#!..), letters(AaZbgc), and must have at least 6 characters ");
            return false;
        } else {
            pass2.setError(null);
            pass1.setError(null);
            return true;
        }
    }

    public void confirmInput() {
        /*if (!validateFname() && !validateLName() && !validateEmail() && !validatePhone() && !validatePassword()) {
            return;
        }
        //ConfirmPass();
         String input = "Email: " + Emails.getText().toString();
         input += "\n";
         input += "Password: " + pass1.getText().toString();
         Toast.makeText(this, input,Toast.LENGTH_SHORT).show();*/
        validateFname();
        validateLName();
        validateEmail();
        validatePhone();
        validatePassword();
    }

    private boolean ConfirmPass() {
        if (!pass1.getText().toString().isEmpty() && !pass2.getText().toString().isEmpty()) {
            final String password = this.pass1.getText().toString().trim();
            final String pass = this.pass2.getText().toString().trim();
            if (!password.equals(pass)) {
                Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show();
                pass2.setError("Password does not match");
                pass2.setText("");
                confirmInput();
                return false;
            }/* else if (pass2.getText().toString().length() <= 8) {
                Toast.makeText(this, "Password too short", Toast.LENGTH_SHORT).show();
                pass1.setError("Password too short");
                pass1.setText("");
                pass2.setText("");
                return false;
            }*/ else {
                return true;
            }
        } else {
            return false;
        }

    }

    private void RegisterUser() {
        confirmInput();
        if (validateFname() && validateLName() && validateEmail() && validatePhone() && validatePassword() && ConfirmPass()) {
            loading.setVisibility(View.VISIBLE);
            btn_sign.setVisibility(View.VISIBLE);
            loading.setVisibility(View.GONE);
            final String First_Name = this.FName.getText().toString().trim();
            final String Last_Name = this.LName.getText().toString().trim();
            final String Email = this.Emails.getText().toString().trim();
            final String Phone = this.phone.getText().toString().trim();
            final String password = this.pass1.getText().toString().trim();
            final String pass = this.pass2.getText().toString().trim();
            StringRequest requestStr = new StringRequest(Request.Method.POST, "https://lamp.ms.wits.ac.za/home/s2094705/Users.php",
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
                                //JSONObject jsonObject = new JSONArray(response).getJSONObject(0);
                                String success = jsonObject.optString("success");

                                if (success.equals("1")) {
                                    Toast.makeText(Registration.this, "Register Success!", Toast.LENGTH_SHORT).show();
                                    //SharedPreferences.Editor myPref = getSharedPreferences( "Right2Wrightsharedpref", MODE_PRIVATE).edit();
                                    //myPref.putString("Email", Emails.getText().toString());
                                    //myPref.putString("Name", FName.getText().toString() + " " + LName.getText().toString());
                                    //myPref.putString("Phone", phone.getText().toString());
                                    //myPref.apply();
                                    startActivity(new Intent(Registration.this, Terms_And_Conditions.class));
                                }
                                else if(success.equals("2")){
                                    Emails.setError("Email already exists");
                                    Toast.makeText(Registration.this, "Email Already Exists", Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(Registration.this, "Register Error!" + e.toString(), Toast.LENGTH_SHORT).show();
                                loading.setVisibility(View.GONE);
                                btn_sign.setVisibility(View.VISIBLE);

                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Registration.this, "Register Error!" + error.toString(), Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.VISIBLE);
                            btn_sign.setVisibility(View.VISIBLE);
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("First_Name", First_Name);
                    params.put("Last_Name", Last_Name);
                    params.put("Email", Email);
                    params.put("Phone", Phone);
                    params.put("password", password);
                    params.put("pass1", pass);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(requestStr);


        }
    }

}
