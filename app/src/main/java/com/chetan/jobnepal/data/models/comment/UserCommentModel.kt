package com.chetan.jobnepal.data.models.comment

data class UserCommentModel(
    val self : Boolean = false,
    val comment: String = "",
    val commentId: String = "",
    val videoId : String = "",
    val userName: String = ""
)
