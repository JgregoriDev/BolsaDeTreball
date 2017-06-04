package com.example.lpc.bolsadetreball;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lpc.bolsadetreball.Entitat.OfertesTreball;

import java.util.ArrayList;


public class LlistaOfertesActivity extends MenuActivity {
    private ListView listView;
    private SQLiteHelper sqLiteHelper;
    private SQLiteDatabase bd;
    private Button b_clear;
    private CheckBox cb_dam, cb_asix;

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
        setTitle(R.string.title_activity_dades_oferta);
        boolean cb_d = false, cb_a = false;
        boolean dadesAplegades = false;

        bundle = getIntent().getExtras();
        if (bundle == null) {
//          bundle=getIntent().getExtras();
            Log.d("Bundle", "Bundle vacio");
        } else {
//            bundle=getIntent().getExtras();
            String bundleContingut = bundle.getString("LlistaOfertesActivity");
            if (bundleContingut.equals("LlistaOfertesActivity.class")) {
                cb_a = bundle.getBoolean("cb_asix");
                cb_d = bundle.getBoolean("cb_dam");
                dadesAplegades = true;
            }

            Log.d("Bundle", "El Bundle no està buit");
        }

        sqLiteHelper = new SQLiteHelper(getApplicationContext());
        listView = (ListView) findViewById(R.id.listView);
        cb_dam = (CheckBox) findViewById(R.id.cb_dam);
        cb_asix = (CheckBox) findViewById(R.id.cb_asix);
        b_clear = (Button) findViewById(R.id.b_clear);
        if (dadesAplegades) {
            cb_dam.setChecked(cb_d);
            cb_asix.setChecked(cb_a);
            Log.d("Bundle", "dades Aplegades verdader");
        } else {
            Log.d("Bundle", "dades Aplegades falç");

        }



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
            }
        });
        cb_asix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CondicioArray();

//                CondicioArrayLlistaOfertes();
            }
        });
//        arrayOfertesTreballs = sqLiteHelper.cargarArrayListOfertesTreball(condicio);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                arrayOfertesTreballs = sqLiteHelper.cargarArrayListOfertesTreball(condicio);
                if (arrayOfertesTreballs != null) {

//                    MostrarEnpantalla("Carrega de codi exitosa " + codi);
                    Intent intent = new Intent(LlistaOfertesActivity.this, TabbedActivityOfertesTreball.class);
                    intent.putExtra("posicio", position);
                    Log.d("Jack","Llista Codi:"+position);
                    intent.putParcelableArrayListExtra("ArrayOfertesTreball", arrayOfertesTreballs);
                    startActivity(intent);
                } else {

                    MostrarEnpantalla("No s'ha trobat la llista");

                }
            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final int pos=position;
                MostrarMenuLlistaOpcions(pos);

                return true;
            }

        });

    }

    private void MostrarEnpantalla(String missatge) {
        Toast.makeText(getApplicationContext(), missatge, Toast.LENGTH_SHORT).show();
    }

    public AlertDialog MostrarMenuLlistaOpcions(int position) {

        final String[] listaElementos =
                {"Borrar", "Compartir per..."};
        final int posicio=position;
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);
        builder.setTitle("Selecciona el element");
        builder.setItems(listaElementos,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                MostrarEnpantalla("Opción elegida: " + listaElementos[item]);
                                OpcioMenuBorrar(posicio);
                                break;
                            case 1:
                                MostrarEnpantalla("Opción elegida: " + listaElementos[item]);
                                int codi=getCodi(posicio);
                                OfertesTreball ot=buscarofertaTreball(codi);
                                if(ot!=null){
                                    share(ot);
                                }
                                break;
                        }
                    }
                });
        builder.show();

        return builder.create();
    }
    private void share(OfertesTreball ot){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "Oferta de Treball " + "" + ot.getCodi() + "\n"
                + " Nom: " + ot.getNom()+"\n"
                +" Poblacio:"+ot.getPoblacio()+"\n"
                +" Telefono:"+ot.getTelefono()+"\n"
                +" E-mail:"+ot.getEmail()+"\n"
                +" Curs:"+ot.getCicle()+"\n"
                +" Requisits:"+ot.getDescripcio()+"\n"
        );
        startActivity(Intent.createChooser(intent, "Share with"));
    }
    private int getCodi(int position){
        String contingutItem = listView.getItemAtPosition(position).toString();
        String contingutItemtractat[] = contingutItem.split("\n");
        String parraf = contingutItemtractat[0];
        String codill[] = parraf.split(":");
        return Integer.parseInt(codill[1]);
    }
    private void OpcioMenuBorrar(int position){
        int codi=getCodi(position);

        BorrarOfertaTreball(codi);
    }

    private void BorrarOfertaTreball(final int codi) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmar");
        builder.setMessage("Estàs segur de borrar l'informació de la oferta de treball " + codi + "?");
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                sqLiteHelper.BorrarRegistre(codi);
                /*adaptador.remove(adaptador.getItem(proba));
                adaptador.notifyDataSetChanged();*/
                Intent intent = getIntent();
                intent.putExtra("LlistaOfertesActivity", "LlistaOfertesActivity.class");

                intent.putExtra("cb_asix", cb_asix.isChecked());
                intent.putExtra("cb_dam", cb_dam.isChecked());

                finish();
                startActivity(intent);
                MostrarEnpantalla("Has borrado la notificación correspondiente");
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
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
        Log.d("Jack", condicio);
        llista = sqLiteHelper.cargarArrayListString(condicio);
        CarregarLV();
        arrayOfertesTreballs = sqLiteHelper.cargarArrayListOfertesTreball(condicio);
    }


    private void CarregarLV() {

        if (llista == null) {
            MostrarEnpantalla("No disposes de registres en la llista");
            listView.setAdapter(null);
        } else {
            adaptador = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, llista);
            listView.setAdapter(adaptador);
        }
    }
    public OfertesTreball buscarofertaTreball(int codi){
        OfertesTreball ot=null;
        for (OfertesTreball ofertesTreball:arrayOfertesTreballs){
            if(codi==ofertesTreball.getCodi()){
                ot=ofertesTreball;
                break;
            }
        }
        return ot;
    }


}

