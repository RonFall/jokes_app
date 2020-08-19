package core.jokes.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import core.jokes.R
import core.jokes.api.API
import core.jokes.ui.fragment.JokesFragment
import core.jokes.ui.fragment.WebFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        API.initApi()

        supportFragmentManager.beginTransaction().replace(R.id.app_fragment_container, JokesFragment()).commit()
        bottom_navigation.setOnNavigationItemSelectedListener {menuItem ->
            var fg: Fragment? = null
            when(menuItem.itemId) {
                R.id.item_jokes -> fg = JokesFragment()
                R.id.item_web -> fg = WebFragment()
            }
            supportFragmentManager.beginTransaction().replace(R.id.app_fragment_container, fg!!).commit()
            return@setOnNavigationItemSelectedListener true
        }
    }
}