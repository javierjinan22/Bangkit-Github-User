package com.example.githubuser.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.fragment.app.FragmentContainer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.githubuser.data.response.ItemsItem
import com.example.githubuser.databinding.FragmentFollowerBinding

import com.example.githubuser.viewmodel.FollowerFollowingViewModel


class FragmentFollower : Fragment() {
    private val viewModel : FollowerFollowingViewModel by viewModels<FollowerFollowingViewModel>()
    private lateinit var binding: FragmentFollowerBinding

    private var position: Int = 0
    private var username: String? = null

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }
     override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):View {
         binding = FragmentFollowerBinding.inflate(inflater,container, false)

         val layoutManager = LinearLayoutManager(requireContext())
         binding.rvFollow.layoutManager = layoutManager

         val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
         binding.rvFollow.addItemDecoration(itemDecoration)

         arguments?.let {
             position = it.getInt(ARG_POSITION)
             username = it.getString(ARG_USERNAME)
         }
         viewModel.isLoading.observe(viewLifecycleOwner){
             isLoading -> showLoading(isLoading)
         }
         viewModel._daftarFollower.observe(viewLifecycleOwner){ _daftarFollower ->
             setFollow(_daftarFollower)
         }

         if (position == 1) {
             viewModel.getFollower(username?:"")
         } else {
             viewModel.getFollowing(username?:"")
         }
         return binding.root
     }

    private fun setFollow( user : List<ItemsItem>) {
        val adapter = UserAdapter()
        adapter.submitList(user)
        binding.rvFollow.adapter = adapter
    }
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}