package com.example.lpc.bolsadetreball;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lpc.bolsadetreball.Entitat.OfertesTreball;

import java.util.ArrayList;

public class TabbedActivityOfertesTreball extends MenuActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    public static String Telefon;
    public static String Email;
    public static ArrayList<OfertesTreball> ofertesTreballs = null;
    public static int pos = -1;
    static int codi = 0;
//    public static int[] posi = new int[3];
    private Button b_share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed_ofertes_treball);
//        ofertesTreballs=new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        if (ofertesTreballs == null) {
            ofertesTreballs = bundle.getParcelableArrayList("ArrayOfertesTreball");
            Log.d("Jack", "Numero  d'elements " + ofertesTreballs.size());

        } else {
            Log.d("Jack", "Numero  d'elements " + ofertesTreballs.size());
            ofertesTreballs = null;
            ofertesTreballs = bundle.getParcelableArrayList("ArrayOfertesTreball");
        }
        if (pos == -1) {
            pos = bundle.getInt("posicio");
            Log.d("Jack","Tabbed Codi"+pos);
//            bundle = null;
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        b_share = (Button) findViewById(R.id.share);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(pos);

        b_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "Oferta de Trabajo " + "Codi:" + ofertesTreballs.get(pos).getCodi() + "\n" + " Nombre: " + ofertesTreballs.get(pos).getNom());
                startActivity(Intent.createChooser(intent, "Share with"));

            }
        });
/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BorrarOfertaTreball();
            }
        });
*/

    }

    @Override
    protected void onStop() {
        super.onStop();
        /*i=0;*/
        if (pos != -1)
            pos = -1;
    }

    private void BorrarOfertaTreball() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmar");
        builder.setMessage("Estàs segur de borrar l'informació de la oferta de treball " + codi + "?");
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                SQLiteHelper sqLiteHelper = new SQLiteHelper(getApplicationContext());
                sqLiteHelper.BorrarRegistre(codi);
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Has borrado la notificación correspondiente", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TabbedActivityOfertesTreball.this, LlistaOfertesActivity.class);
                intent.putExtra("Activity", "DadesOfertaActivity");
                startActivity(intent);

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


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";


        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static int sectNumber = 0;

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            /*int p = 0;
            if (i == 1) {
                pos = sectionNumber;
                p = ofertesTreballs.get(pos).getCodi();
            }*/
//            posi[i]=ofertesTreballs.get(sectionNumber).getCodi();
//            Log.d("Proba", "" + p);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_tabbed_activity_ofertes_treball, container, false);
            int id = getArguments().getInt(ARG_SECTION_NUMBER);
            id++;

            TextView tv_codi = (TextView) rootView.findViewById(R.id.tv_codi);
            TextView tv_nom = (TextView) rootView.findViewById(R.id.tv_nom);
            TextView tv_poblacio = (TextView) rootView.findViewById(R.id.tv_poblacio);
            TextView tv_email = (TextView) rootView.findViewById(R.id.tv_email);
            TextView tv_telefon = (TextView) rootView.findViewById(R.id.tv_telefon);
            TextView tv_cicle = (TextView) rootView.findViewById(R.id.tv_cicle);
            TextView tv_descripcio = (TextView) rootView.findViewById(R.id.tv_descripcio);
            TextView tv_data = (TextView) rootView.findViewById(R.id.tv_data);
            TextView tv_question = (TextView) rootView.findViewById(R.id.tv_question);
            TextView tv_question1 = (TextView) rootView.findViewById(R.id.tv_question1);
            int cod;
            cod = 0;
            String nom = ofertesTreballs.get(id).getNom();
            String Poblacio = ofertesTreballs.get(id).getPoblacio();
            tv_question.append(" ");
            tv_question.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Sí haces un click al número puedes llamar por telefono", Toast.LENGTH_SHORT).show();
                }
            });
            tv_question1.append(" ");
            tv_question1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Sí haces un click al email puedes enviar un mensaje", Toast.LENGTH_SHORT).show();
                }
            });
            final String Telefon = ofertesTreballs.get(id).getTelefono();
            String Cicle = ofertesTreballs.get(id).getCicle();
            final String Email = ofertesTreballs.get(id).getEmail();
            String Descripcio = ofertesTreballs.get(id).getDescripcio();
            codi = ofertesTreballs.get(id).getCodi();
            tv_codi.setText("" + codi);
            //Aconsegueix el codi de la oferta
            Log.d("Codi", "" + cod);

            tv_nom.setText("Nom: " + nom);
            if (Telefon != null) {
                if (Telefon.equals("null")) {
                    tv_telefon.setText("Telefon: La informació no està registrada");
                } else {
                    tv_telefon.setText("Telefon: " + Telefon);
                    tv_telefon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:" + Telefon));
                            startActivity(callIntent);
                        }
                    });
                }
            }

            if (Poblacio != null) {
                if (Poblacio.equals("null")) {
                    tv_poblacio.setText("Població: La informació no està registrada");
                } else {
                    tv_poblacio.setText("Població: " + Poblacio);

                }
            } else {
                tv_poblacio.setText("Població:  La informació no està registrada");
            }
            if (Email != null) {
                if (Email.equals("null")) {
                    tv_email.setText("E-mail: La informació no està registrada");
                } else {
                    tv_email.setText("E-mail: " + Email);
                    tv_email.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent sendEmail = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", Email, null));
                            startActivity(Intent.createChooser(sendEmail, "Send email"));
                        }
                    });

                }
            } else {
                tv_email.setText("E-mail:  La informació no està registrada");
            }
            if (Descripcio != null) {
                if (!Descripcio.equals("null")) {
                    tv_descripcio.setText("Descripcio: " + Descripcio);
                } else {
                    tv_descripcio.setText("Descripcio: No s'ha deixat ningun registre");
                }
            } else {
                tv_descripcio.setText("Descripcio: No s'ha deixat ningun registre");
            }
            if (Cicle != null) {
                if (!Cicle.equals("null")) {
                    tv_cicle.setText("Cicle: " + Cicle);
                } else {

                    tv_cicle.setText("Cicle: " + Cicle);
                }
            } else {
                tv_cicle.setText("Cicle: " + Cicle);
            }
            if (Descripcio != null) {
                if (!Descripcio.equals("null")) {
                    tv_descripcio.setText("Descripcio: " + Descripcio);
                } else {
                    tv_descripcio.setText("Descripcio: No s'ha deixat ningun registre");

//                    tv_descripcio.setTextSize();
                }
            } else {
                tv_descripcio.setText("Descripcio: No s'ha deixat ningun registre");
            }


            tv_descripcio.setText("Descripcio: " + Descripcio);
            tv_data.setText("Data: " + ofertesTreballs.get(id).getDataNotificacio());
            return rootView;
        }


    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            int posicio = position - 1;

            return TabbedActivityOfertesTreball.PlaceholderFragment.newInstance(posicio);
//            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {

            return ofertesTreballs.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            for (int i = ofertesTreballs.size() - 1; i >= 0; i--) {
                if (i == position) {
                    return "" + ofertesTreballs.get(i).getNom();
                }

            }
           /* for (int i=0;i<=ofertesTreballs.size();i++){
                return "" + ofertesTreballs.get(i).getNom();
            }*/

            return null;
        }
    }

}
