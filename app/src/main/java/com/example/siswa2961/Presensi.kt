package com.example.siswa2961

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

class Presensi : AppCompatActivity() {
    private lateinit var tv_hasil:TextView
    private lateinit var btn_scan:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.presensi)

        tv_hasil = findViewById(R.id.tv_hasil)
        btn_scan = findViewById(R.id.btn_scan)

        btn_scan.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
                mulai_scan_qr_code()
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), 919)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 919) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mulai_scan_qr_code()
            } else {
                tv_hasil.text = "Untuk presensi wajib izinkan kamera!"
            }
        }
    }

    private fun mulai_scan_qr_code() {
        val aturan = ScanOptions()
        aturan.setPrompt("Arahkan kamera ke qr code")
        aturan.setBeepEnabled(true)
        aturan.setOrientationLocked(true)
        jalankan_qr_code.launch(aturan)
    }

    private val jalankan_qr_code = registerForActivityResult(ScanContract()) { hasil ->
        if (hasil.contents != null) {
            tv_hasil.text = "Hasil scan Anda " + hasil.contents.toString()
        } else {
            tv_hasil.text = "QR code tidak ditemukan"
        }
    }
}