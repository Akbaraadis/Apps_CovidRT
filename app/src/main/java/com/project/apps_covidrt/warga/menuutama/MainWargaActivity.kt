package com.project.apps_covidrt.warga.menuutama

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import com.project.apps_covidrt.R
import com.project.apps_covidrt.warga.editprofile.EditProfileWargaActivity
import com.project.apps_covidrt.warga.gantipassword.GantiPasswordWargaActivity
import kotlinx.android.synthetic.main.activity_main_warga.*
import kotlinx.android.synthetic.main.popup_editprofile_warga.*

class MainWargaActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_warga)

        cl_warga_editprofile.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.cl_warga_editprofile -> {
                showPopup()
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