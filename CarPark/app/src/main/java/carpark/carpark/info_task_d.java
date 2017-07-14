package carpark.carpark;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class info_task_d extends AppCompatActivity {

    String Id_task_driver;

    String Car_id, Driver_id;

    Button accept_bn, complete_bn, cancle_bn;


    TextView Type_bid_driver, Address_start_driver, Address_finish_driver, Comment_driver;// определяем все текстовые поля
    String server_url_driver_bid = "http://auto-park.mywebcommunity.org/php/json/getBidInfoDriver.php"; // ссылка на Json с активными заявками
    String server_url_driver_accept_bid = "http://auto-park.mywebcommunity.org/php/query/acceptBidDriver.php";
    String server_url_driver_cancle_bid = "http://auto-park.mywebcommunity.org/php/query/cancleBidDriver.php";
    String server_url_driver_complete_bid = "http://auto-park.mywebcommunity.org/php/query/completeBidDriver.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_task_d);

        Intent intent = getIntent();
        Id_task_driver = intent.getStringExtra("id");// принимаем id с предыдущего activity

        // Определяем TextView по id
        Type_bid_driver = (TextView) findViewById(R.id.type_bid_driver);
        Address_start_driver = (TextView) findViewById(R.id.address_start_driver);
        Address_finish_driver = (TextView) findViewById(R.id.address_finish_driver);
        Comment_driver = (TextView) findViewById(R.id.comment_driver);
        accept_bn = (Button)findViewById(R.id.accept_task);
        complete_bn = (Button)findViewById(R.id.complete_task);
        cancle_bn = (Button)findViewById(R.id.cancle_task);

        SharedPreferences sharedPreferences = getSharedPreferences("Session", Context.MODE_PRIVATE);
        Driver_id = sharedPreferences.getString(getString(R.string.driverId),"");

        getInfoDriverBid(); // вызываем метод
    }

    public void getInfoDriverBid( ) {
        final String id_task_driver = Id_task_driver; // записываем id  новую переменную
        SharedPreferences sharedPreferences = getSharedPreferences("Session", Context.MODE_PRIVATE);
        final String bid_id = sharedPreferences.getString(getString(R.string.bidId),"");
        // Устанавливаем соединение ссервером по методу POST запроса
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url_driver_bid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(info_task_d.this,response,Toast.LENGTH_LONG).show();

                        try {
                            // Создаем Json объект
                            JSONObject jsonObject = new JSONObject(response);
                            // Получаем Json массив с сервера
                            JSONArray jsonArray = jsonObject.getJSONArray("bid_driver");
                            JSONObject data = jsonArray.getJSONObject(0);
                            // Устанавливаем полученые значения массива в тексттовые поля
                            Type_bid_driver.setText(data.getString("type"));
                            Address_start_driver.setText(data.getString("address_start"));
                            Address_finish_driver.setText(data.getString("address_finish"));
                            Comment_driver.setText(data.getString("comment"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                      /*  Toast.makeText(edit_task.this,response,Toast.LENGTH_LONG).show();*/
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(info_task_d.this, error.toString(), Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            // Отправляем id серверу
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                if (bid_id.equals("")) {
                    params.put("id", id_task_driver);
                    accept_bn.setVisibility(View.VISIBLE);
                    cancle_bn.setVisibility(View.GONE);
                    complete_bn.setVisibility(View.GONE);}
                else {
                    accept_bn.setVisibility(View.GONE);
                    cancle_bn.setVisibility(View.VISIBLE);
                    complete_bn.setVisibility(View.VISIBLE);
                    params.put("id", bid_id);}
                return params;
            }
        };
        MySingleton.getInstance(info_task_d.this).addTorequestque(stringRequest); // отправляем запрос

    }

    public void acceptTask(View view) {
        final String id_task = Id_task_driver;
        final String driver_id;
        driver_id = Driver_id;
        SharedPreferences sharedPreferences2 = getSharedPreferences("Session", Context.MODE_PRIVATE);
        final String bid_id = sharedPreferences2.getString(getString(R.string.bidId),"");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url_driver_accept_bid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(info_task_d.this, "Error", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                if (bid_id.equals("")) {
                    params.put("id", id_task);
                } else {params.put("id", bid_id);}
                params.put("driver_id", driver_id);
                return params;
            }
        };
        MySingleton.getInstance(info_task_d.this).addTorequestque(stringRequest);
        accept_bn.setVisibility(View.GONE);
        cancle_bn.setVisibility(View.VISIBLE);
        complete_bn.setVisibility(View.VISIBLE);
        SharedPreferences sharedPreferences = getSharedPreferences("Session", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.bidId),id_task);
        editor.commit();

    }

    public void completeTask(View view) {
        final String id_task = Id_task_driver;
        SharedPreferences sharedPreferences2 = getSharedPreferences("Session", Context.MODE_PRIVATE);
        final String bid_id = sharedPreferences2.getString(getString(R.string.bidId),"");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url_driver_complete_bid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(info_task_d.this, "Error", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                if (bid_id.equals("")) {
                    params.put("id", id_task);
                } else {params.put("id", bid_id);}
                return params;
            }
        };
        MySingleton.getInstance(info_task_d.this).addTorequestque(stringRequest);
        Intent intent = new Intent(info_task_d.this, task_list_d.class);
        startActivity(intent);
        SharedPreferences sharedPreferences = getSharedPreferences("Session", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.bidId),"");
        editor.commit();
    }

    public void cancleTask(View view) {
        final String id_task = Id_task_driver;
        SharedPreferences sharedPreferences2 = getSharedPreferences("Session", Context.MODE_PRIVATE);
        final String bid_id = sharedPreferences2.getString(getString(R.string.bidId),"");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url_driver_cancle_bid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(info_task_d.this, "Error", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                if (bid_id.equals("")) {
                    params.put("id", id_task);
                } else {params.put("id", bid_id);}
                return params;
            }
        };
        MySingleton.getInstance(info_task_d.this).addTorequestque(stringRequest);
        accept_bn.setVisibility(View.VISIBLE);
        cancle_bn.setVisibility(View.GONE);
        complete_bn.setVisibility(View.GONE);
        SharedPreferences sharedPreferences = getSharedPreferences("Session", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.bidId),"");
        editor.commit();
    }
}
