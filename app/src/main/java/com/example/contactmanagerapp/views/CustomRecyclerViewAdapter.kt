package com.example.contactmanagerapp.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.contactmanagerapp.R
import com.example.contactmanagerapp.databinding.SingleContactCardItemBinding
import com.example.contactmanagerapp.room.User

class CustomRecyclerViewAdapter(private val users: List<User>, private val onClick:  (User)-> Unit):RecyclerView.Adapter<CustomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
       val inflater = LayoutInflater.from(parent.context)
        val binding: SingleContactCardItemBinding = DataBindingUtil.inflate(inflater, R.layout.single_contact_card_item,parent,false)
        return CustomViewHolder(binding)

    }

    override fun getItemCount(): Int {
       return users.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(users[position],onClick)
    }
}

class CustomViewHolder(private val binding: SingleContactCardItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(user: User, onClick: (User) -> Unit){
        binding.nameTextView.text = user.name
        binding.emailTextView.text = user.email
        binding.listItemLayout.setOnClickListener {
            onClick(user)
        }
    }
}