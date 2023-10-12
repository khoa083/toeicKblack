package com.khoa.demotoeictest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialSharedAxis
import com.khoa.demotoeictest.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@Suppress("DEPRECATION")
@AndroidEntryPoint
class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private var navController: NavController? = null
//    private var pressedTime: Long? = null


    private val currentNavigationFragment: Fragment?
        get() = supportFragmentManager.findFragmentById(R.id.fragmentView)
            ?.childFragmentManager
            ?.fragments
            ?.first()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
//        onBackPressedDispatcher.addCallback(this,onBackPressedCallback)
        binding.lifecycleOwner = this
        setUpNavigationFragment()
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(this)
    }

    private fun setUpNavigationFragment() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentView) as NavHostFragment
        navController = navHostFragment.navController
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.home ->{
                animationSharedX()
                findNavController(R.id.fragmentView).navigate(R.id.homeFragment)
                Log.d("khoa", "home")
            }
            R.id.testfull ->{
                animationSharedX()
                findNavController(R.id.fragmentView).navigate(R.id.fulltestFragment)
                Log.d("khoa", "test")
            }
            R.id.vocab ->{
                animationSharedX()
                findNavController(R.id.fragmentView).navigate(R.id.vocabFragment)
                Log.d("khoa", "vocab")
            }
            R.id.favor ->{
                animationSharedX()
                findNavController(R.id.fragmentView).navigate(R.id.favoriteFragment)
                Log.d("khoa", "favor")
            }
            R.id.setting ->{
                animationSharedX()
                findNavController(R.id.fragmentView).navigate(R.id.settingFragment)
                Log.d("khoa", "setting")
            }
            else -> return false
        }
        return true
    }

    private fun animationSharedX(){
        currentNavigationFragment?.apply {
            exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true).apply {
                duration = 300.toLong()
            }
            reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false).apply {
                duration = 300.toLong()
            }
        }
    }

    fun handleShowBottomNav(isShow: Boolean){
        binding.bottomNavigationView.visibility = if (isShow) View.VISIBLE else View.GONE
    }

//    private var onBackPressedCallback = object : OnBackPressedCallback(true) {
//        override fun handleOnBackPressed() {
//            if ((pressedTime?:0) + 2000 > System.currentTimeMillis()) {
//                finish();
//            } else {
//                Toast.makeText(this@MainActivity,"Press back again to exit",Toast.LENGTH_SHORT).show()
//            }
//            pressedTime = System.currentTimeMillis();
//        }
//    }


}