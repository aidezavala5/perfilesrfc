package com.example.catalogo.agregarperfil;

import android.content.Context;

import com.example.catalogo.R;
import com.example.catalogo.utils.ValidadorRfc;

import io.realm.Realm;

public class ValidarRfcPresenter implements ValidarRfcContract.Presenter {

    private ValidarRfcContract.View mView;

    public ValidarRfcPresenter(ValidarRfcContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void validarRfc(String rfc, Realm realm, Context context) {
        int opcion = 0;
        opcion = ValidadorRfc.validarRfc(rfc,realm );
        switch (opcion){
            case 1:
                mView.vistaPersonaMoral();
                break;
            case 2:
                mView.vistaPersonaFisica();
                break;
            case 3:
                mView.msjError(context.getResources().getString(R.string.msj_rfc_existe));
                break;
            default:
                mView.msjError(context.getResources().getString(R.string.msj_rfc_correcto));
                break;
        }


    }
}
