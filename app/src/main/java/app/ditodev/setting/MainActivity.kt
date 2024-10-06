package app.ditodev.setting

import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.switchmaterial.SwitchMaterial


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val switchTheme = findViewById<SwitchMaterial>(R.id.switchTheme)
        switchTheme.setOnCheckedChangeListener { _: View?, isChecked: Boolean ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }
        val pref = SettingPreferences.getInstance(application.dataStore)
        val vm = ViewModelProvider(this, ViewModelFactory(pref))[MainViewModel::class.java]

        vm.getThemeSettings().observe(this) {
            Boolean
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            vm.saveThemeSettings(isChecked)
        }

        //        supportFragmentManager
        //            .beginTransaction()
        //            .replace(R.id.main, MyPreferencesFragment())
        //            .commit()
        //
        //
        //        PreferenceManager
        //            .getDefaultSharedPreferences(this)
        //            .registerOnSharedPreferenceChangeListener(this)
    }

//    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
//        val darkModeString = getString(R.string.dark_mode)
//        p1?.let {
//            p0?.let { pref ->
//                val darkModeVal = resources.getStringArray(R.array.dark_mode_values)
//                when (pref.getString(darkModeString, darkModeVal[0])) {
//                    darkModeVal[0] -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
//                    darkModeVal[1] -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//                    darkModeVal[2] -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//                }
//            }
//        }
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        PreferenceManager.getDefaultSharedPreferences(this)
//            .unregisterOnSharedPreferenceChangeListener(this)
//    }
}