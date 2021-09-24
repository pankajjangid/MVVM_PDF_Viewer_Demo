package com.example.demoproject.ui.main.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ActivityNavigator
import androidx.navigation.fragment.navArgs
import com.downloader.Error
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import com.example.demoproject.R
import com.example.demoproject.databinding.FragmentDetailsBinding
import com.example.demoproject.model.network.ContentResponse
import com.example.demoproject.utils.Utils
import java.io.File


class DetailsFragment : Fragment() {

    val args: DetailsFragmentArgs by navArgs()
    lateinit var mBinding: FragmentDetailsBinding
    lateinit var mViewModel: DetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        mBinding.lifecycleOwner = this
        mViewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)
        PRDownloader.initialize(requireContext())

        mBinding.mViewModel = mViewModel

        val content: ContentResponse.Content = args.content

        mViewModel.mediaTitleCustom.value = content.mediaTitleCustom
        mViewModel.mediaType.value = content.mediaType
        mViewModel.dateString.value = content.mediaDate.dateString



        mBinding.progressBar2.visibility = View.VISIBLE
        val fileName = "myFile.pdf"
        downloadPdfFromInternet(
            content.mediaUrl,
            Utils.getRootDirPath(requireContext()),
            fileName
        )







        return mBinding.root
    }

    private fun downloadPdfFromInternet(url: String, dirPath: String, fileName: String) {
        PRDownloader.download(
            url,
            dirPath,
            fileName
        ).build()
            .start(object : OnDownloadListener {
                override fun onDownloadComplete() {
                    Toast.makeText(requireContext(), "downloadComplete", Toast.LENGTH_LONG)
                        .show()
                    val downloadedFile = File(dirPath, fileName)
                   mBinding.progressBar2.visibility = View.GONE
                    showPdfFromFile(downloadedFile)
                }

                override fun onError(error: Error?) {
                   Utils.toast(

                        "Error in downloading file : $error"

                    )

                }
            })
    }


    private fun showPdfFromFile(file: File) {
        mBinding.pdfView.fromFile(file)
            .password(null)
            .defaultPage(0)
            .enableSwipe(true)
            .swipeHorizontal(false)
            .enableDoubletap(true)
            .onPageError { page, _ ->
               Utils.toast(

                    "Error at page: $page"
                )
            }
            .load()
    }

    companion object {


        @JvmStatic
        fun newInstance() =
            DetailsFragment().apply {
                arguments = Bundle().apply {


                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityNavigator.applyPopAnimationsToPendingTransition(requireActivity())

    }
}