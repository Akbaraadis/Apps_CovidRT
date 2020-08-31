package com.project.apps_covidrt.warga.pendaftaran

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
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
        }
        rb_pr.setOnClickListener {
            ll_hamil.visibility = View.VISIBLE
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

                if(TextUtils.isEmpty(et_daftarwarga_email.getText().toString()) || TextUtils.isEmpty(et_daftarwarga_pass.getText().toString()) ||
                        TextUtils.isEmpty(et_daftarwarga_nama.getText().toString()) || TextUtils.isEmpty(et_daftarwarga_nokk.getText().toString()) ||
                        TextUtils.isEmpty(et_daftarwarga_nik.getText().toString()) || TextUtils.isEmpty(et_warga_tanggallahir.getText().toString()) ||
                        TextUtils.isEmpty(et_daftarwarga_kec.getText().toString()) || TextUtils.isEmpty(et_daftarwarga_kel.getText().toString()) ||
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
                                params2["kecamatan"] = et_daftarwarga_kec.text.toString().trim()
                                params2["kelurahan"] = et_daftarwarga_kel.text.toString().trim()
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