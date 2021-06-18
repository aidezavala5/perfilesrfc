package com.example.catalogo.domicilio;

import android.content.Context;

import com.example.catalogo.objetos.Perfil;

import io.realm.Realm;
import io.realm.RealmResults;

public class DomicilioContract {


    interface View {

        void msj(String msj);
        void vistaPrincipal();
    }

    interface Presenter {
        void guardarDatos(Perfil perfil,Realm realm, String modo,Context context);
        boolean validarDatos(String pais, String estado, String municipio, String ciudad, String codgPos, String col,
                          String calle,Context context);

    }
}



