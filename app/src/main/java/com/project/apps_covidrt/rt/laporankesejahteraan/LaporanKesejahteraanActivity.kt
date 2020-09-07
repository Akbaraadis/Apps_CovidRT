package com.project.apps_covidrt.rt.laporankesejahteraan

import android.Manifest
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.os.Environment
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.itextpdf.text.pdf.PdfDocument
import com.project.apps_covidrt.R
import kotlinx.android.synthetic.main.activity_laporan_kesejahteraan.*
import java.io.File
import java.util.*


class LaporanKesejahteraanActivity : AppCompatActivity() {

//    private val STORAGE_CODE: Int = 100
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
//            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
//                if(checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
//                    val permissions = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                    requestPermissions(permissions, STORAGE_CODE)
//                }
//                else{
//                    savePDF()
//                }
//            }
//            else{
//                savePDF()
//            }

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

//    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>?, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions!!, grantResults)
//        if (requestCode == REQUEST_PERMISSIONS) {
//            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                boolean_permission = true
//            } else {
//                Toast.makeText(
//                    applicationContext,
//                    "Please allow the permission",
//                    Toast.LENGTH_LONG
//                ).show()
//            }
//        }
//    }
//
//    private fun createPdf() {
//        val wm =
//            getSystemService<Any>(Context.WINDOW_SERVICE) as WindowManager
//        val display = wm.defaultDisplay
//        val displaymetrics = DisplayMetrics()
//        this.windowManager.defaultDisplay.getMetrics(displaymetrics)
//        val hight = displaymetrics.heightPixels.toFloat()
//        val width = displaymetrics.widthPixels.toFloat()
//        val convertHighet = hight.toInt()
//        val convertWidth = width.toInt()
//
////        Resources mResources = getResources();
////        Bitmap bitmap = BitmapFactory.decodeResource(mResources, R.drawable.screenshot);
//        val document = PdfDocument()
//        val pageInfo: android.graphics.pdf.PdfDocument.PageInfo = AlertDialog.Builder(convertWidth, convertHighet, 1).create()
//        val page: PdfDocument.Page = document.startPage(pageInfo)
//        val canvas: Canvas = page.getCanvas()
//        val paint = Paint()
//        canvas.drawPaint(paint)
//        bitmap = Bitmap.createScaledBitmap(bitmap!!, convertWidth, convertHighet, true)
//        paint.setColor(Color.WHITE)
//        canvas.drawBitmap(bitmap, 0f, 0f, null)
//        document.finishPage(page)
//        val path: String = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
//                .toString().toString() + "/PdfTett/"
//        val dir = File(path)
//        if (!dir.exists()) dir.mkdirs()
//        val filePath = File(dir, "Testtt.pdf")
//        try {
//            document.writeTo(FileOutputStream(filePath))
//            btn_generate.setText("Check PDF")
//            boolean_save = true
//        } catch (e: IOException) {
//            e.printStackTrace()
//            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show()
//        }
//
//        // close the document
//        document.close()
//    }


//    private fun savePDF() {
//        //Object atau class
//        val mDoc = Document()
//
//        //pdf file name
//        val mFileName = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis())
//
//        //file path
//        val mFilePath = Environment.getExternalStorageDirectory().toString() + "/" + mFileName + ".pdf"
//        try {
//            PdfWriter.getInstance(mDoc, FileOutputStream(mFilePath))
//
//            mDoc.open()
//
//            val mText = tv_kesejahteraan_text_penghasilan.text.toString()
//            val mTable = table_penghasilan.toString()
//
//            mDoc.addAuthor("Siaga Covid-19")
//
////            mDoc.add(TableHeader(mText))
////            mDoc.add(TableLayout(mTable))
//        }
//        catch (e: Exception){
//            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
//        }
//    }

//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        when(requestCode){
//            STORAGE_CODE -> {
//                if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                    savePDF()
//                }
//                else{
//                    Toast.makeText(this, "Gagal diakses...", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }

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