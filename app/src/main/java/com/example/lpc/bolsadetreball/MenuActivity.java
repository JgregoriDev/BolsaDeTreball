package com.example.lpc.bolsadetreball;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inici, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent intent = null;
        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.Inici:
                intent = new Intent(this, Inici.class);
                Toast.makeText(getApplicationContext(), "Obrint Inici.class", Toast.LENGTH_SHORT).show();
                break;
            case R.id.Llista_Ofertes:
                Toast.makeText(getApplicationContext(), "Obrint LlistaOfertesActivity.class", Toast.LENGTH_SHORT).show();
                intent = new Intent(this, LlistaOfertesActivity.class);
                break;
            /*case R.id.Insertar:
                Toast.makeText(getApplicationContext(), "Obrint InsertarActivity.class", Toast.LENGTH_SHORT).show();
                intent = new Intent(this, InsertarActivity.class);
                break;*/
        }

        startActivity(intent);

        return super.onOptionsItemSelected(item);
    }
}
