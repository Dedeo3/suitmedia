package com.myapplication.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.myapplication.R
import com.myapplication.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)

        supportActionBar?.title = "Second screen"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val receiveUsername = intent.getStringExtra(USERNAME).toString()

        val receiveName = sharedPreferences.getString(NAME, "")

        binding.name.text = receiveName ?: getString(R.string.name)

        if (receiveUsername == "null") {
            binding.selectedUsername.text = getString(R.string.selected_user_name)
        } else {
            binding.selectedUsername.text = receiveUsername
        }

        binding.btnChoose.setOnClickListener {
            startActivity(Intent(this, ThirdActivity::class.java))
        }
    }

    companion object {
        const val NAME = "name"
        const val USERNAME = "username"
    }
}
