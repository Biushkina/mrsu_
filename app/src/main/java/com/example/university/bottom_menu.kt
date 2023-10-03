package com.example.university

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.university.databinding.ActivityBottomMenuBinding

class bottom_menu : AppCompatActivity() {

    private lateinit var binding : ActivityBottomMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(prof())

        binding.bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId){

                R.id.lk -> replaceFragment(prof())
                R.id.table -> replaceFragment(table())
                else ->{
                }
            }
            true

        }
    }

    private fun replaceFragment(fragment : Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }
}