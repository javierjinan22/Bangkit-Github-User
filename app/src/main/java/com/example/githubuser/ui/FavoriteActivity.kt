package com.example.githubuser.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.data.database.FavoriteUser
import com.example.githubuser.databinding.ActivityFavoriteBinding
import com.example.githubuser.viewmodel.FavViewModelFactory
import com.example.githubuser.viewmodel.FavoritViewModel

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var favoriteViewModel: FavoritViewModel
    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Favorite User"

        val favoriteModelFactory = FavViewModelFactory(application)
        favoriteViewModel = ViewModelProvider(this, favoriteModelFactory).get(FavoritViewModel::class.java)

        val rvFav = binding.rvFav
        favoriteAdapter = FavoriteAdapter { favoriteUser ->
            val intentFav = Intent(this, DetailActivity::class.java)
            intentFav.putExtra("username", favoriteUser.login)
            startActivity(intentFav)
        }
        rvFav.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = favoriteAdapter
        }

        favoriteViewModel.favoriteUsers.observe(this) { favoriteUsers ->
            favoriteAdapter.submitList(favoriteUsers)
        }
        showLoading(false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
    fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar2.visibility = View.VISIBLE
        } else {
            binding.progressBar2.visibility = View.GONE
        }
    }
}
