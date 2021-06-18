package com.example.catalogo.inicio;

import android.content.Context;

import com.example.catalogo.objetos.Perfil;

import io.realm.Realm;
import io.realm.RealmResults;

public class CatalogoContract {

    interface View {
        void mostrarPerfiles(RealmResults<Perfil> perfil);
        void noPerfiles();
        void mostrarMsj(String msj);
    }

    interface Presenter {
        void getPerfiles(Realm realm);
        void eliminarPerfil(Perfil perfil, Realm realm, Context context);
        void buscarPersona(String nombre,Realm realm,Context context);

    }
}
