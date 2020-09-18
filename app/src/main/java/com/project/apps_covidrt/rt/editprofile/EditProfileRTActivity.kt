package com.project.apps_covidrt.rt.editprofile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
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
import kotlinx.android.synthetic.main.activity_pendaftaran_warga.*
import org.json.JSONObject
import java.util.HashMap

class EditProfileRTActivity : AppCompatActivity() {

    var check:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile_r_t)

        jsonParseGet()

        val status = resources.getStringArray(R.array.Kecamatan)

        // access the spinner
        val spinner = findViewById<Spinner>(R.id.sp_editrt_kecamatan)
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
                    check = spinner.selectedItemPosition
                    checkkelurahan()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }

        btn_editrt_simpan.setOnClickListener(View.OnClickListener {
            if(et_editrt_rt.length() != 3 || et_editrt_rw.length() != 3){
                Toast.makeText(this,"Gunakan 3 digit pada keterangan RT dan RW", Toast.LENGTH_SHORT).show()
            }
            else if(TextUtils.isEmpty(et_editrt_email.getText().toString()) ||
                TextUtils.isEmpty(et_editrt_nama.getText().toString()) ||
                TextUtils.isEmpty(et_editrt_nik.getText().toString()) ||
                TextUtils.isEmpty(et_editrt_rt.getText().toString()) ||
                TextUtils.isEmpty(et_editrt_rw.getText().toString())){

                Toast.makeText(applicationContext,"Data masih ada yang belum diisi", Toast.LENGTH_LONG).show()
            }
            else{
                jsonParse()
            }
        })
    }

    fun checkkelurahan(){
        if (check == 0) {
            val status_lurah = resources.getStringArray(R.array.Kecamatan_Ciledug)

            // access the spinner
            val spinner_lurah = findViewById<Spinner>(R.id.sp_editrt_kelurahan)
            if (spinner_lurah != null) {
                val adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item, status_lurah
                )
                spinner_lurah.adapter = adapter
            }
        }
        else if (check == 1) {
            val status_lurah = resources.getStringArray(R.array.Kecamatan_Karang)

            // access the spinner
            val spinner_lurah = findViewById<Spinner>(R.id.sp_editrt_kelurahan)
            if (spinner_lurah != null) {
                val adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item, status_lurah
                )
                spinner_lurah.adapter = adapter
            }
        }
        else{
            val status_lurah = resources.getStringArray(R.array.Kecamatan_Larangan)

            // access the spinner
            val spinner_lurah = findViewById<Spinner>(R.id.sp_editrt_kelurahan)
            if (spinner_lurah != null) {
                val adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item, status_lurah
                )
                spinner_lurah.adapter = adapter
            }
        }
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

            val kecamatanedit = response.getString("kecamatan")
            val kelurahanedit = response.getString("kelurahan")
            if(kecamatanedit == "Ciledug"){
                sp_editrt_kecamatan.setSelection(0)
                sp_editrt_kelurahan.resources.getStringArray(R.array.Kecamatan_Ciledug)

                if (kelurahanedit == "Paninggilan") {
                    sp_editrt_kelurahan.setSelection(0)
                } else if (kelurahanedit == "Paninggilan Utara") {
                    sp_editrt_kelurahan.setSelection(1)
                } else if (kelurahanedit == "Parung Serab") {
                    sp_editrt_kelurahan.setSelection(2)
                } else if (kelurahanedit == "Sudimara Barat") {
                    sp_editrt_kelurahan.setSelection(3)
                } else if (kelurahanedit == "Sudimara Jaya") {
                    sp_editrt_kelurahan.setSelection(4)
                } else if (kelurahanedit == "Sudimara Selatan") {
                    sp_editrt_kelurahan.setSelection(5)
                } else if (kelurahanedit == "Sudimara Timur") {
                    sp_editrt_kelurahan.setSelection(6)
                } else {
                    sp_editrt_kelurahan.setSelection(7)
                }
            }

            else if(kecamatanedit == "Karang Tengah"){
                sp_editrt_kecamatan.setSelection(1)
                sp_editrt_kelurahan.resources.getStringArray(R.array.Kecamatan_Karang)

                if (kelurahanedit == "Karang Mulya") {
                    sp_editrt_kelurahan.setSelection(0)
                } else if (kelurahanedit == "Karang Tengah") {
                    sp_editrt_kelurahan.setSelection(1)
                } else if (kelurahanedit == "Karang Timur") {
                    sp_editrt_kelurahan.setSelection(2)
                } else if (kelurahanedit == "Parung Jaya") {
                    sp_editrt_kelurahan.setSelection(3)
                } else if (kelurahanedit == "Pedurenan") {
                    sp_editrt_kelurahan.setSelection(4)
                } else if (kelurahanedit == "Pondok Bahar") {
                    sp_editrt_kelurahan.setSelection(5)
                } else {
                    sp_editrt_kelurahan.setSelection(6)
                }
            }
            else{
                sp_editrt_kecamatan.setSelection(2)
                sp_editrt_kelurahan.resources.getStringArray(R.array.Kecamatan_Larangan)

                if(kelurahanedit == "Cipadu"){
                    sp_editrt_kelurahan.setSelection(0)
                }
                else if(kelurahanedit == "Cipadu Jaya"){
                    sp_editrt_kelurahan.setSelection(1)
                }
                else if(kelurahanedit == "Gaga"){
                    sp_editrt_kelurahan.setSelection(2)
                }
                else if(kelurahanedit == "Kreo"){
                    sp_editrt_kelurahan.setSelection(3)
                }
                else if(kelurahanedit == "Kreo Selatan"){
                    sp_editrt_kelurahan.setSelection(4)
                }
                else if(kelurahanedit == "Larangan Indah"){
                    sp_editrt_kelurahan.setSelection(5)
                }
                else if(kelurahanedit == "Larangan Selatan"){
                    sp_editrt_kelurahan.setSelection(6)
                }
                else{
                    sp_editrt_kelurahan.setSelection(7)
                }
            }

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
                params2["kecamatan"] = sp_editrt_kecamatan.selectedItem.toString().trim()
                params2["kelurahan"] = sp_editrt_kelurahan.selectedItem.toString().trim()
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