package com.example.siswa2961

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import com.android.volley.Request
import android.widget.Toast

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val edt_nis: EditText = findViewById(R.id.edt_nis)
        val edt_password: EditText = findViewById(R.id.edt_password)
        val btn_login: Button = findViewById(R.id.btn_login)

// btn login ditekan
        btn_login.setOnClickListener {
            val isi_nis = edt_nis.text.toString()
            val isi_password = edt_password.text.toString()

            val mintaData = Volley.newRequestQueue(this)
            val mintadata = object : StringRequest(
                Request.Method.POST,
                "http://192.168.0.101/android_amikom_2961/siswa_login.php",
                Response.Listener { response ->
                    val respon = JSONObject(response)
                    if (respon.getString("hasil") == "sukses") {
                        // Simpan akun pelogin dalam session
                        val session = getSharedPreferences("siswa", MODE_PRIVATE).edit()
                        session.putString("nis", respon.getJSONObject("data").getString("nis"))
                        session.putString("nama_siswa", respon.getJSONObject("data").getString("nama_siswa"))
                        session.putString("foto_siswa", respon.getJSONObject("data").getString("foto_siswa"))
                        session.commit()

                        // Pindah halaman
                        val pindah: Intent = Intent(this, Dashboard::class.java)
                        startActivity(pindah)
                    } else {
                        Toast.makeText(this, "NIS atau password salah", Toast.LENGTH_LONG).show()
                        val pindah: Intent = Intent(this, Login::class.java)
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
                    bawaan["nis"] = isi_nis
                    bawaan["password_siswa"] = isi_password
                    return bawaan
                }
            }

            mintaData.add(mintadata)
        }
    }
}