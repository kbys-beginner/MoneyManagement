package com.example.moneymanagement

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson

class RecyclerViewAdapter(
    private val context: Context,
    private val resourceID: Int,
    private val items: MutableList<Item>
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
        val item = items[position] as Item
        holder.setIsRecyclable(false)
        holder.title.text = item.Title
        holder.date.text = item.Date
        holder.price.text = "¥${item.Price}"
        holder.category.text = "${item.Category}"
        holder.priority.text = "優先度: ${item.Priority}"
    }
}