package com.martin.sofu.features.home

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
        setupUsersList()
    }

    private fun setupUsersList() {
        adapter = UserAdapter()
        rvUsers.adapter = adapter
    }

    private fun observeChanges() {
        viewModel.users.observe(this, Observer {
            it?.let { users ->
                adapter?.swapData(users, arrayOf())
            }
        })
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadData()
    }
}
