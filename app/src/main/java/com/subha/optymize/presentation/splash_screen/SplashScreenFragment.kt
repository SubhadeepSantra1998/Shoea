package com.subha.optymize.presentation.splash_screen

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.subha.optymize.R
import com.subha.optymize.databinding.FragmentSplashScreenBinding

class SplashScreenFragment : Fragment(R.layout.fragment_splash_screen) {


    private var _binding: FragmentSplashScreenBinding? = null
    private val binding: FragmentSplashScreenBinding?
        get() = _binding


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSplashScreenBinding.bind(view)

        setUI()

    }

    private fun setUI() {


        Toast.makeText(requireContext(), "Developed by Subhadeep Santra", Toast.LENGTH_SHORT).show()

        binding!!.motionLayout.startLayoutAnimation()
        binding!!.motionLayout.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {

            }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {

            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                findNavController().navigate(R.id.action_splashScreenFragment_to_productListFragment)
            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {

            }

        })

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}