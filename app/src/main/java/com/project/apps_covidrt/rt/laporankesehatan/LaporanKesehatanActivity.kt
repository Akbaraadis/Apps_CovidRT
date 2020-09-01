package com.project.apps_covidrt.rt.laporankesehatan

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.project.apps_covidrt.LoginActivity
import com.project.apps_covidrt.R
import com.project.apps_covidrt.warga.pendaftaran.DatePickerHelper
import kotlinx.android.synthetic.main.activity_edit_profile_warga.*
import kotlinx.android.synthetic.main.activity_laporan_kesehatan.*
import kotlinx.android.synthetic.main.activity_laporan_kesejahteraan.*
import kotlinx.android.synthetic.main.activity_pendaftaran_warga.*
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList


class LaporanKesehatanActivity : AppCompatActivity() {

    lateinit var datePicker: DatePickerHelper
    var inidia = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laporan_kesehatan)

        datePicker = DatePickerHelper(this, true)

        btn_kshtn_awal.setOnClickListener{
            showDatePickerDialog()
        }
        btn_kshtn_akhir.setOnClickListener{
            showDatePickerDialog2()
            if(inidia == "0"){
                jsonParseGet()
            }
            else{
                tv_sakit_nomer.setText("")
                tv_sakit_nik.setText("")
                tv_sakit_nama.setText("")
                tv_sakit_jk.setText("")
                tv_sakit_ttl.setText("")
                tv_sakit_alamat.setText("")
                jsonParseGet()
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
                et_kshtn_awal.setText("${year}-${monthStr}-${dayStr}")
            }
        })
    }

    private fun showDatePickerDialog2() {
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
                et_kshtn_akhir.setText("${year}-${monthStr}-${dayStr}")
            }
        })
    }

    fun jsonParseGet() {

        val intent = intent
        var tokennya= intent.getStringExtra("Token")

        //url
        val url:String = "http://test-api.online/covid-rt/public/rt/laporan-kesehatan?tanggal_awal${et_kshtn_awal.text}=&tanggal_akhir=${et_kshtn_akhir.text}"

        //RequestQueue initialized
        val mRequestQueue = Volley.newRequestQueue(this)

        //String Request initialized
        val mStringRequest = object : JsonObjectRequest(Request.Method.GET, url,null, Response.Listener { response ->

            val datasehat = response.getString("jumlah_warga_sehat")
            val datasakit = response.getString("jumlah_warga_sakit")

            val pie = AnyChart.pie()

            val data: MutableList<DataEntry> = ArrayList()
            data.add(ValueDataEntry("Sehat", datasehat.toInt()))
            data.add(ValueDataEntry("Sakit", datasakit.toInt()))

            pie.data(data)

            val anyChartView = findViewById(R.id.any_chart_view) as AnyChartView
            anyChartView.setChart(pie)


            val jsonArray = response.getJSONArray("data_warga_sakit")
            for (i in 0 until jsonArray.length()) {
                val warga = jsonArray.getJSONObject(i)
                val nik = warga.getString("nik")
                val nama = warga.getString("nama")
                val jeniskelamin = warga.getJSONObject("warga").getString("jenis_kelamin")
                val tanggal_lahir = warga.getJSONObject("warga").getString("tanggal_lahir")
                val alamatt = warga.getJSONObject("warga").getString("alamat")
                val nomer = i+1
                if((i+1)==jsonArray.length()){
                    tv_sakit_nomer.append("$nomer")
                    tv_sakit_nik.append("$nik")
                    tv_sakit_nama.append("$nama")
                    tv_sakit_jk.append("$jeniskelamin")
                    tv_sakit_ttl.append("$tanggal_lahir")
                    tv_sakit_alamat.append("$alamatt")
                } else{
                    tv_sakit_nomer.append("$nomer\n")
                    tv_sakit_nik.append("$nik\n")
                    tv_sakit_nama.append("$nama\n")
                    tv_sakit_jk.append("$jeniskelamin\n")
                    tv_sakit_ttl.append("$tanggal_lahir\n")
                    tv_sakit_alamat.append("$alamatt\n")
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
        inidia = "2"
        mRequestQueue!!.add(mStringRequest!!)
    }

}