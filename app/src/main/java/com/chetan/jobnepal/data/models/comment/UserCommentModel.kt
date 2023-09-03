package com.chetan.jobnepal.data.models.comment

data class UserCommentModel(
    val isSelf : Boolean = false,
    val comment: String = "",
    val commentId: String = "",
    val videoId : String = "",
    val date : String = "",
)
