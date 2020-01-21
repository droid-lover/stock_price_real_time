package com.vs.views.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.vs.R
import com.vs.utils.RxBus
import com.vs.utils.Utils
import com.vs.views.fragments.HistoryFragment
import com.vs.views.fragments.HomeFragment
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.toolbar_custom_font.*


/**
 * Created By Sachin
 */
class HomeActivity : AppCompatActivity() {

    private var currentStatus = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        Utils.setActivityTitle(this@HomeActivity, toolbar, "Stocks")
        setHomeFragment()

        ivPlay.setOnClickListener {
            currentStatus = !currentStatus
            RxBus.playClicked.onNext(currentStatus)
        }
        ivHistory.setOnClickListener {
            RxBus.historyClicked.onNext(true)
            supportFragmentManager.beginTransaction()
                    .add(R.id.rlContainer, HistoryFragment()).addToBackStack("HomeFragment").commitAllowingStateLoss()
        }
    }

    private fun setHomeFragment() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.rlContainer, HomeFragment(), "HomeFragment").commitAllowingStateLoss()
    }


    /*-- handling back press here --*/
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == android.R.id.home) {
            onBackPressed()
        } else if (item!!.itemId == R.id.action_settings) {
            Utils.showToastMessage("Crafted by ~ Sachin Rajput")
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

}
