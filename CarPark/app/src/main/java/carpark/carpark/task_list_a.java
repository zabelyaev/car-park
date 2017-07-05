package carpark.carpark;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class task_list_a extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_list_a);
    }

    public void add_task(View view) { startActivity(new Intent(this, add_task.class)); }
}
