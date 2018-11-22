package x.co.tips

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.SearchView
import x.co.tips.databinding.ActivitySearchView1Binding



class SearchView1Activity : AppCompatActivity() {

    lateinit var activitySearchBinding: ActivitySearchView1Binding
    lateinit var adapterMonth: MonthListAdapter

    private var arrayList: MutableList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySearchBinding = DataBindingUtil.setContentView(this, R.layout.activity_search_view1)

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

        adapterMonth = MonthListAdapter()
        adapterMonth.setList(arrayList)
        activitySearchBinding.listView.adapter = adapterMonth

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

                adapterMonth.filter.filter(newText)

                return false
            }
        })
    }
}
