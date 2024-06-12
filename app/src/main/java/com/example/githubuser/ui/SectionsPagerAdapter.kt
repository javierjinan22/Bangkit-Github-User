package com.example.githubuser.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: AppCompatActivity,  var username : String) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        val fragment = FragmentFollower()
        fragment.arguments = Bundle().apply {
            putInt(FragmentFollower.ARG_POSITION, position + 1)
            putString(FragmentFollower.ARG_USERNAME, username)
        }
        return fragment
    }
    override fun getItemCount(): Int {
        return 2
    }
    companion object {
        var username =""
    }
}