package com.ariqhikari.sortingcomparison

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ariqhikari.sortingcomparison.databinding.ActivityMainBinding
import com.ariqhikari.sortingcomparison.model.AdapterMain
import com.ariqhikari.sortingcomparison.model.QuoteModel
import com.ariqhikari.sortingcomparison.model.QuoteResponse
import com.ariqhikari.sortingcomparison.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    lateinit var adapterMain: AdapterMain

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        getQuotes()
    }

    private fun setupRecyclerView() {
        adapterMain = AdapterMain(arrayListOf(), object: AdapterMain.OnAdapterListener {
            override fun onClick(quote: QuoteModel) {

            }
        })

        binding.adapter.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterMain
        }
    }

    fun getQuotes(name: String = "all") {
        adapterMain.quotes.clear()
        adapterMain.notifyDataSetChanged()

        showLoading(true)

        ApiService().endpoint.getQuotes(100)
            .enqueue(object: Callback<QuoteResponse> {
                override fun onResponse(
                    call: Call<QuoteResponse>,
                    response: Response<QuoteResponse>
                ) {
                    showLoading(false)

                    if(response.isSuccessful) {
                        response.body()?.let {
                            if(name === "bubble_sort") {
                                adapterMain.bubbleSort(it.quotes.toMutableList())
                            } else if(name === "selection_sort") {
                                adapterMain.selectionSort(it.quotes.toMutableList())
                            } else {
                                adapterMain.setData( it.quotes.toMutableList() )
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<QuoteResponse>, t: Throwable) {
                    showLoading(false)
                }

            })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        val inflater = menuInflater
        inflater.inflate(R.menu.optionmenu, menu)
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() === R.id.bubble_sort) {
            getQuotes("bubble_sort")
        } else if (item.getItemId() === R.id.selection_sort) {
            getQuotes("selection_sort")
        } else if (item.getItemId() === R.id.reset) {
            getQuotes()
        }
        return true
    }

    fun showLoading(loading: Boolean) {
        when(loading) {
            true -> binding.progress.visibility = View.VISIBLE
            false -> binding.progress.visibility = View.GONE
        }
    }
}