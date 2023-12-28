package com.myapplication.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myapplication.EndlessRecyclerViewScrollListener
import com.myapplication.Result
import com.myapplication.UserAdapter
import com.myapplication.databinding.ActivityThirdBinding
import com.myapplication.view_model.DataViewModel
import com.myapplication.view_model.ViewModelFactory

class ThirdActivity : AppCompatActivity() {
    private lateinit var binding: ActivityThirdBinding
    private val viewModel by viewModels<DataViewModel> { ViewModelFactory.getInstance(this) }
    private lateinit var adapter: UserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Third screen"
        observeViewModel()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        adapter = UserAdapter(this)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        binding.rvUser.adapter = adapter
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)


        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshUsers()
        }

        binding.rvUser.addOnScrollListener(object :
            EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                viewModel.loadNextPage()
            }
        })
    }

    private fun observeViewModel() {
        viewModel.users.observe(this) { result ->
            when (result) {
                is Result.Success -> {
                    Log.d("MyApp", "Data received: ${result.data.data}")
                    adapter.submitList(result.data.data)
                    showEmptyState(result.data.data!!.isEmpty())
                    hideLoading()
                }

                is Result.Loading -> {
                    showLoading()
                }

                is Result.Error -> {
                    Log.e("MyApp", "Error: ${result.exception}")
                    hideLoading()
                }
            }
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE

        binding.swipeRefreshLayout.isRefreshing = false
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showEmptyState(isEmpty: Boolean) {
        binding.emptyState.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }
}