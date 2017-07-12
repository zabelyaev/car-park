package carpark.carpark;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class activ_task extends AppCompatActivity {

    String Id_activ_task; // здесь храним id

    TextView  Driver_active, Car_active, Type_bid_active, Address_start_active, Address_finish_active, Comment_active;// определяем все текстовые поля

    String server_url_active_bid = "http://auto-park.mywebcommunity.org/php/json/getBidActive.php"; // ссылка на Json с активными заявками

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activ_task);

        Intent intent = getIntent(); // получаем intent с предыдущего activity
        Id_activ_task=intent.getStringExtra("id"); // принимаем id с предыдущего activity

        // Определяем TextView по id
        Driver_active = (TextView) findViewById(R.id.driver_active);
        Car_active = (TextView) findViewById(R.id.car_active);
        Type_bid_active = (TextView) findViewById(R.id.type_bid_active);
        Address_start_active = (TextView) findViewById(R.id.address_start_active);
        Address_finish_active = (TextView) findViewById(R.id.address_finish_active);
        Comment_active = (TextView) findViewById(R.id.comment_active);

        getActiveBid(); // вызываем метод
    }

    // Получаем данные из JSON
    public void getActiveBid( ) {
        final String id_task_active = Id_activ_task; // записываем id  новую переменную
        // Устанавливаем соединение ссервером по методу POST запроса
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url_active_bid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(activ_task.this,response,Toast.LENGTH_LONG).show();

                        try {
                            // Создаем Json объект
                            JSONObject jsonObject = new JSONObject(response);
                            // Получаем Json массив с сервера
                            JSONArray jsonArray = jsonObject.getJSONArray("bid_active");
                            JSONObject data = jsonArray.getJSONObject(0);
                            // Устанавливаем полученые значения массива в тексттовые поля
                            Driver_active.setText(data.getString("driver"));
                            Car_active.setText(data.getString("car"));
                            Type_bid_active.setText(data.getString("type"));
                            Address_start_active.setText(data.getString("address_start"));
                            Address_finish_active.setText(data.getString("address_finish"));
                            Comment_active.setText(data.getString("comment"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                      /*  Toast.makeText(edit_task.this,response,Toast.LENGTH_LONG).show();*/
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activ_task.this, error.toString(), Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            // Отправляем id серверу
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id_task_active);
                return params;
            }
        };
        MySingleton.getInstance(activ_task.this).addTorequestque(stringRequest); // отправляем запрос

    }
}
