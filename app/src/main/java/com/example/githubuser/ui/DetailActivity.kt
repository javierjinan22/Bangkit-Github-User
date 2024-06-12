package com.example.githubuser.ui
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.data.database.FavoriteUser
import com.example.githubuser.databinding.ActivityDetailBinding
import com.example.githubuser.viewmodel.DetailViewModel
import com.example.githubuser.data.response.DetailUserResponse
import com.example.githubuser.viewmodel.FavoritViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class DetailActivity : AppCompatActivity() {
    private val viewModel: DetailViewModel by viewModels<DetailViewModel>()
    private lateinit var binding: ActivityDetailBinding

    companion object {
        val DETAIL = "Detail User"
        private val TAB_TITLE = arrayOf("Followers", "Following")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Detail User"


        val favViewModel = ViewModelProvider(this)[FavoritViewModel::class.java]
        viewModel.detailUser.observe(this){detailUser ->
            setUserDetail(detailUser)
            var favorite = false
            detailUser.login.let {
                if (it!=null){
                    favViewModel.getFavoriteUser(it).observe(this){favoriteUser ->
                            if (favoriteUser != null && favoriteUser.favorite){
                                binding.fabFavorit.setImageResource(R.drawable.baseline_favorite_24)
                                favorite = true
                            }else{
                                binding.fabFavorit.setImageResource(R.drawable.baseline_favorite_border_24)
                                favorite = false
                            }
                    }
                }
            }
            binding.fabFavorit.setOnClickListener{
                val insert = FavoriteUser(
                    id = detailUser.id, login = detailUser.login,
                    avatarUrl = detailUser.avatarUrl, favorite=!favorite
                )
                val delete = FavoriteUser(
                    id = detailUser.id, login = detailUser.login,
                    avatarUrl = detailUser.avatarUrl, favorite=favorite
                )
                if(favorite){
                    favViewModel.delete(delete)
                    Toast.makeText(this,"user has been delete from favorite list", Toast.LENGTH_SHORT).show()
                }else{
                    favViewModel.insert(insert)
                    Toast.makeText(this,"user has been add from favorite list", Toast.LENGTH_SHORT).show()
                }
                favorite = !favorite
            }
        }

        val username = intent.getStringExtra(DETAIL)
        binding.tvName.text = username

        username?.let {  viewModel.getUserDetail(it)}
        viewModel.isLoading.observe(this){
                isLoading -> showLoading(isLoading)
        }
        viewModel.detailUser.observe(this) { detailUser ->
            setUserDetail(detailUser)
            val sectionPagerAdapter = username?.let { SectionsPagerAdapter(this, it) }
            val viewPager: ViewPager2 = binding.viewPager
            viewPager.adapter = sectionPagerAdapter
            val tabs: TabLayout = binding.tabs

            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = TAB_TITLE[position]
            }.attach()

        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home ->{
                onBackPressed()
                startActivity(Intent(this@DetailActivity,MainActivity::class.java))
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
    override fun onBackPressed(){
        super.onBackPressed()
        startActivity(Intent(this@DetailActivity,MainActivity::class.java))
        finish()
    }

    fun setUserDetail( userDetail : DetailUserResponse) {
        binding.tvUsername.text = userDetail.login
        binding.tvName.text = userDetail?.name?.toString()
        Glide.with(binding.root.context)
            .load(userDetail.avatarUrl)
            .into(binding.ivFoto)

        binding.tvFollower.text = StringBuilder(userDetail.followers.toString()).append(" Followers")
        binding.tvFollowing.text = StringBuilder(userDetail.following.toString()).append(" Following")
    }


    fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}