package carpark.carpark;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class task_list_a extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_list_a);
    }

    public void add_task(MenuItem item) {
        startActivity(new Intent(this, add_task.class));
    }

    public void edit_task(View view) {
        startActivity(new Intent(this, edit_task.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.task_list_a_m, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
