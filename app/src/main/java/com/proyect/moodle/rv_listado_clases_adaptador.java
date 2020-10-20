package com.proyect.moodle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class rv_listado_clases_adaptador extends RecyclerView.Adapter<rv_listado_clases_adaptador.ViewHolder> implements View.OnClickListener {
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
        private TextView id_clase, nombre_clase, creditos, nombre_docente, asistencia, descripcion;

        public ViewHolder(View itemView) {
            super(itemView);
            id_clase = (TextView)itemView.findViewById(R.id.text_id_clase);
            nombre_clase = (TextView)itemView.findViewById(R.id.text_nombre_clase);
            creditos = (TextView)itemView.findViewById(R.id.text_creditos);
            nombre_docente = (TextView)itemView.findViewById(R.id.text_nombre_docente);
            asistencia = (TextView)itemView.findViewById(R.id.text_asistencia);
            descripcion = (TextView)itemView.findViewById(R.id.text_descripcion);
        }
    }

    public List<clase_modelo> semestre_Lista;

    public rv_listado_clases_adaptador(List<clase_modelo> semestre_Lista) {
        this.semestre_Lista = semestre_Lista;
    }

    @Override
    public rv_listado_clases_adaptador.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_clases,parent,false);
        rv_listado_clases_adaptador.ViewHolder viewHolder = new rv_listado_clases_adaptador.ViewHolder(view);

        view.setOnClickListener(this);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(rv_listado_clases_adaptador.ViewHolder holder, int position) {
        holder.id_clase.setText(semestre_Lista.get(position).getId_clase());
        holder.nombre_clase.setText(semestre_Lista.get(position).getNombre_clase());
        holder.creditos.setText(semestre_Lista.get(position).getCreditos());
        holder.nombre_docente.setText(semestre_Lista.get(position).getNombre_docente());
        holder.asistencia.setText(semestre_Lista.get(position).getAsistencia());
        holder.descripcion.setText(semestre_Lista.get(position).getDescripcion());
    }

    @Override
    public int getItemCount() {
        return semestre_Lista.size();
    }
}
