package com.vikayarska.mvi

import android.os.Build
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.vikayarska.mvi.fragment.UserListFragment
import com.vikayarska.mvi.view.adapters.UsersAdapter
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
@Config(
    manifest = Config.NONE,
    sdk = [Build.VERSION_CODES.O_MR1],
    application = HiltTestApplication::class
)
class UserListFragmentTest {
    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    private lateinit var fragment: UserListFragment

    @Before
    fun setUp() {
        launchFragmentInHiltContainer<UserListFragment> {
            fragment = this as UserListFragment
        }
    }

    @Test
    fun testEmptyList() {
        assert(
            fragment.view?.findViewById<RecyclerView>(
                R.id.rv_users_list
            )?.visibility == View.GONE
        )
        assert(
            fragment.view?.findViewById<TextView>(
                R.id.tv_empty_users_list
            )?.visibility == View.VISIBLE
        )
    }

    @Test
    fun testAddUser() {
        assert(
            fragment.view?.findViewById<TextView>(
                R.id.tv_empty_users_list
            )?.visibility == View.VISIBLE
        )

        fragment.view?.findViewById<FloatingActionButton>(
            R.id.bt_add_users_list
        )?.callOnClick()

        assert(
            fragment.view?.findViewById<TextView>(
                R.id.tv_empty_users_list
            )?.visibility == View.GONE
        )

        val recyclerView = fragment.view?.findViewById<RecyclerView>(
            R.id.rv_users_list
        )

        assert(recyclerView?.visibility == View.VISIBLE)

        val expectedUserCount = 1
        assert(
            recyclerView?.adapter?.itemCount == expectedUserCount
        )

        assert(
            (recyclerView?.adapter as? UsersAdapter)?.getItems()?.first()?.name == "Test User"
        )
    }


    @Test
    fun testDeleteUser() {
        fragment.view?.findViewById<FloatingActionButton>(
            R.id.bt_delete_users_list
        )?.callOnClick()

        assert(
            fragment.view?.findViewById<TextView>(
                R.id.tv_empty_users_list
            )?.visibility == View.VISIBLE
        )

        val recyclerView = fragment.view?.findViewById<RecyclerView>(
            R.id.rv_users_list
        )

        assert(recyclerView?.visibility == View.GONE)
        val expectedUserCount = 0
        assert(
            recyclerView?.adapter?.itemCount == expectedUserCount
        )
    }
}


