package com.example.demoproject.ui.main.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.demoproject.R
import com.example.demoproject.databinding.RowLayoutContentBinding
import com.example.demoproject.model.network.ContentResponse
import java.util.ArrayList

class ListAdapter(
    val list : ArrayList<ContentResponse.Content>,val callBack:ClickCallback) : RecyclerView.Adapter<ListAdapter.ListHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {

        val mBinding = DataBindingUtil.inflate<RowLayoutContentBinding>(LayoutInflater.from(parent.context), R.layout.row_layout_content,parent,false)

        return  ListHolder(mBinding)

    }

    override fun onBindViewHolder(holder: ListHolder, position: Int) {

        holder.bind(list[position])

    }

    override fun getItemCount(): Int {
     return  list.size
    }


    inner  class ListHolder(val mBinding: RowLayoutContentBinding) : RecyclerView.ViewHolder(mBinding.root) {


        fun bind(content: ContentResponse.Content) {

            mBinding.item = content

            mBinding.mainLayout.setOnClickListener {

                callBack.onItemClick(content)
            }
            mBinding.executePendingBindings()


        }

    }


    interface  ClickCallback{
        fun onItemClick(item:ContentResponse.Content)
    }
}