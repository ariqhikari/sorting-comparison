package com.ariqhikari.sortingcomparison.retrofit

import com.ariqhikari.sortingcomparison.model.QuoteResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiEndpoint {
    @GET("quotes")
    fun getQuotes(
        @Query("limit") limit: Int
    ): Call<QuoteResponse>
}