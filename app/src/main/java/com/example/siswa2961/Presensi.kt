package com.example.siswa2961

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import org.json.JSONObject

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

            val kode_sesi: String = hasil.contents.toString()
            val nis = getSharedPreferences("siswa", MODE_PRIVATE).getString("nis", "").toString()

            val mintaData = Volley.newRequestQueue(this)
            val mintadata = object : StringRequest(
                Request.Method.POST,
                Backend().url_presensi,
                Response.Listener { response ->
                    val respon: JSONObject = JSONObject(response)
                    if (respon.getString("hasil") == "sudah") {
                        Toast.makeText(this, "sudah presensi", Toast.LENGTH_LONG).show()
                        val pindah = Intent(this, Dashboard::class.java)
                        finish()
                        startActivity(pindah)
                    } else if (respon.getString("hasil") == "sukses") {
                        Toast.makeText(this, "presensi berhasil", Toast.LENGTH_LONG).show()
                        val pindah = Intent(this, Dashboard::class.java)
                        finish()
                        startActivity(pindah)
                    } else {
                        Toast.makeText(this, "gagal presensi", Toast.LENGTH_LONG).show()
                        val pindah = Intent(this, Dashboard::class.java)
                        finish()
                        startActivity(pindah)
                    }
                },
                Response.ErrorListener { error ->
                    Log.d("error", error.toString())
                }
            ) {
                override fun getParams(): MutableMap<String, String>? {
                    val bawaan: MutableMap<String, String> = HashMap()
                    bawaan["kode"] = "amikomoke"
                    bawaan["nis"] = nis
                    bawaan["kode_sesi"] = kode_sesi
                    return bawaan
                }
            }

            mintaData.add(mintadata)
        } else {
            tv_hasil.text = "QR code tidak ditemukan"
        }
    }
}