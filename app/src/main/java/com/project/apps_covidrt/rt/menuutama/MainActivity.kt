package com.project.apps_covidrt.rt.menuutama

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import com.project.apps_covidrt.MenuPendaftaran
import com.project.apps_covidrt.R
import com.project.apps_covidrt.rt.editprofile.EditProfileRTActivity
import com.project.apps_covidrt.rt.gantipassword.GantiPasswordRTActivity
import com.project.apps_covidrt.warga.menuutama.MainWargaActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.popup_editprofile_rt.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cl_rt_editprofile.setOnClickListener(this)
        cl_rt_lap_kesehatan.setOnClickListener(this)
        cl_rt_lap_kesejahteraan.setOnClickListener(this)
        cl_rt_phbs.setOnClickListener(this)
        cl_rt_informasi.setOnClickListener(this)
        cl_rt_logout.setOnClickListener(this)
    }

    private fun showPopup() {
        val myDialog = Dialog(this)
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        myDialog.setContentView(R.layout.popup_editprofile_rt)
        myDialog.setTitle("Popup Edit Profile")

        myDialog.show()

        myDialog.cl_rt_pop_editprofile.setOnClickListener{
            val popedit = Intent(this, EditProfileRTActivity::class.java).also {
                startActivity(it)
            }
        }

        myDialog.cl_rt_pop_password.setOnClickListener{
            val poppass = Intent(this, GantiPasswordRTActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.cl_rt_editprofile -> {
                showPopup()
            }

            R.id.cl_rt_logout -> {
                val logout = Intent(this, MenuPendaftaran::class.java).also {
                    startActivity(it)
                }
            }
        }
    }


}