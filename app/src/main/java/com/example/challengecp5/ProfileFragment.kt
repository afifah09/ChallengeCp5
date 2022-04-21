package com.example.challengecp5

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.challengecp5.databinding.FragmentHomeBinding
import com.example.challengecp5.databinding.FragmentProfileBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    lateinit var authRepository: AuthRepository
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authRepository = AuthRepository(requireContext())
        val sharedPreference = context?.getSharedPreferences("SHARED_FILE", Context.MODE_PRIVATE)
        val email = sharedPreference?.getString("email","")
        var userid : Int? = -1
        //untuk mengambil user berdasarkan email
        lifecycleScope.launch(Dispatchers.IO){
            val user = authRepository.getuser(email.toString())
            activity?.runOnUiThread {
                binding.apply {
                    etUpdateUsername.setText(user?.username)
                    etUpdateEmail.setText(user?.email)
                    etUpdateEmail.isEnabled = false
                    etUbahPassword.setText(user?.password)
                }
            }
            userid = user?.id
        }
        //fungsi updatenya
        binding.btnUpdet.setOnClickListener{
            val username = binding.etUpdateUsername.text.toString()
            val ubahpass = binding.etUbahPassword.text.toString()
            val updateuser = User(userid,username,email.toString(), ubahpass)
            lifecycleScope.launch(Dispatchers.IO){
                val newuser = authRepository.update(updateuser)
                activity?.runOnUiThread {
                    if (newuser != 0){
                        Toast.makeText(context, "Update Sukses", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}