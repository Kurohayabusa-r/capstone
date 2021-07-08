package com.b21cap0397.endf.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.b21cap0397.endf.R
import android.os.Bundle
import com.b21cap0397.endf.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener{
            Intent(this@RegisterActivity, LoginActivity::class.java).also{
                startActivity(it)
            }
        }
    }
}