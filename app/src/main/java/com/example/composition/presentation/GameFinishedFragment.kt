package com.example.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.composition.R
import com.example.composition.databinding.FragmentGameFinishedBinding
import com.example.composition.domain.entity.GameResult

class GameFinishedFragment : Fragment() {

    private val args by navArgs<GameFinishedFragmentArgs>()
    private val gameResult by lazy {
        args.gameResult
    }

    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("FragmentGameFinishedBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        displayGameResult()

        binding.buttonRetry.setOnClickListener {
            restartGame()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun displayGameResult() {
        displayEmoji()
        displayRequiredAnswers()
        displayScoreAnswers()
        displayRequiredPercentage()
        displayScorePercentage()

    }

    private fun displayEmoji() {
        val emojiResource = if (gameResult.winner) {
            R.drawable.ic_smile
        } else {
            R.drawable.ic_sad
        }
        binding.emojiResult.setImageResource(emojiResource)
    }

    private fun displayRequiredAnswers() {
        binding.tvRequiredAnswers.text = String.format(
            requireContext().resources.getString(R.string.required_score),
            gameResult.gameSettings.minCountOfRightAnswers.toString()
        )
    }

    private fun displayScoreAnswers() {
        binding.tvScoreAnswers.text = String.format(
            requireContext().resources.getString(R.string.score_answers),
            gameResult.countOfRightAnswers.toString()
        )
    }

    private fun displayRequiredPercentage() {
        binding.tvRequiredPercentage.text = String.format(
            requireContext().resources.getString(R.string.required_percentage),
            gameResult.gameSettings.minPercentOfRightAnswers.toString()
        )
    }

    private fun displayScorePercentage() {
        var scorePercentage = 0
        if (gameResult.countOfQuestions != 0){
            scorePercentage =((gameResult.countOfRightAnswers /
                    gameResult.countOfQuestions.toDouble()) * 100).toInt()
        }
        binding.tvScorePercentage.text = String.format(
            requireContext().resources.getString(R.string.score_percentage),
            scorePercentage.toString()
        )
    }

    private fun restartGame() {
        findNavController().popBackStack()
    }
}