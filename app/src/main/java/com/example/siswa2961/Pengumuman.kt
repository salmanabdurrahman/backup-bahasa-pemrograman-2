package com.example.siswa2961

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class Pengumuman : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pengumuman)

        // buat  data juduls pengumuman
        var juduls: MutableList<String> = mutableListOf()
        var tanggals: MutableList<String> = mutableListOf()

        var lv_pengumuman: ListView = findViewById(R.id.lv_pengumuman)
        val minta = Volley.newRequestQueue(this)
        val mintadata: StringRequest = object : StringRequest(
            Request.Method.POST,
            "http://192.168.0.101/android_amikom_2961/pengumuman_tampil.php",
            Response.Listener<String> { response ->
                val dataarray = JSONObject(response).getJSONArray("data")

                for (i in 0 until  dataarray.length()) {
                    val jdl = dataarray.getJSONObject(i).getString("judul_pengumuman")
                    val tgl = dataarray.getJSONObject(i).getString("tanggal_pengumuman")

                    juduls.add(jdl)
                    tanggals.add(tgl)
                }
                var perulangandata = Pengumuman_Item(this, juduls, tanggals)
                lv_pengumuman.adapter = perulangandata
            },
            Response.ErrorListener {
                Log.d("eekror", "bermasalah")
            }

        ) {
            override fun getParams(): MutableMap<String, String>? {
                val bawaan: MutableMap<String, String> = HashMap()
                bawaan.put("kode", "amikomoke")

                return bawaan
            }
        }
        minta.add(mintadata)
    }
}