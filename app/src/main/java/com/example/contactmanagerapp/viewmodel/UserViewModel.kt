package com.example.contactmanagerapp.viewmodel

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactmanagerapp.room.User
import com.example.contactmanagerapp.room.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository):ViewModel(), Observable {
    val users = userRepository.users
    private var isUpdateOrDelete = false
    private lateinit var userToUpdateOrDelete: User

    @Bindable
    val inputName= MutableLiveData<String?>()
    @Bindable
    val inputEmail= MutableLiveData<String?>()
    @Bindable
    var saveOrUpdateBtnText = MutableLiveData<String>()
    @Bindable
    var resetOrDeleteBtnText = MutableLiveData<String>()
    init {
        saveOrUpdateBtnText.value = "Save"
        resetOrDeleteBtnText.value = "Reset All"
    }

    fun saveOrUpdate(){
        if(isUpdateOrDelete){
            // UPDATE TO DATABASE [UPDATE]
            userToUpdateOrDelete.name = inputName.value!!
            userToUpdateOrDelete.email = inputEmail.value!!
            updateUser(userToUpdateOrDelete)
        }else{
            // SAVE TO DATABASE [INSERT]
            val name = inputName.value!!
            val email = inputEmail.value!!
            insertUser(User(0,name, email))
            inputName.value = null
            inputEmail.value = null
        }

    }
    fun resetOrDelete(){
       if(isUpdateOrDelete){
           deleteUser(userToUpdateOrDelete)
       }else{
           resetAll()
       }
    }
    private fun insertUser(user: User) = viewModelScope.launch {
        userRepository.insertUser(user)
    }

    private fun resetAll() = viewModelScope.launch {
        userRepository.deleteAll()
    }
    fun initUpdateAndDelete(user:User){
        inputEmail.value = user.email
        inputName.value = user.name
        isUpdateOrDelete = true
        userToUpdateOrDelete = user
        saveOrUpdateBtnText.value = "Update"
        resetOrDeleteBtnText.value = "Delete"

    }
    private fun updateUser(user:User) = viewModelScope.launch {
        userRepository.updateUser(user)
        resetAfterUpdateOrDelete()

    }
    private fun resetAfterUpdateOrDelete(){
        // RESET BTN TEXT AND ONCLICK
        inputName.value = null
        inputEmail.value = null
        isUpdateOrDelete = false
        saveOrUpdateBtnText.value = "Save"
        resetOrDeleteBtnText.value = "Reset All"
    }
    private fun deleteUser(user:User) = viewModelScope.launch {
        userRepository.deleteUser(user)
        resetAfterUpdateOrDelete()
    }
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        TODO("Not yet implemented")
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        TODO("Not yet implemented")
    }
}