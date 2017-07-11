package carpark.carpark;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
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


public class edit_task extends AppCompatActivity implements Spinner.OnItemSelectedListener {
    AlertDialog.Builder builder;
    EditText Address_start_edit, Address_finish_edit, Comment_edit;
    TextView Id_task_edit;
    String server_url_edit = "http://auto-park.mywebcommunity.org/php/json/getBidEdit.php";
    String server_url_update = "http://auto-park.mywebcommunity.org/php/updateBid.php";
    String server_url_delete = "http://auto-park.mywebcommunity.org/php/deleteBid.php";
    String server_url_type_bid = "http://auto-park.mywebcommunity.org/php/json/getTypeBid.php";
    public String index;

    ArrayList<String> typeBid;
    JSONArray jsonArrayType;
    Spinner spinner;
    ArrayAdapter<String> adapter;

    String type_bid;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_task);
        Intent intent = getIntent();
        Id_task_edit = (TextView)findViewById(R.id.id_task_edit);
        Address_start_edit = (EditText) findViewById(R.id.address_start_edit);
        Address_finish_edit = (EditText) findViewById(R.id.address_finish_edit);
        Comment_edit = (EditText) findViewById(R.id.comment_edit);

        Id_task_edit.setText(intent.getStringExtra("id")); // принимаем id с предыдущего activity
        type_bid=intent.getStringExtra("type_bid"); // принимаем type_bid с предыдущего activity

        typeBid = new ArrayList<String>();
        spinner = (Spinner) findViewById(R.id.spinner_edit);
        spinner.setOnItemSelectedListener(this);

        builder = new AlertDialog.Builder(edit_task.this);

        getDataSpinner();
        updates();
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
        spinner.setAdapter(adapter = new ArrayAdapter<String>(edit_task.this, android.R.layout.simple_spinner_dropdown_item, typeBid));
        int selectionPosition= adapter.getPosition(type_bid);
        spinner.setSelection(selectionPosition);

    }

    //this method will execute when we pic an item from the spinner
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
    public void onNothingSelected(AdapterView<?> parent) {
    }


   public void delete(View view) {
        postId(server_url_delete);
        Intent intent = new Intent(this,task_list_a.class);
        startActivity(intent);
    }

    public void postId(String server_url_edit) {
        final String id_task = Id_task_edit.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url_edit,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(edit_task.this,response,Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(edit_task.this, error.toString(), Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id_task);
                return params;
            }
        };
        MySingleton.getInstance(edit_task.this).addTorequestque(stringRequest);
    }


    // Получаем данные из JSON
    public void updates( ) {
        final String id_task2 = Id_task_edit.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url_edit,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(edit_task.this,response,Toast.LENGTH_LONG).show();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("bid_edit");
/*                            result  = jsonObject.getJSONArray("type");
                            getTypeBid(result);*/
                            JSONObject data = jsonArray.getJSONObject(0);
                            Address_start_edit.setText(data.getString("address_start"));
                            Address_finish_edit.setText(data.getString("address_finish"));
                            Comment_edit.setText(data.getString("comment"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(edit_task.this,response,Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(edit_task.this, error.toString(), Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id_task2);
                return params;
            }
        };
        MySingleton.getInstance(edit_task.this).addTorequestque(stringRequest);

    }

    public void update(View view) {
        final String id_task = Id_task_edit.getText().toString().trim();
        final String address_start, address_finish, comment, type;
        address_start = Address_start_edit.getText().toString();
        address_finish = Address_finish_edit.getText().toString();
        comment = Comment_edit.getText().toString();
        type=index;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url_update,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        builder.setTitle("Ответ от сервера:");
                        builder.setMessage("Response:" + response);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Address_start_edit.setText("");
                                Address_finish_edit.setText("");
                                Comment_edit.setText("");

                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(edit_task.this, "Error", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id_task);
                params.put("type_bid", type);
                params.put("address_start", address_start);
                params.put("address_finish", address_finish);
                params.put("comment", comment);
                return params;
            }
        };
        MySingleton.getInstance(edit_task.this).addTorequestque(stringRequest);
        Intent intent = new Intent(this,task_list_a.class);
        startActivity(intent);
    }

}
