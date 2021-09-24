package com.example.demoproject.utils

import android.app.ProgressDialog
import android.content.Context
import com.example.demoproject.R


class ProgressDialogUtil {

    companion object {

        private var isLoadingVisible: Boolean = false
        private var mProgressDialog: ProgressDialog? = null

        fun showProgress(mContext: Context?) {
            if (isLoadingVisible) {
                hideProgress()
            }
            isLoadingVisible = true
            mProgressDialog = ProgressDialog(mContext)
            mProgressDialog!!.setMessage(mContext!!.getString(R.string.loading))
            mProgressDialog!!.isIndeterminate = false
            mProgressDialog!!.setCanceledOnTouchOutside(false)
            mProgressDialog!!.setCancelable(false)
            mProgressDialog!!.show()
        }

        fun showProgress(mContext: Context?, message: String?) {
            if (isLoadingVisible) {
                hideProgress()
            }
            isLoadingVisible = true
            mProgressDialog = ProgressDialog(mContext)
            mProgressDialog!!.setMessage(message)
            mProgressDialog!!.isIndeterminate = false
            mProgressDialog!!.setCanceledOnTouchOutside(false)
            mProgressDialog!!.setCancelable(false)
            mProgressDialog!!.show()
        }

        /**
         * method to hide progress
         */
        fun hideProgress() {
            try {
                if (mProgressDialog != null && mProgressDialog!!.isShowing) {
                    mProgressDialog!!.dismiss()
                    isLoadingVisible = false
                }
            } catch (e: Exception) {

                e.printStackTrace()
            }
        }
    }

}
