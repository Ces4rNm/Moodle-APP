package com.proyect.moodle.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.proyect.moodle.R;
import com.proyect.moodle.SQLite.Models.materia_modelo;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class rv_horario_docente_adaptador extends RecyclerView.Adapter<rv_horario_docente_adaptador.ViewHolder> implements View.OnClickListener {
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

    public List<materia_modelo> semestre_Lista;

    public rv_horario_docente_adaptador(List<materia_modelo> semestre_Lista) {
        this.semestre_Lista = semestre_Lista;
    }

    @Override
    public rv_horario_docente_adaptador.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_materia,parent,false);
        rv_horario_docente_adaptador.ViewHolder viewHolder = new rv_horario_docente_adaptador.ViewHolder(view);

        view.setOnClickListener(this);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(rv_horario_docente_adaptador.ViewHolder holder, int position) {
        holder.nombre.setText(semestre_Lista.get(position).getNombre());
        holder.salon.setText(semestre_Lista.get(position).getSalon());
        holder.hora.setText(semestre_Lista.get(position).getHora());
    }

    @Override
    public int getItemCount() {
        return semestre_Lista.size();
    }
}
