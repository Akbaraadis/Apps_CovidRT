package com.project.apps_covidrt.rt.laporankesejahteraan

import android.Manifest
import android.app.ProgressDialog
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.itextpdf.text.Document
import com.itextpdf.text.PageSize
import com.itextpdf.text.Paragraph
import com.itextpdf.text.Phrase
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import com.project.apps_covidrt.R
import kotlinx.android.synthetic.main.activity_laporan_kesejahteraan.*
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*


class LaporanKesejahteraanActivity : AppCompatActivity() {

    private val STORAGE_CODE: Int = 100
    var REQUEST_PERMISSIONS = 1
    var boolean_permission = false
    var boolean_save = false
    var bitmap: Bitmap? = null
    var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laporan_kesejahteraan)

        jsonParseGet()


        btn_kesejahteraan_download.setOnClickListener(View.OnClickListener {
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

        })

    }

    fun loadBitmapFromView(v: View, width: Int, height: Int): Bitmap? {
        val b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val c = Canvas(b)
        v.draw(c)
        return b
    }

    private fun fn_permission() {
        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_PERMISSIONS
                )
            }
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    REQUEST_PERMISSIONS
                )
            }
        } else {
            boolean_permission = true
        }
    }

    private fun savePDF() {
        //Object atau class
        val mDoc = Document()
        mDoc.setPageSize(PageSize.A4.rotate())

        //pdf file name
        val mFileName = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis())

        //file path
        val mFilePath = Environment.getExternalStorageDirectory().toString() + "/" + mFileName + ".pdf"
        try {
            PdfWriter.getInstance(mDoc, FileOutputStream(mFilePath))

            mDoc.open()

            //Text Paragraph
            val mText1 = tv_kesejahteraan_text_penghasilan.text.toString()
            val mText2 = tv_kesejahteraan_text_pekerjaan.text.toString()
            val mText3 = tv_kesejahteraan_text_namawarga.text.toString()

            mDoc.addAuthor("Siaga Covid-19")

            //Table Penghasilan
            val table = PdfPTable(3)
            table.setWidthPercentage(75F)
            table.setWidths(intArrayOf(1, 4, 2))
            table.horizontalAlignment = PdfPTable.ALIGN_LEFT

            // Adding cells to the table
            table.addCell("No")
            table.addCell("Penghasilan Perbulan")
            table.addCell("Jumlah")

            val text11 = tv_kesejahteraan_penghasilan_no.text.toString()
            val text12 = tv_kesejahteraan_penghasilan_perbulan.text.toString()
            val text13 = tv_kesejahteraan_penghasilan.text.toString()
            val text14 = tv_kesejahteraan_penghasilan_total.text.toString()

            table.addCell(text11)
            table.addCell(text12)
            table.addCell(text13)
            val cell: PdfPCell
            cell = PdfPCell(Phrase("Total"))
            cell.colspan = 2
            table.addCell(cell)
            table.addCell(text14)

            //Table Pekerjaan
            val table2 = PdfPTable(3)
            table2.setWidthPercentage(75F)
            table2.setWidths(intArrayOf(1, 4, 2))
            table2.horizontalAlignment = PdfPTable.ALIGN_LEFT

            // Adding cells to the table
            table2.addCell("No")
            table2.addCell("Status Pekerjaan")
            table2.addCell("Jumlah")

            val text21 = tv_kesejahteraan_pekerjaan_no.text.toString()
            val text22 = tv_kesejahteraan_pekerjaan_perbulan.text.toString()
            val text23 = tv_kesejahteraan_pekerjaan.text.toString()

            table2.addCell(text21)
            table2.addCell(text22)
            table2.addCell(text23)

            //Table Bantuan
            val table3 = PdfPTable(6)
            table3.setWidthPercentage(100F)
            table3.setWidths(intArrayOf(1, 3, 3, 2, 2, 4))
            table3.horizontalAlignment = PdfPTable.ALIGN_LEFT

            // Adding cells to the table
            table3.addCell("No")
            table3.addCell("NIK")
            table3.addCell("Nama")
            table3.addCell("Jenis Kelamin")
            table3.addCell("Tanggal Lahir")
            table3.addCell("Alamat")

            val text31 = tv_bantuan_nomer.text.toString()
            val text32 = tv_bantuan_nik.text.toString()
            val text33 = tv_bantuan_nama.text.toString()
            val text34 = tv_bantuan_jk.text.toString()
            val text35 = tv_bantuan_ttl.text.toString()
            val text36 = tv_bantuan_alamat.text.toString()

            table3.addCell(text31)
            table3.addCell(text32)
            table3.addCell(text33)
            table3.addCell(text34)
            table3.addCell(text35)
            table3.addCell(text36)

            //Masukan ke document
            mDoc.add(Paragraph(mText1))
            mDoc.add(Paragraph("\n"))
            mDoc.add(table)

            mDoc.add(Paragraph("\n\n"))
            mDoc.add(Paragraph(mText2))
            mDoc.add(Paragraph("\n"))
            mDoc.add(table2)

            mDoc.add(Paragraph("\n\n"))
            mDoc.add(Paragraph(mText3))
            mDoc.add(Paragraph("\n"))
            mDoc.add(table3)

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
        val url:String = "http://test-api.online/covid-rt/public/rt/laporan-kesejahteraan"

        //RequestQueue initialized
        val mRequestQueue = Volley.newRequestQueue(this)

        //String Request initialized
        val mStringRequest = object : JsonObjectRequest(Request.Method.GET, url,null, Response.Listener { response ->
            val penghasilan1 = response.getJSONObject("data_penghasilan").getString("1")
            val penghasilan2 = response.getJSONObject("data_penghasilan").getString("2")
            val penghasilan3 = response.getJSONObject("data_penghasilan").getString("3")
            val penghasilan4 = response.getJSONObject("data_penghasilan").getString("4")
            val total = response.getJSONObject("data_penghasilan").getString("total_data_penghasilan")
            tv_kesejahteraan_penghasilan.setText("$penghasilan1\n$penghasilan2\n$penghasilan3\n$penghasilan4")
            tv_kesejahteraan_penghasilan_total.setText("$total")

            val pekerjaan1 = response.getJSONObject("data_pekerjaan").getString("phk")
            val pekerjaan2 = response.getJSONObject("data_pekerjaan").getString("usaha")
            val pekerjaan3 = response.getJSONObject("data_pekerjaan").getString("bekerja")
            tv_kesejahteraan_pekerjaan.setText("$pekerjaan1\n$pekerjaan2\n$pekerjaan3")

            val jsonArray = response.getJSONArray("data_warga")
            for (i in 0 until jsonArray.length()) {
                val warga = jsonArray.getJSONObject(i)
                val nik = warga.getString("nik")
                val nama = warga.getString("nama")
                val jeniskelamin = warga.getJSONObject("warga").getString("jenis_kelamin")
                val tanggal_lahir = warga.getJSONObject("warga").getString("tanggal_lahir")
                val alamatt = warga.getJSONObject("warga").getString("alamat")
                val nomer = i+1
                if((i+1)==jsonArray.length()){
                    tv_bantuan_nomer.append("$nomer")
                    tv_bantuan_nik.append("$nik")
                    tv_bantuan_nama.append("$nama")
                    tv_bantuan_jk.append("$jeniskelamin")
                    tv_bantuan_ttl.append("$tanggal_lahir")
                    tv_bantuan_alamat.append("$alamatt")
                } else{
                    tv_bantuan_nomer.append("$nomer\n")
                    tv_bantuan_nik.append("$nik\n")
                    tv_bantuan_nama.append("$nama\n")
                    tv_bantuan_jk.append("$jeniskelamin\n")
                    tv_bantuan_ttl.append("$tanggal_lahir\n")
                    tv_bantuan_alamat.append("$alamatt\n")
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
        mRequestQueue!!.add(mStringRequest!!)
    }
}