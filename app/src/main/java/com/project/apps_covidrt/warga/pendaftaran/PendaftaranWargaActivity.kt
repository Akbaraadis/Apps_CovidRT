package com.project.apps_covidrt.warga.pendaftaran

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Request.Method.POST
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.project.apps_covidrt.LoginActivity
import com.project.apps_covidrt.R
import com.project.apps_covidrt.warga.menuutama.MainWargaActivity
import kotlinx.android.synthetic.main.activity_pendaftaran_rt.*
import kotlinx.android.synthetic.main.activity_pendaftaran_warga.*
import org.json.JSONException
import org.json.JSONObject
import java.security.AccessController.getContext
import java.util.*
import java.util.concurrent.TimeUnit


class PendaftaranWargaActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var datePicker: DatePickerHelper
    var jenis_kelamin = "P"
    var flag_hamil = "0"
    var flag_paru = "0"
    var flag_jantung = "0"
    var flag_autoimun = "0"
    var flag_diabet = "0"
    var flag_liver = "0"
    var flag_ginjal = "0"
    var flag_hipertensi = "0"
    var flag_perokok = "0"

    var check:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pendaftaran_warga)

        datePicker = DatePickerHelper(this, true)
        btn_warga_tanggallahir.setOnClickListener {
            showDatePickerDialog()
        }
        btn_daftar_warga.setOnClickListener(this)

        rb_lk.setOnClickListener {
            ll_hamil.visibility = View.GONE
            rb_hamil_tidak.isChecked = true
        }
        rb_pr.setOnClickListener {
            ll_hamil.visibility = View.VISIBLE
        }

        val status = resources.getStringArray(R.array.Kecamatan)

        // access the spinner
        val spinner = findViewById<Spinner>(R.id.sp_daftarwarga_kec)
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
    }

    fun checkkelurahan(){
        if (check == 0) {
            val status_lurah = resources.getStringArray(R.array.Kecamatan_Ciledug)

            // access the spinner
            val spinner_lurah = findViewById<Spinner>(R.id.sp_daftarwarga_kel)
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
            val spinner_lurah = findViewById<Spinner>(R.id.sp_daftarwarga_kel)
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
            val spinner_lurah = findViewById<Spinner>(R.id.sp_daftarwarga_kel)
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
            val spinner_lurah = findViewById<Spinner>(R.id.sp_daftarwarga_kel)
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
            val spinner_lurah = findViewById<Spinner>(R.id.sp_daftarwarga_kel)
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
            val spinner_lurah = findViewById<Spinner>(R.id.sp_daftarwarga_kel)
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
            val spinner_lurah = findViewById<Spinner>(R.id.sp_daftarwarga_kel)
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
            val spinner_lurah = findViewById<Spinner>(R.id.sp_daftarwarga_kel)
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
            val spinner_lurah = findViewById<Spinner>(R.id.sp_daftarwarga_kel)
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
            val spinner_lurah = findViewById<Spinner>(R.id.sp_daftarwarga_kel)
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
            val spinner_lurah = findViewById<Spinner>(R.id.sp_daftarwarga_kel)
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
            val spinner_lurah = findViewById<Spinner>(R.id.sp_daftarwarga_kel)
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
            val spinner_lurah = findViewById<Spinner>(R.id.sp_daftarwarga_kel)
            if (spinner_lurah != null) {
                val adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item, status_lurah
                )
                spinner_lurah.adapter = adapter
            }
        }
    }

    private fun showDatePickerDialog() {
        val cal = Calendar.getInstance()
        val d = cal.get(Calendar.DAY_OF_MONTH)
        val m = cal.get(Calendar.MONTH)
        val y = cal.get(Calendar.YEAR)

        val maxDate = Calendar.getInstance()
        maxDate.add(Calendar.DAY_OF_MONTH, 0)
        datePicker.setMaxDate(maxDate.timeInMillis)

        datePicker.showDialog(d, m, y, object : DatePickerHelper.Callback {
            override fun onDateSelected(dayofMonth: Int, month: Int, year: Int) {
                val dayStr = if (dayofMonth < 10) "0${dayofMonth}" else "${dayofMonth}"
                val mon = month + 1
                val monthStr = if (mon < 10) "0${mon}" else "${mon}"
                et_warga_tanggallahir.setText("${year}-${monthStr}-${dayStr}")
            }
        })
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_daftar_warga -> {
                if(rb_lk.isChecked)
                    jenis_kelamin = "L"
                if(rb_hamil_iya.isChecked)
                    flag_hamil = "1"
                if(cb_daftarwarga_paru.isChecked)
                    flag_paru = "1"
                if(cb_daftarwarga_jantung.isChecked)
                    flag_jantung = "1"
                if(cb_daftarwarga_imun.isChecked)
                    flag_autoimun = "1"
                if(cb_daftarwarga_liver.isChecked)
                    flag_liver = "1"
                if(cb_daftarwarga_ginjal.isChecked)
                    flag_ginjal = "1"
                if(cb_daftarwarga_hipertensi.isChecked)
                    flag_hipertensi = "1"
                if(cb_daftarwarga_perokok.isChecked)
                    flag_perokok = "1"
                if(cb_daftarwarga_diabetes.isChecked)
                    flag_diabet = "1"

                if(et_daftarwarga_rt.length() != 3 || et_daftarwarga_rw.length() != 3){
                    Toast.makeText(this,"Gunakan 3 digit pada keterangan RT dan RW", Toast.LENGTH_SHORT).show()
                }
                else if(TextUtils.isEmpty(et_daftarwarga_email.getText().toString()) || TextUtils.isEmpty(et_daftarwarga_pass.getText().toString()) ||
                        TextUtils.isEmpty(et_daftarwarga_nama.getText().toString()) || TextUtils.isEmpty(et_daftarwarga_nokk.getText().toString()) ||
                        TextUtils.isEmpty(et_daftarwarga_nik.getText().toString()) || TextUtils.isEmpty(et_warga_tanggallahir.getText().toString()) ||
                        TextUtils.isEmpty(et_daftarwarga_alamat.getText().toString()) || TextUtils.isEmpty(et_daftarwarga_rt.getText().toString()) ||
                        TextUtils.isEmpty(et_daftarwarga_rw.getText().toString()) || TextUtils.isEmpty(et_daftarwarga_nohp.getText().toString())){

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
                        val url:String = "http://test-api.online/covid-rt/public/register-warga"

                        //RequestQueue initialized
                        val mRequestQueue = Volley.newRequestQueue(this)

                        //String Request initialized
                        val mStringRequest = object : StringRequest(Request.Method.POST, url, Response.Listener { response ->
                            Toast.makeText(applicationContext, "Registrasi Berhasil", Toast.LENGTH_SHORT).show()
                            var gotomainwarga = Intent(this, LoginActivity::class.java).also {
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
                                params2["username"] = et_daftarwarga_email.text.toString().trim()
                                params2["password"] = et_daftarwarga_pass.text.toString().trim()
                                params2["nama"] = et_daftarwarga_nama.text.toString().trim()
                                params2["nik"] = et_daftarwarga_nik.text.toString().trim()
                                params2["kecamatan"] = sp_daftarwarga_kec.selectedItem.toString().trim()
                                params2["kelurahan"] = sp_daftarwarga_kel.selectedItem.toString().trim()
                                params2["rt"] = et_daftarwarga_rt.text.toString().trim()
                                params2["rw"] = et_daftarwarga_rw.text.toString().trim()
                                params2["no_kk"] = et_daftarwarga_nokk.text.toString().trim()
                                params2["jenis_kelamin"] = jenis_kelamin
                                params2["tanggal_lahir"] = et_warga_tanggallahir.text.toString().trim()
                                params2["alamat"] = et_daftarwarga_alamat.text.toString().trim()
                                params2["no_hp"] = et_daftarwarga_nohp.text.toString().trim()
                                params2["flag_hamil"] = flag_hamil
                                params2["flag_paru"] = flag_paru
                                params2["flag_jantung"] = flag_jantung
                                params2["flag_autoimun"] = flag_autoimun
                                params2["flag_diabet"] = flag_diabet
                                params2["flag_ginjal"] = flag_ginjal
                                params2["flag_liver"] = flag_liver
                                params2["flag_hipertensi"] = flag_hipertensi
                                params2["flag_perokok"] = flag_perokok
                                return JSONObject(params2 as Map<*, *>).toString().toByteArray()
                            }

                        }
        mRequestQueue!!.add(mStringRequest!!)
    }
}