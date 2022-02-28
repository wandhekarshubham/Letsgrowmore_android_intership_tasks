package com.example.face_detectationapp

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions


class MainActivity() : AppCompatActivity() {
    private val REQUEST_CAPTURE = 111
    var button_camera:Button?=null
    var iv_imagecamera:ImageView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_camera=findViewById(R.id.btn_camera_main)
        iv_imagecamera=findViewById(R.id.iv_capturedimage_main)

        button_camera?.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent,REQUEST_CAPTURE)
            }
        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode==111){
            recreate()
        }
        if (requestCode==REQUEST_CAPTURE&& resultCode== RESULT_OK){
            if(data!=null){
                val extra: Bundle? =data.extras
                val bitmap = extra?.get("data") as Bitmap?
                iv_imagecamera?.setImageBitmap(bitmap)
                if (bitmap != null) {
                   facedetection(bitmap)
                }
            }



        }
        else{
            Toast.makeText(applicationContext, "error to on activity result", Toast.LENGTH_SHORT).show()
        }

    }
    fun facedetection(bitmap:Bitmap){
        val options = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
            .build()
        val image = InputImage.fromBitmap(bitmap, 0)
        val detector = FaceDetection.getClient(options)
        detector.process(image)
            .addOnSuccessListener { faces ->
                if(faces.isNotEmpty()){
                    Toast.makeText(this,"face found",Toast.LENGTH_LONG).show()
                    var i=1
                    var facecount:Int?=null
                    var smileprob:Float?=null
                    var lefteyeopenprob:Float?=null
                    var righteyeopenprob:Float?=null
                    var result=""

                    for (face in faces){
                        facecount=i
                        smileprob=(face.smilingProbability!! *100)
                        lefteyeopenprob=(face.leftEyeOpenProbability!! *100)
                        righteyeopenprob=(face.rightEyeOpenProbability!! *100)
                        result=result+"Face Number: "+facecount+"\n"+
                                "SmileProb: "+smileprob+"\n"+
                                "Righteyeopen Prob: "+lefteyeopenprob+"\n"+
                                "Lefteyeopen Prob: "+righteyeopenprob+"\n\n"
                        i++

                    }
                    if (faces.equals(0)) {
                        Toast
                            .makeText(this@MainActivity,
                                "NO FACE DETECT",
                                Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        val bundle = Bundle()
                        bundle.putString(
                            LCOFaceDetection.RESULT_TEXT,
                            result)
                        val resultDialog=ResultDialog()
                        resultDialog.arguments = bundle
                        resultDialog.isCancelable = true
                        resultDialog.show(fragmentManager,LCOFaceDetection.RESULT_DIALOG)
                    }



                }
                else{
                    Toast.makeText(this,"face not found",Toast.LENGTH_LONG).show()

                }
            }
            .addOnFailureListener { e ->
                var error=e.message.toString()
                Toast.makeText(this@MainActivity, "Oops, Something went wrong error:  \n"+error, Toast.LENGTH_SHORT).show();


            }

    }
    }
