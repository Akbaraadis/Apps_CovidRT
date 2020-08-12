package com.project.apps_covidrt.warga.editprofile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.project.apps_covidrt.R
import kotlinx.android.synthetic.main.activity_edit_profile_warga.*

class EditProfileWargaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile_warga)

        rb_lk.setOnClickListener{
            ll_hamil.visibility = View.GONE
        }
        rb_pr.setOnClickListener{
            ll_hamil.visibility = View.VISIBLE
        }
    }
}