package com.example.lpc.bolsadetreball;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.lpc.bolsadetreball.Entitat.OfertesTreball;

import java.util.ArrayList;

public class LlistaOfertesActivity extends MenuActivity {
    private ArrayList<OfertesTreball> llistaOfertes;
    private ListView listView;
    private SQLiteHelper sqLiteHelper;
    private SQLiteDatabase bd;
    private Button b_clear;
    private CheckBox cb_dam, cb_asix;
    private int num;

    //    private ArrayList<OfertesTreball> llista;
    private ArrayList<String> llista;
    private ArrayList<OfertesTreball> arrayOfertesTreballs;
    private ArrayAdapter adaptador;
    private String condicio = "";
    Bundle bundle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llista);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        sqLiteHelper = new SQLiteHelper(getApplicationContext());
        listView = (ListView) findViewById(R.id.listView);
        cb_dam = (CheckBox) findViewById(R.id.cb_dam);
        cb_asix = (CheckBox) findViewById(R.id.cb_asix);
        b_clear = (Button) findViewById(R.id.b_clear);


        CondicioArray();
        b_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cb_dam.setChecked(false);
                cb_asix.setChecked(false);
                CondicioArray();
            }
        });
        cb_dam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CondicioArray();
//                CondicioArrayLlistaOfertes();
            }
        });
        cb_asix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CondicioArray();

//                CondicioArrayLlistaOfertes();
            }
        });
        arrayOfertesTreballs = sqLiteHelper.cargarArrayList(condicio);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(),""+position,Toast.LENGTH_SHORT).show();
                //Obting el bloc de text del listview i la guarde en una variable
                String valors = listView.getItemAtPosition(position).toString();
                //Cree un array de String
                String[] arrayValorsOt = new String[3];
                //Almacene en el array anterior totes les linees amb bot de text
                arrayValorsOt = valors.split("\n");
                //Almacene la posició cero del array en un string
                String codilletres = arrayValorsOt[0];
                //Cree un array
                String[] codill = new String[2];
                //Y repartixc el codi per un :
                codill = codilletres.split(":");
                int codi = 0;
                //Transforme el codi amb mode text i el transforme en un int
                codi = Integer.parseInt(codill[1]);

                arrayOfertesTreballs=sqLiteHelper.cargarArrayList(condicio);
                if (arrayOfertesTreballs != null) {
                    Toast.makeText(getApplicationContext(), "Trobat", Toast.LENGTH_SHORT);
                    /*Intent intent = new Intent(LlistaOfertesActivity.this, DadesOfertaActivity.class);
                    intent.putExtra("Nom", listView.getItemAtPosition(position).toString());
                    intent.putExtra("OfertesTreball", ot);*/
                    Intent intent = new Intent(LlistaOfertesActivity.this, TabbedActivityOfertesTreball.class);
//                    CondicioArrayLlistaOfertes();
//                    arrayOfertesTreballs = sqLiteHelper.cargarArrayList(condicio);
                    Log.d("Jack", ""+position);
                    intent.putExtra("posicio", position);
                    Log.d("Jack","Enviant arraylist amb tamany "+arrayOfertesTreballs.size());
                    intent.putParcelableArrayListExtra("ArrayOfertesTreball", arrayOfertesTreballs);
//                    arrayOfertesTreballs=null;
//                    intent.putExtra("Activity", "LlistaOfertesActivity.class");
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "No s'ha trobat", Toast.LENGTH_SHORT);

                }
            }
        });


    }

    private OfertesTreball BuscarOfertesTreball(int num) {
        //Agafe l'array list del sqlitehelper
        ArrayList<OfertesTreball> ofertesTreballs = sqLiteHelper.getOfertesTreballs();
//        Log.d("Jack",""+ofertesTreballs.size());
        //Faig un objecte null
        OfertesTreball ot = null;
        //Recorrec el array de objecte en busca d'un codi
        for (OfertesTreball ofertesTreball : ofertesTreballs) {
//            Log.d("Jack",""+ofertesTreball.getCodi()+ofertesTreball.getNom()+ofertesTreball.getDataNotificacio());
            //Si resulta que el numero es igual al objecte get codi
            if (num == ofertesTreball.getCodi()) {
                //Guarda ofertesTreball en un ot y passa a tindre els valors del objecte
                ot = ofertesTreball;
                break;
            }
        }
        if (ot != null) {
//            Log.d("Jack", "trobat");
            return ot;

        } else {
//            Log.d("Jack", "no trobat");
            return null;
        }
    }

    private void CondicioArray() {
        if (cb_dam.isChecked() && cb_asix.isChecked()) {

            condicio = " WHERE Cicle='DAM+ASIX' ORDER BY Codi DESC;";

        } else {
            condicio = " ORDER BY Codi DESC;";

        }
        if (cb_dam.isChecked() && !cb_asix.isChecked()) {
            condicio = " WHERE Cicle='DAM' ORDER BY Codi DESC;";
        }
        if (!cb_dam.isChecked() && cb_asix.isChecked()) {
            condicio = " WHERE Cicle='ASIX' ORDER BY Codi DESC;";
        }
        Log.d("Jack",condicio);
        llista = sqLiteHelper.cargarArrayList(condicio, llistaOfertes);
        CarregarLV();
        arrayOfertesTreballs=sqLiteHelper.cargarArrayList(condicio);
    }

  /*  private void CondicioArrayLlistaOfertes() {
        if (cb_dam.isChecked() && cb_asix.isChecked()) {

            condicio = " WHERE Cicle='DAM+ASIX' ORDER BY Codi DESC;";

        } else {
            condicio = " ORDER BY Codi DESC;";

        }
        if (cb_dam.isChecked() && !cb_asix.isChecked()) {
            condicio = " WHERE Cicle='DAM' ORDER BY Codi DESC;";
        }
        if (!cb_dam.isChecked() && cb_asix.isChecked()) {
            condicio = " WHERE Cicle='ASIX' ORDER BY Codi DESC;";
        }
        arrayOfertesTreballs=sqLiteHelper.cargarArrayList(condicio);


    }*/



    private void CarregarLV() {

        if (llista == null) {
//            Log.d("Arraylist", "No disposes de elements en la llista");
            Toast.makeText(getApplicationContext(), "No disposes de registres en la llista", Toast.LENGTH_SHORT).show();
            listView.setAdapter(null);
        } else {
            adaptador = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, llista);
            listView.setAdapter(adaptador);

        }
    }

/*
    private void LlegirValorsFirebase() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("-KjZyP6e8VuvFIYlosQx");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String valor = dataSnapshot.getValue(String.class);
                Log.d("Jack", valor);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
*/

}

