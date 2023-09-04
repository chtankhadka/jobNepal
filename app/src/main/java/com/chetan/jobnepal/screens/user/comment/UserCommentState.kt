package com.chetan.jobnepal.screens.user.comment

import com.chetan.jobnepal.data.models.comment.UserCommentModel

data class UserCommentState(
    val userCommentList: List<UserCommentModel> = emptyList(),
    val videoId: String = "",
    val userComment : String = ""
    )
