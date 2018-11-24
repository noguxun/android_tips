package x.co.tips

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import x.co.tips.databinding.T3ActivitySearchViewBinding


class T3SearchViewActivity : AppCompatActivity() {

    lateinit var activitySearchBinding: T3ActivitySearchViewBinding
    lateinit var adapterT3Month: T3MonthListAdapter

    private var arrayList: MutableList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySearchBinding = DataBindingUtil.setContentView(this, R.layout.t3_activity_search_view)

        arrayList.add("January")
        arrayList.add("February")
        arrayList.add("March")
        arrayList.add("April")
        arrayList.add("May")
        arrayList.add("June")
        arrayList.add("July")
        arrayList.add("August")
        arrayList.add("September")
        arrayList.add("October")
        arrayList.add("November")
        arrayList.add("December")

        adapterT3Month = T3MonthListAdapter()
        adapterT3Month.setList(arrayList)
        activitySearchBinding.listView.adapter = adapterT3Month

        activitySearchBinding.search.isActivated = true
        activitySearchBinding.search.queryHint = "Type your keyword here"
        activitySearchBinding.search.onActionViewExpanded()
        activitySearchBinding.search.isIconified = false
        activitySearchBinding.search.clearFocus()

        activitySearchBinding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {

                adapterT3Month.filter.filter(newText)

                return false
            }
        })
    }
}
