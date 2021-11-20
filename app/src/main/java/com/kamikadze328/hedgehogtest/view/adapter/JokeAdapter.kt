package com.kamikadze328.hedgehogtest.view.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.kamikadze328.hedgehogtest.data.dto.Joke

class JokeAdapter : ListAdapter<Joke, JokeViewHolder>(JokeCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder =
        JokeViewHolder.from(parent)

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) =
        holder.bind(getItem(position))
}
