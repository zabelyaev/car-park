package carpark.carpark;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.Map;

public class add_task extends AppCompatActivity implements Spinner.OnItemSelectedListener{

    EditText Address_start, Address_finish, Comment;
    String server_url = "http://auto-park.mywebcommunity.org/php/query/bidInsert.php";
    AlertDialog.Builder builder;
    public String index;
    Intent intent;

    String server_url_type_bid = "http://auto-park.mywebcommunity.org/php/json/getTypeBid.php";

    ArrayList<String> typeBid;
    JSONArray jsonArrayType;
    Spinner spinner;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task);

        Address_start = (EditText) findViewById(R.id.address_start);
        Address_finish = (EditText) findViewById(R.id.address_finish);
        Comment = (EditText) findViewById(R.id.comment);
        builder = new AlertDialog.Builder(add_task.this);

        typeBid = new ArrayList<String>();
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

         intent = new Intent(this, task_list_a.class);

        getDataSpinner();

    }

    public void getDataSpinner(){
        //Creating a string request
        StringRequest stringRequest = new StringRequest(server_url_type_bid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            //Parsing the fetched Json String to JSON Object
                            j = new JSONObject(response);

                            //Storing the Array of JSON String to our JSON Array
                            jsonArrayType = j.getJSONArray("type");

                            //Calling method getStudents to get the students from the JSON Array
                            getType(jsonArrayType);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    public void getType(JSONArray j){
        //Traversing through all the items in the json array
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                typeBid.add(json.getString("type"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spinner.setAdapter(adapter = new ArrayAdapter<String>(add_task.this, android.R.layout.simple_spinner_dropdown_item, typeBid));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Setting the values to textviews for a selected item
        if (position == 0) {
            index = "1";
        } else if(position == 1) {
            index = "2";
        }
    }

    //When no item is selected this method would execute
    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    public void addTask(View view) {
        final String address_start, address_finish, comment, type;
        address_start = Address_start.getText().toString();
        address_finish = Address_finish.getText().toString();
        comment = Comment.getText().toString();
        type=index;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(add_task.this, "Error", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("type_bid", type);
                params.put("address_start", address_start);
                params.put("address_finish", address_finish);
                params.put("comment", comment);
                return params;
            }
        };
        MySingleton.getInstance(add_task.this).addTorequestque(stringRequest);
        startActivity(intent);
    }
}
