package com.example.loginapplication.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.complete.weatherapplication.utils.Resources
import com.example.loginapplication.databinding.ActivityMainBinding
import com.example.loginapplication.repos.AppRepository
import com.example.loginapplication.utils.Utils.Companion.isOnline
import com.example.loginapplication.vm.AppViewModel
import com.example.loginapplication.vm.AppViewModelFactory

class MainActivity : AppCompatActivity() {
    private var userType: String = "Executive"
    var _binding :ActivityMainBinding? = null
    val binding get() = _binding!!
    private lateinit var viewModel: AppViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setSpinner()
        val repo = AppRepository();
        val factory = AppViewModelFactory(repo)
        viewModel = ViewModelProvider(this,factory).get(AppViewModel::class.java)
        binding.loginbutton.setOnClickListener {
            login()
        }

    }
    fun login(){
        if(isOnline(this)){
            val email = binding.email.text.toString()
            val pass = binding.pass.text.toString()
            viewModel.getLogin(email,pass,userType!!)
            viewModel.loginUser.observe(this, Observer {
                when(it){
                    is Resources.Success ->{
                        hideProgressBar()
                        it.data?.let {
                            getSharedPreferences("SHARED",Context.MODE_PRIVATE)?.edit()?.apply {
                                putString("email",email)
                                putString("pass",pass)
                                putString("userType",userType)
                                apply()
                            }
                            startActivity(Intent(this,UserDetailsActivity::class.java))
                        }
                    }
                    is Resources.Error ->{
                        hideProgressBar()
                        Toast.makeText(this,"wrong credentials",Toast.LENGTH_SHORT).show()

                    }
                    is Resources.Loading ->{
                        showProgressBar()
                    }
                }
            })
        }else{
            Toast.makeText(this,"No Internet Connection!", Toast.LENGTH_SHORT).show()
        }
    }
    fun setSpinner() {
        binding.spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View, position: Int, id: Long) {
                userType = parentView?.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(parentView: AdapterView<*>?) {}
        })
    }
    private fun hideProgressBar(){
        binding.progressBar.visibility = View.INVISIBLE
    }
    private fun showProgressBar(){
        binding.progressBar.visibility = View.VISIBLE
    }
}