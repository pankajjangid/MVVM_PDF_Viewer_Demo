package com.example.demoproject.ui.main.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.demoproject.R
import com.example.demoproject.databinding.FragmentListBinding
import com.example.demoproject.model.network.ContentResponse
import com.example.demoproject.utils.ProgressDialogUtil
import com.example.demoproject.utils.Utils
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.util.*
import com.google.android.material.snackbar.Snackbar

import androidx.recyclerview.widget.RecyclerView

import androidx.annotation.NonNull

import androidx.recyclerview.widget.ItemTouchHelper





class ListFragment : Fragment(), KodeinAware, ListListener {

    lateinit var adapter: ListAdapter
    lateinit var mBinding: FragmentListBinding
    lateinit var mViewModel: ListViewModel
    val factory: ListViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)
        mBinding.lifecycleOwner = this
        mViewModel = ViewModelProvider(this, factory).get(ListViewModel::class.java)
        mViewModel.listener = this

        mBinding.mViewModel = mViewModel
        mViewModel.requestContent()


        return mBinding.root
    }

    companion object {


        @JvmStatic
        fun newInstance() =
            ListFragment().apply {
                arguments = Bundle().apply {


                }
            }
    }

    override val kodein: Kodein by kodein()
    override fun onStarted() {
        ProgressDialogUtil.showProgress(requireContext())
    }

    override fun onSuccess(responseData: Any?) {
        ProgressDialogUtil.hideProgress()

        val contentResponse = responseData as ContentResponse
        adapter = ListAdapter(contentResponse.content as ArrayList<ContentResponse.Content>,
            object : ListAdapter.ClickCallback {
                override fun onItemClick(item: ContentResponse.Content) {


                    val actionListFragmentToDetailsFragment =
                        ListFragmentDirections.actionListFragmentToDetailsFragment(item)
                    findNavController().navigate(actionListFragmentToDetailsFragment)
                }
            })

        mBinding.rvContent.adapter = adapter

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                // this method is called
                // when the item is moved.
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // this method is called when we swipe our item to right direction.
                // on below line we are getting the item at a particular position.
                val item: ContentResponse.Content = adapter.list[viewHolder.adapterPosition]

                // below line is to get the position
                // of the item at that position.
                val position = viewHolder.adapterPosition

                // this method is called when item is swiped.
                // below line is to remove item from our array list.
                adapter.list.removeAt(viewHolder.adapterPosition)

                // below line is to notify our item is removed from adapter.
                adapter.notifyItemRemoved(viewHolder.adapterPosition)

                // below line is to display our snackbar with action.
                Snackbar.make(mBinding.rvContent, item.mediaTitleCustom, Snackbar.LENGTH_LONG)
                    .setAction("Undo",
                        View.OnClickListener { // adding on click listener to our action of snack bar.
                            // below line is to add our item to array list with a position.
                            adapter.list.add(position, item)

                            // below line is to notify item is
                            // added to our adapter class.
                            adapter.notifyItemInserted(position)
                        }).show()
            } // at last we are adding this
            // to our recycler view.
        }).attachToRecyclerView(mBinding.rvContent)

    }

    override fun onFailure(message: String?) {
        ProgressDialogUtil.hideProgress()

        message?.let {

            Utils.toast(message)
        }
    }

}