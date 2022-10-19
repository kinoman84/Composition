package com.example.composition.presentation

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.composition.R

@BindingAdapter("requiredAnswers")
fun bindingRequiredAnswers(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.resources.getString(R.string.required_score),
        count
    )
}

@BindingAdapter("scoreAnswers")
fun bindingScoreAnswers(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.resources.getString(R.string.score_answers),
        count
    )
}

@BindingAdapter("requiredPercentage")
fun bindingRequiredPercentage(textView: TextView, percent: Int) {
    textView.text = String.format(
        textView.context.resources.getString(R.string.required_percentage),
        percent
    )
}

@BindingAdapter("countOfRightAnswers","countOfQuestions")
fun bindingScorePercentage(
    textView: TextView,
    countOfRightAnswers: Int,
    countOfQuestions: Int
) {
    var scorePercentage = 0
    if (countOfQuestions != 0) {
        scorePercentage = ((countOfRightAnswers /
                countOfQuestions.toDouble()) * 100).toInt()
    }
    textView.text = String.format(
        textView.context.resources.getString(R.string.score_percentage),
        scorePercentage.toString()
    )
}

@BindingAdapter("winner")
fun bindEmoji(imageView: ImageView, winner: Boolean) {
    val emojiResource = if (winner) {
        R.drawable.ic_smile
    } else {
        R.drawable.ic_sad
    }
    imageView.setImageResource(emojiResource)
}
