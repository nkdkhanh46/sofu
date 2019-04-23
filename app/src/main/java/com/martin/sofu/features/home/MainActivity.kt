package com.martin.sofu.features.home

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.martin.sofu.R
import com.martin.sofu.application.MainApplication
import com.martin.sofu.base.BaseActivity
import com.martin.sofu.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    @Inject lateinit var viewModel: MainViewModel
    private var adapter: UserAdapter? = null
    private var scrollListener: EndlessScrollListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initView()
        observeChanges()
    }

    private fun initBinding() {
        (application as MainApplication?)?.appComponent?.inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        binding.viewModel = viewModel
        binding.executePendingBindings()
    }

    private fun initView() {
        vRefresh.isRefreshing = true
        setupUsersList()
    }

    private fun setupUsersList() {
        adapter = UserAdapter()
        adapter?.listener = object : UserAdapter.Listener {
            override fun onBookmarksListChanged(bookmarkedIds: ArrayList<Long>) {
                viewModel.updateBookmarksList(bookmarkedIds)
            }
        }
        val layoutManager = LinearLayoutManager(this)
        rvUsers.layoutManager = layoutManager
        rvUsers.adapter = adapter

        setupInfinityScrolling(layoutManager)
        setupSwipeToRefreshView()
    }

    private fun setupInfinityScrolling(layoutManager: LinearLayoutManager) {
        scrollListener = object : EndlessScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                viewModel.loadUsers(true)
            }
        }

        rvUsers.addOnScrollListener(scrollListener!!)
    }

    private fun setupSwipeToRefreshView() {
        vRefresh.setOnRefreshListener {
            viewModel.loadUsers()
        }
    }

    private fun observeChanges() {
        viewModel.users.observe(this, Observer {
            it?.let { users ->
                adapter?.swapData(users, viewModel.bookmarkedIds, viewModel.hasMore)
                if (viewModel.currentPage == 1) scrollListener?.resetState()
            }
        })

        viewModel.refreshCompleted.observe(this, Observer { success ->
            success?.let {
                vRefresh.isRefreshing = false
                viewModel.refreshCompleted.value = null
            }
        })
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadUsers()
    }
}
