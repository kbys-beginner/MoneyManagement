package com.example.moneymanagement

import android.app.AlertDialog
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson

class RecyclerViewAdapter(
    private val context: Context,
    private val resourceID: Int,
    private val items: MutableList<Data>
) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.titleTextView)
        val date = itemView.findViewById<TextView>(R.id.dateTextView)
        val price = itemView.findViewById<TextView>(R.id.priceTextView)
        val category = itemView.findViewById<TextView>(R.id.categoryTextView)
        val priority = itemView.findViewById<TextView>(R.id.priorityTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(resourceID, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sp = context.getSharedPreferences("INFO", MODE_PRIVATE)
        val gson = Gson()
        val item = items[position] as Data
        holder.setIsRecyclable(false)
        holder.title.setText(item.Title)
        holder.date.text = "${item.Year}-${item.Month}-${item.Day}"
        holder.price.text = "¥${item.Price}"
        holder.category.text = "${item.Category}"
        holder.priority.text = "重要度: ${item.Priority}"
    }
}