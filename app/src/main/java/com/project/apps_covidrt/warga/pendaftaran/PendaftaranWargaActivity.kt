package com.project.apps_covidrt.warga.pendaftaran

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.project.apps_covidrt.R
import com.project.apps_covidrt.rt.menuutama.MainActivity
import com.project.apps_covidrt.warga.menuutama.MainWargaActivity
import kotlinx.android.synthetic.main.activity_pendaftaran_warga.*
import java.text.SimpleDateFormat
import java.util.*

class PendaftaranWargaActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var datePicker: DatePickerHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pendaftaran_warga)

        datePicker = DatePickerHelper(this, true)
        btn_warga_tanggallahir.setOnClickListener {
            showDatePickerDialog()
        }
        btn_daftar_warga.setOnClickListener(this)
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
                et_warga_tanggallahir.setText("${dayStr}-${monthStr}-${year}")
            }
        })
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_daftar_warga -> {
                Toast.makeText(this, "Pendaftaran berhasil", Toast.LENGTH_SHORT).show()
                val gotomainwarga = Intent(this, MainWargaActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
    }

}