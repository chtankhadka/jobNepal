package com.chetan.jobnepal.screens.user.comment

import com.chetan.jobnepal.data.models.comment.UserCommentModel

data class UserCommentState(
    val userCommentList : List<UserCommentModel> = emptyList(),
    val userComment : String = ""

    )
