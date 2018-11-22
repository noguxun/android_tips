package x.co.tips

import android.content.Context
import android.widget.BaseAdapter
import android.widget.Filterable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.databinding.DataBindingUtil
import android.widget.Filter
import x.co.tips.databinding.RowItemBinding



class MonthListAdapter : BaseAdapter(), Filterable {
    lateinit var mData: List<String>
    lateinit var mStringFilterList: List<String>
    private var valueFilter: ValueFilter? = null
    //private var inflater: LayoutInflater? = null


    fun setList(cancel_type: List<String>){
        mData = cancel_type
        mStringFilterList = cancel_type
    }


    override fun getCount(): Int {
        return mData.size
    }

    override fun getItem(position: Int): String {
        return mData[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = parent?.context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        /*
        if (inflater == null) {
            inflater = parent.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }*/
        //val rowItemBinding : RowItemBinding = DataBindingUtil.inflate(inflater, R.layout.month_item, parent, false)
        val rowItemBinding = DataBindingUtil.inflate(inflater, R.layout.month_item, null, false) as RowItemBinding
        rowItemBinding.stringName.text = mData[position]


        return rowItemBinding.root
    }

    override fun getFilter(): Filter {
        if (valueFilter == null) {
            valueFilter = ValueFilter()
        }
        return valueFilter as Filter
    }

    private inner class ValueFilter : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val results = FilterResults()

            if (constraint != null && constraint.length > 0) {
                val filterList = ArrayList<String>()
                for (i in mStringFilterList.indices) {
                    if (mStringFilterList[i].toUpperCase().contains(constraint.toString().toUpperCase())) {
                        filterList.add(mStringFilterList[i])
                    }
                }
                results.count = filterList.size
                results.values = filterList
            } else {
                results.count = mStringFilterList.size
                results.values = mStringFilterList
            }
            return results

        }

        override fun publishResults(
            constraint: CharSequence,
            results: FilterResults
        ) {
            mData = results.values as List<String>
            notifyDataSetChanged()
        }

    }
}