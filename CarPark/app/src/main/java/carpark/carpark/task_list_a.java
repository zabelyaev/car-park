package carpark.carpark;


import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class task_list_a extends AppCompatActivity {

    List<GetDataAdapter> GetDataAdapter1;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerViewlayoutManager;
    RecyclerViewAdapter recyclerViewadapter;
    ProgressBar progressBar;
    String json_url = "http://auto-park.mywebcommunity.org/php/json/getBid.php";
    String TYPE_BID = "type";
    String ADDRESS_START = "address_start";
    String ADDRESS_FINISH = "address_finish";
    String BID_ID = "id";
    String STATUS_BID = "status_bid";

    ImageView type_bid_icon;

    JsonArrayRequest jsonArrayRequest;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_list_a);
        GetDataAdapter1 = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        recyclerView.setHasFixedSize(true);
        recyclerViewlayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recyclerViewlayoutManager);

        type_bid_icon = (ImageView)findViewById(R.id.type_bid_icon);

        progressBar.setVisibility(View.VISIBLE);
        getData();



    }

    public void getData() {
        jsonArrayRequest = new JsonArrayRequest(json_url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        progressBar.setVisibility(View.GONE);
                        parser(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(task_list_a.this, "Упс... Ошибочка", Toast.LENGTH_LONG);
            }
        });
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    public void parser(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            GetDataAdapter GetDataAdapter2 = new GetDataAdapter();
            JSONObject json = null;
            try {
                json = array.getJSONObject(i);
                GetDataAdapter2.setType_bid(json.getString(TYPE_BID));
                GetDataAdapter2.setId_bid(json.getString(BID_ID));
                GetDataAdapter2.setAddress_start(json.getString(ADDRESS_START));
                GetDataAdapter2.setAddress_finish(json.getString(ADDRESS_FINISH));
                GetDataAdapter2.setStatus_bid(json.getString(STATUS_BID));

            } catch (JSONException e) {
                Toast.makeText(task_list_a.this, "Не могу соедениться с сервером...", Toast.LENGTH_LONG);
                e.printStackTrace();
            }
            GetDataAdapter1.add(GetDataAdapter2);
        }

        recyclerViewadapter = new RecyclerViewAdapter(GetDataAdapter1, this);
        recyclerView.setAdapter(recyclerViewadapter);



    }

    public void add_task(MenuItem item) {

        startActivity(new Intent(this, add_task.class));
    }


    public void edit_task(View view) {
        TextView id_task_item = (TextView) findViewById(R.id.id_task_item);
        TextView type_bid = (TextView)findViewById(R.id.type_bid);
        TextView status_bid = (TextView)findViewById(R.id.status_bid);
        if (status_bid.getText().toString().equals("Не принято")) {
            Intent intent = new Intent(this, edit_task.class);
            intent.putExtra("id", id_task_item.getText().toString());
            intent.putExtra("type_bid", type_bid.getText().toString());
            startActivity(intent);
        } else if (status_bid.getText().toString().equals("Принято")) {
            Intent intent = new Intent(this, activ_task.class);
            intent.putExtra("id", id_task_item.getText().toString());
            startActivity(intent);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.task_list_a_m, menu);
        return super.onCreateOptionsMenu(menu);
    }

}

