package com.example.appwhere.adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.example.appwhere.R;
import com.example.appwhere.models.POJO_sucursales;

import java.util.ArrayList;

public class AdaptadorSucursal extends RecyclerView.Adapter<AdaptadorSucursal.ViewHolder> {

    private ArrayList<POJO_sucursales> dataset;

    private Context context;

    public AdaptadorSucursal(ArrayList<POJO_sucursales> dataset, Context context) {
        this.dataset = dataset;
        this.context = context;
    }

    @NonNull
    @Override
    public AdaptadorSucursal.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adaptador_sucursales, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorSucursal.ViewHolder holder, int position) {

        POJO_sucursales p = dataset.get(position);
        holder.adapta_txt_nombre.setText(p.getMerchantName());
        holder.adapta_txt_direccion.setText(p.getMerchantAddress());
        holder.adapta_txt_telefono.setText(p.getMerchantTelephone());

        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(50f);
        circularProgressDrawable.start();

        Glide.with(context)
                .load(context.getResources().getDrawable(R.drawable.logo_appwhere_transparente_300))
                .centerCrop()
                .placeholder(circularProgressDrawable)
                .into(holder.adapta_img);

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView adapta_img;
        TextView adapta_txt_nombre;
        TextView adapta_txt_direccion;
        TextView adapta_txt_telefono;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            adapta_img = itemView.findViewById(R.id.adapta_img);
            adapta_txt_nombre = itemView.findViewById(R.id.adapta_txt_nombre);
            adapta_txt_direccion = itemView.findViewById(R.id.adapta_txt_direccion);
            adapta_txt_telefono = itemView.findViewById(R.id.adapta_txt_telefono);
        }
    }
}
