package com.example.challengecp5

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.challengecp5.databinding.FragmentHomeBinding
import com.example.challengecp5.databinding.FragmentLoginBinding


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    lateinit var homeViewModel: HomeViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreference = context?.getSharedPreferences("SHARED_FILE", Context.MODE_PRIVATE)
        homeViewModel=ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        //menampilkan list movie dari api
        homeViewModel.dataMovie.observe(viewLifecycleOwner){
            showlistmovie(it.results)
        }
        //tombol untuk profile
        binding.homeProfile.setOnClickListener{
            it.findNavController().navigate(R.id.action_fragmentHome_to_profileFragment)
        }
        //tombol untuk logout
        binding.btnOut.setOnClickListener{
            val editor = sharedPreference!!.edit()
            editor.clear()
            editor.apply()
            it.findNavController().navigate(R.id.action_fragmentHome_to_fragmentLogin)
        }
    }
    //mengatur adaptor untuk ke receycleview
    private fun showlistmovie(results: List<Result>) {
        val adapter= MovieAdapter{
            val bundle = bundleOf("id" to it.id)
            findNavController().navigate(R.id.action_fragmentHome_to_detailFragment,bundle)
        }
        adapter.submitList(results)
        binding.reycycleview1.adapter=adapter
        binding.reycycleview2.adapter=adapter
    }
}