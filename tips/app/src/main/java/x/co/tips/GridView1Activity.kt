package x.co.tips

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridView
import kotlinx.android.synthetic.main.activity_grid_view1.*


class GridView1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grid_view1)

        // Set the grid view adapterMonth

        val gridView = findViewById(R.id.grid_view) as GridView


        gridView.adapter = ColorGridAdapter()
        // or grid_view.adapterMonth

        // Configure the grid view
        grid_view.numColumns = 2
        grid_view.horizontalSpacing = 15
        grid_view.verticalSpacing = 15
        grid_view.stretchMode = GridView.STRETCH_COLUMN_WIDTH
    }
}
