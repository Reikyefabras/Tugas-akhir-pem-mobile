package com.D121201102.aplikasitask

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.D121201102.aplikasitask.databinding.FragmentUpdateBinding
import kotlinx.coroutines.launch

class UpdateFragment : Fragment() {

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<UpdateFragmentArgs>()
    private lateinit var mTasksViewModel : TasksViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        val view = binding.root

        mTasksViewModel = ViewModelProvider(this)[TasksViewModel::class.java]
        binding.apply {
            updateTitle.setText(args.currentTask.title)
            updateDesc.setText(args.currentTask.desc)
            updateCategory.setText(args.currentTask.category)
            updateCategory.setAdapter(ArrayAdapter.createFromResource(requireContext(),R.array.kategori_list, android.R.layout.simple_list_item_1))

            back.setOnClickListener {
                findNavController().navigate(R.id.action_updateFragment_to_listFragment)
            }
            updateButton.setOnClickListener{
                updateTask()
            }

            menuDelete.setOnClickListener {
                deleteTask()
            }

        }
        // Inflate the layout for this fragment
        return view
    }

    private fun updateTask() {
        binding.apply {
            val title = updateTitle.text.toString()
            val desc = updateDesc.text.toString()
            val category = updateCategory.text.toString()

            if(inputCheck(title, desc, category)) {
                lifecycleScope.launch {
                    val updated = Task(args.currentTask.id, title, desc, category)
                    mTasksViewModel.updateTask(updated)
                    Toast.makeText(requireContext(), "Update Berhasil!!", Toast.LENGTH_LONG).show()

                    findNavController().navigate(R.id.action_updateFragment_to_listFragment)
                }
            }else{
                Toast.makeText(requireContext(), "Tolong Lengkapi Data!!", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun inputCheck(title: String, desc: String, category: String): Boolean {
        return !(TextUtils.isEmpty(title) || (TextUtils.isEmpty(desc) || (TextUtils.isEmpty(category))))

    }


    private fun deleteTask() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Ya") {_, _ ->
            mTasksViewModel.deleteTask(args.currentTask)
            Toast.makeText(
                requireContext(),
                "Berhasil Dihapus!",
                Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("Tidak") {_, _ -> }
        builder.setTitle("Hapus Task?")
        builder.setMessage("Yakin ingin menghapus task ini?")
        builder.create().show()
    }
}