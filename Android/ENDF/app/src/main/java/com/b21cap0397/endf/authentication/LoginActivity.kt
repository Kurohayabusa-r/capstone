package com.b21cap0397.endf.authentication

import android.content.Intent
import android.os.Bundle
import com.b21cap0397.endf.R
import androidx.appcompat.app.AppCompatActivity
import com.b21cap0397.endf.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            Intent(this@LoginActivity, RegisterActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}