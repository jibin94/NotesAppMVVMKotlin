package com.jibin.notesappmvvmkotlin.View.Fragments

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.jibin.notesappmvvmkotlin.Model.Notes
import com.jibin.notesappmvvmkotlin.R
import com.jibin.notesappmvvmkotlin.ViewModel.NotesViewModel
import com.jibin.notesappmvvmkotlin.databinding.FragmentEditNotesBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.Date


class EditNotesFragment : Fragment(), MenuProvider {

    val oldNotes by navArgs<EditNotesFragmentArgs>()
    val viewModel: NotesViewModel by viewModels()
    lateinit var binding: FragmentEditNotesBinding
    var priorityString: String = "1"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditNotesBinding.inflate(layoutInflater, container, false)
        val toolbar: Toolbar = binding.root.findViewById(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        setHasOptionsMenu(false)
        binding.editTitle.setText(oldNotes.data.title)
        binding.editSubtitle.setText(oldNotes.data.subTitle)
        binding.editNotes.setText(oldNotes.data.notes)
        when (oldNotes.data.priority) {
            "1" -> {
                priorityString = "1"
                binding.priorityPurple.setImageResource(R.drawable.baseline_done_24)
                binding.priorityGreen.setImageResource(0)
                binding.priorityBlue.setImageResource(0)
            }

            "2" -> {
                priorityString = "2"
                binding.priorityGreen.setImageResource(R.drawable.baseline_done_24)
                binding.priorityPurple.setImageResource(0)
                binding.priorityBlue.setImageResource(0)
            }

            "3" -> {
                priorityString = "3"
                binding.priorityBlue.setImageResource(R.drawable.baseline_done_24)
                binding.priorityPurple.setImageResource(0)
                binding.priorityGreen.setImageResource(0)
            }
        }

        binding.priorityPurple.setOnClickListener {
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

        binding.btnEditSaveNotes.setOnClickListener {
            updateNotes(it)
        }

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return binding.root
    }

    fun updateNotes(it: View?) {
        oldNotes.data.title = binding.editTitle.text.toString()
        oldNotes.data.subTitle = binding.editSubtitle.text.toString()
        oldNotes.data.notes = binding.editNotes.text.toString()
        oldNotes.data.priority = priorityString
        viewModel.updateNotes(oldNotes.data)

        val title = binding.editTitle.text.toString()
        val subtitle = binding.editSubtitle.text.toString()
        val notes = binding.editNotes.text.toString()

        val d = Date()
        val sdf = SimpleDateFormat("MMMM d, YYYY")
        val notesDate = sdf.format(Date()).toString()
        val notesData = Notes(
            oldNotes.data.id,
            title = title,
            subTitle = subtitle,
            notes = notes,
            date = notesDate,
            priority = priorityString
        )
        viewModel.updateNotes(notesData)
        Toast.makeText(requireContext(), "Notes updated successfully", Toast.LENGTH_SHORT).show()
        Navigation.findNavController(it!!).navigate(R.id.action_editNotesFragment_to_homeFragment)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.menu_delete) {
            val bottomSheet: BottomSheetDialog =
                BottomSheetDialog(requireContext(), R.style.BottomSheetStyle)
            bottomSheet.setContentView(R.layout.dialog_delete)

            val btnYes = bottomSheet.findViewById<Button>(R.id.dialog_yes)
            val btnNo = bottomSheet.findViewById<Button>(R.id.dialog_no)
            btnYes?.setOnClickListener {
                viewModel.deleteNotes(oldNotes.data.id!!)
                bottomSheet.dismiss()
                Toast.makeText(requireContext(), "Notes deleted", Toast.LENGTH_SHORT).show()
                Navigation.findNavController(requireView()).navigate(R.id.action_editNotesFragment_to_homeFragment)
            }

            btnNo?.setOnClickListener {
                bottomSheet.dismiss()
            }
            bottomSheet.show()
        }
        return true
    }
}