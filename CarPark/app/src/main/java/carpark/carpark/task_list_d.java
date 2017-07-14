package carpark.carpark;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class task_list_d extends AppCompatActivity {

    ArrayList<GetDataAdapterD> listD = new ArrayList<GetDataAdapterD>();
    RecyclerView recyclerView_d;
    RecyclerView.Adapter adapter_d;
    RecyclerView.LayoutManager layoutManager_d;
    ProgressBar progressBar_d;

    String json_url = "http://auto-park.mywebcommunity.org/php/json/getBidDriver.php";
    String TYPE_BID = "type";
    String ADDRESS_START = "address_start";
    String ADDRESS_FINISH = "address_finish";
    String BID_ID = "id";

    JsonArrayRequest jsonArrayRequest;
    RequestQueue requestQueue;

    String car_id, driver_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_list_d);

        recyclerView_d = (RecyclerView) findViewById(R.id.recyclerView_d);
        progressBar_d = (ProgressBar) findViewById(R.id.progressBar_d);
        recyclerView_d.setHasFixedSize(true);
        layoutManager_d = new LinearLayoutManager(this);
        recyclerView_d.setLayoutManager(layoutManager_d);

        progressBar_d.setVisibility(View.VISIBLE);

/*        Intent getIntent = getIntent();

        driver_id=getIntent.getStringExtra("driver_id");
        car_id=getIntent.getStringExtra("car_id");
        Toast.makeText(task_list_d.this,driver_id+car_id,Toast.LENGTH_SHORT).show();

        Intent intent2 = new Intent(task_list_d.this, info_task_d.class);
        intent2.putExtra("driver_id",driver_id);
        intent2.putExtra("car_id",car_id);*/
        getDataD();
    }

    public void getDataD() {
        jsonArrayRequest = new JsonArrayRequest(json_url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        progressBar_d.setVisibility(View.GONE);
                        parserD(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(task_list_d.this, "Упс... Ошибочка", Toast.LENGTH_LONG);
            }
        });
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    public void parserD(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            GetDataAdapterD GetDataAdapterD = new GetDataAdapterD(TYPE_BID,BID_ID,ADDRESS_START,ADDRESS_FINISH);
            JSONObject json = null;
            try {
                json = array.getJSONObject(i);
                GetDataAdapterD.setType_bid_d(json.getString(TYPE_BID));
                GetDataAdapterD.setId_bid_d(json.getString(BID_ID));
                GetDataAdapterD.setAddress_start_d(json.getString(ADDRESS_START));
                GetDataAdapterD.setAddress_finish_d(json.getString(ADDRESS_FINISH));

            } catch (JSONException e) {
                Toast.makeText(task_list_d.this, "Не могу соедениться с сервером...", Toast.LENGTH_LONG);
                e.printStackTrace();
            }
            listD.add(GetDataAdapterD);
        }

        adapter_d = new RecyclerViewAdapterD(listD, this);
        recyclerView_d.setAdapter(adapter_d);
    }

}
