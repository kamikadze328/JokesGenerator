package com.kamikadze328.hedgehogtest.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.kamikadze328.hedgehogtest.data.dto.Joke

class JokeCallback : DiffUtil.ItemCallback<Joke>() {
    override fun areContentsTheSame(oldItem: Joke, newItem: Joke): Boolean = oldItem == newItem
    override fun areItemsTheSame(oldItem: Joke, newItem: Joke): Boolean = oldItem.id == newItem.id
}
