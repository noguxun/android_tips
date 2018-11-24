package x.co.tips

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.GridView
import kotlinx.android.synthetic.main.activity_grid_view1.*


class T2GridViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grid_view1)

        // Set the grid view adapterT3Month

        val gridView = findViewById<GridView>(R.id.grid_view)


        gridView.adapter = T2ColorGridAdapter()
        // or grid_view.adapterT3Month

        // Configure the grid view
        grid_view.numColumns = 2
        grid_view.horizontalSpacing = 15
        grid_view.verticalSpacing = 15
        grid_view.stretchMode = GridView.STRETCH_COLUMN_WIDTH
    }
}
