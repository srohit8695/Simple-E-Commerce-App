package com.example.simpleecommerceapp.callbacks

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import java.util.*

abstract class SwipeHelperKotlin(dragDirs: Int, swipeDirs: Int) :
    ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs)
{

    val BUTTON_WIDTH = 200
    private var recyclerView: RecyclerView? = null
    var buttons: MutableList<UnderlayButton>? = null
    private var gestureDetector: GestureDetector? = null
    private var swipedPos = -1
    private var swipeThreshold = 0.5f
    private var buttonsBuffer: MutableMap<Int, List<UnderlayButton>>? = null
    private var recoverQueue: Queue<Int>? = null
    var animate: Boolean? = null

    private val gestureListener: GestureDetector.SimpleOnGestureListener = object : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            for (button in buttons!!) {
                if (button.onClick(e.x, e.y)) break
            }
            return true
        }
    }

    private val onTouchListener = OnTouchListener { view, e ->
        if (swipedPos < 0) return@OnTouchListener false
        val point = Point(
            e.rawX.toInt(),
            e.rawY.toInt()
        )
        val swipedViewHolder = recyclerView!!.findViewHolderForAdapterPosition(swipedPos)
        val swipedItem = swipedViewHolder!!.itemView
        val rect = Rect()
        swipedItem.getGlobalVisibleRect(rect)
        if (e.action == MotionEvent.ACTION_DOWN || e.action == MotionEvent.ACTION_UP || e.action == MotionEvent.ACTION_MOVE) {
            if (rect.top < point.y && rect.bottom > point.y) gestureDetector!!.onTouchEvent(e) else {
                recoverQueue!!.add(swipedPos)
                swipedPos = -1
                recoverSwipedItem()
            }
        }
        false
    }



    constructor(context: Context?, recyclerView: RecyclerView?, animate: Boolean?) : this(0, ItemTouchHelper.LEFT) {
//        super(0, ItemTouchHelper.LEFT)
        this.animate = animate
        this.recyclerView = recyclerView
        buttons = ArrayList()
        gestureDetector = GestureDetector(context, gestureListener)
        this.recyclerView!!.setOnTouchListener(onTouchListener)
        buttonsBuffer = HashMap()
        recoverQueue = object : LinkedList<Int>() {
            override fun add(o: Int): Boolean {
                return if (contains(o)) false else super.add(o)
            }
        }
        attachSwipe()
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }



    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val pos = viewHolder.adapterPosition
        if (swipedPos != pos) recoverQueue!!.add(swipedPos)
        swipedPos = pos
        if (buttonsBuffer!!.containsKey(swipedPos)) buttons =
            buttonsBuffer!![swipedPos] as MutableList<UnderlayButton>? else buttons!!.clear()
        buttonsBuffer!!.clear()
        swipeThreshold = 0.5f * buttons!!.size * BUTTON_WIDTH
        recoverSwipedItem()
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return swipeThreshold
    }



    override fun getSwipeEscapeVelocity(defaultValue: Float): Float {
        return 0.1f * defaultValue
    }

    override fun getSwipeVelocityThreshold(defaultValue: Float): Float {
        return 5.0f * defaultValue
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val pos = viewHolder.adapterPosition
        var translationX = dX
        val itemView = viewHolder.itemView
        if (pos < 0) {
            swipedPos = pos
            return
        }
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            if (dX < 0) {
                var buffer: List<UnderlayButton>? = ArrayList()
                if (!buttonsBuffer!!.containsKey(pos)) {
                    instantiateUnderlayButton(viewHolder, buffer as MutableList<UnderlayButton>?)
                    buttonsBuffer!!.put(pos, buffer!!)
                } else {
                    buffer = buttonsBuffer!![pos]
                }
                translationX = dX * buffer!!.size * BUTTON_WIDTH / itemView.width
                drawButtons(c, itemView, buffer, pos, translationX)
            }
        }

