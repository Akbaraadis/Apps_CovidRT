package com.project.apps_covidrt.warga.menuutama

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.os.Build.ID
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.project.apps_covidrt.LoginActivity
import com.project.apps_covidrt.R
import com.project.apps_covidrt.warga.editprofile.EditProfileWargaActivity
import com.project.apps_covidrt.warga.gantipassword.GantiPasswordWargaActivity
import com.project.apps_covidrt.warga.kondisikesehatan.KondisiKesehatanActivity
import com.project.apps_covidrt.warga.kondisikesejahteraan.KondisiKesejahteraanActivity
import com.project.apps_covidrt.warga.pendaftaran.PendaftaranWargaActivity
import kotlinx.android.synthetic.main.activity_edit_profile_warga.*
import kotlinx.android.synthetic.main.activity_main_warga.*
import kotlinx.android.synthetic.main.activity_pendaftaran_warga.*
import kotlinx.android.synthetic.main.popup_editprofile_warga.*
import java.text.SimpleDateFormat
import java.util.*

class MainWargaActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_warga)

        val intent = intent
        val tokennya1 = intent.getStringExtra("Token")

        cl_warga_editprofile.setOnClickListener(View.OnClickListener {
            showPopup()
        })
        cl_warga_lap_kesehatan.setOnClickListener(View.OnClickListener {
            val inputkesehatan = Intent(this, KondisiKesehatanActivity::class.java)
                inputkesehatan.putExtra("Token", tokennya1)
            startActivity(inputkesehatan)
        })
        cl_warga_lap_kesejahteraan.setOnClickListener(View.OnClickListener {
            val inputkesejahteraan = Intent(this, KondisiKesejahteraanActivity::class.java)
            inputkesejahteraan.putExtra("Token", tokennya1)
            startActivity(inputkesejahteraan)
        })
        cl_warga_logout.setOnClickListener(View.OnClickListener {
            jsonParseGet()
        })

    }


    private fun showPopup() {
        val myDialog = Dialog(this)
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        myDialog.setContentView(R.layout.popup_editprofile_warga)
        myDialog.setTitle("Popup Edit Profile")

        myDialog.show()

        val intent = intent
        val tokennya1 = intent.getStringExtra("Token")

        myDialog.cl_warga_pop_editprofile.setOnClickListener{
            val popedit = Intent(this, EditProfileWargaActivity::class.java)
            popedit.putExtra("Token", tokennya1)
            startActivity(popedit)
        }

        myDialog.cl_warga_pop_password.setOnClickListener{
            val poppass = Intent(this, GantiPasswordWargaActivity::class.java)
            poppass.putExtra("Token", tokennya1)
            startActivity(poppass)
        }
    }

    fun jsonParseGet() {

        val intent = intent
        var tokennya= intent.getStringExtra("Token")

        //url
        val url:String = "http://test-api.online/covid-rt/public/logout"

        //RequestQueue initialized
        val mRequestQueue = Volley.newRequestQueue(this)

        //String Request initialized
        val mStringRequest = object : JsonObjectRequest(Request.Method.GET, url,null, Response.Listener { response ->
            Toast.makeText(applicationContext, "Anda Telah Keluar", Toast.LENGTH_SHORT).show()
            val logout = Intent(this, LoginActivity::class.java).also {
                startActivity(it)
            }

        }, Response.ErrorListener { error ->
            Log.i("This is the error", "Error :" + error.toString())
            Toast.makeText(applicationContext, "Data Gagal Ditampilkan", Toast.LENGTH_SHORT).show()
        })
        {
            override fun getBodyContentType(): String {
                return "application/json"
            }

            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String>? {
                val headers = HashMap<String, String>()
                headers["token"] = tokennya.toString()
                return headers
            }

        }
        mRequestQueue!!.add(mStringRequest!!)
    }
}