package com.chetan.jobnepal.screens.user.comment

sealed interface UserCommentEvent{
    class GetUsersComment(val value: String) : UserCommentEvent
    data object SetUserComment : UserCommentEvent
    class OnCommentChange(val value: String) : UserCommentEvent

}