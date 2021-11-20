package com.kamikadze328.hedgehogtest.view

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.kamikadze328.hedgehogtest.R
import com.kamikadze328.hedgehogtest.databinding.FragmentJokesBinding
import com.kamikadze328.hedgehogtest.view.adapter.JokeAdapter
import com.kamikadze328.hedgehogtest.view.adapter.JokeItemDecorator
import com.kamikadze328.hedgehogtest.view.model.UIState
import com.kamikadze328.hedgehogtest.view.viewmodel.JokesViewModel

class JokesFragment : Fragment(R.layout.fragment_jokes) {
    private val viewModel: JokesViewModel by activityViewModels()

    private var _binding: FragmentJokesBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentJokesBinding.bind(view)

        setupTextWatcher()
        setupRecyclerView()
    }

    private fun setupTextWatcher() {
        binding.etJokesCount.addTextChangedListener {
            viewModel.onInputCountChanged(it.toString())
        }
    }


    private fun setupRecyclerView() {
        val recyclerView = binding.rvJokes

        val adapter = JokeAdapter()
        recyclerView.adapter = adapter

        val offset = resources.getDimension(R.dimen.big).toInt()

        recyclerView.addItemDecoration(
            JokeItemDecorator(offsetBottomLast = offset)
        )

        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is UIState.LoadingState -> startLoading()
                is UIState.ErrorState -> {
                    stopLoading()
                    setErrorText(it.exception.message)
                    adapter.submitList(emptyList())
                    it.exception.printStackTrace()
                }
                is UIState.DataState -> {
                    stopLoading()
                    setErrorText(isVisible = false)
                    adapter.submitList(it.data)
                }
            }
        }
    }

    private fun setErrorText(text: String? = null, isVisible: Boolean = true) {
        binding.errorText.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
        binding.errorText.text = if (text.isNullOrBlank()) getString(R.string.no_jokes) else text
    }

    private fun stopLoading() {
        binding.progressbarJokes.visibility = View.INVISIBLE
    }

    private fun startLoading() {
        binding.progressbarJokes.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
