package com.project.apps_covidrt.rt.editprofile

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
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.project.apps_covidrt.R
import com.project.apps_covidrt.rt.menuutama.MainActivity
import com.project.apps_covidrt.warga.menuutama.MainWargaActivity
import kotlinx.android.synthetic.main.activity_edit_profile_r_t.*
import kotlinx.android.synthetic.main.activity_edit_profile_warga.*
import org.json.JSONObject
import java.util.HashMap

class EditProfileRTActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile_r_t)

        jsonParseGet()

        btn_editrt_simpan.setOnClickListener(View.OnClickListener {
            if(TextUtils.isEmpty(et_editrt_email.getText().toString()) ||
                TextUtils.isEmpty(et_editrt_nama.getText().toString()) ||
                TextUtils.isEmpty(et_editrt_nik.getText().toString()) ||
                TextUtils.isEmpty(et_editrt_kecamatan.getText().toString()) ||
                TextUtils.isEmpty(et_editrt_kelurahan.getText().toString()) ||
                TextUtils.isEmpty(et_editrt_rt.getText().toString()) ||
                TextUtils.isEmpty(et_editrt_rw.getText().toString())){

                Toast.makeText(applicationContext,"Data masih ada yang belum diisi", Toast.LENGTH_LONG).show()
            }
            else{
                jsonParse()
            }
        })
    }

    fun jsonParseGet() {

        val intent = intent
        var tokennya= intent.getStringExtra("Token")

        //url
        val url:String = "http://test-api.online/covid-rt/public/rt/show-profile"

        //RequestQueue initialized
        val mRequestQueue = Volley.newRequestQueue(this)

        //String Request initialized
        val mStringRequest = object : JsonObjectRequest(Request.Method.GET, url,null, Response.Listener { response ->
            et_editrt_email.setText(response.getString("username"))
            et_editrt_nama.setText(response.getString("nama"))
            et_editrt_nik.setText(response.getString("nik"))
            et_editrt_kecamatan.setText(response.getString("kecamatan"))
            et_editrt_kelurahan.setText(response.getString("kelurahan"))
            et_editrt_rt.setText(response.getString("rt"))
            et_editrt_rw.setText(response.getString("rw"))

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

    fun jsonParse() {

        val intent = intent
        var tokennya= intent.getStringExtra("Token")

        //url
        val url:String = "http://test-api.online/covid-rt/public/rt/update-profile"

        //RequestQueue initialized
        val mRequestQueue = Volley.newRequestQueue(this)

        //String Request initialized
        val mStringRequest = object : StringRequest(Method.PUT, url, Response.Listener { response ->
            Toast.makeText(applicationContext, "Data Berhasil Diupload", Toast.LENGTH_SHORT).show()
            var gotomainrt = Intent(this, MainActivity::class.java)
            gotomainrt.putExtra("Token", tokennya)
            startActivity(gotomainrt)


        }, Response.ErrorListener { error ->
            Log.i("This is the error", "Error :" + error.toString())
            Toast.makeText(applicationContext, "Data Gagal Diperbaharui", Toast.LENGTH_SHORT).show()
        })
        {
            override fun getBodyContentType(): String {
                return "application/json"
            }

            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray {
                val params2 = HashMap<String, String>()
                params2["username"] = et_editrt_email.text.toString().trim()
                params2["nama"] = et_editrt_nama.text.toString().trim()
                params2["nik"] = et_editrt_nik.text.toString().trim()
                params2["kecamatan"] = et_editrt_kecamatan.text.toString().trim()
                params2["kelurahan"] = et_editrt_kelurahan.text.toString().trim()
                params2["rt"] = et_editrt_rt.text.toString().trim()
                params2["rw"] = et_editrt_rw.text.toString().trim()
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