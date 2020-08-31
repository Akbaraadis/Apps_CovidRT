package com.project.apps_covidrt.rt.pendaftaran

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
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
import com.project.apps_covidrt.rt.menuutama.MainActivity
import kotlinx.android.synthetic.main.activity_pendaftaran_rt.*
import kotlinx.android.synthetic.main.activity_pendaftaran_warga.*
import org.json.JSONObject
import java.util.HashMap

class PendaftaranRTActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pendaftaran_rt)

        btn_daftar_rt.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_daftar_rt -> {
                if(TextUtils.isEmpty(et_daftarrt_email.getText().toString()) || TextUtils.isEmpty(et_daftarrt_password.getText().toString()) ||
                    TextUtils.isEmpty(et_daftarrt_nama.getText().toString()) || TextUtils.isEmpty(et_daftarrt_nik.getText().toString()) ||
                    TextUtils.isEmpty(et_daftarrt_kecamatan.getText().toString()) || TextUtils.isEmpty(et_daftarrt_kelurahan.getText().toString()) ||
                    TextUtils.isEmpty(et_daftarrt_rt.getText().toString()) || TextUtils.isEmpty(et_daftarrt_rw.getText().toString())){

                    Toast.makeText(applicationContext,"Data masih ada yang belum diisi", Toast.LENGTH_LONG).show()
                }
                else{
                    jsonParse()
                }
            }
        }
    }

    fun jsonParse() {

        //url
        val url:String = "http://test-api.online/covid-rt/public/register-rt"

        //RequestQueue initialized
        val mRequestQueue = Volley.newRequestQueue(this)

        //String Request initialized
        val mStringRequest = object : StringRequest(Request.Method.POST, url, Response.Listener { response ->
            Toast.makeText(applicationContext, "Registrasi Berhasil", Toast.LENGTH_SHORT).show()
            var gotomainrt = Intent(this, LoginActivity::class.java).also {
                startActivity(it)
            }


        }, Response.ErrorListener { error ->
            Log.i("This is the error", "Error :" + error.toString())
            Toast.makeText(applicationContext, "Registrasi Gagal", Toast.LENGTH_SHORT).show()
            Toast.makeText(applicationContext, "Username dan NIK telah digunakan", Toast.LENGTH_SHORT).show()
        }) {
            override fun getBodyContentType(): String {
                return "application/json"
            }

            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray {
                val params2 = HashMap<String, String>()
                params2["username"] = et_daftarrt_email.text.toString().trim()
                params2["password"] = et_daftarrt_password.text.toString().trim()
                params2["nama"] = et_daftarrt_nama.text.toString().trim()
                params2["nik"] = et_daftarrt_nik.text.toString().trim()
                params2["kecamatan"] = et_daftarrt_kecamatan.text.toString().trim()
                params2["kelurahan"] = et_daftarrt_kelurahan.text.toString().trim()
                params2["rt"] = et_daftarrt_rt.text.toString().trim()
                params2["rw"] = et_daftarrt_rw.text.toString().trim()
                return JSONObject(params2 as Map<*, *>).toString().toByteArray()
            }

        }
        mRequestQueue!!.add(mStringRequest!!)
    }
}