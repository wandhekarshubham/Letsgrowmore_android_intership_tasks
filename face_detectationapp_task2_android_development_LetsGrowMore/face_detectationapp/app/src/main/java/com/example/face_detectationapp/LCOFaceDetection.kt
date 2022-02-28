package com.example.face_detectationapp

import android.app.Application
import com.google.firebase.FirebaseApp




public class LCOFaceDetection:Application(){


    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
    companion object{
        val RESULT_TEXT = "RESULT_TEXT"
        val RESULT_DIALOG = "RESULT_DIALOG"
    }
}