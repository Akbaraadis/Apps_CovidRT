package com.project.apps_covidrt.rt.pendaftaran

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

    var check:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pendaftaran_rt)

        val status = resources.getStringArray(R.array.Kecamatan)

        // access the spinner
        val spinner = findViewById<Spinner>(R.id.sp_daftarrt_kecamatan)
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

        btn_daftar_rt.setOnClickListener(this)
    }

    fun checkkelurahan(){
        if (check == 0) {
            val status_lurah = resources.getStringArray(R.array.Kecamatan_Ciledug)

            // access the spinner
            val spinner_lurah = findViewById<Spinner>(R.id.sp_daftarrt_kelurahan)
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
            val spinner_lurah = findViewById<Spinner>(R.id.sp_daftarrt_kelurahan)
            if (spinner_lurah != null) {
                val adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item, status_lurah
                )
                spinner_lurah.adapter = adapter
            }
        }
        else if(check == 2){
            val status_lurah = resources.getStringArray(R.array.Kecamatan_Larangan)

            // access the spinner
            val spinner_lurah = findViewById<Spinner>(R.id.sp_daftarrt_kelurahan)
            if (spinner_lurah != null) {
                val adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item, status_lurah
                )
                spinner_lurah.adapter = adapter
            }
        }
        else if(check == 3){
            val status_lurah = resources.getStringArray(R.array.Kecamatan_Pinang)

            // access the spinner
            val spinner_lurah = findViewById<Spinner>(R.id.sp_daftarrt_kelurahan)
            if (spinner_lurah != null) {
                val adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item, status_lurah
                )
                spinner_lurah.adapter = adapter
            }
        }
        else if(check == 4){
            val status_lurah = resources.getStringArray(R.array.Kecamatan_Cipondoh)

            // access the spinner
            val spinner_lurah = findViewById<Spinner>(R.id.sp_daftarrt_kelurahan)
            if (spinner_lurah != null) {
                val adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item, status_lurah
                )
                spinner_lurah.adapter = adapter
            }
        }
        else if(check == 5){
            val status_lurah = resources.getStringArray(R.array.Kecamatan_Tangerang)

            // access the spinner
            val spinner_lurah = findViewById<Spinner>(R.id.sp_daftarrt_kelurahan)
            if (spinner_lurah != null) {
                val adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item, status_lurah
                )
                spinner_lurah.adapter = adapter
            }
        }
        else if(check == 6){
            val status_lurah = resources.getStringArray(R.array.Kecamatan_Batuceper)

            // access the spinner
            val spinner_lurah = findViewById<Spinner>(R.id.sp_daftarrt_kelurahan)
            if (spinner_lurah != null) {
                val adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item, status_lurah
                )
                spinner_lurah.adapter = adapter
            }
        }
        else if(check == 7){
            val status_lurah = resources.getStringArray(R.array.Kecamatan_Benda)

            // access the spinner
            val spinner_lurah = findViewById<Spinner>(R.id.sp_daftarrt_kelurahan)
            if (spinner_lurah != null) {
                val adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item, status_lurah
                )
                spinner_lurah.adapter = adapter
            }
        }
        else if(check == 8){
            val status_lurah = resources.getStringArray(R.array.Kecamatan_Cibodas)

            // access the spinner
            val spinner_lurah = findViewById<Spinner>(R.id.sp_daftarrt_kelurahan)
            if (spinner_lurah != null) {
                val adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item, status_lurah
                )
                spinner_lurah.adapter = adapter
            }
        }
        else if(check == 9){
            val status_lurah = resources.getStringArray(R.array.Kecamatan_Jatiuwung)

            // access the spinner
            val spinner_lurah = findViewById<Spinner>(R.id.sp_daftarrt_kelurahan)
            if (spinner_lurah != null) {
                val adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item, status_lurah
                )
                spinner_lurah.adapter = adapter
            }
        }
        else if(check == 10){
            val status_lurah = resources.getStringArray(R.array.Kecamatan_Periuk)

            // access the spinner
            val spinner_lurah = findViewById<Spinner>(R.id.sp_daftarrt_kelurahan)
            if (spinner_lurah != null) {
                val adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item, status_lurah
                )
                spinner_lurah.adapter = adapter
            }
        }
        else if(check == 11){
            val status_lurah = resources.getStringArray(R.array.Kecamatan_Neglasari)

            // access the spinner
            val spinner_lurah = findViewById<Spinner>(R.id.sp_daftarrt_kelurahan)
            if (spinner_lurah != null) {
                val adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item, status_lurah
                )
                spinner_lurah.adapter = adapter
            }
        }
        else{
            val status_lurah = resources.getStringArray(R.array.Kecamatan_Karawaci)

            // access the spinner
            val spinner_lurah = findViewById<Spinner>(R.id.sp_daftarrt_kelurahan)
            if (spinner_lurah != null) {
                val adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item, status_lurah
                )
                spinner_lurah.adapter = adapter
            }
        }
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_daftar_rt -> {
                if(et_daftarrt_rt.length() != 3 || et_daftarrt_rw.length() != 3){
                    Toast.makeText(this,"Gunakan 3 digit pada keterangan RT dan RW", Toast.LENGTH_SHORT).show()
                }
                else if(TextUtils.isEmpty(et_daftarrt_email.getText().toString()) || TextUtils.isEmpty(et_daftarrt_password.getText().toString()) ||
                        TextUtils.isEmpty(et_daftarrt_nama.getText().toString()) || TextUtils.isEmpty(et_daftarrt_nik.getText().toString())){

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
                params2["kecamatan"] = sp_daftarrt_kecamatan.selectedItem.toString().trim()
                params2["kelurahan"] = sp_daftarrt_kelurahan.selectedItem.toString().trim()
                params2["rt"] = et_daftarrt_rt.text.toString().trim()
                params2["rw"] = et_daftarrt_rw.text.toString().trim()
                return JSONObject(params2 as Map<*, *>).toString().toByteArray()
            }

        }
        mRequestQueue!!.add(mStringRequest!!)
    }
}