package com.example.pst3_appmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;

public class main_menu extends AppCompatActivity {

    private ArrayList<ExempleItem> mExempleList;

    private RecyclerView mRecyclerView;
    private ExempleAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private RequestQueue mRequestQueue;

    private ImageButton buttonInsert, buttonCancel;

    private ScrollView addCapteurList;
    private RelativeLayout addCapteurInfo;

    private CardView door, temp, humid, peer, lumiere;

    private EditText nomCapteur, numCapteur;

    private Button btnAjouter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        mRecyclerView = findViewById(R.id.recycle_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mExempleList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON();

        //loadList(); //charger la liste de capteurs qui est stocké dans la bdd

        //buildRecyclerView();
        
        buttonInsert = findViewById(R.id.button_insert);
        buttonCancel = findViewById(R.id.button_cancel);

        addCapteurList = findViewById(R.id.add_capteur_list);
        addCapteurInfo = findViewById(R.id.add_capteur_info);

        door = findViewById(R.id.nav_door);
        temp = findViewById(R.id.nav_tmp);
        humid = findViewById(R.id.nav_humid);
        peer = findViewById(R.id.nav_motion);
        lumiere = findViewById(R.id.nav_lumiere);

        nomCapteur = findViewById(R.id.txt_nom);
        numCapteur = findViewById(R.id.txt_num);

        btnAjouter = findViewById(R.id.btnAdd);

        final int[] i = {0};

        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCapteurList.setVisibility(View.VISIBLE);
                buttonCancel.setVisibility(View.VISIBLE);
                buttonInsert.setVisibility(View.GONE);

            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCapteurList.setVisibility(View.GONE);
                buttonCancel.setVisibility(View.GONE);
                buttonInsert.setVisibility(View.VISIBLE);
                addCapteurInfo.setVisibility(View.GONE);

            }
        });

        door.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCapteurList.setVisibility(View.GONE);
                addCapteurInfo.setVisibility(View.VISIBLE);

                i[0] = 0;
            }
        });
        temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCapteurList.setVisibility(View.GONE);
                addCapteurInfo.setVisibility(View.VISIBLE);

                i[0] = 1;
            }
        });
        humid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCapteurList.setVisibility(View.GONE);
                addCapteurInfo.setVisibility(View.VISIBLE);

                i[0] = 2;
            }
        });
        peer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCapteurList.setVisibility(View.GONE);
                addCapteurInfo.setVisibility(View.VISIBLE);

                i[0] = 3;
            }
        });
        lumiere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCapteurList.setVisibility(View.GONE);
                addCapteurInfo.setVisibility(View.VISIBLE);

                i[0] = 4;
            }
        });

        btnAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ValnomCapteur = nomCapteur.getText().toString();
                String ValnumCapteur = numCapteur.getText().toString();

                if (ValnomCapteur.matches ("") || ValnumCapteur.matches(""))
                {
                    Context context = getApplicationContext();
                    CharSequence text = "Tous les champs ne sont pas remplies";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else
                {
                    if (i[0] == 0)
                    {
                        final String type = "Porte";
                        addCapteurBDD(ValnomCapteur, ValnumCapteur, type);
                    }

                    if (i[0] == 1)
                    {
                        final String type = "Temperature";
                        addCapteurBDD(ValnomCapteur, ValnumCapteur, type);
                    }

                    if (i[0] == 2)
                    {
                        final String type = "Humidite";
                        addCapteurBDD(ValnomCapteur, ValnumCapteur, type);
                    }

                    if (i[0] == 3)
                    {
                        final String type = "Presence";
                        addCapteurBDD(ValnomCapteur, ValnumCapteur, type);
                    }

                    if (i[0] == 4)
                    {
                        final String type = "Lumiere";
                        addCapteurBDD(ValnomCapteur, ValnumCapteur, type);
                    }

                    insertItem(ValnomCapteur, ValnumCapteur, i[0]);
                    closeKeyboard();
                    addCapteurList.setVisibility(View.GONE);
                    buttonCancel.setVisibility(View.GONE);
                    buttonInsert.setVisibility(View.VISIBLE);
                    addCapteurInfo.setVisibility(View.GONE);
                }
            }
        });
    }

    private void addCapteurBDD(final String captername, final String numserie, final String i)
    {
        final ProgressDialog progressDialog = new ProgressDialog(main_menu.this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("Add sensor in database");
        progressDialog.show();
        String uRl ="http://10.0.2.2//AppMobile/addCapteur.php";
        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                if (response.equals("Successfully added"))
                {
                    progressDialog.dismiss();
                    //finish();
                }
                else
                {
                    progressDialog.dismiss();
                    Toast.makeText(main_menu.this, response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                progressDialog.dismiss();
                Toast.makeText(main_menu.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("captername", captername);
                param.put("numserie", numserie);
                param.put("type", i);
                return param;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(main_menu.this).addToRequestQueue(request);
    }

    private void parseJSON()
    {
        String url = "https://nimble-lead-277612.ew.r.appspot.com/?id=3";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject sensor = jsonArray.getJSONObject(i);

                                String type = sensor.getString("typeCapteur");
                                String numserie = sensor.getString("numSerie");
                                String captername = sensor.getString("nomCapteur");

                                mExempleList.add(0, new ExempleItem(R.drawable.ic_door, captername, numserie));
                            }
                            mAdapter = new ExempleAdapter(main_menu.this, mExempleList);
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


    private void loadList()
    {


        String url ="https://nimble-lead-277612.ew.r.appspot.com/?id=3";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray products = new JSONArray(response);

                    for (int i = 0; i < products.length(); i++)
                    {
                        JSONObject productObject = products.getJSONObject(i);

                        String type = productObject.getString("typeCapteur");
                        String numserie = productObject.getString("numSerie");
                        String captername = productObject.getString("nomCapteur");

                        if (type == "Porte" )
                        {
                            mExempleList.add(0, new ExempleItem(R.drawable.ic_door, captername, numserie));
                        }

                        if (type == "Temperature")
                        {
                            mExempleList.add(0, new ExempleItem(R.drawable.ic_tmp, captername, numserie));
                        }

                        if (type == "Humidite")
                        {
                            mExempleList.add(0, new ExempleItem(R.drawable.ic_humidity, captername, numserie));
                        }

                        if (type == "Presence")
                        {
                            mExempleList.add(0, new ExempleItem(R.drawable.ic_motion_sensor, captername, numserie));
                        }

                        if (type == "Lumiere")
                        {
                            mExempleList.add(0, new ExempleItem(R.drawable.ic_lumiere, captername, numserie));
                        }

                        //ExempleAdapter adapter = new ExempleAdapter(mExempleList);
                        //mRecyclerView.setAdapter(adapter);

                        //mAdapter.notifyItemInserted(0);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(main_menu.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(this).add(stringRequest);
    }

    public void insertItem(String ValnomCapteur, String ValnumCapteur, int i) {
        if (i == 0 )
        {
            mExempleList.add(0, new ExempleItem(R.drawable.ic_door, ValnomCapteur, ValnumCapteur));
        }

        if (i == 1)
        {
            mExempleList.add(0, new ExempleItem(R.drawable.ic_tmp, ValnomCapteur, ValnumCapteur));
        }

        if (i == 2)
        {
            mExempleList.add(0, new ExempleItem(R.drawable.ic_humidity, ValnomCapteur, ValnumCapteur));
        }

        if (i == 3)
        {
            mExempleList.add(0, new ExempleItem(R.drawable.ic_motion_sensor, ValnomCapteur, ValnumCapteur));
        }

        if (i == 4){
            mExempleList.add(0, new ExempleItem(R.drawable.ic_lumiere, ValnomCapteur, ValnumCapteur));
        }

        mAdapter.notifyItemInserted(0);
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            mExempleList.remove(viewHolder.getAdapterPosition());
            mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
        }
    };

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP| ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, 0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();

            Collections.swap(mExempleList, fromPosition, toPosition);

            recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);

            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
    };

    public void createExempleList() {
        mExempleList = new ArrayList<>();
        /*mExempleList.add(new ExempleItem(R.drawable.ic_android, "Line 1", "Line 2"));
        mExempleList.add(new ExempleItem(R.drawable.ic_audio, "Line 3", "Line 4"));
        mExempleList.add(new ExempleItem(R.drawable.ic_sun, "Line 5", "Line 6"));*/
    }

    public void buildRecyclerView() {
        //mRecyclerView = findViewById(R.id.recycle_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        //mAdapter = new ExempleAdapter(mExempleList);

        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView);

        new ItemTouchHelper(simpleCallback).attachToRecyclerView(mRecyclerView);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new ExempleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                OpenCapteurActivity();
            }
        });
    }

    public void OpenCapteurActivity(){
        Intent intent = new Intent(this, Capteur.class);
        startActivity(intent);
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}