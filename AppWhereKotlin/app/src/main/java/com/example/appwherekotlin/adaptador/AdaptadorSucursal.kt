package com.example.appwherekotlin.adaptador

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.appwherekotlin.R
import com.example.appwherekotlin.models.POJO_sucursales
import java.util.*

class AdaptadorSucursal(dataset: ArrayList<POJO_sucursales>, context: Context) :
    RecyclerView.Adapter<AdaptadorSucursal.ViewHolder>() {

    private val dataset: ArrayList<POJO_sucursales> = dataset
    private val context: Context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.adaptador_sucursales, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val p: POJO_sucursales = dataset[position]
        holder.adapta_txt_nombre.text = p.merchantName
        holder.adapta_txt_direccion.text = p.merchantAddress
        holder.adapta_txt_telefono.text = p.merchantTelephone

        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 50f
        circularProgressDrawable.start()
        Glide.with(context)
            .load(R.drawable.logo_appwhere_transparente_300)
            .centerCrop()
            .placeholder(circularProgressDrawable)
            .into(holder.adapta_img)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var adapta_img: ImageView = itemView.findViewById(R.id.adapta_img)
        var adapta_txt_nombre: TextView = itemView.findViewById(R.id.adapta_txt_nombre)
        var adapta_txt_direccion: TextView = itemView.findViewById(R.id.adapta_txt_direccion)
        var adapta_txt_telefono: TextView = itemView.findViewById(R.id.adapta_txt_telefono)
    }

}