package com.lucasurbas.masterdetail.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lucasurbas.masterdetail.R
import com.lucasurbas.masterdetail.databinding.FragmentEmptyBinding
import com.lucasurbas.masterdetail.ui.main.MainActivity
import com.lucasurbas.masterdetail.ui.widget.CustomAppBar

/**
 * Created by Lucas on 04/01/2017.
 */
class FavoritesFragment : Fragment() {

    private var _binding: FragmentEmptyBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_empty, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragmentEmptyTitle.text = getString(R.string.fragment_favorites__title)
        setupToolbar()
    }

    private fun setupToolbar() {
        val appBar: CustomAppBar = (activity as MainActivity).customAppBar
        appBar.setTitle(getString(R.string.fragment_favorites__title))
        appBar.setMenuRes(
            R.menu.favorites_general,
            R.menu.favorites_specific,
            R.menu.favorites_merged
        )
    }

    companion object {
        fun newInstance(): FavoritesFragment {
            val fragment = FavoritesFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }
}