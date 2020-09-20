package com.project.apps_covidrt.warga.editprofile

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
import com.android.volley.Request.Method.*
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.project.apps_covidrt.R
import com.project.apps_covidrt.warga.menuutama.MainWargaActivity
import com.project.apps_covidrt.warga.pendaftaran.DatePickerHelper
import kotlinx.android.synthetic.main.activity_edit_profile_warga.*
import kotlinx.android.synthetic.main.activity_edit_profile_warga.ll_hamil
import kotlinx.android.synthetic.main.activity_edit_profile_warga.rb_lk2
import kotlinx.android.synthetic.main.activity_edit_profile_warga.rb_pr2
import kotlinx.android.synthetic.main.activity_pendaftaran_warga.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class EditProfileWargaActivity : AppCompatActivity() {

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
        setContentView(R.layout.activity_edit_profile_warga)

        val intent = intent
        val tokennya1 = intent.getStringExtra("Token")

        datePicker = DatePickerHelper(this, true)
        btn_edit_tanggallahir.setOnClickListener {
            showDatePickerDialog()
        }

        jsonParseGet()

        val status = resources.getStringArray(R.array.Kecamatan)

        // access the spinner
        val spinner = findViewById<Spinner>(R.id.sp_edit_kecamatan)
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

        rb_lk2.setOnClickListener{
            ll_hamil.visibility = View.GONE
        }
        rb_pr2.setOnClickListener{
            ll_hamil.visibility = View.VISIBLE
        }

        btn_edit_simpan.setOnClickListener(View.OnClickListener {
            if(rb_lk2.isChecked)
                jenis_kelamin = "L"
            else
                jenis_kelamin = "P"

            if(rb_hamil_iya2.isChecked)
                flag_hamil = "1"
            else
                flag_hamil = "0"

            if(cb_penyakit_asma.isChecked)
                flag_paru = "1"
            else
                flag_paru = "0"

            if(cb_penyakit_jantung.isChecked)
                flag_jantung = "1"
            else
                flag_jantung = "0"

            if(cb_penyakit_autoimun.isChecked)
                flag_autoimun = "1"
            else
                flag_autoimun = "0"

            if(cb_penyakit_hati.isChecked)
                flag_liver = "1"
            else
                flag_liver = "0"

            if(cb_penyakit_ginjal.isChecked)
                flag_ginjal = "1"
            else
                flag_ginjal = "0"

            if(cb_penyakit_hipertensi.isChecked)
                flag_hipertensi = "1"
            else
                flag_hipertensi = "0"

            if(cb_penyakit_perokok.isChecked)
                flag_perokok = "1"
            else
                flag_perokok = "0"

            if(cb_penyakit_diabetes.isChecked)
                flag_diabet = "1"
            else
                flag_diabet = "0"

            if(et_edit_rt.length() != 3 || et_edit_rw.length() != 3){
                Toast.makeText(this,"Gunakan 3 digit pada keterangan RT dan RW", Toast.LENGTH_SHORT).show()
            }
            else if(TextUtils.isEmpty(et_edit_email.getText().toString()) ||
                TextUtils.isEmpty(et_edit_nama.getText().toString()) || TextUtils.isEmpty(et_edit_nokk.getText().toString()) ||
                TextUtils.isEmpty(et_edit_nik.getText().toString()) || TextUtils.isEmpty(et_edit_tanggallahir.getText().toString()) ||
                TextUtils.isEmpty(et_edit_alamat.getText().toString()) || TextUtils.isEmpty(et_edit_rt.getText().toString()) ||
                TextUtils.isEmpty(et_edit_rw.getText().toString()) || TextUtils.isEmpty(et_edit_nohp.getText().toString())){

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
            val spinner_lurah = findViewById<Spinner>(R.id.sp_edit_kelurahan)
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
            val spinner_lurah = findViewById<Spinner>(R.id.sp_edit_kelurahan)
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
            val spinner_lurah = findViewById<Spinner>(R.id.sp_edit_kelurahan)
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
            val spinner_lurah = findViewById<Spinner>(R.id.sp_edit_kelurahan)
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
            val spinner_lurah = findViewById<Spinner>(R.id.sp_edit_kelurahan)
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
            val spinner_lurah = findViewById<Spinner>(R.id.sp_edit_kelurahan)
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
            val spinner_lurah = findViewById<Spinner>(R.id.sp_edit_kelurahan)
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
            val spinner_lurah = findViewById<Spinner>(R.id.sp_edit_kelurahan)
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
            val spinner_lurah = findViewById<Spinner>(R.id.sp_edit_kelurahan)
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
            val spinner_lurah = findViewById<Spinner>(R.id.sp_edit_kelurahan)
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
            val spinner_lurah = findViewById<Spinner>(R.id.sp_edit_kelurahan)
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
            val spinner_lurah = findViewById<Spinner>(R.id.sp_edit_kelurahan)
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
            val spinner_lurah = findViewById<Spinner>(R.id.sp_edit_kelurahan)
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
                et_edit_tanggallahir.setText("${year}-${monthStr}-${dayStr}")
            }
        })
    }

    fun jsonParse() {

        val intent = intent
        var tokennya= intent.getStringExtra("Token")

        //url
        val url:String = "http://test-api.online/covid-rt/public/warga/update-profile"

        //RequestQueue initialized
        val mRequestQueue = Volley.newRequestQueue(this)

        //String Request initialized
        val mStringRequest = object : StringRequest(PUT, url, Response.Listener { response ->
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
                params2["username"] = et_edit_email.text.toString().trim()
                params2["nama"] = et_edit_nama.text.toString().trim()
                params2["nik"] = et_edit_nik.text.toString().trim()
                params2["kecamatan"] = sp_edit_kecamatan.selectedItem.toString().trim()
                params2["kelurahan"] = sp_edit_kelurahan.selectedItem.toString().trim()
                params2["rt"] = et_edit_rt.text.toString().trim()
                params2["rw"] = et_edit_rw.text.toString().trim()
                params2["no_kk"] = et_edit_nokk.text.toString().trim()
                params2["jenis_kelamin"] = jenis_kelamin
                params2["tanggal_lahir"] = et_edit_tanggallahir.text.toString().trim()
                params2["alamat"] = et_edit_alamat.text.toString().trim()
                params2["no_hp"] = et_edit_nohp.text.toString().trim()
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
        val url:String = "http://test-api.online/covid-rt/public/warga/show-profile"

        //RequestQueue initialized
        val mRequestQueue = Volley.newRequestQueue(this)

        //String Request initialized
        val mStringRequest = object : JsonObjectRequest(Request.Method.GET, url,null, Response.Listener { response ->
            et_edit_email.setText(response.getString("username"))
            et_edit_nama.setText(response.getString("nama"))
            et_edit_nik.setText(response.getString("nik"))

            val kecamatanedit = response.getString("kecamatan")
            val kelurahanedit = response.getString("kelurahan")
            if(kecamatanedit == "Ciledug"){
                sp_edit_kecamatan.setSelection(0)
                sp_edit_kelurahan.resources.getStringArray(R.array.Kecamatan_Ciledug)

                    if (kelurahanedit == "Paninggilan") {
                        sp_edit_kelurahan.setSelection(0)
                    } else if (kelurahanedit == "Paninggilan Utara") {
                        sp_edit_kelurahan.setSelection(1)
                    } else if (kelurahanedit == "Parung Serab") {
                        sp_edit_kelurahan.setSelection(2)
                    } else if (kelurahanedit == "Sudimara Barat") {
                        sp_edit_kelurahan.setSelection(3)
                    } else if (kelurahanedit == "Sudimara Jaya") {
                        sp_edit_kelurahan.setSelection(4)
                    } else if (kelurahanedit == "Sudimara Selatan") {
                        sp_edit_kelurahan.setSelection(5)
                    } else if (kelurahanedit == "Sudimara Timur") {
                        sp_edit_kelurahan.setSelection(6)
                    } else {
                        sp_edit_kelurahan.setSelection(7)
                    }
            }

            else if(kecamatanedit == "Karang Tengah"){
                sp_edit_kecamatan.setSelection(1)
                sp_edit_kelurahan.resources.getStringArray(R.array.Kecamatan_Karang)

                    if (kelurahanedit == "Karang Mulya") {
                        sp_edit_kelurahan.setSelection(0)
                    } else if (kelurahanedit == "Karang Tengah") {
                        sp_edit_kelurahan.setSelection(1)
                    } else if (kelurahanedit == "Karang Timur") {
                        sp_edit_kelurahan.setSelection(2)
                    } else if (kelurahanedit == "Parung Jaya") {
                        sp_edit_kelurahan.setSelection(3)
                    } else if (kelurahanedit == "Pedurenan") {
                        sp_edit_kelurahan.setSelection(4)
                    } else if (kelurahanedit == "Pondok Bahar") {
                        sp_edit_kelurahan.setSelection(5)
                    } else {
                        sp_edit_kelurahan.setSelection(6)
                    }
            }
            else if (kecamatanedit == "Larangan"){
                sp_edit_kecamatan.setSelection(2)
                sp_edit_kelurahan.resources.getStringArray(R.array.Kecamatan_Larangan)

                    if(kelurahanedit == "Cipadu"){
                        sp_edit_kelurahan.setSelection(0)
                    }
                    else if(kelurahanedit == "Cipadu Jaya"){
                        sp_edit_kelurahan.setSelection(1)
                    }
                    else if(kelurahanedit == "Gaga"){
                        sp_edit_kelurahan.setSelection(2)
                    }
                    else if(kelurahanedit == "Kreo"){
                        sp_edit_kelurahan.setSelection(3)
                    }
                    else if(kelurahanedit == "Kreo Selatan"){
                        sp_edit_kelurahan.setSelection(4)
                    }
                    else if(kelurahanedit == "Larangan Indah"){
                        sp_edit_kelurahan.setSelection(5)
                    }
                    else if(kelurahanedit == "Larangan Selatan"){
                        sp_edit_kelurahan.setSelection(6)
                    }
                    else{
                        sp_edit_kelurahan.setSelection(7)
                    }
            }
            else if (kecamatanedit == "Pinang"){
                sp_edit_kecamatan.setSelection(3)
            }
            else if (kecamatanedit == "Cipondoh"){
                sp_edit_kecamatan.setSelection(4)
            }
            else if (kecamatanedit == "Tangerang"){
                sp_edit_kecamatan.setSelection(5)
            }
            else if (kecamatanedit == "Batu Ceper"){
                sp_edit_kecamatan.setSelection(6)
            }
            else if (kecamatanedit == "Benda"){
                sp_edit_kecamatan.setSelection(7)
            }
            else if (kecamatanedit == "Cibodas"){
                sp_edit_kecamatan.setSelection(8)
            }
            else if (kecamatanedit == "Jatiuwung"){
                sp_edit_kecamatan.setSelection(9)
            }
            else if (kecamatanedit == "Periuk"){
                sp_edit_kecamatan.setSelection(10)
            }
            else if (kecamatanedit == "Neglasari"){
                sp_edit_kecamatan.setSelection(11)
            }
            else{
                sp_edit_kecamatan.setSelection(12)
            }

            et_edit_rt.setText(response.getString("rt"))
            et_edit_rw.setText(response.getString("rw"))
            et_edit_nokk.setText(response.getJSONObject("warga").getString("no_kk"))
            et_edit_tanggallahir.setText(response.getJSONObject("warga").getString("tanggal_lahir"))
            et_edit_alamat.setText(response.getJSONObject("warga").getString("alamat"))
            et_edit_nohp.setText(response.getJSONObject("warga").getString("no_hp"))
            jenis_kelamin = response.getJSONObject("warga").getString("jenis_kelamin")
            flag_hamil = response.getJSONObject("warga").getString("flag_hamil")
            flag_jantung = response.getJSONObject("warga").getString("flag_jantung")
            flag_paru = response.getJSONObject("warga").getString("flag_paru")
            flag_autoimun= response.getJSONObject("warga").getString("flag_autoimun")
            flag_diabet = response.getJSONObject("warga").getString("flag_diabet")
            flag_ginjal = response.getJSONObject("warga").getString("flag_ginjal")
            flag_liver = response.getJSONObject("warga").getString("flag_liver")
            flag_hipertensi = response.getJSONObject("warga").getString("flag_hipertensi")
            flag_perokok = response.getJSONObject("warga").getString("flag_perokok")

            if (jenis_kelamin == "L"){
                rb_lk2.setChecked(true)
            }
            else{
                rb_pr2.setChecked(true)
            }

            if(flag_hamil == "1")
                rb_hamil_iya2.setChecked(true)
            else
                rb_hamil_tidak2.setChecked(true)

            if(flag_paru == "1")
                cb_penyakit_asma.isChecked = true
            if(flag_jantung == "1")
                cb_penyakit_jantung.setChecked(true)
            if(flag_autoimun == "1")
                cb_penyakit_autoimun.setChecked(true)
            if(flag_ginjal == "1")
                cb_penyakit_ginjal.setChecked(true)
            if(flag_liver == "1")
                cb_penyakit_hati.setChecked(true)
            if(flag_diabet == "1")
                cb_penyakit_diabetes.setChecked(true)
            if(flag_hipertensi == "1")
                cb_penyakit_hipertensi.setChecked(true)
            if(flag_perokok == "1")
                cb_penyakit_perokok.setChecked(true)


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