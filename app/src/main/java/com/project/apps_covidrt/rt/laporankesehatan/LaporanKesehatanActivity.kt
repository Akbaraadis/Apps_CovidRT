package com.project.apps_covidrt.rt.laporankesehatan

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.itextpdf.text.*
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import com.project.apps_covidrt.R
import com.project.apps_covidrt.warga.pendaftaran.DatePickerHelper
import kotlinx.android.synthetic.main.activity_laporan_kesehatan.*
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class LaporanKesehatanActivity : AppCompatActivity() {

    lateinit var datePicker: DatePickerHelper
    var inidia = "0"
    private val STORAGE_CODE: Int = 100
    lateinit var datasehat: String
    lateinit var datasakit: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laporan_kesehatan)

        datePicker = DatePickerHelper(this, true)

        btn_kshtn_awal.setOnClickListener{
            showDatePickerDialog()
        }
        btn_kshtn_akhir.setOnClickListener{
            showDatePickerDialog2()
        }
        btn_kesehatan_tampilkan.setOnClickListener{
            if(TextUtils.isEmpty(et_kshtn_awal.getText().toString()) || TextUtils.isEmpty(et_kshtn_akhir.getText().toString())){
                Toast.makeText(this, "Keterangan waktu belum dipilih", Toast.LENGTH_SHORT).show()
            }
            else
            {
                if(inidia == "0"){
                    jsonParseGet()
                    btn_kesehatan_tampilkan.setVisibility(View.GONE)
                }
                else{
                    any_chart_view.clear()
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

        btn_kesehatan_download.setOnClickListener{
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
                if(checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                    val permissions = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    requestPermissions(permissions, STORAGE_CODE)
                }
                else{
                    savePDF()
                }
            }
            else{
                savePDF()
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

    private fun savePDF() {
        //Object atau class
        val mDoc = Document()
        mDoc.setPageSize(PageSize.A4.rotate())

        //pdf file name
        val mFileName = "Laporan Kesehatan Warga " + SimpleDateFormat("dd-MM-yyyy, HH:mm:ss", Locale.getDefault()).format(System.currentTimeMillis())

        //file path
        val mFilePath = Environment.getExternalStorageDirectory().toString() + "/" + mFileName + ".pdf"
        try {
            val writer = PdfWriter.getInstance(mDoc, FileOutputStream(mFilePath))

            mDoc.open()

            //Text Paragraph
            val mText1 = "Jumlah Warga Sehat dan Sakit"
            val mText2 = tv_kesehatan_text_namasakit.text.toString()

            val Tanggal_waktu = SimpleDateFormat("dd-MM-yyyy, HH:mm:ss", Locale.getDefault()).format(System.currentTimeMillis())

            mDoc.addAuthor("Siaga Covid-19")

            //Pie Chart
            val table = PdfPTable(2)
            table.setWidthPercentage(20F)
            table.setWidths(intArrayOf(1, 2))
            table.horizontalAlignment = PdfPTable.ALIGN_LEFT

            var cell = PdfPCell(Phrase("Sehat"))
            cell.horizontalAlignment = Element.ALIGN_CENTER
            table.addCell(cell)

            cell = PdfPCell(Phrase(datasehat))
            cell.horizontalAlignment = Element.ALIGN_CENTER
            table.addCell(cell)

            cell = PdfPCell(Phrase("Sakit"))
            cell.horizontalAlignment = Element.ALIGN_CENTER
            table.addCell(cell)

            cell = PdfPCell(Phrase(datasakit))
            cell.horizontalAlignment = Element.ALIGN_CENTER
            table.addCell(cell)

            //Table Bantuan
            val table3 = PdfPTable(6)
            table3.setWidthPercentage(100F)
            table3.setWidths(intArrayOf(1, 3, 5, 1, 3, 5))
            table3.horizontalAlignment = PdfPTable.ALIGN_LEFT

            // Adding cells to the table
            var cell3 = PdfPCell(Phrase("No"))
            cell3.horizontalAlignment = Element.ALIGN_CENTER
            table3.addCell(cell3)

            cell3 = PdfPCell(Phrase("NIK"))
            cell3.horizontalAlignment = Element.ALIGN_CENTER
            table3.addCell(cell3)

            cell3 = PdfPCell(Phrase("Nama"))
            cell3.horizontalAlignment = Element.ALIGN_CENTER
            table3.addCell(cell3)

            cell3 = PdfPCell(Phrase("JK"))
            cell3.horizontalAlignment = Element.ALIGN_CENTER
            table3.addCell(cell3)

            cell3 = PdfPCell(Phrase("Tanggal Lahir"))
            cell3.horizontalAlignment = Element.ALIGN_CENTER
            table3.addCell(cell3)

            cell3 = PdfPCell(Phrase("Alamat"))
            cell3.horizontalAlignment = Element.ALIGN_CENTER
            table3.addCell(cell3)

            cell3 = PdfPCell(Phrase(tv_sakit_nomer.text.toString()))
            cell3.horizontalAlignment = Element.ALIGN_CENTER
            table3.addCell(cell3)

            val text32 = tv_sakit_nik.text.toString()
            val text33 = tv_sakit_nama.text.toString()

            table3.addCell(text32)
            table3.addCell(text33)

            cell3 = PdfPCell(Phrase(tv_sakit_jk.text.toString()))
            cell3.horizontalAlignment = Element.ALIGN_CENTER
            table3.addCell(cell3)

            cell3 = PdfPCell(Phrase(tv_sakit_ttl.text.toString()))
            cell3.horizontalAlignment = Element.ALIGN_CENTER
            table3.addCell(cell3)

            val text36 = tv_sakit_alamat.text.toString()

            table3.addCell(text36)

            //Masukan ke document
            mDoc.add(Paragraph(mText1))
            mDoc.add(Paragraph("\n"))
            mDoc.add(table)

            mDoc.add(Paragraph("\n\n"))
            mDoc.add(Paragraph(mText2))
            mDoc.add(Paragraph("\n"))
            mDoc.add(table3)

            mDoc.add(Paragraph("\nDokumen ini dicetak pada : " +Tanggal_waktu))

            mDoc.close()
            Toast.makeText(this, "File telah disimpan", Toast.LENGTH_SHORT).show()
        }
        catch (e: Exception){
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            STORAGE_CODE -> {
                if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    savePDF()
                }
                else{
                    Toast.makeText(this, "Gagal diakses...", Toast.LENGTH_SHORT).show()
                }
            }
        }
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

            datasehat = response.getString("jumlah_warga_sehat")
            datasakit = response.getString("jumlah_warga_sakit")

            val anyChartView = findViewById(R.id.any_chart_view) as AnyChartView

            val pie = AnyChart.pie()
            val data1: MutableList<DataEntry> = ArrayList()

            data1.add(ValueDataEntry("Sehat", datasehat.toInt()))
            data1.add(ValueDataEntry("Sakit", datasakit.toInt()))

            pie.data(data1)

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
        return
    }

}