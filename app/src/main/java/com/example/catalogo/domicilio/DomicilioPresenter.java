package com.example.catalogo.domicilio;

import android.content.Context;

import com.example.catalogo.R;
import com.example.catalogo.inicio.CatalogoActivity;
import com.example.catalogo.inicio.CatalogoContract;
import com.example.catalogo.objetos.Perfil;

import io.realm.Realm;

public class DomicilioPresenter implements DomicilioContract.Presenter{

    private DomicilioContract.View mView;
    public static final String TAG_VER = "ver";

    public DomicilioPresenter(DomicilioContract.View mView) {
        this.mView = mView;
    }
    @Override
    public void guardarDatos(Perfil perfil, Realm realm, String modo, Context context) {
        if(TAG_VER.equals(modo)){
            mView.vistaPrincipal();
        }else{
            realm.beginTransaction();

            realm.copyToRealmOrUpdate(perfil);
            realm.commitTransaction();
            mView.msj(context.getResources().getString(R.string.msj_guardado));
            mView.vistaPrincipal();
        }
    }

    @Override
    public boolean validarDatos(String pais, String estado, String municipio, String ciudad, String codgPos, String col, String calle, Context context) {
       boolean datos=false;
        if(!pais.equals("")  && !estado.equals("") && !municipio.equals("") && !ciudad.equals("") && !codgPos.equals("")
                && !col.equals("")&& !calle.equals("")) {
                datos=true;

        }else{
            mView.msj(context.getResources().getString(R.string.llenar_campos));
        }
        return  datos;

    }
}
