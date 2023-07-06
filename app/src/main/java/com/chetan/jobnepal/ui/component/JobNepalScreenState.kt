package com.chetan.jobnepal.ui.component

import com.chetan.jobnepal.ui.component.dialogs.Message
import com.chetan.jobnepal.ui.component.dialogs.Progress

open class JobNepalScreenState(
    open val infoMsg: Message?,
    open val progress: Progress?,
)