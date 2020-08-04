package com.project.apps_covidrt

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.project.apps_covidrt.rt.menuutama.MainActivity
import com.project.apps_covidrt.rt.pendaftaran.PendaftaranRTActivity
import com.project.apps_covidrt.warga.menuutama.MainWargaActivity
import com.project.apps_covidrt.warga.pendaftaran.PendaftaranWargaActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.popup_login_daftar.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val status = resources.getStringArray(R.array.Status)

        // access the spinner
        val spinner = findViewById<Spinner>(R.id.sp_login_status)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, status
            )
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    Toast.makeText(
                        this@LoginActivity,
                        getString(R.string.selected_item) + " " +
                                "" + status[position], Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }

        btn_login.setOnClickListener {
            val check = spinner.selectedItemPosition
            if (check == 0) {
                val wargamain = Intent(this, MainWargaActivity::class.java).also {
                    startActivity(it)
                }
            }
            else{
                val rtmain = Intent(this, MainActivity::class.java).also {
                    startActivity(it)
                }
            }
        }

        tv_login_daftar.setOnClickListener{
            Showpopup()
        }
    }

    private fun Showpopup() {
        val myDialog= Dialog(this)
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        myDialog.setContentView(R.layout.popup_login_daftar)
        myDialog.setTitle("Popup daftar")

        myDialog.show()

        myDialog.tv_popup_daftar_rt.setOnClickListener{
            val gotoa = Intent(this, PendaftaranRTActivity::class.java).also {
                startActivity(it)
            }
        }

        myDialog.tv_popup_daftar_warga.setOnClickListener{
            val gotob = Intent(this, PendaftaranWargaActivity::class.java).also {
                startActivity(it)
            }
        }
    }

}