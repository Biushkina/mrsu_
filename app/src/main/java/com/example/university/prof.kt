package com.example.university

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.university.retr.SharedPrefManager
import com.example.university.retr.api
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [prof.newInstance] factory method to
 * create an instance of this fragment.
 */

class prof : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var adapter: AdapterClass
    private lateinit var recyclerView: RecyclerView
    private lateinit var dataList: ArrayList<DataClass>
    lateinit var titleList: Array<String>
    lateinit var infoList: Array<String>
    lateinit var imageList: Array<Int>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        SharedPrefManager.refreshDataUsingRefreshToken()
        val view:View = inflater.inflate(R.layout.fragment_prof, container, false)
        val image:ImageView = view.findViewById(R.id.profile_image)
        val FIO:TextView = view.findViewById(R.id.student_name)
        val ID:TextView = view.findViewById(R.id.student_id)
        val group:TextView = view.findViewById(R.id.group)


        val profilePhotoUrl = SharedPrefManager.getUserData()?.Photo?.UrlMedium
        Glide.with(this)
            .load(profilePhotoUrl)
            .into(image)
        FIO.text = SharedPrefManager.getUserData()?.FIO
        ID.text = "ID: ${SharedPrefManager.getUserData()?.StudentCod}"
        //group.text = SharedPrefManager.getUserEducation()?.Institution

        return view

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        val data = arguments?.getString("data")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataList = arrayListOf<DataClass>()
        titleList = arrayOf(
            "Документы об образовании",
            "Публикации",
            "НИОКР",
            "Гранты",
            "Интеллектуальная собственность"
        )
        infoList = arrayOf(
            "Записей нет",
            "Записей нет",
            "Записей нет",
            "Записей нет",
            "Записей нет"
        )
        imageList = arrayOf(
            R.drawable.baseline_expand_more_24,
            R.drawable.baseline_expand_more_24,
            R.drawable.baseline_expand_more_24,
            R.drawable.baseline_expand_more_24,
            R.drawable.baseline_expand_more_24
        )
        for(i in titleList.indices){
            val dataClass = DataClass(titleList[i],infoList[i],imageList[i])
            dataList.add(dataClass)
        }

        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = AdapterClass(dataList)
        recyclerView.adapter = adapter
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment prof.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            prof().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

    }



}