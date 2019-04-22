package com.martin.sofu.features.home

import android.os.Bundle
import com.martin.sofu.R
import com.martin.sofu.application.MainApplication
import com.martin.sofu.base.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
    }

    private fun initBinding() {
        (application as MainApplication?)?.appComponent?.inject(this)
        setContentView(R.layout.activity_main)
    }
}
