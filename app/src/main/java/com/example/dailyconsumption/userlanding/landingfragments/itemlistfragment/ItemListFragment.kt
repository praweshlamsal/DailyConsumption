package com.example.dailyconsumption.userlanding.landingfragments.itemlistfragment

import android.app.Activity
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.dailyconsumption.R
import com.example.dailyconsumption.base.basefragment.BaseFragment
import com.example.dailyconsumption.model.Post
import com.example.dailyconsumption.model.UserDetail
import com.example.dailyconsumption.userlanding.UserLandingActivity

class ItemListFragment : BaseFragment(), ItemListContractor.View {



    lateinit var recyclerViewHorizontal: RecyclerView
    lateinit var recyclerViewVertical: RecyclerView
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var user_adapter: MainUserListAdapter
    lateinit var item_adapter: MainItemListAdapter
    lateinit var presenter: ItemListPresenter
    var userDetails = arrayListOf<UserDetail>()
    var postDetails = arrayListOf<Post>()


    override fun getActivityContext(): Activity {
        return activity as UserLandingActivity
    }

    override fun myLayout(): Int {
        return R.layout.fragment_item_list
    }

    override fun activityCreated() {
        presenter = ItemListPresenter(getActivityContext(), this)
        user_adapter = MainUserListAdapter(userDetails,getActivityContext())
        item_adapter = MainItemListAdapter(postDetails,getActivityContext())
        setupviews()
    }

    fun setupviews() {
        swipeRefreshLayout = rootView.findViewById(R.id.swiper)
        horizontalRecyclerSet()
        verticalRecyclerSet()
        presenter.onListResponse()
        swiperefreshlist()
    }

    private fun horizontalRecyclerSet() {
        recyclerViewHorizontal = rootView.findViewById(R.id.recyclerview_horizontal)
        val layoutManager = LinearLayoutManager(getActivityContext())
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerViewHorizontal.layoutManager = layoutManager
        recyclerViewHorizontal.adapter = user_adapter

    }

    private fun verticalRecyclerSet() {
        recyclerViewVertical = rootView.findViewById(R.id.recyclerview_vertical)
        val layoutManager = LinearLayoutManager(getActivityContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerViewVertical.layoutManager = layoutManager
        recyclerViewVertical.adapter = item_adapter

    }

    fun swiperefreshlist() {
        swipeRefreshLayout.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            userDetails.clear()
            presenter.onListResponse()
            user_adapter.notifyDataSetChanged()
            item_adapter.notifyDataSetChanged()
            swipeRefreshLayout.isRefreshing = false
        })
    }

    override fun onUserListSuccess(usersDetailList: ArrayList<UserDetail>) {
        userDetails.clear()
        userDetails.addAll(usersDetailList)
        user_adapter.notifyDataSetChanged()
    }

    override fun onUserPostSuccess(post: ArrayList<Post>) {
        postDetails.clear()
        postDetails.addAll(post)
        item_adapter.notifyDataSetChanged()
    }

    override fun onError(error: String) {
        Log.d("Error",error)
    }
}