package example.com.right2wright;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {

    private String URL_LOGIN = "https://lamp.ms.wits.ac.za/home/s2094705/Retrieve.php";
    private Button art_btn;
    private String email;

    TextView textView_Email ,a , b, c;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

             a = view.findViewById(R.id.layLowName);
             b = view.findViewById(R.id.layLowName1);
             textView_Email= view.findViewById(R.id.layLowName2);
             c = view.findViewById(R.id.text);
             art_btn = getActivity().findViewById(R.id.artisan1);


            email = getActivity().getSharedPreferences("Right2Wrightsharedpref", Context.MODE_PRIVATE).getString("Email", "");
            /*if(!email.isEmpty()) {
                //textView_Email.setText(email);
                //Toast.makeText(getActivity().getApplicationContext(), email, Toast.LENGTH_SHORT).show();
            }*/

            Retrieve();



        return view;
    }
    private void Retrieve() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("response", "" + response);
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);

                            a.setText(String.format("%s %s", jsonObject.getString("First_Name"), jsonObject.getString("Last_Name")));
                             textView_Email.setText(email);
                             b.setText(jsonObject.getString("Phone_Number"));
                             c.setText(jsonObject.getString("Job"));

                            SharedPreferences.Editor myPref = getActivity().getSharedPreferences( "Right2Wrightsharedpref", Context.MODE_PRIVATE).edit();
                            myPref.putString("Name", String.format("%s %s", jsonObject.getString("First_Name"), jsonObject.getString("Last_Name")));
                            myPref.apply();

                            // String Rating = jsonArray.getString(4);
                            //Toast.makeText(getActivity().getApplicationContext(),response , Toast.LENGTH_SHORT).show();


                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                return params;
            }

        };
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }

}

