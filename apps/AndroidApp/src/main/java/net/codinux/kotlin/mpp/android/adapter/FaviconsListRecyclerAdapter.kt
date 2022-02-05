package net.codinux.kotlin.mpp.android.adapter

import android.view.View
import com.squareup.picasso.Picasso
import net.codinux.kotlin.example.favicon.model.Favicon
import net.codinux.kotlin.mpp.android.R
import net.codinux.kotlin.mpp.android.viewholder.FaviconViewHolder
import net.dankito.utils.android.ui.adapter.ListRecyclerAdapter

class FaviconsListRecyclerAdapter : ListRecyclerAdapter<Favicon, FaviconViewHolder>() {

    override fun getListItemLayoutId() = R.layout.list_item_favicon

    override fun createViewHolder(itemView: View): FaviconViewHolder {
        return FaviconViewHolder(itemView)
    }

    override fun bindItemToView(viewHolder: FaviconViewHolder, item: Favicon) {
        viewHolder.txtvwFaviconSize.text = item.size?.getDisplayText() ?: "unknown"

        viewHolder.txtvwFaviconType.text = item.iconType.toString()

        Picasso.get().load(item.url).into(viewHolder.imgvwFavicon)
    }

}