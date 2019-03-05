package com.tranphuc.mvvm.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tranphuc.mvvm.R
import com.tranphuc.mvvm.repository.database.entity.People
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_people.view.*

import java.util.*

class PeopleAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private var mListPeople: MutableList<People> = ArrayList()
    private var mContext: Context


    public var ListPeople: MutableList<People>
        get() {
            return mListPeople
        }
        set(value) {
            mListPeople = value
        }


    constructor(mListPeople: MutableList<People>, mContext: Context) : super() {
        this.mListPeople = mListPeople
        this.mContext = mContext
    }


    override fun getItemViewType(position: Int): Int {
        return 0
    }

    override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        lateinit var viewHolder: RecyclerView.ViewHolder
        var layoutInflater = LayoutInflater.from(p0.context)
        var viewHolderPeople: View? = null
        if (viewType == 0) {
            viewHolderPeople = layoutInflater.inflate(R.layout.item_people, p0, false)
        }
        viewHolder = ViewHolderPeople(viewHolderPeople)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return mListPeople.size
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, position: Int) {
        when (p0.itemViewType) {
            0 -> {
                var viewHolderPeople = p0 as ViewHolderPeople


                val requestOptions = RequestOptions()
                requestOptions.placeholder(R.color.colorPlaceHolder)
                requestOptions.error(R.color.colorPlaceHolder)


                Glide.with(mContext)
                    .applyDefaultRequestOptions(requestOptions)
                    .load(mListPeople.get(position).LinkImage)
                    .into(viewHolderPeople.itemView.ciAvatar)

                viewHolderPeople.itemView.tvName.text = mListPeople.get(position).Name
            }
        }
    }

    inner class ViewHolderPeople : RecyclerView.ViewHolder {
        private var mCiAvatar: CircleImageView
        private var mTvName: TextView

        constructor(itemView: View?) : super(itemView!!) {
            mCiAvatar = itemView.findViewById(R.id.ciAvatar) as CircleImageView
            mTvName = itemView.findViewById(R.id.tvName) as TextView
        }
    }

}