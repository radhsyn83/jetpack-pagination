package com.radhsyn83.jetpackpaggingexample

import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list_footer.view.*
import kotlinx.android.synthetic.main.item_news.view.*

class NewsAdapter(private val retry: () -> Unit) :
    PagedListAdapter<NewsModel.Article, RecyclerView.ViewHolder>(NewsDiffCallback) {

    private val DATA_VIEW_TYPE = 1
    private val FOOTER_VIEW_TYPE = 2

    private var state = State.LOADING

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == DATA_VIEW_TYPE)
            NewsViewHolder.create(parent)
        else
            ListFooterViewHolder.create(retry, parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == DATA_VIEW_TYPE)
            (holder as NewsViewHolder).bind(getItem(position))
        else (holder as ListFooterViewHolder).bind(state)
    }

    class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(news: NewsModel.Article?) {
            if (news != null) {
                itemView.txt_news_name.text = news.title
                Picasso.get().load(news.urlToImage).into(itemView.img_news_banner)
            }
        }

        companion object {
            fun create(parent: ViewGroup): NewsViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_news_horizontal, parent, false)
//                val view = LayoutInflater.from(parent.context)
//                    .inflate(R.layout.item_news, parent, false)
                return NewsViewHolder(view)
            }
        }
    }

    class ListFooterViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(status: State?) {
            itemView.progress_bar.visibility =
                if (status == State.LOADING) VISIBLE else View.INVISIBLE
            itemView.txt_error.visibility = if (status == State.ERROR) VISIBLE else View.INVISIBLE
        }

        companion object {
            fun create(retry: () -> Unit, parent: ViewGroup): ListFooterViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_list_footer, parent, false)
                view.txt_error.setOnClickListener { retry() }
                return ListFooterViewHolder(view)
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) DATA_VIEW_TYPE else FOOTER_VIEW_TYPE
    }

    companion object {
        val NewsDiffCallback = object : DiffUtil.ItemCallback<NewsModel.Article>() {
            override fun areItemsTheSame(
                oldItem: NewsModel.Article,
                newItem: NewsModel.Article
            ): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(
                oldItem: NewsModel.Article,
                newItem: NewsModel.Article
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasFooter()) 1 else 0
    }

    private fun hasFooter(): Boolean {
        return super.getItemCount() != 0 && (state == State.LOADING || state == State.ERROR)
    }

    fun setState(state: State) {
        this.state = state
        notifyItemChanged(super.getItemCount())
    }
}
