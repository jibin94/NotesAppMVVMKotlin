package com.jibin.notesappmvvmkotlin.View.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.jibin.notesappmvvmkotlin.databinding.FragmentHomeBinding
import com.jibin.notesappmvvmkotlin.Model.Notes
import com.jibin.notesappmvvmkotlin.R
import com.jibin.notesappmvvmkotlin.ViewModel.NotesViewModel
import com.jibin.notesappmvvmkotlin.View.Adapter.NotesAdapter


class HomeFragment : Fragment(), MenuProvider {

    lateinit var binding: FragmentHomeBinding
    val viewModel: NotesViewModel by viewModels()
    var oldNotesList = arrayListOf<Notes>()
    lateinit var adapter: NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        val toolbar: Toolbar = binding.root.findViewById(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        setHasOptionsMenu(false)
        viewModel.getNotes().observe(viewLifecycleOwner) { notesList ->
            oldNotesList = notesList as ArrayList<Notes>
            adapter = NotesAdapter(requireContext(), notesList)
            binding.recViewAllNotes.layoutManager = GridLayoutManager(requireContext(), 2)
            binding.recViewAllNotes.adapter = adapter
        }
        binding.btnAddNotes.setOnClickListener {
            findNavController(it).navigate(R.id.action_homeFragment_to_createNotesFragment)
        }

        binding.recViewAllNotes.layoutManager = GridLayoutManager(requireContext(), 2)

        binding.btnLow.setOnClickListener {
            viewModel.getLowNotes().observe(viewLifecycleOwner) { notesList ->
                oldNotesList = notesList as ArrayList<Notes>
                binding.recViewAllNotes.adapter = NotesAdapter(requireContext(), notesList)
            }
        }

        binding.btnHigh.setOnClickListener {
            viewModel.getHighNotes().observe(viewLifecycleOwner) { notesList ->
                oldNotesList = notesList as ArrayList<Notes>
                binding.recViewAllNotes.adapter = NotesAdapter(requireContext(), notesList)
            }
        }

        binding.btnMedium.setOnClickListener {
            viewModel.getMediumNotes().observe(viewLifecycleOwner) { notesList ->
                oldNotesList = notesList as ArrayList<Notes>
                binding.recViewAllNotes.adapter = NotesAdapter(requireContext(), notesList)
            }
        }

        binding.btnFilter.setOnClickListener {
            viewModel.getNotes().observe(viewLifecycleOwner) { notesList ->
                oldNotesList = notesList as ArrayList<Notes>
                binding.recViewAllNotes.adapter = NotesAdapter(requireContext(), notesList)
            }
        }

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        return binding.root
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.search_menu, menu)
        val item = menu.findItem(R.id.app_bar_search)


        val searchView = item.actionView as SearchView
        searchView.queryHint = "Enter Notes here...."
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                notesFiltering(newText)
                return true
            }
        })

    }

    private fun notesFiltering(p0: String?) {
        val newFilteredList = arrayListOf<Notes>()
        for (i in oldNotesList) {
            if (i.title.contains(p0!!) || i.subTitle.contains(p0!!)) {
                newFilteredList.add(i)
            }
        }
        adapter.filteringList(newFilteredList)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return true
    }

}