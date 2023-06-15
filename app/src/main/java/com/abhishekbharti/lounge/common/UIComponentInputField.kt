package com.abhishekbharti.lounge.common

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.InputFilter
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.abhishekbharti.lounge.R
import com.abhishekbharti.lounge.databinding.UiComponentInputFieldBinding

class UIComponentInputField : FrameLayout {

    interface TextChangeListener {
        fun onTextChanged()
    }

    private var titleHintColor: ColorStateList? = null
    private var mTitleHint: String? = null
    private var mSubTitleHint: String? = null
    private var mInputHint: String? = null
    private var mandatory: Boolean = true
    private var mInputRightIcon: Int = -1
    var mContext: Context? = null
    private var textChangeListener: TextChangeListener? = null
    var binding: UiComponentInputFieldBinding?=null

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        mContext = context
        initAttributes(attrs)
        initView()
        setViews()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        mContext = context
        initAttributes(attrs)
        initView()
        setViews()
    }

    constructor(context: Context) : super(context) {
        mContext = context
        initView()
    }

    private fun setViews() {
        binding?.apply {
            setDrawableRight()
            if (mTitleHint?.isNotEmpty() == true) {
                title.hint = mTitleHint
            }

            if (mSubTitleHint?.isNotEmpty() == true) {
                subTitle.hint = mTitleHint
            }

            if (mInputHint?.isNotEmpty() == true) {
                input.hint = mInputHint
            }
            //getText()
        }
    }

    private fun initAttributes(attrs: AttributeSet?) {
        val styleAttrs = context.obtainStyledAttributes(
            attrs, R.styleable.UIComponentInputField
        )

        if (styleAttrs.hasValue(R.styleable.UIComponentInputField_titleHint))
            mTitleHint = styleAttrs.getString(R.styleable.UIComponentInputField_titleHint)

        if (styleAttrs.hasValue(R.styleable.UIComponentInputField_subTitleHint))
            mSubTitleHint = styleAttrs.getString(R.styleable.UIComponentInputField_subTitleHint)

        if (styleAttrs.hasValue(R.styleable.UIComponentInputField_inputHint))
            mInputHint = styleAttrs.getString(R.styleable.UIComponentInputField_inputHint)

        if (styleAttrs.hasValue(R.styleable.UIComponentInputField_mandatory))
            mandatory = styleAttrs.getBoolean(R.styleable.UIComponentInputField_mandatory, true)

        if (styleAttrs.hasValue(R.styleable.UIComponentInputField_inputRightIcon)) {
            mInputRightIcon =
                styleAttrs.getResourceId(
                    R.styleable.UIComponentInputField_inputRightIcon,
                    mInputRightIcon
                )
        }
        styleAttrs.recycle()
    }

    private fun initView() {
        binding = UiComponentInputFieldBinding.inflate(LayoutInflater.from(context))
        if (mandatory) {
            binding?.mandatory?.visibility = View.VISIBLE
        } else {
            binding?.mandatory?.visibility = View.GONE
        }
        addView(binding?.root)
    }

    fun setCharLimit(limit: Int) {
        binding?.input?.filters = binding?.input?.filters?.plus(InputFilter.LengthFilter(limit))
    }

    fun setTitle(t: String) {
        binding?.input?.setText(t)
    }

    fun setHint(t: String) {
        binding?.input?.setHint(t)
    }

    fun setTitleHint(t: String) {
        binding?.title?.setHint(t)
    }

    fun setSubTitleHint(t: String) {
        binding?.apply {
            subTitle.setHint(t)
            if (subTitle.visibility == View.GONE) {
                subTitle.visibility = View.VISIBLE
            }
        }
    }

    fun getTitleHint(): String? {
        return binding?.title?.hint?.toString()
    }

    fun setMaxLines(n: Int) {
        binding?.apply {
            input.maxLines = n
            input.ellipsize = TextUtils.TruncateAt.END
        }
    }

    fun setAsSelection() {
        binding?.apply {
            input.setHint("Click Here")
            input.isFocusable = false
            input.isClickable = true
        }
    }

    fun enableMic() {
        binding?.mic?.visibility = View.VISIBLE
    }

    fun setMicClick(listener: (View) -> Unit) {
        binding?.mic?.setOnClickListener {
            listener(it)
        }
    }

    fun getTitle(): String? {
        return binding?.input?.text?.toString()
    }

    private fun setDrawableRight() {
        if (mInputRightIcon > -1) {
            try {
                val icon: Drawable? = mContext?.let { ContextCompat.getDrawable(it,mInputRightIcon) }
                binding?.input?.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            setAsSelection()
        }
    }

    fun setOnClick(listener: (Any) -> Unit) {
        setAsSelection()
        binding?.card?.setOnClickListener {
            listener(it)
        }
        binding?.input?.setOnClickListener {
            listener(it)
        }
    }

    fun setHelp(listener: (Any) -> Unit) {
        binding?.apply {
            help.visibility = View.VISIBLE
            help.setOnClickListener {
                listener(it)
            }
        }
    }

    fun setErrorState() {
        binding?.apply {
            titleHintColor = title.hintTextColors
            title.setHintTextColor(ContextCompat.getColor(title.context, R.color.yellow_E3B100))
            input.isSelected = true
        }
    }

    fun setNormalState() {
        binding?.apply {
            if (titleHintColor != null) {
                title.setHintTextColor(titleHintColor)
            }
            input.isSelected = false
        }
    }

    fun setTextChangeListener(listener: TextChangeListener) {
        binding?.apply {
            this@UIComponentInputField.textChangeListener = listener
            input.tag = true
            input.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {

                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (input.tag != null && input.tag == true) {
                        input.tag = false
                        return
                    }
                    textChangeListener?.onTextChanged()
//                mTitleTv?.setHintTextColor(Color.parseColor("#ffff00"))
//                mInputEt?.isSelected = false
                }

            })
        }
    }

    fun setInputType(type: Int) {
        binding?.input?.inputType = type
    }

    fun getText(): String? {
        return binding?.input?.text?.toString()
    }

    fun setVerified() {
        binding?.verified?.visibility = View.VISIBLE
    }

    fun setEmojiFilter() {
//        binding?.input?.filterEmoji()
    }

}