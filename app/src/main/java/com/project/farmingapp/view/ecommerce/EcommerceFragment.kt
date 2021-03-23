package com.project.farmingapp.view.ecommerce

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.ChipGroup
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.project.farmingapp.R
import com.project.farmingapp.adapter.EcommerceAdapter
import com.project.farmingapp.viewmodel.EcommViewModel


import kotlinx.android.synthetic.main.fragment_ecommerce.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EcommerceFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class EcommerceFragment : Fragment() {
    private lateinit var viewmodel: EcommViewModel
    private var adapter : EcommerceAdapter? = null
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

            viewmodel = ViewModelProviders.of(requireActivity())
                .get<EcommViewModel>(EcommViewModel::class.java)

//            viewmodel = ViewModelProviders.of(requireActivity())
//                .get<EcommViewModel>(EcommViewModel::class.java)

            viewmodel.loadAllEcommItems()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewmodel = ViewModelProviders.of(requireActivity())
            .get<EcommViewModel>(EcommViewModel::class.java)
        viewmodel.ecommLiveData.observe(viewLifecycleOwner, Observer {
            adapter = EcommerceAdapter(activity!!.applicationContext, it)
            Log.d("ecommFragment ", it.toString())
            ecommrcyclr.adapter = adapter
            ecommrcyclr.layoutManager = LinearLayoutManager(activity!!.applicationContext)
        })
        return inflater.inflate(R.layout.fragment_ecommerce, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel.loadAllEcommItems()

        chipgrp.setOnCheckedChangeListener { group, checkedId ->

when(checkedId){
    R.id.chip1 -> {
        viewmodel.loadAllEcommItems()

        Toast.makeText(context,"toast clicked1",Toast.LENGTH_LONG).show()

    }
    R.id.chip2 -> { Toast.makeText(context,"toast clicked2",Toast.LENGTH_LONG).show()
        viewmodel.loadSpecificTypeEcomItem("fertilizer")}
}

        }


    }
    fun getProductData() {
        val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()


        firebaseFirestore.collection("products").get()
            .addOnSuccessListener {
                Log.d("Posts data", it.documents[0].getString("title").toString())

            }
            .addOnFailureListener {

            }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EcommerceFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EcommerceFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}