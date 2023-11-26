package com.example.contactmanagerapp

import UserDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactmanagerapp.viewmodel.UserViewModel
import com.example.contactmanagerapp.viewmodel.UserViewModelFactory
import com.example.contactmanagerapp.databinding.ActivityMainBinding
import com.example.contactmanagerapp.room.User
import com.example.contactmanagerapp.room.UserRepository
import com.example.contactmanagerapp.views.CustomRecyclerViewAdapter
import android.util.Log


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var factory: UserViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        // DAO
        val dao = UserDatabase.getInstance(application).userDao()
        val repository = UserRepository(dao)
         factory = UserViewModelFactory(repository)

        viewModel = ViewModelProvider(this,factory,)[UserViewModel::class.java]
        binding.userViewModel = viewModel
        binding.lifecycleOwner = this
        initRecyclerView()

    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        displayUsersList()
    }

    private fun displayUsersList() {
        viewModel.users.observe(this) {
            binding.recyclerView.adapter =
                CustomRecyclerViewAdapter(it) { selectedUser: User -> onUserClick(selectedUser) }
        }
    }

    private fun onUserClick(selectedUser: User) {
        Toast.makeText(this,"SELECTED : ${selectedUser.name}",Toast.LENGTH_SHORT).show()
        viewModel.initUpdateAndDelete(selectedUser)
    }
}