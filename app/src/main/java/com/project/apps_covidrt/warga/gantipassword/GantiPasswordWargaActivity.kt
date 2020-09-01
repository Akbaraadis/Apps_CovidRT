package com.project.apps_covidrt.warga.gantipassword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.project.apps_covidrt.LoginActivity
import com.project.apps_covidrt.R
import com.project.apps_covidrt.warga.menuutama.MainWargaActivity
import kotlinx.android.synthetic.main.activity_ganti_password_warga.*
import kotlinx.android.synthetic.main.activity_pendaftaran_warga.*
import org.json.JSONObject
import java.util.HashMap

class GantiPasswordWargaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ganti_password_warga)

        btn_ganti_password.setOnClickListener(View.OnClickListener {
            jsonParse()
        })
    }

    fun jsonParse() {

        //Token
        val intent = intent
        var tokennya= intent.getStringExtra("Token")

        //url
        val url:String = "http://test-api.online/covid-rt/public/change-password"

        //RequestQueue initialized
        val mRequestQueue = Volley.newRequestQueue(this)

        //String Request initialized
        val mStringRequest = object : StringRequest(Request.Method.POST, url, Response.Listener { response ->
            Toast.makeText(applicationContext, "Ubah Password Berhasil", Toast.LENGTH_SHORT).show()
            var gotomainwarga = Intent(this, MainWargaActivity::class.java)
            gotomainwarga.putExtra("Token", tokennya)
            startActivity(gotomainwarga)


        }, Response.ErrorListener { error ->
            Log.i("This is the error", "Error :" + error.toString())
            Toast.makeText(applicationContext, "Gagal, Password Tidak Cocok", Toast.LENGTH_SHORT).show()
        }) {
            override fun getBodyContentType(): String {
                return "application/json"
            }

            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray {
                val params2 = HashMap<String, String>()
                params2["old_password"] = et_ganti_password_lama.text.toString().trim()
                params2["new_password"] = et_ganti_password_baru.text.toString().trim()
                params2["new_password_confirmation"] = et_ganti_password_konfirm.text.toString().trim()
                return JSONObject(params2 as Map<*, *>).toString().toByteArray()
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