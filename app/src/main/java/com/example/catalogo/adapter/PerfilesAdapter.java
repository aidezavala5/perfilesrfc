package com.example.catalogo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catalogo.R;
import com.example.catalogo.inicio.CatalogoActivity;
import com.example.catalogo.objetos.Perfil;

import io.realm.RealmResults;

public class PerfilesAdapter extends RecyclerView.Adapter<PerfilesAdapter.PerfilesViewHolder> {
    private RealmResults<Perfil> perfil;
    private static PerfilesAdapter.OnItemClickListener itemClickListener;
    private PerfilesAdapter.OnItemClickListener listener;
    public Context contextl;

    public PerfilesAdapter(RealmResults<Perfil> perfil, PerfilesAdapter.OnItemClickListener listener, Context context) {
        this.perfil = perfil;
        this.listener = listener;
        this.contextl = context;
    }


    @NonNull
    @Override
    public PerfilesAdapter.PerfilesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_perfil, parent, false);
        PerfilesAdapter.PerfilesViewHolder myViewHolder = new PerfilesAdapter.PerfilesViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PerfilesAdapter.PerfilesViewHolder holder, int position) {
        String nombre = perfil.get(position).getNombre().toString();
        holder.nombre.setText(nombre);
        holder.nombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popup = new PopupMenu(v.getContext(), v);
                popup.getMenuInflater().inflate(R.menu.menu_item, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit:
                                listener.onItemClickListener(perfil.get(position), CatalogoActivity.TAG_EDITAR);
                                break;
                            case R.id.delete:
                                listener.onItemClickListener(perfil.get(position), CatalogoActivity.TAG_ELIMINAR);
                                break;
                            case R.id.show:
                                listener.onItemClickListener(perfil.get(position), CatalogoActivity.TAG_VER);
                                break;
                        }
                        return false;

                    }
                });
                popup.show();

            }
        });

    }

    @Override
    public int getItemCount() {

        return perfil.size();
    }

    public static class PerfilesViewHolder extends RecyclerView.ViewHolder {

        TextView nombre;


        public PerfilesViewHolder(View v) {
            super(v);
            this.nombre = itemView.findViewById(R.id.edittext_nombre_perfil);

        }
    }

    public interface OnItemClickListener {
        /**
         * @param perfil
         */

        public void onItemClickListener(Perfil perfil, String tipo);
    }


    public void setOnItemClickListener(PerfilesAdapter.OnItemClickListener itemClickListene) {
        PerfilesAdapter.itemClickListener = itemClickListener;
    }
}
