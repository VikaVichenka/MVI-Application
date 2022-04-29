package com.vikayarska.mvi.view.custom

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.util.AttributeSet
import android.view.*
import android.widget.*
import com.bumptech.glide.Glide
import com.vikayarska.domain.model.User
import com.vikayarska.mvi.R


class UserLayout : ViewGroup {

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attributeSet: AttributeSet) : super(context, attributeSet)

    private var userLayoutListener: UserLayoutListener? = null

    private val childMargin: Int = resources.getDimension(R.dimen.regularPadding).toInt()

    private val items = ArrayList<User>()

    private val swipeListener = OnSwipeTouchListener(context,
        onSwipeRight = {
            it?.let {
                val index = this@UserLayout.indexOfChild(it)
                it.animate().x(this.width.toFloat()).setDuration(500)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                            this@UserLayout.removeViewAt(index)
                            userLayoutListener?.onItemRemoved(items[index])
                            items.removeAt(index)
                        }
                    })
            }
        },
        onSwipeDown = {
            it?.let {
                val indexChild = this.indexOfChild(it)
                if (indexChild > 0) {
                    scrollToView(indexChild - 1)
                }
            }

        }, onSwipeUp = {
            it?.let {
                val indexChild = this.indexOfChild(it)
                if (indexChild < this.childCount) {
                    scrollToView(indexChild + 1)
                }
            }
        },
        onSwipeLeft = {})

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val layoutWidth = MeasureSpec.getSize(widthMeasureSpec)
        val layoutHeight = MeasureSpec.getSize(heightMeasureSpec)
        val childWidth = layoutWidth - (childMargin * 2)
        val childHeight = layoutHeight - (childMargin * 3)

        for (i in 0..childCount) {
            val child: View? = getChildAt(i)
            child?.measure(
                MeasureSpec.makeMeasureSpec(
                    childWidth,
                    MeasureSpec.EXACTLY
                ),
                MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY)
            )
        }

        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val count: Int = childCount
        var childTop = 0

        for (i in 0..count) {
            getChildAt(i)?.let { child ->
                if (child.visibility != GONE) {
                    childTop += childMargin
                    val childWidth = child.measuredWidth
                    val childHeight = child.measuredHeight

                    setChildFrame(
                        child, childMargin, childTop,
                        childWidth, childHeight
                    )
                    childTop += childHeight
                }
            }
        }
    }

    private fun setChildFrame(child: View, left: Int, top: Int, width: Int, height: Int) {
        child.layout(left, top, width + left, top + height)
    }


    fun setItems(list: List<User>) {
        items.clear()
        items.addAll(list)
        addItemsToView(items)
    }

    fun addItems(list: List<User>) {
        items.addAll(list)
        addItemsToView(list)
        requestLayout()
    }

    fun getItems() = items

    fun setUserLayoutListener(listener: UserLayoutListener?) {
        this.userLayoutListener = listener
    }

    private fun addItemsToView(list: List<User>) {
        list.forEach {
            this.addView(generateChildView(it))
        }

        requestLayout()
    }

    private fun generateChildView(user: User): View {
        val view = LayoutInflater.from(this.context)
            .inflate(R.layout.layout_card_item, this, false)
        view.findViewById<TextView>(R.id.tv_title_card).text = user.name
        view.findViewById<TextView>(R.id.tv_description_card).text = user.intro
        val image = view.findViewById<ImageView>(R.id.iv_card)
        Glide.with(image.context)
            .load(user.imageUrl)
            .centerCrop()
            .placeholder(R.drawable.ic_user_avatar)
            .into(image)
        view.setOnTouchListener(swipeListener)

        return view
    }

    private fun scrollToView(nextViewIndex: Int) {
        val nextView = this.getChildAt(nextViewIndex)
        val y = nextView.top - childMargin
        this.scrollTo(0, y)
    }


    interface UserLayoutListener {
        fun onItemRemoved(item: User)
    }
}