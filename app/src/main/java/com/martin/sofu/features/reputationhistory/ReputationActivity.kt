package com.martin.sofu.features.reputationhistory

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.martin.sofu.R
import com.martin.sofu.application.MainApplication
import com.martin.sofu.base.BaseActivity
import com.martin.sofu.customviews.EndlessScrollListener
import com.martin.sofu.databinding.ActivityReputationHistoryBinding
import com.martin.sofu.utils.Constants
import kotlinx.android.synthetic.main.activity_reputation_history.*
import javax.inject.Inject

class ReputationActivity : BaseActivity() {

    private lateinit var binding: ActivityReputationHistoryBinding
    @Inject lateinit var viewModel: ReputationViewModel
    private var adapter: ReputationAdapter? = null
    private var scrollListener: EndlessScrollListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initView()
        observeChanges()
    }

    private fun initBinding() {
        (application as MainApplication?)?.appComponent?.inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reputation_history)
        viewModel = ViewModelProvider(this, viewModelFactory)[ReputationViewModel::class.java]
        binding.viewModel = viewModel
        viewModel.userId = intent.getLongExtra(Constants.INTEN_NAME_USER_ID, 0L)
        binding.executePendingBindings()
    }

    private fun initView() {
        setTitle(R.string.user_reputation_history)
        vRefresh.isRefreshing = true
        setupUsersList()
    }

    private fun setupUsersList() {
        adapter = ReputationAdapter()
        val layoutManager = LinearLayoutManager(this)
        rvReputations.layoutManager = layoutManager
        rvReputations.adapter = adapter

        setupInfinityScrolling(layoutManager)
        setupSwipeToRefreshView()
    }

    private fun setupInfinityScrolling(layoutManager: LinearLayoutManager) {
        scrollListener = object : EndlessScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                viewModel.loadReputations(true)
            }
        }

        rvReputations.addOnScrollListener(scrollListener!!)
    }

    private fun setupSwipeToRefreshView() {
        vRefresh.setOnRefreshListener {
            viewModel.loadReputations()
        }
    }

    private fun observeChanges() {
        viewModel.reputations.observe(this, Observer {
            it?.let { reputations ->
                adapter?.swapData(reputations, viewModel.hasMore)
                if (viewModel.currentPage == 1) scrollListener?.resetState()
            }
        })

        viewModel.refreshCompleted.observe(this, Observer { success ->
            success?.let {
                vRefresh.isRefreshing = false
                viewModel.refreshCompleted.value = null
            }
        })

        viewModel.profileImage.observe(this, Observer { success ->
            success?.let {
                Glide.with(this@ReputationActivity).load(it).into(ivAvatar)
            }
        })
    }
}