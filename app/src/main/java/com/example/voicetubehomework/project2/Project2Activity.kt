package com.example.voicetubehomework.project2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.voicetubehomework.R
import com.example.voicetubehomework.project2.setting.SettingFragment
import kotlinx.android.synthetic.main.activity_project2.*

class Project2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project2)
        //init back button
        imageView_project2_back.setOnClickListener { onBackPressed() }
        //init setting fragment
        val settingFragment = SettingFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.constraintLayout_project2_content, settingFragment)
            .commit()
    }
}