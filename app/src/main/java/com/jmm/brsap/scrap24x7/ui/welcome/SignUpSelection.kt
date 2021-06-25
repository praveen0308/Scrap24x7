package com.jmm.brsap.scrap24x7.ui.welcome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.databinding.FragmentSignUpSelectionBinding

class SignUpSelection : Fragment() {
    //UI
    private var _binding: FragmentSignUpSelectionBinding? = null
    private val binding get() = _binding!!

    //ViewModels


    // Adapters


    // Variable


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnSignupWithEmail.setOnClickListener {
            findNavController().navigate(R.id.action_loginSignupScreen_to_signUp)

        }

            btnSignupWithGoogle.setOnClickListener {
                Toast.makeText(requireContext(), "Coming Soon !!!", Toast.LENGTH_SHORT).show()

            }
        }
    }

}