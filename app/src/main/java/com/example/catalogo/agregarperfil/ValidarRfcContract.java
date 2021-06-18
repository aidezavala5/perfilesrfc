package com.example.catalogo.agregarperfil;

import android.content.Context;

import io.realm.Realm;

public class ValidarRfcContract {

    interface View {
        void vistaPersonaMoral();
        void vistaPersonaFisica();
        void msjError(String msj);
    }

    interface Presenter {
        void validarRfc(String rfc, Realm realm,Context context);

    }
}
