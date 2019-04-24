package com.martin.sofu.features.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.martin.sofu.R
import com.martin.sofu.databinding.DialogUsersFilterBinding
import kotlinx.android.synthetic.main.dialog_users_filter.*

class UserFilterDialog: BottomSheetDialogFragment() {

    interface Listener {
        fun onFilterSelected(showAll: Boolean)
    }

    companion object {
        const val TAG = "UserFilterDialog"
        private const val KEY_SHOWING_ALL = "ShowingAll"

        fun newInstance(showingAll: Boolean) = UserFilterDialog().apply {
            arguments = Bundle().apply {
                putBoolean(KEY_SHOWING_ALL, showingAll)
            }
        }
    }

    var listener: Listener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: DialogUsersFilterBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_users_filter, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setupEvents()
    }

    private fun initView() {
        val showingAll = arguments?.getBoolean(KEY_SHOWING_ALL)?: true

        if (showingAll) {
            ivShowAllCheck.visibility = View.VISIBLE
            ivBookmarksCheck.visibility = View.GONE
        } else {
            ivShowAllCheck.visibility = View.GONE
            ivBookmarksCheck.visibility = View.VISIBLE
        }
    }

    private fun setupEvents() {
        btnShowAll.setOnClickListener { onFilterOptionSelected(true) }
        btnBookmarks.setOnClickListener { onFilterOptionSelected(false) }
    }

    private fun onFilterOptionSelected(showAll: Boolean) {
        listener?.onFilterSelected(showAll)
        dismiss()
    }
}