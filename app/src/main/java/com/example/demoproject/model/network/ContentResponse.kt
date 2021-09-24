package com.example.demoproject.model.network
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ContentResponse(
    @SerializedName("content")
    val content: List<Content>,
    @SerializedName("lang")
    val lang: String,
    @SerializedName("status")
    val status: Boolean
):Parcelable {

    @Parcelize
    data class Content(
        @SerializedName("mediaDate")
        val mediaDate: MediaDate,
        @SerializedName("mediaId")
        val mediaId: Int,
        @SerializedName("mediaTitleCustom")
        val mediaTitleCustom: String,
        @SerializedName("mediaType")
        val mediaType: String,
        @SerializedName("mediaUrl")
        val mediaUrl: String,
        @SerializedName("mediaUrlBig")
        val mediaUrlBig: String
    ):Parcelable {
        @Parcelize
        data class MediaDate(
            @SerializedName("dateString")
            val dateString: String,
            @SerializedName("year")
            val year: String
        ):Parcelable
    }
}