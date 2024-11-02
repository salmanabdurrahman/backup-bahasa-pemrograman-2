package com.example.siswa2961

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentContainerView
import com.google.android.material.button.MaterialButton

class Nilai : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nilai)

        val btn_uts: MaterialButton = findViewById(R.id.btn_uts)
        val btn_uas: MaterialButton = findViewById(R.id.btn_uas)
        val fcv_nilai: FragmentContainerView = findViewById(R.id.fcv_nilai)

        supportFragmentManager.beginTransaction().replace(fcv_nilai.id, Uts()).commit()

        btn_uts.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(fcv_nilai.id, Uts()).commit()
        }

        btn_uas.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(fcv_nilai.id, Uas()).commit()
        }
    }
}