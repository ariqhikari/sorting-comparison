package com.ariqhikari.sortingcomparison.model

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Adapter
import androidx.recyclerview.widget.RecyclerView
import com.ariqhikari.sortingcomparison.databinding.CardViewBinding

class AdapterMain(var quotes: ArrayList<QuoteModel>, var listener: OnAdapterListener):
    RecyclerView.Adapter<AdapterMain.ViewHolder>() {
    private val TAG: String = "AdapterMain"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= ViewHolder (
        CardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = quotes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val quote = quotes[position]
        holder.binding.tvAuthor.text = quote.author
    }

    class ViewHolder(val binding: CardViewBinding): RecyclerView.ViewHolder(binding.root)

    public fun setData(newQuotes: List<QuoteModel>) {
        quotes.clear()
        quotes.addAll(newQuotes)
        notifyDataSetChanged()
    }

    public fun setDataNextPage(newMovies: List<QuoteModel>) {
        quotes.addAll(newMovies)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(quote: QuoteModel)
    }
}