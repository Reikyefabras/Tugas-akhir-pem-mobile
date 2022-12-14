package com.D121201102.aplikasitask

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView

class TasksAdapter: RecyclerView.Adapter<TasksAdapter.TasksviewHolder>() {

    private var taskList = emptyList<Task>()
    private lateinit var tasksViewModel : TasksViewModel
    private var context: Context? = null

    class TasksviewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title:TextView = itemView.findViewById(R.id.title)
        val desc:TextView = itemView.findViewById(R.id.desc)
        val category:TextView = itemView.findViewById(R.id.category)
        val layout:ConstraintLayout = itemView.findViewById(R.id.task_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksviewHolder {
        context = parent.context
        tasksViewModel = ViewModelProvider(context as FragmentActivity)[TasksViewModel::class.java]
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_list, parent, false)
        return TasksviewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TasksviewHolder, position: Int) {
        val currentTask = taskList[position]

        holder.title.text = currentTask.title
        holder.desc.text = currentTask.desc
        holder.category.text = currentTask.category

        when(currentTask.category) {
            context!!.resources.getStringArray(R.array.kategori_list)[0] -> {
                holder.category.backgroundTintList = ColorStateList.valueOf(context!!.resources.getColor(R.color.red))
            }
            context!!.resources.getStringArray(R.array.kategori_list)[1] -> {
                holder.category.backgroundTintList = ColorStateList.valueOf(context!!.resources.getColor(R.color.yellow))
            }
            context!!.resources.getStringArray(R.array.kategori_list)[2] -> {
                holder.category.backgroundTintList = ColorStateList.valueOf(context!!.resources.getColor(R.color.blue))
            }
            context!!.resources.getStringArray(R.array.kategori_list)[3] -> {
                holder.category.backgroundTintList = ColorStateList.valueOf(context!!.resources.getColor(R.color.green))
            }
        }
        holder.itemView.setOnClickListener{
            val intent= Intent(holder.itemView.context,AddFragment::class.java)
            intent.putExtra("id",position)
            holder.itemView.context.startActivity(intent)
        }

        holder.layout.setOnClickListener{
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentTask)
            holder.itemView.findNavController().navigate(action)
        }


    }


    @SuppressLint("NotifyDataSetChanged")
    fun setData(task: List<Task>) {
        this.taskList = task
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return taskList.size
    }
}