package com.example.loginapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.loginapplication.models.TodoItem
import com.example.loginapplication.databinding.ListitemBinding

class TodosAdapter(val ls : List<TodoItem>,val ctx:Context) : RecyclerView.Adapter<TodosAdapter.ViewHolder>() {
    class ViewHolder(val b : ListitemBinding): RecyclerView.ViewHolder(b.root)  {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ListitemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = ls[position]
        holder.b.apply {
            completed.text = task.completed.toString()
            id.text = task.id.toString()
            pos.text = (position+1).toString()
            title.text = task.title.toString()
        }
    }

    override fun getItemCount(): Int {
        return 20
    }
}