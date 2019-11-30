package com.martin.sofu.features.home

import android.content.Intent
import android.os.Bundle
import android.util.LongSparseArray
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.martin.sofu.R
import com.martin.sofu.base.BaseActivity
import com.martin.sofu.customviews.EndlessScrollListener
import com.martin.sofu.databinding.ActivityMainBinding
import com.martin.sofu.features.reputationhistory.ReputationActivity
import com.martin.sofu.model.User
import com.martin.sofu.utils.Constants
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()
    private var adapter: UserAdapter? = null
    private var scrollListener: EndlessScrollListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initView()
        observeChanges()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.executePendingBindings()
    }

    private fun initView() {
        setTitle(R.string.stack_over_flow_users)
        vRefresh.isRefreshing = true
        setupUsersList()
    }

    private fun setupUsersList() {
        adapter = UserAdapter()
        adapter?.listener = object : UserAdapter.Listener {
            override fun onUserSelected(user: User) {
                viewModel.setCurrentUser(user)
                openUserReputationsScreen(user.userId)
            }

            override fun onBookmarksChanged(bookmarks: LongSparseArray<User>) {
                viewModel.updateBookmarksList(bookmarks)
            }
        }
        val layoutManager = LinearLayoutManager(this)
        rvUsers.layoutManager = layoutManager
        rvUsers.adapter = adapter

        setupInfinityScrolling(layoutManager)
        setupSwipeToRefreshView()
    }

    private fun openUserReputationsScreen(userId: Long) {
        val intent = Intent(this, ReputationActivity::class.java)
        intent.putExtra(Constants.INTEN_NAME_USER_ID, userId)
        startActivity(intent)
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
                adapter?.swapData(users, viewModel.bookmarks, viewModel.hasMore, viewModel.showingAll)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item == null) return super.onOptionsItemSelected(item)

        when (item.itemId) {
            R.id.action_filter -> showFilterDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showFilterDialog() {
        val dialog = UserFilterDialog.newInstance(viewModel.showingAll)
        dialog.listener = object : UserFilterDialog.Listener {
            override fun onFilterSelected(showAll: Boolean) {
                viewModel.updateFilterOption(showAll)
                adapter?.setFilter(showAll)
            }
        }
        dialog.show(supportFragmentManager, UserFilterDialog.TAG)
    }

    override fun onStop() {
        super.onStop()
        viewModel.saveBookmarks()
    }
}
