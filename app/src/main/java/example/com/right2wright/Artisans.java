package example.com.right2wright;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Artisans extends AppCompatActivity {

    ListView listView;
    Model1Adapter adapter;
    String[] title;
    String[] description ;

    ArrayList<Model1> model1List;

    private static final String url = "https://lamp.ms.wits.ac.za/home/s2094705/Retrival.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artisans);

        String job = getSharedPreferences("Right2Wrightsharedpref", Context.MODE_PRIVATE).getString("job", "");
        loadArtisans(job);

        //Toast.makeText(Artisans.this, title.get(1) + " " + description.get(1), Toast.LENGTH_SHORT).show();
    }
    public void loadArtisans(final String job){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{

                    Log.e("response", "" + response);
                    JSONArray jsonObject = new JSONArray(response);

                    model1List = new ArrayList<>();

                    title = new String[jsonObject.length()];
                    description = new String[jsonObject.length()];
                    for(int i = 0; i<jsonObject.length(); i++) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                        title[i] = jsonObject1.getString("Username");
                        description[i] = jsonObject1.getString("Rating");
                    }
                    listView = findViewById(R.id.listView1);
                    for (int i = 0; i < title.length; i++) {
                        Model1 model = new Model1(title[i], description[i]);
                        //bind all string in array
                        model1List.add(model);
                    }
                    //Toast.makeText(Artisans.this, String.valueOf(title.length), Toast.LENGTH_SHORT).show();
                    //pass results in listViewAdapter class
                    adapter = new Model1Adapter(Artisans.this,model1List);

                    //bind the adapter to the listview
                    listView.setAdapter(adapter);
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
                params.put("job", job);
                return params;
            }

        };


        Volley.newRequestQueue(this).add(stringRequest);



    }



}