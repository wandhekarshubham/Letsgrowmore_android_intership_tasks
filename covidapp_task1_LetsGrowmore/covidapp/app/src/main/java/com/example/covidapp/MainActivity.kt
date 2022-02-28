package com.example.covidapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.apiapp.Listitems
import com.example.apiapp.MySingleton
import com.example.apiapp.adapter
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    var recyclerView:RecyclerView?=null
    var myadapter:adapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView=findViewById(R.id.recyclerview_main)

        myadapter= adapter(this)

        fetchdata()
        recyclerView?.layoutManager=LinearLayoutManager(this)
        recyclerView?.adapter=myadapter




    }

    private fun fetchdata() {
        val url = "https://data.covid19india.org/state_district_wise.json"
        var newarray=ArrayList<Listitems>()

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                val jsonObject=JSONObject(response.toString())
                var keystate_base:Iterator<String>?=null

                keystate_base=jsonObject.keys()
                while (keystate_base.hasNext()){
                    //state name by : keybase_state_name
                    var keystate_state_name=keystate_base.next()
                    //Log.e("state",keystate_state_name.toString())

                    var object_state_name=jsonObject.getJSONObject(keystate_state_name)
                    var object_state_data=object_state_name.getJSONObject("districtData")

                    val key_district_base:Iterator<String>?=object_state_data.keys()

                    while (key_district_base!!.hasNext()){
                        var key_district_name=key_district_base.next()
                        Log.e("districtname",key_district_name.toString())

                        var object_district_data=object_state_data.getJSONObject(key_district_name)
                        var object_delta_data=object_district_data.getJSONObject("delta")

                        var active="Active: "+object_district_data.getString("active")
                        var confirmed="Confirmed: "+object_district_data.getString("confirmed")
                        var deceased="deceased: "+object_district_data.getString("deceased")
                        var recovered="recovered: "+object_district_data.getString("recovered")
                        Log.e("active",active.toString())
                        Log.e("confirmed",confirmed.toString())
                        Log.e("deceased",deceased.toString())
                        Log.e("recovered",recovered.toString())

                        var confirmed2="Delta confirmed: "+object_delta_data.getString("confirmed")
                        var deceased2="Delta deceased: "+object_delta_data.getString("deceased")
                        var recovered2="Delta recovered: "+object_delta_data.getString("recovered")
                        Log.e("confirmed2",confirmed2.toString())
                        Log.e("deceased2",deceased2.toString())
                        Log.e("recovered2",recovered2.toString())


                        var newcoviddata=Listitems(key_district_name,active,confirmed,deceased,recovered,confirmed2,deceased2,recovered2)
                        newarray.add(newcoviddata)
                    }
                }

                myadapter?.update(newarray)


            },
            { error ->

            }
        )

// Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
}