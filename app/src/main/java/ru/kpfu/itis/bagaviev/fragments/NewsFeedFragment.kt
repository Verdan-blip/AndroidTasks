package ru.kpfu.itis.bagaviev.fragments

import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import ru.kpfu.itis.bagaviev.MainActivity
import ru.kpfu.itis.bagaviev.R
import ru.kpfu.itis.bagaviev.databinding.FragmentNewsBinding
import ru.kpfu.itis.bagaviev.model.NewsFeedModel
import ru.kpfu.itis.bagaviev.model.NewsItemType
import ru.kpfu.itis.bagaviev.model.NewsListItem
import ru.kpfu.itis.bagaviev.recylerview.adapter.NewsFeedAdapter
import ru.kpfu.itis.bagaviev.recylerview.decoration.DividerItemDecoration
import ru.kpfu.itis.bagaviev.util.ActionType
import ru.kpfu.itis.bagaviev.util.NewsFeedHandler
import ru.kpfu.itis.bagaviev.util.NewsFeedRepository
import ru.kpfu.itis.bagaviev.util.getValueInPx


class NewsFeedFragment : Fragment(R.layout.fragment_news) {

    private var viewBinding: FragmentNewsBinding? = null
    private var newsFeedAdapter: NewsFeedAdapter? = null
    private var bottomSheetFragment: NewsAddBottomSheetFragment? = null

    private var newsItemList: MutableList<NewsListItem>? = null

    private fun defineLayoutManager(newsItemList: MutableList<NewsListItem>) : LayoutManager {
        val realSize = newsItemList.count { it.getType() == NewsItemType.NEWS_FEED_ITEM }
        if (realSize <= 12) {
            return LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        } else {
            val gridLayoutManager = GridLayoutManager(context, 2)
            gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (newsItemList[position].getType()) {
                        NewsItemType.NEWS_FEED_ITEM -> 1
                        else -> 2
                    }
                }
            }
            return gridLayoutManager
        }
    }

    private fun onNewsFeedItemClicked(newsFeedModel: NewsFeedModel) {
        (requireActivity() as MainActivity).navigateTo(
            ActionType.REPLACE,
            R.id.fv_container,
            NewsDescriptionFragment.newInstance(newsFeedModel),
            true
        )
    }

    private fun onAddToFavouritesButtonClicked(position: Int, newsFeedModel: NewsFeedModel) {
        newsFeedAdapter?.updateItem(position, newsFeedModel)
    }

    private fun onAddNewsButtonClicked(count: Int) {
        newsFeedAdapter?.addItems(NewsFeedHandler.take(NewsFeedRepository.getAll(), count))
    }

    private fun onAddNewsButtonClicked() {
        bottomSheetFragment?.show(childFragmentManager, null)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewBinding = FragmentNewsBinding.inflate(inflater, container, false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottomSheetFragment = NewsAddBottomSheetFragment()
        bottomSheetFragment?.onAddNewsButtonClicked = ::onAddNewsButtonClicked

        arguments?.apply {
            if (newsItemList == null) {
                newsItemList = NewsFeedHandler.structurize(
                    NewsFeedHandler.take(
                        NewsFeedRepository.getAll(),
                        getInt(NEWS_COUNT_KEY)
                    )
                )
                viewBinding?.rvNews?.layoutManager = defineLayoutManager(newsItemList!!)
            }
        }

        newsItemList?.also {newsItemList ->
            newsFeedAdapter = NewsFeedAdapter(
                onNewsFeedItemClicked = ::onNewsFeedItemClicked,
                onAddToFavouritesButtonClicked = ::onAddToFavouritesButtonClicked,
                onAddNewsButtonClicked = ::onAddNewsButtonClicked
            )
            newsFeedAdapter?.setItems(newsItemList)

            viewBinding?.apply {
                rvNews.adapter = newsFeedAdapter
                with (resources) {
                    rvNews.addItemDecoration(DividerItemDecoration(Rect(
                        8.getValueInPx(displayMetrics),
                        4.getValueInPx(displayMetrics),
                        8.getValueInPx(displayMetrics),
                        4.getValueInPx(displayMetrics))
                    ))
                }
            }

        }

    }

    override fun onDestroy() {
        viewBinding = null
        super.onDestroy()
    }

    companion object {
        private const val NEWS_COUNT_KEY = "newCountKey"
        fun newInstance(newsCount: Int) = NewsFeedFragment().apply {
            arguments = Bundle().apply {
                putInt(NEWS_COUNT_KEY, newsCount)
            }
        }
    }

}