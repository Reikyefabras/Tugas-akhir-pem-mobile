package com.D121201102.aplikasitask

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.D121201102.aplikasitask.databinding.FragmentAddTaskBinding
import kotlinx.coroutines.launch


class AddFragment : Fragment() {

    private var _binding: FragmentAddTaskBinding? = null
    private val binding get() = _binding!!

    lateinit var taskCategory: ArrayAdapter<CharSequence>
    private lateinit var mTasksViewModel : TasksViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        val view = binding.root

        mTasksViewModel = ViewModelProvider(this)[TasksViewModel::class.java]
        setDropDown()
        binding.apply {
            back.setOnClickListener {
                findNavController().navigate(R.id.action_addFragment_to_listFragment)
            }
            addButton.setOnClickListener{
                insertDataToDatabase()
            }
        }

        return view
    }

    private fun setDropDown() {
        taskCategory = ArrayAdapter.createFromResource(requireContext(),R.array.kategori_list, android.R.layout.simple_list_item_1)
        binding.addCategory.setAdapter(taskCategory)
    }
    private fun insertDataToDatabase() {
        binding.apply {
            val title = addTitle.text.toString()
            val desc = addDesc.text.toString()
            val category = addCategory.text.toString()

            if(inputCheck(title, desc, category)) {
                lifecycleScope.launch {
                    val task = Task(0, title, desc, category)
                    mTasksViewModel.insertTask(task)
                    Toast.makeText(requireContext(), "Berhasil Ditambahkan!", Toast.LENGTH_LONG).show()

                    findNavController().navigate(R.id.action_addFragment_to_listFragment)
                }
            }else{
                Toast.makeText(requireContext(), "Tolong Lengkapi Data!", Toast.LENGTH_LONG).show()
            }


        }
    }

    private fun inputCheck(title: String, desc: String, category: String): Boolean {
        return !(TextUtils.isEmpty(title) || (TextUtils.isEmpty(desc) || (TextUtils.isEmpty(category))))

    }


}