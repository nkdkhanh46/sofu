package com.martin.sofu.features.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.martin.sofu.R
import com.martin.sofu.databinding.ItemUserBinding
import com.martin.sofu.model.User
import com.martin.sofu.utils.DateTimeUtils

class UserAdapter: RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    interface Listener {
        fun onBookmarksListChanged(bookmarkedIds: ArrayList<Long>)
    }

    private val users = ArrayList<User>()
    private var bookmarkedIds: ArrayList<Long> = ArrayList()
    var listener: Listener? = null

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

    fun swapData(users: ArrayList<User>, bookmarkedIds: ArrayList<Long>, hasMore: Boolean) {
        this.users.clear()
        this.users.addAll(users)
        if (hasMore) addLoadingFooter()

        this.bookmarkedIds = bookmarkedIds

        notifyDataSetChanged()
    }

    private fun addLoadingFooter() {
        val footer = User()
        footer.isFooter = true
        this.users.add(footer)
    }

    inner class UserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            if (user.isFooter) {
                binding.vLoading.visibility = View.VISIBLE
                return
            }

            binding.vLoading.visibility = View.GONE

            binding.tvName.text = user.name

            val date = "${itemView.context.getString(R.string.last_access)} ${DateTimeUtils.timestampToDisplayDate(user.lastAccessDate)}"
            binding.tvAccessDate.text = date

            val reputation = "${user.reputation} ${itemView.context.getString(R.string.reputation)}"
            binding.tvReputation.text = reputation

            val location = if (user.location.isNullOrEmpty()) itemView.context.getString(R.string.unknown_location) else user.location
            binding.tvLocation.text = location

            Glide.with(itemView.context).load(user.profileImage).into(binding.ivAvatar)

            val color = if (bookmarkedIds.contains(user.userId)) R.color.colorPrimary else R.color.greyDark
            binding.ivBookmark.setColorFilter(ContextCompat.getColor(itemView.context, color))
            val icon = if (bookmarkedIds.contains(user.userId)) R.drawable.ic_bookmarked else R.drawable.ic_bookmark
            binding.ivBookmark.setImageResource(icon)

            binding.ivBookmark.setOnClickListener {
                onBookmarkClicked(user.userId, adapterPosition)
            }
        }

        private fun onBookmarkClicked(userId: Long, position: Int) {
            if (bookmarkedIds.contains(userId)) {
                bookmarkedIds.remove(userId)
            } else {
                bookmarkedIds.add(userId)
            }

            listener?.onBookmarksListChanged(bookmarkedIds)
            notifyItemChanged(position)
        }
    }
}