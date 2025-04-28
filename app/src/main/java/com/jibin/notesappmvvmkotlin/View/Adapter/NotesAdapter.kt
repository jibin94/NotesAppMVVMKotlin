package com.jibin.notesappmvvmkotlin.View.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.jibin.notesappmvvmkotlin.Model.Notes
import com.jibin.notesappmvvmkotlin.R
import com.jibin.notesappmvvmkotlin.databinding.ItemNotesBinding
import com.jibin.notesappmvvmkotlin.View.Fragments.HomeFragmentDirections
import java.util.ArrayList

class NotesAdapter(val requireContext: Context, var notesList: List<Notes>) :
    RecyclerView.Adapter<NotesAdapter.notesViewHolder>() {

    fun filteringList(newFilteredList: ArrayList<Notes>) {
        notesList = newFilteredList
        notifyDataSetChanged()
    }

    class notesViewHolder(val binding: ItemNotesBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): notesViewHolder {
        return notesViewHolder(
            ItemNotesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = notesList.size

    override fun onBindViewHolder(holder: notesViewHolder, position: Int) {
        val data = notesList[position]
        holder.binding.notesTitle.text = data.title
        holder.binding.notesSubtitle.text = data.subTitle
        holder.binding.notesDate.text = data.date

        when (data.priority) {
            "1" -> {
                holder.binding.viewPriority.setBackgroundResource(R.drawable.purple_dot)
            }

            "2" -> {
                holder.binding.viewPriority.setBackgroundResource(R.drawable.green_dot)
            }

            "3" -> {
                holder.binding.viewPriority.setBackgroundResource(R.drawable.blue_dot)
            }
        }

        holder.binding.root.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToEditNotesFragment(data)
            Navigation.findNavController(it).navigate(action)
        }
    }
}