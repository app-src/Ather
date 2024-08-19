package io.github.app_src.ather.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import io.github.app_src.ather.R
import io.github.app_src.ather.utils.SideBarMainMenuItem
import io.github.app_src.ather.databinding.ActivityMainBinding
import io.github.app_src.ather.ui.fragments.SavedRoutesFragment
import io.github.app_src.ather.ui.fragments.SideBarFragment

class MainActivity : AppCompatActivity(), SideBarFragment.OnSideBarMainMenuItemSelectedListener{
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Android 11+ (API 30+)
            window.setDecorFitsSystemWindows(false)
            window.insetsController?.let { controller ->
                controller.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                controller.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            // For older versions
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    )
        }

    }

    override fun OnSideBarMainMenuItemSelected(item: SideBarMainMenuItem) {
        binding.mainMenuDetailFragmentContainer!!.visibility = View.VISIBLE
        binding.blurLayer!!.visibility = View.VISIBLE
        when(item.title) {
            "Location"-> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.mainMenuDetailFragmentContainer, SavedRoutesFragment())
                    .commit()
            }
            else -> {
                binding.mainMenuDetailFragmentContainer!!.visibility = View.GONE
                binding.blurLayer!!.visibility = View.GONE
            }
//            "Bluetooth" -> {
//                supportFragmentManager
//                    .beginTransaction()
//                    .replace(R.id.mainMenuDetailFragmentContainer, SettingsFragment())
//                    .commit()
//            }
//            "Settings" -> {
//                supportFragmentManager
//                    .beginTransaction()
//                    .replace(R.id.mainMenuDetailFragmentContainer, SettingsFragment())
//                    .commit()
//            }
        }
    }
}