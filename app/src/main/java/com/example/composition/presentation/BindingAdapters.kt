package com.example.composition.presentation

import android.content.res.ColorStateList
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.composition.R

interface OnOptionClickListener {

    fun onOptionClick(option: Int)
}

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

@BindingAdapter("numberByText")
fun numberByText(textView: TextView, number: Int) {
    textView.text = number.toString()
}

@BindingAdapter("percentageProgress")
fun bindPercentageProgress(progressBar: ProgressBar, percent: Int) {
    progressBar.setProgress(percent, true)
}

@BindingAdapter("minPercent")
fun bindSecondaryProgress(progressBar: ProgressBar, minPercent: Int) {
    progressBar.secondaryProgress = minPercent
}

@BindingAdapter("colorByState")
fun bindColorByState(view: View, goodState: Boolean) {

    val colorResId = if (goodState) {
        android.R.color.holo_green_light
    } else {
        android.R.color.holo_red_light
    }
    val colorResourceId = ContextCompat.getColor(view.context, colorResId)

    if (view is ProgressBar) {
        view.progressTintList = ColorStateList.valueOf(colorResourceId)
    }

    if (view is TextView) {
        view.setTextColor(colorResourceId)
    }
}

@BindingAdapter("onOptionClickListener")
fun bindOnOptionClickListener(textView: TextView, clickListener: OnOptionClickListener) {
    textView.setOnClickListener {
        clickListener.onOptionClick(textView.text.toString().toInt())
    }
}