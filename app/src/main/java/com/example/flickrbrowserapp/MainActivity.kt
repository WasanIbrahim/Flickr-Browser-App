package com.example.flickrbrowserapp


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flickrbrowserapp.databinding.ActivityMainBinding
import com.example.flickrbrowserapp.temp.Photo
import com.example.flickrbrowserapp.temp.Photos
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var recyclerViewAdapter: RecyclerView
    private lateinit var myAdapter: RecyclerViewAdapter
    private lateinit var images: ArrayList<Photo>

    private val apiInterface by lazy { APIClient().getClient()?.create(APIInterface::class.java) }
    var searchedTopic = ""
    var api_key = "97e862491d7cb14bf97c36f9bbf15bdc"
    var searchMethod = "text"
    var baseURL = ""

    //?method=flickr.photos.search -> type of service is searching
    //&api_key=$api_key -> api key
    //&format=json&&nojsoncallback=1  -> format
    //&extras=url_h,tags -> to use the image url
    //&text=cat -> topic of search


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerViewAdapter = binding.mainRV
        recyclerViewAdapter.layoutManager = LinearLayoutManager(this)

        binding.textSearchButton.setOnClickListener {
            //searching for text
            images = arrayListOf()
            searchedTopic = binding.searchTopic.text.toString()
            baseURL =
                "https://www.flickr.com/services/rest/?method=flickr.photos.search&api_key=$api_key&format=json&&nojsoncallback=1&extras=url_h,tags&$searchMethod=$searchedTopic"
            getAPI(searchedTopic)
        }
    }

    private fun getAPI(topic: String) {

        val dataReceived =
            apiInterface?.getData("?method=flickr.photos.search&api_key=$api_key&format=json&nojsoncallback=1&extras=url_h,tags&$searchMethod=$searchedTopic")
        dataReceived?.enqueue(object : Callback<Photos> {
            override fun onResponse(
                call: Call<Photos>,
                response: Response<Photos>
            ) {
                var myResponse = response.body()
                println("here is my response: $myResponse")

                if (myResponse != null) {
                    for (i in myResponse) {
                        images.add(i)
                        recyclerViewAdapter.adapter = RecyclerViewAdapter(images)
                        recyclerViewAdapter.adapter?.notifyDataSetChanged()
                    }
                }
                myAdapter.update(images)

            }

            override fun onFailure(call: Call<Photos>, t: Throwable) {
                Log.d("response", "failed to get data , ${t.message}")

            }
        })
    }

}