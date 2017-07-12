package carpark.carpark;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class login extends AppCompatActivity {

    String server_url_login = "http://auto-park.mywebcommunity.org/php/json/authorization.php";
    EditText Login, Password;
    String privelege;

    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Login = (EditText)findViewById(R.id.login);
        Password = (EditText)findViewById(R.id.password);
        builder = new AlertDialog.Builder(login.this);
    }

    public void login(View view) {
        final String login = Login.getText().toString();
        final String password = Password.getText().toString();

        if (login.equals("") || password.equals("")) {
            builder.setTitle("Пидр, введи все данные");
            displayAlert("Ну ты и пидр");
        } else {


            StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url_login,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            final Intent intent;
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = jsonObject.getJSONArray("users");
                                JSONObject data = jsonArray.getJSONObject(0);
                                privelege = data.getString("privilege");

                                if (privelege.equals("admin")) {
                                    intent = new Intent(login.this, task_list_a.class);;
                                    startActivity(intent);
                                } else if (privelege.equals("driver")) {
                                    intent = new Intent(login.this, add_task.class);
                                    startActivity(intent);

                                  }


                                Toast.makeText(login.this,response,Toast.LENGTH_SHORT).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("login", login);
                    params.put("password", password);
                    return params;
                }

            };
            MySingleton.getInstance(login.this).addTorequestque(stringRequest);

        }

    }

    public void displayAlert (String messqge) {
        builder.setMessage(messqge);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Login.setText("");
                Password.setText("");
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}
