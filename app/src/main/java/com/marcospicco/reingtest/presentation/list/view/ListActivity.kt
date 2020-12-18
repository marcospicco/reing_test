package com.marcospicco.reingtest.presentation.list.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.marcospicco.reingtest.R
import com.marcospicco.reingtest.core.domain.ListItemModel
import com.marcospicco.reingtest.core.instance.CoreDependenciesInstance
import com.marcospicco.reingtest.presentation.list.view.adapter.ListAdapter
import com.marcospicco.reingtest.presentation.list.view.adapter.ListItemViewHolder
import com.marcospicco.reingtest.presentation.list.viewmodel.ListViewModel
import com.marcospicco.reingtest.presentation.webview.view.WebViewActivity
import kotlinx.android.synthetic.main.activity_list.*


class ListActivity : AppCompatActivity(), ListItemViewHolder.OnClickListener {

    private val viewModel by lazy {
        ListViewModel(
            CoreDependenciesInstance.resolveSharedPreference(this),
            CoreDependenciesInstance.resolveScheduler(),
            CoreDependenciesInstance.resolveGetList(),
            CoreDependenciesInstance.resolveGetLocalList(),
            CoreDependenciesInstance.resolveDeleteItem(),
            CoreDependenciesInstance.PLATFORM
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        window.statusBarColor = ContextCompat.getColor(this, R.color.gray)

        viewModel.apply {
            lifecycle.addObserver(this)
            listDataLD.observe(this@ListActivity) { setRecyclerData(it) }
            linkLD.observe(this@ListActivity) { goToWebView(it) }
        }

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = true
            viewModel.getList()
        }
    }

    fun setRecyclerData(data: MutableList<ListItemModel>) {

        swipeRefreshLayout.isRefreshing = false

        // set decoration divider
        val decoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        decoration.setDrawable(
            ContextCompat.getDrawable(this, R.drawable.item_list_decorator_divider)!!
        )
        listRecycler.addItemDecoration(decoration)

        // set adapter
        if (listRecycler.adapter == null) {
            listRecycler.adapter = ListAdapter(this)
        }
        (listRecycler.adapter as ListAdapter).setData(data)

        // set swipe to delete
        val itemTouchHelper =
            ItemTouchHelper(
                SwiperItemTouchHelper(
                    0,
                    ItemTouchHelper.LEFT,
                    this,
                    object : SwiperItemTouchHelper.SwipeEventListener {
                        override fun onSwipe(position: Int) {
                            val item = (listRecycler.adapter as ListAdapter).list[position]
                            viewModel.deleteItem(item.id)
                            (listRecycler.adapter as ListAdapter).removeItem(position)

                        }
                    }
                )
            )
        itemTouchHelper.attachToRecyclerView(listRecycler)
    }

    override fun onClick(link: String?) {
        viewModel.onItemClick(link)
    }

    private fun goToWebView(link: String) {
        startActivity(WebViewActivity.getIntent(this, link))
    }
}