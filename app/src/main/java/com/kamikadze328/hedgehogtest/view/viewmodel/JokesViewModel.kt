package com.kamikadze328.hedgehogtest.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kamikadze328.hedgehogtest.data.dto.Joke
import com.kamikadze328.hedgehogtest.data.dto.JokesResponse
import com.kamikadze328.hedgehogtest.data.repository.JokesRepository
import com.kamikadze328.hedgehogtest.data.repository.MessageRepository
import com.kamikadze328.hedgehogtest.view.model.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.math.BigInteger
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@HiltViewModel
class JokesViewModel @Inject constructor(
    private val jokesRepository: JokesRepository,
    private val messageRepository: MessageRepository
) : ViewModel() {

    private val _state: MutableLiveData<UIState<List<Joke>>> = MutableLiveData()
    val state: LiveData<UIState<List<Joke>>> = _state

    private var subject: PublishSubject<String> = PublishSubject.create()
    private var compositeDisposable = CompositeDisposable()

    init {
        initSubject()
    }

    private fun initSubject() {
        subject = PublishSubject.create()
        val disposable = subject.observeOn(Schedulers.computation())
            .map(String::softToBigInteger)
            .distinctUntilChanged()
            .debounce(INPUT_TIMEOUT, TimeUnit.MILLISECONDS)
            .observeOn(Schedulers.io())
            .switchMap {
                if (it < BigInteger.ONE) throw IllegalStateException(messageRepository.noJokes()) else loadJokes(
                    it
                )
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::onNextJoke, ::onJokesLoadError)
        compositeDisposable.add(disposable)
    }

    fun onInputCountChanged(str: String) {
        subject.onNext(str)
    }

    private fun loadJokes(count: BigInteger): Observable<JokesResponse> {
        _state.postValue(UIState.LoadingState)
        return jokesRepository.loadJokes(count)
    }

    private fun onJokesLoadError(e: Throwable) {
        _state.value = UIState.ErrorState(e)
        initSubject()
    }

    private fun onNextJoke(jokesResponse: JokesResponse) {
        _state.value = UIState.DataState(jokesResponse.value)
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}

const val INPUT_TIMEOUT = 500L

private fun String.softToBigInteger() = try {
    toBigInteger()
} catch (e: NumberFormatException) {
    BigInteger.ZERO
}