//        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    /* open fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView?,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val pos = viewHolder.adapterPosition
        var translationX = dX
        val itemView = viewHolder.itemView
        if (pos < 0) {
            swipedPos = pos
            return
        }
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            if (dX < 0) {
                var buffer: List<UnderlayButton>? = ArrayList()
                if (!buttonsBuffer!!.containsKey(pos)) {
                    instantiateUnderlayButton(viewHolder, buffer)
                    buttonsBuffer.put(pos, buffer)
                } else {
                    buffer = buttonsBuffer!![pos]
                }
                translationX = dX * buffer!!.size * BUTTON_WIDTH / itemView.width
                drawButtons(c, itemView, buffer, pos, translationX)
            }
        }
        super.onChildDraw(
            c,
            recyclerView!!, viewHolder, translationX, dY, actionState, isCurrentlyActive
        )
    }*/

    @Synchronized
    private  fun recoverSwipedItem() {
        while (!recoverQueue!!.isEmpty()) {
            val pos = recoverQueue!!.poll()
            if (pos > -1) {
                recyclerView!!.adapter!!.notifyItemChanged(pos)
            }
        }
    }

    open fun drawButtons(
        c: Canvas,
        itemView: View,
        buffer: List<UnderlayButton>?,
        pos: Int,
        dX: Float
    ) {
        var right = itemView.right.toFloat()
        val dButtonWidth = -1 * dX / buffer!!.size
        for (button in buffer) {
            val left = right - dButtonWidth

            button.onDraw(
                c,
                RectF(
                    left,
                    itemView.top.toFloat(),
                    right,
                    itemView.bottom.toFloat()
                ),
                pos
            )
            right = left
        }
    }

    open fun attachSwipe() {
        val itemTouchHelper = ItemTouchHelper(this)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    abstract fun instantiateUnderlayButton(
        viewHolder: RecyclerView.ViewHolder?,
        underlayButtons: MutableList<UnderlayButton>?
    )

    inner class UnderlayButton(
        private val text: String,
        private val imageResId: Drawable?,
        private val buttonBackgroundcolor: Int,
        private val textColor: Int,
        private val clickListener: UnderlayButtonClickListener
    ) {
        private var pos = 0
        private var clickRegion: RectF? = null
        fun onClick(x: Float, y: Float): Boolean {
            if (clickRegion != null && clickRegion!!.contains(x, y)) {
                clickListener.onClick(pos)
                return true
            }
            return false
        }

        fun onDraw(canvas: Canvas, rect: RectF, pos: Int) {
            val p = Paint()
            // Draw background
            p.color = buttonBackgroundcolor
            canvas.drawRect(rect, p)
            if (animate!!.not()) {
                // Draw Text
//                p.setColor(Color.BLACK);
                p.color = textColor
                p.textSize = 40f
                val r = Rect()
                val cHeight = rect.height()
                val cWidth = rect.width()
                p.textAlign = Paint.Align.LEFT
                p.getTextBounds(text, 0, text.length, r)
                val x = cWidth / 2f - r.width() / 2f - r.left
                val y = cHeight / 2f + r.height() / 2f - r.bottom - 40
                canvas.drawText(text, rect.left + x, rect.top + y, p)
                if (imageResId != null) {
                    imageResId.setBounds(
                        (rect.left + 50).toInt(),
                        (rect.top + cHeight / 2f).toInt(),
                        (rect.right - 50).toInt(),
                        (rect.bottom - cHeight / 10f).toInt()
                    )
                    imageResId.draw(canvas)
                }
            } else {
                //animate
                // Draw Text
                val textPaint = TextPaint()
                textPaint.textSize = 40f
                textPaint.color = textColor
                val sl = StaticLayout(
                    text, textPaint, rect.width().toInt(),
                    Layout.Alignment.ALIGN_CENTER, 1.0F, 1.0F, false
                )
                if (imageResId != null) {
                    imageResId.setBounds(
                        (rect.left + 50).toInt(),
                        (rect.top + rect.height() / 2f).toInt(),
                        (rect.right - 50).toInt(),
                        (rect.bottom - rect.height() / 10f).toInt()
                    )
                    imageResId.draw(canvas)
                }
                canvas.save()
                val r = Rect()
                val y = rect.height() / 2f + r.height() / 2f - r.bottom - sl.height / 2
                if (imageResId == null) canvas.translate(
                    rect.left,
                    rect.top + y
                ) else canvas.translate(rect.left, rect.top + y - 30)
                sl.draw(canvas)
                canvas.restore()
            }
            clickRegion = rect
            this.pos = pos
        }
    }

    interface UnderlayButtonClickListener {
        fun onClick(pos: Int)
    }
}