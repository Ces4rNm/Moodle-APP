package com.proyect.moodle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class rv_semanas_adaptador extends RecyclerView.Adapter<rv_semanas_adaptador.ViewHolder> implements View.OnClickListener {

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
        private TextView nombre;

        public ViewHolder(View itemView) {
            super(itemView);
            nombre = (TextView)itemView.findViewById(R.id.text_semana);
        }
    }

    public List<semana_modelo> semana_Lista;

    public rv_semanas_adaptador(List<semana_modelo> semana_Lista) {
        this.semana_Lista = semana_Lista;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_semana,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        view.setOnClickListener(this);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nombre.setText(semana_Lista.get(position).getNombre());
    }

    @Override
    public int getItemCount() {
        return semana_Lista.size();
    }
}