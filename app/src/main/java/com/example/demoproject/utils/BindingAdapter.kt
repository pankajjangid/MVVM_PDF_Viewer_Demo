package com.example.demoproject.utils

import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.demoproject.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


@BindingAdapter("bind:loadUrl")
fun bindUrlImage(view: ImageView, url: String?) {

    if (!url.isNullOrEmpty()){

        Glide.with(view)
            .load(url)
            .circleCrop()
            .into(view)

    }

}




@BindingAdapter("bind:applyText")
fun applyText(view: TextView, text: String?) {

    if (text.isNullOrEmpty()){
        view.text="N/A"
    }else{
        view.text=text
    }
}




@BindingAdapter("bind:toSmallDate")
fun bindToSmallDate(view: TextView, date: String) {
    try {
        //view.text = Converters.toSmallDate(date)

        // Convert input string into a date

        // Convert input string into a date
        val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S")
        val date: Date = inputFormat.parse(date)

// Format date into output format

// Format date into output format
        val outputFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
        val outputString: String = outputFormat.format(date)
        view.text = outputString

    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}






@BindingAdapter("bind:show")
fun bindToSmallDate(view: View, show: Boolean) {
    view.visibility = if (show) View.VISIBLE else View.GONE
}




@RequiresApi(Build.VERSION_CODES.M)
@BindingAdapter("bind:statusMessage")
fun statusMessage(textView: TextView, status: Int) {



         when (status) {
            1 ->{
                textView.setTextColor(textView.context.getColor(R.color.green))
                textView.text =  "Member Accepted"
            }
            2 ->{
                textView.setTextColor(textView.context.getColor(R.color.purple_700))

                textView.text =  "Member Declined"
            }
            else ->textView.text =  ""
        }

}