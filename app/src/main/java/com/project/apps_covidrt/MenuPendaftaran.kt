package com.project.apps_covidrt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.project.apps_covidrt.rt.pendaftaran.PendaftaranRTActivity
import com.project.apps_covidrt.warga.pendaftaran.PendaftaranWargaActivity
import kotlinx.android.synthetic.main.activity_menu_pendaftaran.*

class MenuPendaftaran : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_pendaftaran)

        btn_menudaftar_rt.setOnClickListener(this)
        btn_menudaftar_warga.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_menudaftar_rt -> {
                val gotort = Intent(this, PendaftaranRTActivity::class.java).also {
                    startActivity(it)
                }
            }
            R.id.btn_menudaftar_warga -> {
                val gotowarga = Intent(this, PendaftaranWargaActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
    }
}