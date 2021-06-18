package com.example.catalogo.inicio;

import android.content.Context;

import com.example.catalogo.R;
import com.example.catalogo.objetos.Perfil;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class CatalogoPresenter implements CatalogoContract.Presenter {


    private CatalogoContract.View mView;

    public CatalogoPresenter(CatalogoContract.View mView) {
        this.mView = mView;
    }


    @Override
    public void getPerfiles(Realm realm) {
        RealmResults<Perfil> perfil = realm.where(Perfil.class).findAll();
        if(perfil.size()==0){
            mView.noPerfiles();
        }else{
            mView.mostrarPerfiles(perfil);
        }

    }

    @Override
    public void eliminarPerfil(Perfil perfil, Realm realm, Context context) {
        realm.beginTransaction();
        perfil.deleteFromRealm();
        realm.commitTransaction();
        mView.mostrarMsj(context.getResources().getString( R.string.msj_perfil_eliminado));
    }

    @Override
    public void buscarPersona(String nombre, Realm realm,Context context) {
        RealmResults<Perfil> mPerfil = realm.where(Perfil.class).contains(CatalogoActivity.TAG_NOMBRE, nombre, Case.INSENSITIVE)
                .findAll().sort(CatalogoActivity.TAG_NOMBRE, Sort.DESCENDING);
        if(mPerfil.size()==0){
            mView.mostrarMsj(context.getResources().getString(R.string.no_coincidencias));
        }else{
            mView.mostrarPerfiles(mPerfil);
        }

    }
}
