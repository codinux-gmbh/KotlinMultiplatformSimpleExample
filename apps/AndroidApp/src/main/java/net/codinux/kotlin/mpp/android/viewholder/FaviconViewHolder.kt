package net.codinux.kotlin.mpp.android.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.codinux.kotlin.mpp.android.R

class FaviconViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val txtvwFaviconSize: TextView = itemView.findViewById(R.id.txtvwFaviconSize)

    val txtvwFaviconType: TextView = itemView.findViewById(R.id.txtvwFaviconType)

    val imgvwFavicon: ImageView = itemView.findViewById(R.id.imgvwFavicon)

}