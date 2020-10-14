package com.project.apps_covidrt.warga.kondisikesejahteraan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import com.project.apps_covidrt.warga.menuutama.MainWargaActivity
import kotlinx.android.synthetic.main.activity_kondisi_kesejahteraan.*
import kotlinx.android.synthetic.main.activity_laporan_kesejahteraan.*
import org.json.JSONObject
import java.util.HashMap

class KondisiKesejahteraanActivity : AppCompatActivity() {

    var penghasilan = "1"
    var flag_phk = "0"
    var flag_usaha = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kondisi_kesejahteraan)

        jsonParseGet()

        btn_kesejahteraan_simpan.setOnClickListener(View.OnClickListener {
            if(rb_penghasilan_1.isChecked)
                penghasilan = "1"
            if(rb_penghasilan_2.isChecked)
                penghasilan = "2"
            if(rb_penghasilan_3.isChecked)
                penghasilan = "3"
            if(rb_penghasilan_4.isChecked)
                penghasilan = "4"

            if(cb_phk_iya.isChecked)
                flag_phk = "1"
            else
                flag_phk = "0"
            if(cb_usaha_iya.isChecked)
                flag_usaha = "1"
            else
                flag_usaha = "0"

            jsonParse()
        })
    }


    fun jsonParse() {

        val intent = intent
        var tokennya= intent.getStringExtra("Token")

        //url
        val url:String = "http://test-api.online/covid-rt/public/warga/kesejahteraan"

        //RequestQueue initialized
        val mRequestQueue = Volley.newRequestQueue(this)

        //String Request initialized
        val mStringRequest = object : StringRequest(Request.Method.POST, url, Response.Listener { response ->
            Toast.makeText(applicationContext, "Data Berhasil Diupload", Toast.LENGTH_SHORT).show()
            var gotomainwarga = Intent(this, MainWargaActivity::class.java)
            gotomainwarga.putExtra("Token", tokennya)
            startActivity(gotomainwarga)


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
                params2["penghasilan"] = penghasilan
                params2["flag_phk"] = flag_phk
                params2["flag_usaha"] = flag_usaha
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

    fun jsonParseGet() {

        val intent = intent
        var tokennya= intent.getStringExtra("Token")

        //url
        val url:String = "http://test-api.online/covid-rt/public/warga/get-kesejahteraan"

        //RequestQueue initialized
        val mRequestQueue = Volley.newRequestQueue(this)

        //String Request initialized
        val mStringRequest = object : JsonObjectRequest(Request.Method.GET, url,null, Response.Listener { response ->

            penghasilan = response.getString("penghasilan")
            flag_phk = response.getString("flag_phk")
            flag_usaha = response.getString("flag_usaha")

            if (penghasilan == "1"){
                rb_penghasilan_1.isChecked = true
            }
            else if (penghasilan == "2"){
                rb_penghasilan_2.isChecked = true
            }
            else if (penghasilan == "3"){
                rb_penghasilan_3.isChecked = true
            }
            else{
                rb_penghasilan_4.isChecked = true
            }

            if (flag_phk == "1"){
                cb_phk_iya.isChecked = true
            }

            if (flag_usaha == "1"){
                cb_usaha_iya.isChecked = true
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