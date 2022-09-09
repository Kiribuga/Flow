package com.example.flow.util

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.annotation.LayoutRes
import com.example.flow.R
import com.example.flow.db.movie.MovieType
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun EditText.textChangesFlow(): Flow<String> {
    return callbackFlow {
        val textChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                trySendBlocking(s?.toString().orEmpty())
            }
        }
        this@textChangesFlow.addTextChangedListener(textChangedListener)
        awaitClose {
            this@textChangesFlow.removeTextChangedListener(textChangedListener)
        }
    }
}

fun RadioGroup.checkedChangesFlow(): Flow<MovieType> {
    return callbackFlow {
        val checkedChangesListener = RadioGroup.OnCheckedChangeListener { _, checkedId ->
            val rbMovie = findViewById<RadioButton>(R.id.rbMovie)
            val rbSeries = findViewById<RadioButton>(R.id.rbSeries)
            trySendBlocking(
                when {
                    findViewById<RadioButton>(checkedId) == rbMovie -> {
                        MovieType.MOVIE
                    }
                    findViewById<RadioButton>(checkedId) == rbSeries -> {
                        MovieType.SERIES
                    }
                    else -> {
                        MovieType.EPISODE
                    }
                }
            )
        }
        setOnCheckedChangeListener(checkedChangesListener)
        awaitClose {
            setOnCheckedChangeListener(null)
        }
    }
}