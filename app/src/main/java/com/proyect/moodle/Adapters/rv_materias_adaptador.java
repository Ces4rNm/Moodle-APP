package com.proyect.moodle.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.proyect.moodle.R;
import com.proyect.moodle.SQLite.Models.materia_modelo;

import java.util.List;


import androidx.recyclerview.widget.RecyclerView;

public class rv_materias_adaptador extends RecyclerView.Adapter<rv_materias_adaptador.ViewHolder> implements View.OnClickListener {

    private View.OnClickListener listener;

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener!=null) {
            listener.onClick(view);
        }
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder {
        private TextView nombre, salon, hora;

        public ViewHolder(View itemView) {
            super(itemView);
            nombre = (TextView)itemView.findViewById(R.id.text_nombre);
            salon = (TextView)itemView.findViewById(R.id.text_salon);
            hora = (TextView)itemView.findViewById(R.id.text_semana);
        }

    }

    public List<materia_modelo> materia_Lista;

    public rv_materias_adaptador(List<materia_modelo> materia_Lista) {
        this.materia_Lista = materia_Lista;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_materia,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        view.setOnClickListener(this);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nombre.setText(materia_Lista.get(position).getNombre());
        holder.salon.setText(materia_Lista.get(position).getSalon());
        holder.hora.setText(materia_Lista.get(position).getHora());
    }

    @Override
    public int getItemCount() {
        return materia_Lista.size();
    }
}
