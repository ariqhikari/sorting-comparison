package com.ariqhikari.sortingcomparison

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
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
    lateinit var quotes : ArrayList<QuoteModel>

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

    fun getQuotes() {
//        showLoading(true)

        ApiService().endpoint.getQuotes(100)
            .enqueue(object: Callback<QuoteResponse> {
                override fun onResponse(
                    call: Call<QuoteResponse>,
                    response: Response<QuoteResponse>
                ) {
//                    showLoading(false)
                    if(response.isSuccessful) {
                        Log.d("DEWA", response.body()!!.toString())
                        showQuote(response.body()!!)
                    }
                }

                override fun onFailure(call: Call<QuoteResponse>, t: Throwable) {
//                    showLoading(false)
                }

            })
    }

    private fun showQuote(quote: QuoteResponse?){
        quote?.let {
            quotes = quote.quotes as ArrayList<QuoteModel>
            adapterMain.setData( quotes )
        }
    }

    private fun bubbleSort() {
        // algoritma bubble sort
        val sortedQuotes = quotes.toMutableList()

        for (i in 0 until sortedQuotes.size - 1) {
            for (j in 0 until sortedQuotes.size - i - 1) {
                if (sortedQuotes[j].author!! > sortedQuotes[j + 1].author!!) {
                    val temp = sortedQuotes[j]
                    sortedQuotes[j] = sortedQuotes[j + 1]
                    sortedQuotes[j + 1] = temp
                }
            }
        }

        quotes.clear()
        quotes.addAll(sortedQuotes)
        adapterMain.notifyDataSetChanged()
    }

    private fun selectionSort() {
        // algoritma selection sort
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

        } else if (item.getItemId() === R.id.selection_sort) {
            selectionSort()
        }
        return true
    }
}