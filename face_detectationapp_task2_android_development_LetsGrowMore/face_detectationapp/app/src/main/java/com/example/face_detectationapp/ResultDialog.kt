package com.example.face_detectationapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.Nullable


class ResultDialog: android.app.DialogFragment() {
    var okBtn: Button? = null
    var resultTextView: TextView? = null

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?,
    ): View {

        // importing View so as to inflate
        // the layout of our result dialog
        // using layout inflater.
        val view: View = inflater.inflate(
            R.layout.fragment_resultdialog, container,
            false)
        var resultText = ""

        // finding the elements by their id's.
        okBtn =view.findViewById(R.id.result_ok_button)
        resultTextView = view.findViewById(R.id.result_text_view)

        // To get the result text
        // after final face detection
        // and append it to the text view.
        val bundle:Bundle = arguments

        resultText = bundle.getString(
            LCOFaceDetection.RESULT_TEXT).toString()
        resultTextView!!.text = resultText


        okBtn?.setOnClickListener(
            object : View.OnClickListener {
                override fun onClick(v: View?) {
                    dismiss()
                }
            })
        return view
    }
}