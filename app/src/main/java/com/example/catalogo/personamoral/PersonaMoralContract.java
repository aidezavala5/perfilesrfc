package com.example.catalogo.personamoral;

import android.content.Context;

public class PersonaMoralContract {

    interface View {

        void mostrarVistaDomicilio();
        void msjError(String msj);
    }

    interface Presenter {

        void validarDatos(String nombre, String tel, String correo, Context context);

    }
}
