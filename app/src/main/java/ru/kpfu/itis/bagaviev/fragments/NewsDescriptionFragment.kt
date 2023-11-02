package ru.kpfu.itis.bagaviev.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.kpfu.itis.bagaviev.R
import ru.kpfu.itis.bagaviev.databinding.FragmentNewsDescriptionBinding
import ru.kpfu.itis.bagaviev.model.NewsFeedModel

class NewsDescriptionFragment : Fragment(R.layout.fragment_news_description) {

    private var viewBinding: FragmentNewsDescriptionBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewBinding = FragmentNewsDescriptionBinding.inflate(inflater, container, false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.apply {
            val newsFeedModel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                getSerializable(NEWS_FEED_MODEL_KEY, NewsFeedModel::class.java) as NewsFeedModel
            } else {
                getSerializable(NEWS_FEED_MODEL_KEY) as NewsFeedModel
            }

            viewBinding?.apply {
                tvTitle.text = newsFeedModel.title
                tvDescription.text = newsFeedModel.description
                ivNews.setImageResource(newsFeedModel.image)
            }

        }

    }

    companion object {

        const val NEWS_FEED_MODEL_KEY = "newsFeedKey"

        fun newInstance(newsFeedModel: NewsFeedModel) =
            NewsDescriptionFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(NEWS_FEED_MODEL_KEY, newsFeedModel)
                }
            }
    }

    override fun onDestroy() {
        viewBinding = null
        super.onDestroy()
    }

}