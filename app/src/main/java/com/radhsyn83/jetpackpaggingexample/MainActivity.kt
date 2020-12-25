package com.radhsyn83.jetpackpaggingexample

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: NewsListViewModel
    private lateinit var newsListAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(NewsListViewModel::class.java)
        initAdapter()
        initState()
    }

    private fun initAdapter() {
        newsListAdapter = NewsAdapter { viewModel.retry() }
        recycler_view.adapter = newsListAdapter
        recycler_view.layoutManager = GridLayoutManager(this, 1, RecyclerView.HORIZONTAL, false)

        //Horizontal Setup
        recycler_view.addItemDecoration(PaddingItemDecoration(220))

        viewModel.newsList.observe(this,
            {
                newsListAdapter.submitList(it)
            })
    }

    private fun initState() {
        txt_error.setOnClickListener { viewModel.retry() }
        viewModel.getState().observe(this, { state ->
            progress_bar.visibility =
                if (viewModel.listIsEmpty() && state == State.LOADING) VISIBLE else GONE
            txt_error.visibility =
                if (viewModel.listIsEmpty() && state == State.ERROR) VISIBLE else GONE
            if (!viewModel.listIsEmpty()) {
                newsListAdapter.setState(state ?: State.DONE)
            }
        })
    }

}