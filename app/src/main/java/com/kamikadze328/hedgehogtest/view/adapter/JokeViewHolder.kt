package com.kamikadze328.hedgehogtest.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kamikadze328.hedgehogtest.R
import com.kamikadze328.hedgehogtest.data.dto.Joke

class JokeViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    fun bind(joke: Joke) {
        (view as TextView).text = joke.joke
    }


    companion object {
        fun from(parent: ViewGroup): JokeViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_joke, parent, false)
            return JokeViewHolder(view)
        }
    }
}
