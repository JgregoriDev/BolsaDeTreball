package com.example.lpc.bolsadetreball;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.example.lpc.bolsadetreball.Entitat.OfertesTreball;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by lpc on 2/05/17.
 */

public class MyAndroidFirebaseMsgService extends FirebaseMessagingService {
    ArrayMap<String, String> campsMissatge;
    private String TAG="Jack";
    private SQLiteHelper sqLiteHelper;
    private OfertesTreball ot;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        String Nom = null;
        String Email = null;
        String Poblacio = null;
        String Telefono = null;
        String Cicle = null;
        String Data = null;
        String Descripcio = null;
        if (!remoteMessage.getData().isEmpty()){

            Nom=remoteMessage.getData().get("Nom");
            Email=remoteMessage.getData().get("Email");
            Poblacio=remoteMessage.getData().get("Poblacio");
            Telefono=remoteMessage.getData().get("Telefono");
            Cicle=remoteMessage.getData().get("Cicle");
            Descripcio=remoteMessage.getData().get("Descripcio");
            Data=remoteMessage.getData().get("Data");

        }
        if (Nom!=null){
            sqLiteHelper=new SQLiteHelper(getApplicationContext());

            if (Data == null) {
                Date date = Calendar.getInstance().getTime();
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Data = formatter.format(date);
            }

            ot = new OfertesTreball(Nom, Poblacio, Email, Cicle, Data, Descripcio, Telefono);
//            Log.d("Jack",ot.getNom()+ot.getDescripcio());
        }
        if(ot!=null){
            sqLiteHelper.Insertar(ot);


        }
        campsMissatge = new ArrayMap<>();
        campsMissatge = (ArrayMap<String, String>) remoteMessage.getData();

        createNotification(remoteMessage.getNotification().getBody());
    }

    private void createNotification(String messageBody) {
        Intent intent = new Intent(this, Inici.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent resultIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri notificationSoundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.photo)
                .setContentTitle("Nova oferta de Treball")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(notificationSoundURI)
                .setContentIntent(resultIntent);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, mNotificationBuilder.build());


    }

}
