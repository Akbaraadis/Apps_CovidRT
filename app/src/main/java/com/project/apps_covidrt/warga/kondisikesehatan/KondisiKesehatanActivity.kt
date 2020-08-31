package com.project.apps_covidrt.warga.kondisikesehatan

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.project.apps_covidrt.R
import com.project.apps_covidrt.warga.menuutama.MainWargaActivity
import kotlinx.android.synthetic.main.activity_kondisi_kesehatan.*
import org.json.JSONObject
import java.util.*

class KondisiKesehatanActivity : AppCompatActivity() {

    var demam = "0"
    var batuk_kering = "0"
    var hidung_tersumbat = "0"
    var pilek = "0"
    var sakit_tenggorokan = "0"
    var diare = "0"
    var sulit_bernafas = "0"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kondisi_kesehatan)


        btn_kesehatan_simpan.setOnClickListener(View.OnClickListener {
            if(cb_kesehatan_demam.isChecked)
                demam = "1"
            if(cb_kesehatan_batuk.isChecked)
                batuk_kering = "1"
            if(cb_kesehatan_hidung.isChecked)
                hidung_tersumbat = "1"
            if(cb_kesehatan_pilek.isChecked)
                pilek = "1"
            if(cb_kesehatan_tenggorokan.isChecked)
                sakit_tenggorokan = "1"
            if(cb_kesehatan_diare.isChecked)
                diare = "1"
            if(cb_kesehatan_nafas.isChecked)
                sulit_bernafas = "1"
            jsonParse()
        })
    }

    fun jsonParse() {

        val intent = intent
        var tokennya= intent.getStringExtra("Token")

        //url
        val url:String = "http://test-api.online/covid-rt/public/warga/kesehatan"

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
                params2["demam"] = demam
                params2["batuk_kering"] = batuk_kering
                params2["hidung_tersumbat"] = hidung_tersumbat
                params2["pilek"] = pilek
                params2["sakit_tenggorokan"] = sakit_tenggorokan
                params2["diare"] = diare
                params2["sulit_bernafas"] = sulit_bernafas
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