package com.myapplication.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapplication.Result
import com.myapplication.api.response.DataResponse
import com.myapplication.repo.UserRepository
import kotlinx.coroutines.launch

class DataViewModel(private val repository: UserRepository) : ViewModel() {
    private val _users = MutableLiveData<Result<DataResponse>>()
    val users: LiveData<Result<DataResponse>> get() = _users

    private var currentPage = 1
    private val perPage = 10

    init {
        loadUsers()
    }

    fun refreshUsers() {
        currentPage = 1
        loadUsers()
    }

    fun loadNextPage() {
        if (_users.value !is Result.Loading) {
            currentPage++
            loadUsers()
        }
    }

    private fun loadUsers() {
        viewModelScope.launch {
            _users.value = Result.Loading
            _users.value = repository.getUsers(currentPage, perPage)
        }
    }
}
