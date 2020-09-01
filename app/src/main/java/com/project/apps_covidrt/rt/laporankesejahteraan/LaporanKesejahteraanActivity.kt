package com.project.apps_covidrt.rt.laporankesejahteraan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.project.apps_covidrt.R
import kotlinx.android.synthetic.main.activity_edit_profile_r_t.*
import kotlinx.android.synthetic.main.activity_edit_profile_warga.*
import kotlinx.android.synthetic.main.activity_laporan_kesejahteraan.*
import java.util.HashMap

class LaporanKesejahteraanActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laporan_kesejahteraan)

        jsonParseGet()


    }

    fun jsonParseGet() {

        val intent = intent
        var tokennya= intent.getStringExtra("Token")

        //url
        val url:String = "http://test-api.online/covid-rt/public/rt/laporan-kesejahteraan"

        //RequestQueue initialized
        val mRequestQueue = Volley.newRequestQueue(this)

        //String Request initialized
        val mStringRequest = object : JsonObjectRequest(Request.Method.GET, url,null, Response.Listener { response ->
            val penghasilan1 = response.getJSONObject("data_penghasilan").getString("1")
            val penghasilan2 = response.getJSONObject("data_penghasilan").getString("2")
            val penghasilan3 = response.getJSONObject("data_penghasilan").getString("3")
            val penghasilan4 = response.getJSONObject("data_penghasilan").getString("4")
            val total = response.getJSONObject("data_penghasilan").getString("total_data_penghasilan")
            tv_kesejahteraan_penghasilan.setText("$penghasilan1\n$penghasilan2\n$penghasilan3\n$penghasilan4")
            tv_kesejahteraan_penghasilan_total.setText("$total")

            val pekerjaan1 = response.getJSONObject("data_pekerjaan").getString("phk")
            val pekerjaan2 = response.getJSONObject("data_pekerjaan").getString("usaha")
            val pekerjaan3 = response.getJSONObject("data_pekerjaan").getString("bekerja")
            tv_kesejahteraan_pekerjaan.setText("$pekerjaan1\n$pekerjaan2\n$pekerjaan3")

            val jsonArray = response.getJSONArray("data_warga")
            for (i in 0 until jsonArray.length()) {
                val warga = jsonArray.getJSONObject(i)
                val nik = warga.getString("nik")
                val nama = warga.getString("nama")
                val jeniskelamin = warga.getJSONObject("warga").getString("jenis_kelamin")
                val tanggal_lahir = warga.getJSONObject("warga").getString("tanggal_lahir")
                val alamatt = warga.getJSONObject("warga").getString("alamat")
                val nomer = i+1
                if((i+1)==jsonArray.length()){
                    tv_bantuan_nomer.append("$nomer")
                    tv_bantuan_nik.append("$nik")
                    tv_bantuan_nama.append("$nama")
                    tv_bantuan_jk.append("$jeniskelamin")
                    tv_bantuan_ttl.append("$tanggal_lahir")
                    tv_bantuan_alamat.append("$alamatt")
                } else{
                    tv_bantuan_nomer.append("$nomer\n")
                    tv_bantuan_nik.append("$nik\n")
                    tv_bantuan_nama.append("$nama\n")
                    tv_bantuan_jk.append("$jeniskelamin\n")
                    tv_bantuan_ttl.append("$tanggal_lahir\n")
                    tv_bantuan_alamat.append("$alamatt\n")
                }
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