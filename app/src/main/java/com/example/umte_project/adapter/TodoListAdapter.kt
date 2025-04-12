package com.example.umte_project.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.umte_project.data.model.TodoListItem


class TodoListAdapter(
    private val onItemClicked: (TodoListItem) -> Unit
) : ListAdapter<TodoListItem, TodoListAdapter.TodoListViewHolder>(DiffCallback) {

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<TodoListItem>() {
            override fun areItemsTheSame(oldItem: TodoListItem, newItem: TodoListItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TodoListItem, newItem: TodoListItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder {
        val binding = ItemTodoListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TodoListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
        holder.itemView.setOnClickListener { onItemClicked(currentItem) }
    }

    inner class TodoListViewHolder(private val binding: ItemTodoListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TodoListItem) {
            binding.todoListTitle.text = item.title
            binding.todoListDescription.text = item.description ?: ""
            binding.checkbox.isChecked = item.isCompleted
        }
    }
}