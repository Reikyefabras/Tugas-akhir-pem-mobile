package com.D121201102.aplikasitask

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.D121201102.aplikasitask.databinding.FragmentListBinding

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var mTasksViewModel : TasksViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root

        val adapter = TasksAdapter()
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)

        mTasksViewModel = ViewModelProvider(this)[TasksViewModel::class.java]
        mTasksViewModel.readAllTasks.observe(viewLifecycleOwner) { task ->
            adapter.setData(task)
        }

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        binding.menuDelete.setOnClickListener {
            deleteAllTasks()
        }


        return view
    }


    private fun deleteAllTasks() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Ya") {_, _ ->
            mTasksViewModel.deleteAllTasks()
            Toast.makeText(
                requireContext(),
                "Task Berhasil Dihapus!",
                Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("Tidak") {_, _ -> }
        builder.setTitle("Hapus Semua Task?")
        builder.setMessage("Yakin ingin menghapus semua task?")
        builder.create().show()
    }




}