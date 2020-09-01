package com.project.apps_covidrt

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.project.apps_covidrt.rt.menuutama.MainActivity
import com.project.apps_covidrt.rt.pendaftaran.PendaftaranRTActivity
import com.project.apps_covidrt.warga.menuutama.MainWargaActivity
import com.project.apps_covidrt.warga.pendaftaran.PendaftaranWargaActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_pendaftaran_rt.*
import kotlinx.android.synthetic.main.popup_login_daftar.*
import org.json.JSONObject
import java.util.HashMap

class LoginActivity : AppCompatActivity() {

    var status_user = "2"

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
            if(TextUtils.isEmpty(et_login_username.getText().toString()) || TextUtils.isEmpty(et_login_password.getText().toString())){
                Toast.makeText(applicationContext, "Email atau Password belum diisi", Toast.LENGTH_SHORT).show()
            }
            else {
                if (check == 0) {
                    status_user = "2"
                } else {
                    status_user = "1"
                }
                jsonParse()
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

        myDialog.iv_popup_daftar_rt.setOnClickListener{
            val gotoa = Intent(this, PendaftaranRTActivity::class.java).also {
                startActivity(it)
            }
        }

        myDialog.iv_popup_daftar_warga.setOnClickListener{
            val gotob = Intent(this, PendaftaranWargaActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    fun jsonParse() {


        //url
        val url:String = "http://test-api.online/covid-rt/public/login"

        //RequestQueue initialized
        val mRequestQueue = Volley.newRequestQueue(this)

        //String Request initialized
        val mStringRequest = object : StringRequest(Request.Method.POST, url, Response.Listener { response ->

            val tokennya = response.toString()

            if(status_user=="2"){
                val gotomainwarga = Intent(this, MainWargaActivity::class.java)
                gotomainwarga.putExtra("Token", tokennya)
                startActivity(gotomainwarga)
            }
            else{
                val gotopengelolart = Intent(this, MainActivity::class.java)
                gotopengelolart.putExtra("Token", tokennya)
                startActivity(gotopengelolart)
            }
        }, Response.ErrorListener { error ->
            Log.i("This is the error", "Error :" + error.toString())
            Toast.makeText(applicationContext, "Email belum terdaftar", Toast.LENGTH_SHORT).show()
        }) {
            override fun getBodyContentType(): String {
                return "application/json"
            }

            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray {
                val params2 = HashMap<String, String>()
                params2["username"] = et_login_username.text.toString()
                params2["password"] = et_login_password.text.toString()
                params2["status_user"] = status_user.toString()
                return JSONObject(params2 as Map<*, *>).toString().toByteArray()
            }

        }
        mRequestQueue!!.add(mStringRequest!!)
    }

}