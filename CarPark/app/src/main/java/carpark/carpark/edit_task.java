package carpark.carpark;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static carpark.carpark.R.id.id_task_edit;
import static carpark.carpark.R.id.progressBar;

public class edit_task extends AppCompatActivity {
    AlertDialog.Builder builder;
    EditText Address_start_edit, Address_finish_edit, Comment_edit;
    TextView Id_task_edit;
    String server_url_edit = "http://auto-park.mywebcommunity.org/php/json/getBidEdit.php";
    String server_url_update = "http://auto-park.mywebcommunity.org/php/deleteinfo.php";
    String server_url_delete = "http://auto-park.mywebcommunity.org/php/deleteBid.php";
    RequestQueue requestQueue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_task);
        Intent intent = getIntent();
        Id_task_edit = (TextView)findViewById(R.id.id_task_edit);
        Address_start_edit = (EditText) findViewById(R.id.address_start_edit);
        Address_finish_edit = (EditText) findViewById(R.id.address_finish_edit);
        Comment_edit = (EditText) findViewById(R.id.comment_edit);
        /*Spinner spinner = (Spinner) findViewById(R.id.spinner_edit);*/
        Id_task_edit.setText(intent.getStringExtra("id"));
       // requestQueue = Volley.newRequestQueue(this);
        getDataEdit();

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
    // Отправляем id серверу
/*    public void delete(View view) {



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, server_url_edit, (String)null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            Address_start_edit.setText(response.getString("Address_start"));
                            Address_finish_edit.setText(response.getString("Address_finish"));
                            Comment_edit.setText(response.getString("Comment"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton.getInstance(edit_task.this).addTorequestque(jsonObjectRequest);

    }*/


    // Получаем данные из JSON
    public void getDataEdit() {
        postId(server_url_edit);

        TextView Id_task_edit = (TextView)findViewById(R.id.id_task_item);
        //TextView id_task_item = (TextView)findViewById(R.id.id_task_edit);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, server_url_edit, (String)null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            Address_start_edit.setText(response.getString("Address_start"));
                            Address_finish_edit.setText(response.getString("Address_finish"));
                            Comment_edit.setText(response.getString("Comment"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton.getInstance(edit_task.this).addTorequestque(jsonObjectRequest);
    }

}
