package com.example.flickrbrowserapp


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flickrbrowserapp.databinding.ActivityMainBinding
import com.example.flickrbrowserapp.model.Photo
import com.example.flickrbrowserapp.model.Photos
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var myAdapter: RecyclerViewAdapter
    private lateinit var images: ArrayList<Photo>

    private val apiInterface by lazy { APIClient().getClient()?.create(APIInterface::class.java) }
    var api_key = "97e862491d7cb14bf97c36f9bbf15bdc"
    var searchMethod = ""
    var baseURL = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recyclerView = binding.mainRV
        recyclerView.layoutManager = LinearLayoutManager(this)
        images = arrayListOf()


        binding.textSearchButton.setOnClickListener {
            //searching for text
            searchMethod = "text"
            var searchedTopic = binding.searchTopic.text.toString()
            baseURL = "https://www.flickr.com/services/rest/?method=flickr.photos.search&api_key=$api_key&format=json&&nojsoncallback=1&extras=url_h,tags&$searchMethod=$searchedTopic"
            getAPI(searchedTopic, searchMethod)
        }

        binding.tagSearchButton.setOnClickListener {
            //searching for tags
            searchMethod = "tags"
            var searchedTopic = binding.searchTopic.text.toString()
            baseURL = "https://www.flickr.com/services/rest/?method=flickr.photos.search&api_key=$api_key&format=json&&nojsoncallback=1&extras=url_h,tags&$searchMethod=$searchedTopic"
            getAPI(searchedTopic, searchMethod)
        }
    }


    private fun getAPI(topic: String, method: String) {
        val dataReceived =
            apiInterface?.getData("?method=flickr.photos.search&api_key=$api_key&format=json&nojsoncallback=1&extras=url_h,tags&$method=$topic")
        dataReceived?.enqueue(object : Callback<Photos> {
            override fun onResponse(
                call: Call<Photos>,
                response: Response<Photos>
            ) {
                var dataList = response.body()
                println("here is my response: $dataList")

                if (dataList != null) {
                    for (i in dataList.photos.photo) {
                        images.add(i)

                        myAdapter = RecyclerViewAdapter(images)
                        recyclerView.adapter = myAdapter
                        myAdapter.update(images)
                    }
                }
            }
            override fun onFailure(call: Call<Photos>, t: Throwable) {
                Log.d("response", "failed to get data , ${t.message}")

            }
        })
    }
}