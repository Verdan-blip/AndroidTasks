package ru.kpfu.itis.bagaviev.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.kpfu.itis.bagaviev.R
import ru.kpfu.itis.bagaviev.databinding.DialogFragmentNewsAddBinding
import ru.kpfu.itis.bagaviev.textwatcher.AddingNewsCountTextWatcher

class NewsAddBottomSheetFragment : BottomSheetDialogFragment(R.layout.dialog_fragment_news_add) {

    private var viewBinding: DialogFragmentNewsAddBinding? = null
    var onAddNewsButtonClicked: ((count: Int) -> Unit)? = null

    private fun calculateViewDialogHeight() {
        val displayMetrics = requireContext().resources.displayMetrics
        val dialogHeight = displayMetrics.heightPixels / 3

        val layoutParams =
            FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .apply { height = dialogHeight }

        viewBinding?.root?.layoutParams = layoutParams
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = DialogFragmentNewsAddBinding.inflate(inflater, container, false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calculateViewDialogHeight()

        viewBinding?.apply {
            btnAddNews.setOnClickListener {
                onAddNewsButtonClicked?.invoke(etAddingNewsCount.text.toString().toInt())
                dismiss()
            }
            etAddingNewsCount.addTextChangedListener(AddingNewsCountTextWatcher(etAddingNewsCount))
        }

    }

    override fun onDestroy() {
        viewBinding = null
        super.onDestroy()
    }

}