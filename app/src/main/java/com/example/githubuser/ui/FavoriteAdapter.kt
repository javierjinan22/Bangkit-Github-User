package com.example.githubuser.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.data.database.FavoriteUser
import com.example.githubuser.data.response.ItemsItem
import com.example.githubuser.databinding.ItemRowUserBinding


class FavoriteAdapter(private val onFavClick: (FavoriteUser) -> Unit) : ListAdapter<FavoriteUser, FavoriteAdapter.FavViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return FavViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        val item = getItem(position)
        val username = item.login

        holder.bind(item)
        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, DetailActivity::class.java).apply{
                this.putExtra(DetailActivity.DETAIL,username )
            }
            holder.run {
                username.also {
                    if (it != null){
                        SectionsPagerAdapter.username = it
                    }
                }
                itemView.context.startActivity(intent)
            }
        }

    }

    class FavViewHolder(val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FavoriteUser){
            binding.tvUsername.text = item.login
            Glide.with(binding.root.context)
                .load(item.avatarUrl)
                .into(binding.imgItemPhoto)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavoriteUser>() {
            override fun areItemsTheSame(oldItem: FavoriteUser, newItem: FavoriteUser): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: FavoriteUser, newItem: FavoriteUser): Boolean {
                return oldItem == newItem
            }
        }
    }
}

