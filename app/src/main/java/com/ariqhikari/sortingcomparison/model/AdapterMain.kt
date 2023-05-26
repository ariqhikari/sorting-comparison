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

    public fun setData(newQuotes: MutableList<QuoteModel>) {
        quotes.addAll(newQuotes)
        notifyDataSetChanged()
    }

    fun bubbleSort(data: MutableList<QuoteModel>) {
        val size = data.size

        for (i in 0 until size - 1) {
            for (j in 0 until size - i - 1) {
                if (data[j].author!! > data[j + 1].author!!) {
                    val temp = data[j]
                    data[j] = data[j + 1]
                    data[j + 1] = temp
                }
            }
        }

        quotes.addAll(data)
        notifyDataSetChanged()
    }

    fun selectionSort(data: MutableList<QuoteModel>) {
        val size = data.size

        for (i in 0 until size - 1) {
            var minIndex = i
            for (j in i + 1 until size) {
                if (data[j].author!! < data[minIndex].author!!) {
                    minIndex = j
                }
            }
            val temp = data[minIndex]
            data[minIndex] = data[i]
            data[i] = temp
        }

        quotes.addAll(data)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(quote: QuoteModel)
    }
}