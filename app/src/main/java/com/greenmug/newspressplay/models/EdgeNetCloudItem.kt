package com.greenmug.newspressplay.models


import com.google.gson.annotations.SerializedName
/*
Data class for EdgeNetCloudItem
 */
data class EdgeNetCloudItem(
    @SerializedName("content_access")
    val contentAccess: String,
    @SerializedName("content_id")
    val contentId: String,
    @SerializedName("content_size")
    val contentSize: Int,
    @SerializedName("content_status_message")
    val contentStatusMessage: String,
    @SerializedName("content_upload_status")
    val contentUploadStatus: String,
    @SerializedName("content_url")
    val contentUrl: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("partner_cloud_url")
    val partnerCloudUrl: String,
    @SerializedName("partner_content_file_name")
    val partnerContentFileName: String,
    @SerializedName("partner_content_url")
    val partnerContentUrl: String,
    @SerializedName("partner_name")
    val partnerName: String,
    @SerializedName("publish_access")
    val publishAccess: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)