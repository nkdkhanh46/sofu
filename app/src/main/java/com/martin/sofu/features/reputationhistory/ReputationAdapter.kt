package com.martin.sofu.features.reputationhistory

import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.martin.sofu.R
import com.martin.sofu.databinding.ItemReputationBinding
import com.martin.sofu.model.Reputation
import com.martin.sofu.model.ReputationType
import com.martin.sofu.model.User
import com.martin.sofu.utils.DateTimeUtils
import java.util.*
import kotlin.collections.ArrayList

class ReputationAdapter: RecyclerView.Adapter<ReputationAdapter.ReputationViewHolder>() {

    private val reputations = ArrayList<Reputation>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReputationViewHolder {
        val binding: ItemReputationBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_reputation, parent, false)
        return ReputationViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return reputations.size
    }

    override fun onBindViewHolder(holder: ReputationViewHolder, position: Int) {
        holder.bind(reputations[position])
    }

    fun swapData(reputations: ArrayList<Reputation>, hasMore: Boolean) {
        this.reputations.clear()
        this.reputations.addAll(reputations)

        if (hasMore) addLoadingFooter()
        notifyDataSetChanged()
    }

    private fun addLoadingFooter() {
        val footer = Reputation()
        footer.isFooter = true
        this.reputations.add(footer)
    }

    inner class ReputationViewHolder(private val binding: ItemReputationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(reputation: Reputation) {
            if (reputation.isFooter) {
                binding.vLoading.visibility = View.VISIBLE
                return
            }

            binding.vLoading.visibility = View.GONE

            binding.tvPostId.text = String.format(Locale.getDefault(), "#%d", reputation.postId)
            binding.tvDate.text = DateTimeUtils.timestampToDisplayDate(reputation.creationDate)

            val change = String.format(Locale.getDefault(), itemView.context.getString(reputation.type.displayValue), reputation.reputationChange)
            binding.tvChange.text = change

            binding.ivType.setImageResource(reputation.type.icon)
            binding.ivType.setColorFilter(ContextCompat.getColor(itemView.context, reputation.type.color), PorterDuff.Mode.MULTIPLY)
        }
    }
}