package com.anurag.tracer.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anurag.tracer.R

class ScannerAdapter(private val data: List<String>) : RecyclerView.Adapter<ScannerAdapter.ViewHolder>() {
    class ViewHolder(v: View) : RecyclerView.ViewHolder(v){
        val textView : TextView = v.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.contact_holder, parent, false)
        return ViewHolder(textView)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = data[position]
    }
}