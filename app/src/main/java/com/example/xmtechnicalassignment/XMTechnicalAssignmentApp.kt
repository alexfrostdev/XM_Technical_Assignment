package com.example.xmtechnicalassignment

import android.app.Application
import com.example.xmtechnicalassignment.data.di.DataComponent
import com.example.xmtechnicalassignment.data.di.DataModule

class XMTechnicalAssignmentApp : Application() {

    override fun onCreate() {
        super.onCreate()

        DataComponent.instance = DataModule(this)
    }
}