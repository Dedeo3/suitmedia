package com.myapplication.activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.myapplication.R
import com.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.check.setOnClickListener {
            checkPalindrome()
        }

        binding.next.setOnClickListener {
            nextClick()
        }
    }

    private fun checkPalindrome() {
        val palindrom = binding.edPalindrom.text.toString()
        if (palindrom.isNotEmpty()) {
            val cleaned = palindrom.replace("\\s".toRegex(), "").lowercase()
            var starting = 0
            var end = cleaned.length - 1

            while (starting < end) {
                if (cleaned[starting] != cleaned[end]) {
                    showPalindromeResultDialog(false)
                    return
                }
                starting++
                end--
            }
            showPalindromeResultDialog(true)
        } else {
            binding.palindromResult.text = "enter the palindrom sentence"
        }
    }

    private fun showPalindromeResultDialog(isPalindrome: Boolean) {
        val message = if (isPalindrome) {
            "is Palindrome"
        } else {
            "is not palindrome"
        }

        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder
            .setMessage(message)
            .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, id ->
                dialog.dismiss()
            })

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }


    private fun nextClick() {
        val name = binding.edRegisterName.text.toString()
        if (name.isNotEmpty()) {
            val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString(SecondActivity.NAME, name)
            editor.apply()

            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        } else {
            binding.palindromResult.text = getString(R.string.userNothing)
        }
    }

}