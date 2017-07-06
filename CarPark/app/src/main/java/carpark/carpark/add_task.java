package carpark.carpark;

import android.content.DialogInterface;
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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class add_task extends AppCompatActivity {

    Button button;
    EditText Address_start, Address_finish, Comment;
    String server_url = "http://auto-park.mywebcommunity.org/php/query/bidInsert.php";
    AlertDialog.Builder builder;
    public String index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AdapterView.OnItemSelectedListener onSpinner = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent,View view,int position,long id) {
            if (position == 0) {
                    index = "0";
            } else if(position == 1) {
                    index = "1";
            } else {index = "2";}
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {}};

        setContentView(R.layout.add_task);

        button = (Button) findViewById(R.id.add_task);
        Address_start = (EditText) findViewById(R.id.address_start);
        Address_finish = (EditText) findViewById(R.id.address_finish);
        Comment = (EditText) findViewById(R.id.comment);
        builder = new AlertDialog.Builder(add_task.this);
        String[] type_bid = {"","Груз", "Пассажиры"};
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, type_bid);
        spinner.setAdapter(stringArrayAdapter);
        spinner.setOnItemSelectedListener(onSpinner);


    }
}
