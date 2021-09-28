package example.com.right2wright;

import androidx.annotation.NonNull;
import java.text.SimpleDateFormat;
import java.util.Date;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Profile2 extends AppCompatActivity {
    private String URL_LOGIN = "https://lamp.ms.wits.ac.za/home/s2094705/Retrieve1.php";
    private String Username, reviews;
    private Button buttonDone;
    TextView a,b,c, d, textView;
    private EditText editText;
    private String mFinal;
    private ProgressBar loading;
    private String rating;
    //private DatabaseReference mUserDatabase;
    //private ProgressDialog  mProgressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2);
        a = findViewById(R.id.NameArtDisp);
        b = findViewById(R.id.ContactArtEmailDisp);
        c = findViewById(R.id.ContactArtPhoneDisp);
        d = findViewById(R.id.scrollTextView);
        buttonDone = findViewById(R.id.btnRating);
        loading = findViewById(R.id.progressBar3);
        /*mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Loading User Data");
        mProgressDialog.setMessage("Please wait while we load user Data. ");
        mProgressDialog.setCanceledOnTouchOutside(false);*/
        loading.setVisibility(View.GONE);

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        Username = getSharedPreferences("Right2Wrightsharedpref", Context.MODE_PRIVATE).getString("username", "");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://lamp.ms.wits.ac.za/home/s2094705/Retrieve1.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("response", "" + response);
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            a.setText(String.format("%s %s", jsonObject.getString("First_Name"), jsonObject.getString("Last_Name")));
                            b.setText(jsonObject.getString("Email"));
                            c.setText(jsonObject.getString("Phone_Number"));
                            rating = jsonObject.getString("Rating");
                            d.setText(jsonObject.getString("Reviews"));
                            SharedPreferences.Editor myPref = getSharedPreferences( "Right2Wrightsharedpref", MODE_PRIVATE).edit();
                            myPref.putString("Rating",rating);
                            myPref.apply();

                        } catch (JSONException e) {
                            Toast.makeText(Profile2.this, "Error " + e.toString(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Username",Username );
                return params;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


       //////RATINGBAR STARTS HERE///////////////////////////////////////////////////////////////////////////////////////

        buttonDone = findViewById(R.id.btnRating);

        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    AlertDialog.Builder builder = new AlertDialog.Builder(Profile2.this);
                    View layout = null;
                    LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    layout = inflater.inflate(R.layout.ratingbar, null);
                    final EditText editText =layout.findViewById(R.id.edt_comment);
                    final RatingBar ratingBar = layout.findViewById(R.id.ratingBar);

                    final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    final Date date = new Date();

                    builder.setTitle("Rate and Review Your Artisan");
                    builder.setMessage("");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                            loading.setVisibility(View.VISIBLE);

                            Float value = ratingBar.getRating();

                            String review = getSharedPreferences("Right2Wrightsharedpref", Context.MODE_PRIVATE).getString("Name1", "");
                            mFinal = review + " : " + formatter.format(date) + "\n" + editText.getText().toString();

                            double mRating = Double.parseDouble(rating);

                            mRating = (mRating + value)/2;

                            rating = String.valueOf(mRating);

                            mFinal = mFinal + "\n\n" + d.getText().toString();

                            reviews = mFinal;

                            /***********************************************************************************************/

                            StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://lamp.ms.wits.ac.za/home/s2094705/Update.php",
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
                                                    Toast.makeText(Profile2.this, "Thank You!", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(Profile2.this, Home.class));
                                                }

                                            } catch (JSONException e) {
                                                Toast.makeText(Profile2.this, "Error " + e.toString(), Toast.LENGTH_SHORT).show();
                                                e.printStackTrace();

                                            }
                                        }
                                    },

                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {

                                        }
                                    }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<>();;
                                    params.put("Username",Username );
                                    params.put("rating", rating);
                                    params.put("reviews", reviews);

                                    return params;
                                }
                            };
                            Volley.newRequestQueue(Profile2.this).add(stringRequest);


                            /***********************************************************************************************/

                        }
                    });
                    builder.setNegativeButton("No, Thanks", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.setCancelable(false);
                    builder.setView(layout);
                    builder.show();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }



        });

    }



}
