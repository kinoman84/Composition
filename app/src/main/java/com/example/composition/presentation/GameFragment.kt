package com.example.composition.presentation

import android.content.res.ColorStateList
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.composition.R
import com.example.composition.databinding.FragmentGameBinding
import com.example.composition.domain.entity.GameResult
import com.example.composition.domain.entity.Level
import com.example.composition.domain.entity.Question

class GameFragment : Fragment() {

    private lateinit var level: Level
    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(
                requireActivity().application
            )
        )[GameViewModel::class.java]
    }

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")

    private val tvOptions by lazy {
        mutableListOf<TextView>().apply {
            add(binding.tvOption1)
            add(binding.tvOption2)
            add(binding.tvOption3)
            add(binding.tvOption4)
            add(binding.tvOption5)
            add(binding.tvOption6)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            viewModel.startGame(level)
        }

        binding.progressBar.max = 100
        observeViewModel()
        initListeners()


    }

    private fun observeViewModel() {
        viewModel.question.observe(viewLifecycleOwner) {
            showQuestion(it)
        }
        viewModel.progressAnswers.observe(viewLifecycleOwner) {
            updateProgressAnswers(it)
        }
        viewModel.formattedTime.observe(viewLifecycleOwner) {
            updateTimer(it)
        }
        viewModel.gameResult.observe(viewLifecycleOwner) {
            launchGameFinishFragment(it)
        }
        viewModel.percentOfRightAnswers.observe(viewLifecycleOwner) {
            updateProgress(it)
        }
        viewModel.enoughCount.observe(viewLifecycleOwner){
            binding.tvAnswersProgress.setTextColor(getColorByState(it))
        }
        viewModel.enoughPercent.observe(viewLifecycleOwner){
            val color = getColorByState(it)
            binding.progressBar.progressTintList = ColorStateList.valueOf(color)
        }
        viewModel.minPercent.observe(viewLifecycleOwner){
            binding.progressBar.secondaryProgress = it
        }
    }

    private fun getColorByState(goodState: Boolean): Int {
        val colorResId = if (goodState) {
            android.R.color.holo_green_light
        } else {
            android.R.color.holo_red_light
        }
        return ContextCompat.getColor(requireContext(), colorResId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initListeners(){
        for (i in 0 until tvOptions.size) {
            tvOptions[i].setOnClickListener {
                viewModel.chooseAnswer(tvOptions[i].text.toString().toInt())
            }
        }
    }

    private fun updateTimer(leftTime: String) {
        binding.tvTimer.text = leftTime
    }

    private fun updateProgress(percentOfRightAnswers: Int) {
        binding.progressBar.setProgress(percentOfRightAnswers, true)
    }

    private fun updateProgressAnswers(progressAnswers: String) {
        binding.tvAnswersProgress.text = progressAnswers
    }

    private fun showQuestion(question: Question) {
        binding.tvSum.text = question.sum.toString()
        binding.tvLeftNumber.text = question.visibleNumber.toString()
        for(i in 0 until tvOptions.size) {
            tvOptions[i].text = question.options[i].toString()
        }
    }

    private fun launchGameFinishFragment(result: GameResult) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, GameFinishedFragment.newInstance(result))
            .addToBackStack(null)
            .commit()
    }

    private fun parseArgs() {
        requireArguments().getParcelable<Level>(KEY_LEVEL)?.let {
            level = it
        }
    }

    companion object {

        const val NAME = "GameFragment"
        private const val KEY_LEVEL = "level"

        fun newInstance(level: Level): GameFragment {

            return GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_LEVEL, level)
                }
            }
        }
    }

}