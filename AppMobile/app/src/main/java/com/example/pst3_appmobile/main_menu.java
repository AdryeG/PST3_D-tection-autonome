package com.example.pst3_appmobile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class main_menu extends AppCompatActivity implements ExempleAdapter.OnItemClickListener {

    public static final String NumSerieCapteur = "Text2";
    public static final String NomCapteurextra = "Text1";
    public static final String Notif = "notification";

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

        //setButtons();
        
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
                mRecyclerView.setVisibility(View.GONE);

            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCapteurList.setVisibility(View.GONE);
                buttonCancel.setVisibility(View.GONE);
                buttonInsert.setVisibility(View.VISIBLE);
                addCapteurInfo.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);

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
                        final String type = "0";
                        addCapteurBDD(ValnomCapteur, ValnumCapteur, type);
                    }

                    if (i[0] == 1)
                    {
                        final String type = "1";
                        addCapteurBDD(ValnomCapteur, ValnumCapteur, type);
                    }

                    if (i[0] == 2)
                    {
                        final String type = "2";
                        addCapteurBDD(ValnomCapteur, ValnumCapteur, type);
                    }

                    if (i[0] == 3)
                    {
                        final String type = "3";
                        addCapteurBDD(ValnomCapteur, ValnumCapteur, type);
                    }

                    if (i[0] == 4)
                    {
                        final String type = "4";
                        addCapteurBDD(ValnomCapteur, ValnumCapteur, type);
                    }

                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
                    closeKeyboard();
                    addCapteurList.setVisibility(View.GONE);
                    buttonCancel.setVisibility(View.GONE);
                    buttonInsert.setVisibility(View.VISIBLE);
                    addCapteurInfo.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
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
        String url ="https://nimble-lead-277612.ew.r.appspot.com/?id=4&numSerie="+ numserie +"&nomCapteur="+ captername +"&typeCapteur=" + i;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                if (response.equals("Successfully added"))
                {
                    progressDialog.dismiss();
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

    private void parseJSON() {
        String url = "https://nimble-lead-277612.ew.r.appspot.com/?id=3";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("Liste");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);
                                String Text1 = hit.getString("nomCapteur");
                                String Text2 = hit.getString("numSerie");
                                int icon = hit.getInt("typeCapteur");
                                String notif = hit.getString ("notification");
                                if (icon == 0)
                                    mExempleList.add(0, new ExempleItem(R.drawable.ic_door, Text1, Text2, notif));
                                if (icon == 1)
                                    mExempleList.add(0, new ExempleItem(R.drawable.ic_tmp, Text1, Text2, notif));
                                if (icon == 2)
                                    mExempleList.add(0, new ExempleItem(R.drawable.ic_humidity, Text1, Text2, notif));
                                if (icon == 3)
                                    mExempleList.add(0, new ExempleItem(R.drawable.ic_motion_sensor, Text1, Text2, notif));
                                if (icon == 4)
                                    mExempleList.add(0, new ExempleItem(R.drawable.ic_lumiere, Text1, Text2, notif));
                            }
                            mAdapter = new ExempleAdapter(main_menu.this, mExempleList);
                            mRecyclerView.setAdapter(mAdapter);
                            mAdapter.setOnItemClickListener(main_menu.this);
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


    /*ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
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
    };*/

    public void OpenCapteurActivity(){
        Intent intent = new Intent(this, Capteur.class);
        startActivity(intent);
    }

    public void removeItem(int position) {
        mExempleList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, Capteur.class);
        ExempleItem clickItem = mExempleList.get(position);

        detailIntent.putExtra(NumSerieCapteur, clickItem.getText2());
        detailIntent.putExtra(NomCapteurextra, clickItem.getText1());
        detailIntent.putExtra(Notif, clickItem.getNotif());
        startActivity(detailIntent);
    }

    /*@Override
    public void onDeleteClick(int position) {
        removeItem(position);
        final ProgressDialog progressDialog = new ProgressDialog(main_menu.this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("Deleting sensor from database");
        progressDialog.show();
        String url ="https://nimble-lead-277612.ew.r.appspot.com/?id=6&numSerie="+ NumSerieCapteur;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                if (response.equals("Successfully added"))
                {
                    progressDialog.dismiss();
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
                param.put("captername", NumSerieCapteur);
                return param;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(main_menu.this).addToRequestQueue(request);
    }*/
}