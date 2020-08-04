package com.project.apps_covidrt.warga.menuutama

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.os.Build.ID
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import com.project.apps_covidrt.R
import com.project.apps_covidrt.warga.editprofile.EditProfileWargaActivity
import com.project.apps_covidrt.warga.gantipassword.GantiPasswordWargaActivity
import com.project.apps_covidrt.warga.kondisikesehatan.KondisiKesehatanActivity
import com.project.apps_covidrt.warga.kondisikesejahteraan.KondisiKesejahteraanActivity
import com.project.apps_covidrt.warga.pendaftaran.PendaftaranWargaActivity
import kotlinx.android.synthetic.main.activity_main_warga.*
import kotlinx.android.synthetic.main.activity_pendaftaran_warga.*
import kotlinx.android.synthetic.main.popup_editprofile_warga.*
import java.text.SimpleDateFormat
import java.util.*

class MainWargaActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_warga)

        cl_warga_editprofile.setOnClickListener(this)
        cl_warga_lap_kesehatan.setOnClickListener(this)
        cl_warga_lap_kesejahteraan.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.cl_warga_editprofile -> {
                showPopup()
            }
            R.id.cl_warga_lap_kesehatan -> {
                val inputkesehatan = Intent(this, KondisiKesehatanActivity::class.java).also {
                    startActivity(it)
                }
            }
            R.id.cl_warga_lap_kesejahteraan -> {
                val inputkesejahteraan = Intent(this, KondisiKesejahteraanActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
    }

    private fun showPopup() {
        val myDialog = Dialog(this)
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        myDialog.setContentView(R.layout.popup_editprofile_warga)
        myDialog.setTitle("Popup Edit Profile")

        myDialog.show()

        myDialog.cl_warga_pop_editprofile.setOnClickListener{
            val popedit = Intent(this, EditProfileWargaActivity::class.java).also {
                startActivity(it)
            }
        }

        myDialog.cl_warga_pop_password.setOnClickListener{
            val poppass = Intent(this, GantiPasswordWargaActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}