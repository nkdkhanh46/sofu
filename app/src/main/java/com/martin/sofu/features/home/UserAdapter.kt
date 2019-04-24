package com.martin.sofu.features.home

import android.util.LongSparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.util.containsKey
import androidx.core.util.putAll
import androidx.core.util.valueIterator
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.martin.sofu.R
import com.martin.sofu.databinding.ItemUserBinding
import com.martin.sofu.model.User
import com.martin.sofu.utils.DateTimeUtils

class UserAdapter: RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    interface Listener {
        fun onBookmarksChanged(bookmarks: LongSparseArray<User>)
    }

    private val allUsers = ArrayList<User>()
    private val users = ArrayList<User>()
    private var bookmarks = LongSparseArray<User>()
    var listener: Listener? = null
    private var showAll = true

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

    fun swapData(users: ArrayList<User>, bookmarks: LongSparseArray<User>, hasMore: Boolean, showAll: Boolean) {
        this.showAll = showAll
        this.bookmarks.clear()
        this.bookmarks.putAll(bookmarks)
        this.allUsers.clear()
        this.allUsers.addAll(users)

        updateDataWithFiltering(hasMore)

        notifyDataSetChanged()
    }


    private fun updateDataWithFiltering(hasMore: Boolean = false) {
        this.users.clear()

        val data = if (showAll) this.allUsers else getBookmarkUsers()
        this.users.addAll(data)

        if (hasMore && showAll) addLoadingFooter()
    }

    private fun getBookmarkUsers(): ArrayList<User> {
        val users = ArrayList<User>()
        for (user in bookmarks.valueIterator()) {
            users.add(user)
        }

        users.sortByDescending { user -> user.reputation }
        return users
    }

    fun setFilter(showAll: Boolean) {
        this.showAll = showAll
        updateDataWithFiltering()
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

            val color = if (bookmarks.containsKey(user.userId)) R.color.colorPrimary else R.color.greyDark
            binding.ivBookmark.setColorFilter(ContextCompat.getColor(itemView.context, color))
            val icon = if (bookmarks.containsKey(user.userId)) R.drawable.ic_bookmarked else R.drawable.ic_bookmark
            binding.ivBookmark.setImageResource(icon)

            binding.ivBookmark.setOnClickListener {
                onBookmarkClicked(user, adapterPosition)
            }
        }

        private fun onBookmarkClicked(user: User, position: Int) {
            if (bookmarks.containsKey(user.userId)) {
                bookmarks.remove(user.userId)
            } else {
                bookmarks.put(user.userId, user)
            }

            listener?.onBookmarksChanged(bookmarks)
            notifyItemChanged(position)
        }
    }
}