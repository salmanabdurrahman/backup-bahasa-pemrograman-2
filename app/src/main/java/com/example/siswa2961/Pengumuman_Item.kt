package com.example.siswa2961

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class Pengumuman_Item (val halaman: Pengumuman, val juduls: MutableList<String>, val tanggals: MutableList<String>):BaseAdapter() {

    // disini klik kanan, pilih generate, pilih override, pilih 4 method terbawah

    override fun getCount(): Int {
        return juduls.size
    }

    override fun getItem(posisi: Int): Any {
        return juduls.get(posisi)
    }

    override fun getItemId(posisi: Int): Long {
        return posisi.toLong()
    }

    override fun getView(posisi: Int, convertview: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(halaman).inflate(R.layout.pengumuman_item, parent, false)
        val tv_judul: TextView = view.findViewById(R.id.tv_judul)
        val tv_tanggal: TextView = view.findViewById(R.id.tv_tanggal)
        tv_judul.text = juduls.get(posisi)
        tv_tanggal.text = juduls.get(posisi)

        return view
    }
}