package com.lucasurbas.masterdetail.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lucasurbas.masterdetail.R
import com.lucasurbas.masterdetail.databinding.FragmentMapBinding
import com.lucasurbas.masterdetail.ui.main.MainActivity
import com.lucasurbas.masterdetail.ui.widget.CustomAppBar

/**
 * Created by Lucas on 04/01/2017.
 */
class MapFragment : Fragment() {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupToolbar() {
        val appBar: CustomAppBar = (activity as MainActivity).customAppBar
        appBar.setTitle("")
        appBar.clearMenu()
        if (!appBar.hasGeneralToolbar()) {
            binding.fragmentMapMainIcon.setImageResource(R.drawable.ic_menu_24dp)
            binding.fragmentMapMainIcon.setOnClickListener { (activity as MainActivity).toggleDrawer() }
        }
    }

    companion object {
        fun newInstance(): MapFragment {
            val fragment = MapFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }
}