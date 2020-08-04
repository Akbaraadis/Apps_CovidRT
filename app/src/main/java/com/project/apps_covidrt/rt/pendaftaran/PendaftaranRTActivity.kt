package com.project.apps_covidrt.rt.pendaftaran

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.project.apps_covidrt.R
import com.project.apps_covidrt.rt.menuutama.MainActivity
import kotlinx.android.synthetic.main.activity_pendaftaran_rt.*

class PendaftaranRTActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pendaftaran_rt)

        btn_daftar_rt.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_daftar_rt -> {
                Toast.makeText(this, "Pendaftaran berhasil", Toast.LENGTH_SHORT).show()
                val gotomainrt = Intent(this, MainActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
    }
}