package com.project.apps_covidrt.rt.menuutama

import android.app.Dialog
import android.content.Intent
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
import com.project.apps_covidrt.*
import com.project.apps_covidrt.rt.editprofile.EditProfileRTActivity
import com.project.apps_covidrt.rt.gantipassword.GantiPasswordRTActivity
import com.project.apps_covidrt.rt.laporankesehatan.LaporanKesehatanActivity
import com.project.apps_covidrt.rt.laporankesejahteraan.LaporanKesejahteraanActivity
import com.project.apps_covidrt.warga.menuutama.MainWargaActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.popup_editprofile_rt.*
import java.util.HashMap

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = intent
        val tokennya1 = intent.getStringExtra("Token")

        cl_rt_editprofile.setOnClickListener(View.OnClickListener {
            showPopup()
        })
        cl_rt_lap_kesehatan.setOnClickListener(View.OnClickListener {
            val kesehatan = Intent(this, LaporanKesehatanActivity::class.java)
            kesehatan.putExtra("Token", tokennya1)
            startActivity(kesehatan)
        })
        cl_rt_lap_kesejahteraan.setOnClickListener(View.OnClickListener {
            val kesejahteraan = Intent(this, LaporanKesejahteraanActivity::class.java)
            kesejahteraan.putExtra("Token", tokennya1)
            startActivity(kesejahteraan)
        })
        cl_rt_logout.setOnClickListener(View.OnClickListener {
            jsonParseGet()
        })
        cl_rt_phbs.setOnClickListener(View.OnClickListener {
            val phbs = Intent(this, WebActivity::class.java)
            phbs.putExtra("Token", tokennya1)
            startActivity(phbs)
        })
        cl_rt_informasi.setOnClickListener(View.OnClickListener {
            val phbs = Intent(this, InformasiActivity::class.java)
            phbs.putExtra("Token", tokennya1)
            startActivity(phbs)
        })
    }

    private fun showPopup() {
        val myDialog = Dialog(this)
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        myDialog.setContentView(R.layout.popup_editprofile_rt)
        myDialog.setTitle("Popup Edit Profile")

        myDialog.show()

        val intent = intent
        var tokennya= intent.getStringExtra("Token")

        myDialog.cl_rt_pop_editprofile.setOnClickListener{
            val popedit = Intent(this, EditProfileRTActivity::class.java)
            popedit.putExtra("Token", tokennya)
            startActivity(popedit)
        }

        myDialog.cl_rt_pop_password.setOnClickListener{
            val poppass = Intent(this, GantiPasswordRTActivity::class.java)
            poppass.putExtra("Token", tokennya)
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