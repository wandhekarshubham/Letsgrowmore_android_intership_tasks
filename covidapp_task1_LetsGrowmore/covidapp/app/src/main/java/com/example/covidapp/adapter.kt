package com.example.apiapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.covidapp.R

class adapter(var context:Context):
    RecyclerView.Adapter<adapter.viewholder>() {
    private val listitem:ArrayList<Listitems>
    init {
        listitem=ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        var layout=LayoutInflater.from(context).inflate(R.layout.custom_layout,parent,false)
        return viewholder(layout)
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        val itemposition=listitem[position]
        holder.destrict.text=itemposition.districtname
        holder.active.text=itemposition.active
        holder.conformed.text= itemposition.confirmed.toString()
        holder.deceased.text=itemposition.deceased
        holder.recovered.text=itemposition.recovered
        holder.conformed2.text= itemposition.confirmed2.toString()
        holder.deceased2.text=itemposition.deceased2
        holder.recovered2.text=itemposition.recovered2


    }

    override fun getItemCount(): Int {
        return listitem.size
    }
    fun update(updated_data:ArrayList<Listitems>) {
        listitem.clear()
        listitem.addAll(updated_data)
        notifyDataSetChanged()
    }


    class viewholder(itemview:View):RecyclerView.ViewHolder(itemview) {
        val destrict:TextView
        val active:TextView
        val recovered:TextView
        val recovered2:TextView
        val conformed:TextView
        val conformed2:TextView
        val deceased:TextView
        val deceased2:TextView
        init {
            destrict=itemview.findViewById(R.id.tv_title_location)

            active=itemview.findViewById(R.id.tv_active_layout)
            conformed=itemview.findViewById(R.id.tv_conformed_layout)
            deceased=itemview.findViewById(R.id.tv_deceased_layout)
            recovered=itemview.findViewById(R.id.tv_recovered_layout)

            conformed2=itemview.findViewById(R.id.tv_conformed_layout2)
            deceased2=itemview.findViewById(R.id.tv_deceased_layout2)
            recovered2=itemview.findViewById(R.id.tv_recovered_layout2)


        }


    }
}