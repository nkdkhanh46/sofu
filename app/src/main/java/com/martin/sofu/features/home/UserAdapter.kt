package com.martin.sofu.features.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.martin.sofu.R
import com.martin.sofu.databinding.ItemUserBinding
import com.martin.sofu.model.User
import com.martin.sofu.utils.DateTimeUtils

class UserAdapter: RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val users = ArrayList<User>()
    private var bookmarkedIds: Array<String> = arrayOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding: ItemUserBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_user, parent, false)
        return UserViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])
    }

    fun swapData(users: ArrayList<User>, bookmarkedIds: Array<String>) {
        this.users.clear()
        this.users.addAll(users)
        this.bookmarkedIds = bookmarkedIds
        notifyDataSetChanged()
    }

    inner class UserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.tvName.text = user.name

            val date = "${itemView.context.getString(R.string.last_access)} ${DateTimeUtils.timestampToDisplayDate(user.lastAccessDate)}"
            binding.tvAccessDate.text = date

            val reputation = "${user.reputation} ${itemView.context.getString(R.string.reputation)}"
            binding.tvReputation.text = reputation

            val location = if (user.location.isNullOrEmpty()) itemView.context.getString(R.string.unknown_location) else user.location
            binding.tvLocation.text = location

            Glide.with(itemView.context).load(user.profileImage).into(binding.ivAvatar)
        }
    }
}