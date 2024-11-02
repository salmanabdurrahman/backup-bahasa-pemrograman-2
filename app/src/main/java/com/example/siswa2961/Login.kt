package com.example.siswa2961

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val edt_nis:EditText = findViewById(R.id.edt_nis)
        val edt_password:EditText = findViewById(R.id.edt_password)
        val btn_login:Button = findViewById(R.id.btn_login)

        // btn login ditekan
        btn_login.setOnClickListener {
            // pindah halaman dari halaman ini ke halaman Dashboard
            val pindah:Intent = Intent(this, Dashboard::class.java)
            startActivity(pindah)
        }
    }
}