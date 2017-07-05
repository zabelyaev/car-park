package carpark.carpark;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class add_task extends AppCompatActivity {
    EditText

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task);

        final Spinner spinner = (Spinner)findViewById(R.id.spinner);

        ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, R.array.type_task, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
    }
}
