package com.jibin.notesappmvvmkotlin.View.Fragments

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.jibin.notesappmvvmkotlin.Model.Notes
import com.jibin.notesappmvvmkotlin.R
import com.jibin.notesappmvvmkotlin.ViewModel.NotesViewModel
import com.jibin.notesappmvvmkotlin.databinding.FragmentCreateNotesBinding
import java.util.Date

class CreateNotesFragment : Fragment() {

    lateinit var binding: FragmentCreateNotesBinding
    var priorityString: String = "1"
    val viewModel: NotesViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateNotesBinding.inflate(layoutInflater, container, false)
        val toolbar: Toolbar = binding.root.findViewById(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        setHasOptionsMenu(false)
        binding.priorityPurple.setImageResource(R.drawable.baseline_done_24)

        binding.priorityGreen.setOnClickListener {
            priorityString = "1"
            binding.priorityPurple.setImageResource(R.drawable.baseline_done_24)
            binding.priorityGreen.setImageResource(0)
            binding.priorityBlue.setImageResource(0)
        }

        binding.priorityGreen.setOnClickListener {
            priorityString = "2"
            binding.priorityGreen.setImageResource(R.drawable.baseline_done_24)
            binding.priorityPurple.setImageResource(0)
            binding.priorityBlue.setImageResource(0)
        }

        binding.priorityBlue.setOnClickListener {
            priorityString = "3"
            binding.priorityBlue.setImageResource(R.drawable.baseline_done_24)
            binding.priorityPurple.setImageResource(0)
            binding.priorityGreen.setImageResource(0)
        }
        binding.btnSaveNotes.setOnClickListener {
            createNotes(it)
        }
        return binding.root
    }

    fun createNotes(it: View?) {
        val title = binding.editTitle.text.toString()
        val subtitle = binding.editSubtitle.text.toString()
        val notes = binding.createNotes.text.toString()

        val sdf = SimpleDateFormat("MMMM d, YYYY")
        val notesDate = sdf.format(Date()).toString()
        val notesData = Notes(
            null,
            title = title,
            subTitle = subtitle,
            notes = notes,
            date = notesDate,
            priority = priorityString
        )
        viewModel.addNotes(notesData)
        Toast.makeText(requireContext(), "Notes added", Toast.LENGTH_SHORT).show()
        Navigation.findNavController(it!!).navigate(R.id.action_createNotesFragment_to_homeFragment)
    }
}