package com.example.dailyconsumption.userlanding

import android.app.Activity
import android.graphics.BlendMode
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.dailyconsumption.base.BaseActivity
import com.example.dailyconsumption.model.Post
import com.example.dailyconsumption.utils.okdialog.okDialog
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.add_item_fragment.*
import com.bumptech.glide.request.RequestOptions
import com.example.dailyconsumption.R
import com.example.dailyconsumption.model.TabLandingObject
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_landing.*
import kotlinx.android.synthetic.main.bottomdrawersheet.*


class UserLandingActivity : BaseActivity<UserLandingPresenter>(), UserLandingContractor {

    lateinit var username_tv: TextView
    lateinit var user_image: ImageView
    lateinit var plus_sign: CardView
    lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    lateinit var addItemBottomSheetFragment: AddItemBottomSheetFragment
    lateinit var customOkDialog: okDialog


    lateinit var userLandingFragmentAdapter: UserLandingFragmentAdapter

    lateinit var viewpager: ViewPager

    lateinit var tablayout: TabLayout


    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var recyclerview: RecyclerView
    lateinit var overlay: View
    final var TIME_INTERVAL: Int = 2000
    var mBackPressed: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)
        supportActionBar?.hide()
        username_tv = findViewById<TextView>(R.id.user_name)
        user_image = findViewById<ImageView>(R.id.user_img)
        plus_sign = findViewById<CardView>(R.id.plus_sign)
        addItemBottomSheetFragment = AddItemBottomSheetFragment(presenter)
        bottomSheetBehavior = BottomSheetBehavior.from<LinearLayout>(layoutBottomSheet)
        setupviews()
    }

    private fun setupviews() {

        username_tv.setText(presenter.requestusername().capitalize())
        userLandingFragmentAdapter = UserLandingFragmentAdapter(
            supportFragmentManager,
            FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        )
        viewpager = findViewById<ViewPager>(R.id.viewpager)
        tablayout = findViewById<TabLayout>(R.id.tablayout)
        presenter.listrequest()
        val options = RequestOptions()
            .centerCrop()
            .placeholder(R.mipmap.ic_launcher_round)
            .error(R.mipmap.ic_launcher_round)
        Glide.with(this).load(presenter.requestuserimage()).apply(options)
            .into(user_image)
        bottomsheetfragment()
        presenter.onViewCreated()
    }


    fun bottomsheetfragment() {
        plus_sign.setOnClickListener(View.OnClickListener {
            addItemBottomSheetFragment.show(supportFragmentManager, "add_item_dialog_fragment")
        })
    }


    override fun getActivityContext(): Activity {
        return this
    }

    override fun instantiatePresenter(): UserLandingPresenter {
        return UserLandingPresenter(this)
    }

    override fun onSuccessResponse() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onErrorResponse(error: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onListSuccess(listpost: ArrayList<Post>) {
        for (post in listpost) {
            Log.d("okay", post.price.toString())
        }
//        postsAdapter.updatePosts(listpost)
    }

    override fun onSuccessItemAddResponse() {
        presenter.listrequest()
        addItemBottomSheetFragment.dismiss()
        customOkDialog = okDialog()
        customOkDialog.okDialog("Your data has been added", getActivityContext())
    }

    override fun onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed()
            return
        } else {
            Toast.makeText(getContext(), "Press Back button twice to quit.", Toast.LENGTH_SHORT)
                .show()
        }
        mBackPressed = System.currentTimeMillis()
    }


    //    ...........................latest.........................
    override fun tabresponse(response: List<TabLandingObject>) {

        val tabObjects = mutableListOf<TabLandingObject>()
        tabObjects.addAll(response)
        Log.d("sizegain", tabObjects.size.toString())
        createViewPager(tabObjects)
        createTabicons(tabObjects)
        customizeTab(tabObjects)
    }

    private fun customizeTab(tabObjects: MutableList<TabLandingObject>) {
        tablayout?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewpager!!.currentItem = tab!!.position
                val tv_tab_text: TextView = tab.customView!!.findViewById(R.id.tab_text) as TextView
                tv_tab_text.setTypeface(tv_tab_text.typeface, Typeface.BOLD)

                val imageView: ImageView = tab.customView!!.findViewById(R.id.img_tab) as ImageView
                imageView.setImageResource(tabObjects.get(tab.position).bold)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val tv_tab_text: TextView =
                    tab!!.customView!!.findViewById(R.id.tab_text) as TextView
                tv_tab_text.setTypeface(tv_tab_text.typeface, Typeface.NORMAL)

                val imageView: ImageView = tab.customView!!.findViewById(R.id.img_tab) as ImageView
                imageView.setImageResource(tabObjects.get(tab.position).normal)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }

    private fun createViewPager(tabObjects: MutableList<TabLandingObject>) {

        for (i in 0 until tabObjects.size)
            userLandingFragmentAdapter.addFragment(
                tabObjects[i].fragment,
                tabObjects[i].title,
                tabObjects[i].normal,
                tabObjects[i].bold
            )
        viewpager!!.adapter = userLandingFragmentAdapter
        tablayout!!.setupWithViewPager(viewpager)
    }


    private fun createTabicons(tabObjects: MutableList<TabLandingObject>) {
        tablayout?.setSelectedTabIndicator(R.color.transparent)
        Log.d("donetinf",tabObjects[0].normal.toString())
        for (i in 0 until tabObjects.size) {
            val tabView: View? = (tablayout.getChildAt(i) as? ViewGroup)?.getChildAt(i)
            tabView?.requestLayout()
            val view: View = LayoutInflater.from(this).inflate(R.layout.custom_founder_landing_tab, null)
            this.tablayout.getTabAt(i)?.setCustomView(view)
            val image: ImageView = tablayout.getTabAt(i)?.customView?.findViewById(R.id.img_tab) as ImageView
            image.setImageResource(tabObjects[i].normal)
            image.setColorFilter(Color.argb(255, 255, 255, 255));
            val tv_title: TextView = tablayout.getTabAt(i)!!.customView!!.findViewById(R.id.tab_text) as TextView
            tv_title.setText(tabObjects.get(i).title)
        }
        val image: ImageView =
            tablayout!!.getTabAt(0)!!.customView!!.findViewById(R.id.img_tab) as ImageView
        image.setImageResource(tabObjects.get(0).bold)
        image.setColorFilter(Color.argb(255, 255, 255, 255));

        val tv_title: TextView =
            tablayout!!.getTabAt(0)!!.customView!!.findViewById(R.id.tab_text) as TextView
        tv_title.setTypeface(tv_title.typeface, Typeface.BOLD)
    }
}