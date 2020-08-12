package com.project.apps_covidrt.rt.laporankesehatan

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.project.apps_covidrt.R
import kotlinx.android.synthetic.main.activity_laporan_kesehatan.*
import java.util.*
import kotlin.collections.ArrayList


class LaporanKesehatanActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laporan_kesehatan)

        val mcurrentTime = Calendar.getInstance()
        val year = mcurrentTime.get(Calendar.YEAR)
        val month = mcurrentTime.get(Calendar.MONTH)
        val day = mcurrentTime.get(Calendar.DAY_OF_MONTH)

        val datePickerawal = DatePickerDialog(this, object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                et_kshtn_awal.setText(String.format("%d / %d / %d", dayOfMonth, month + 1, year))
            }
        }, year, month, day);

        val datePickerakhir = DatePickerDialog(this, object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                et_kshtn_akhir.setText(String.format("%d / %d / %d", dayOfMonth, month + 1, year))
            }
        }, year, month, day);

        btn_kshtn_awal.setOnClickListener{
            datePickerawal.show()
        }
        btn_kshtn_akhir.setOnClickListener{
            datePickerakhir.show()
        }

        val pie = AnyChart.pie()

        val data: MutableList<DataEntry> = ArrayList()
        data.add(ValueDataEntry("Sakit", 3))
        data.add(ValueDataEntry("Sehat", 20))

        pie.data(data)

        val anyChartView = findViewById(R.id.any_chart_view) as AnyChartView
        anyChartView.setChart(pie)
    }
}