package com.project.apps_covidrt.rt.laporankesehatan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.project.apps_covidrt.R


class LaporanKesehatanActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laporan_kesehatan)

        val pie = AnyChart.pie()

        val data: MutableList<DataEntry> = ArrayList()
        data.add(ValueDataEntry("Sakit", 3))
        data.add(ValueDataEntry("Sehat", 20))

        pie.data(data)

        val anyChartView = findViewById(R.id.any_chart_view) as AnyChartView
        anyChartView.setChart(pie)
    }
}