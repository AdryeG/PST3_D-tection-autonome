package com.example.pst3_appmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.pst3_appmobile.main_menu.NumSerieCapteur;


public class Capteur extends AppCompatActivity {

    private Button button;
    private ImageButton delete;

    private ArrayList<ExempleItem> mExempleList;

    private RecyclerView mRecyclerView;
    private ExempleAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capteur);

        Intent intent = getIntent();
        final String NumSerie = intent.getStringExtra(NumSerieCapteur);

        /*TextView textViewNum = findViewById(R.id.text_view_numserie);
        textViewNum.setText(NumSerie);*/

        button = findViewById(R.id.button);
        delete = findViewById(R.id.delete);

        mRecyclerView = findViewById(R.id.recycle_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mExempleList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON(NumSerie);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(Capteur.this);
                progressDialog.setCancelable(false);
                progressDialog.setIndeterminate(false);
                progressDialog.setTitle("Deleting sensor from database");
                progressDialog.show();
                String url ="https://nimble-lead-277612.ew.r.appspot.com/?id=6&numSerie="+ NumSerie;
                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        if (response.equals("Successfully deleted"))
                        {
                            progressDialog.dismiss();
                            startActivity(new Intent(Capteur.this, main_menu.class));
                        }
                        else
                        {
                            progressDialog.dismiss();
                            Toast.makeText(Capteur.this, response, Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        progressDialog.dismiss();
                        Toast.makeText(Capteur.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> param = new HashMap<>();
                        param.put("captername", NumSerieCapteur);
                        return param;
                    }
                };
                request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                MySingleton.getmInstance(Capteur.this).addToRequestQueue(request);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Capteur.this, main_menu.class));
            }
        });
    }
    private void parseJSON(String numSerie) {
        String url = "https://nimble-lead-277612.ew.r.appspot.com/?id=5&numSerie="+ numSerie;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("Liste");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);
                                String Text1 = hit.getString("time");
                                mExempleList.add(0, new ExempleItem(R.drawable.ic_door, Text1, ""));

                            }
                            mAdapter = new ExempleAdapter(Capteur.this, mExempleList);
                            mRecyclerView.setAdapter(mAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mRequestQueue.add(request);
    }
}