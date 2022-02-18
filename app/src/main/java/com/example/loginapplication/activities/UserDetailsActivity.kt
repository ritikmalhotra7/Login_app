package com.example.loginapplication.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.complete.weatherapplication.utils.Resources
import com.example.loginapplication.adapter.TodosAdapter
import com.example.loginapplication.databinding.ActivityUserDetailsBinding
import com.example.loginapplication.models.TodoItem
import com.example.loginapplication.models.Todos
import com.example.loginapplication.repos.AppRepository
import com.example.loginapplication.vm.AppViewModel
import com.example.loginapplication.vm.AppViewModelFactory

class UserDetailsActivity : AppCompatActivity() {
    private  var viewModel: AppViewModel? = null
    private lateinit var rvAdapter: TodosAdapter
    private lateinit var list:ArrayList<TodoItem>
    var _binding : ActivityUserDetailsBinding? = null
    val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        list = ArrayList()
        val repo = AppRepository();
        val factory = AppViewModelFactory(repo)
        viewModel = ViewModelProvider(this,factory).get(AppViewModel::class.java)
        viewModel!!.gettodos()
        viewModel!!.todos.observe(this, Observer{ response->
            when(response){
                is Resources.Success ->{
                    hideProgressBar()
                    response.data.let{
                        for(i in it!!){
                            list.add(i)
                        }
                        supportActionBar?.title = getSharedPreferences("SHARED", Context.MODE_PRIVATE)?.getString("email","test")
                        setupRecView(list)
                    }
                }
                is Resources.Error ->{
                    hideProgressBar()
                    Toast.makeText(this,"error", Toast.LENGTH_SHORT).show()
                    Log.d("TAG","error")
                }
                is Resources.Loading ->{
                    showProgressBar()
                }
            }
        })
        binding.fab.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
    private fun logout(){

    }
    private fun hideProgressBar(){
        binding.progressBar.visibility = View.INVISIBLE
    }
    private fun showProgressBar(){
        binding.progressBar.visibility = View.VISIBLE
    }
    fun setupRecView(listy:ArrayList<TodoItem>){
        rvAdapter = TodosAdapter(listy,this)
        binding.rv.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }
}