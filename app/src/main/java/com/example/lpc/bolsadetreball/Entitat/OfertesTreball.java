package com.example.lpc.bolsadetreball.Entitat;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lpc on 26/05/17.
 */

public class OfertesTreball implements Parcelable {
    private int Codi;



    public int getCodi() {
        return Codi;
    }

    public void setCodi(int codi) {
        Codi = codi;
    }

    private String Nom;
    private String Poblacio;
    private String Email;
    private String Cicle;
    private String DataNotificacio;
    private String Descripcio;
    private String Telefono;


    public OfertesTreball() {
    }

    public OfertesTreball(String nom, String poblacio, String email, String cicle, String dataNotificacio, String descripcio, String telefono) {

        Nom = nom;
        Poblacio = poblacio;
        Email = email;
        Cicle = cicle;
        DataNotificacio = dataNotificacio;
        Descripcio = descripcio;
        Telefono = telefono;
    }

    public OfertesTreball(String nom, String poblacio, String email, String cicle, String dataNotificacio, String descripcio) {
        Nom = nom;
        Poblacio = poblacio;
        Email = email;
        Cicle = cicle;
        DataNotificacio = dataNotificacio;
        Descripcio = descripcio;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }



    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getPoblacio() {
        return Poblacio;
    }

    public void setPoblacio(String poblacio) {
        Poblacio = poblacio;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getCicle() {
        return Cicle;
    }

    public void setCicle(String cicle) {
        Cicle = cicle;
    }

    public String getDataNotificacio() {
        return DataNotificacio;
    }

    public void setDataNotificacio(String dataNotificacio) {
        DataNotificacio = dataNotificacio;
    }

    public String getDescripcio() {
        return Descripcio;
    }

    public void setDescripcio(String descripcio) {
        Descripcio = descripcio;
    }


    protected OfertesTreball(Parcel in) {
        Codi = in.readInt();
        Nom = in.readString();
        Poblacio = in.readString();
        Email = in.readString();
        Cicle = in.readString();
        DataNotificacio = in.readString();
        Descripcio = in.readString();
        Telefono = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Codi);
        dest.writeString(Nom);
        dest.writeString(Poblacio);
        dest.writeString(Email);
        dest.writeString(Cicle);
        dest.writeString(DataNotificacio);
        dest.writeString(Descripcio);
        dest.writeString(Telefono);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<OfertesTreball> CREATOR = new Parcelable.Creator<OfertesTreball>() {
        @Override
        public OfertesTreball createFromParcel(Parcel in) {
            return new OfertesTreball(in);
        }

        @Override
        public OfertesTreball[] newArray(int size) {
            return new OfertesTreball[size];
        }
    };
}
