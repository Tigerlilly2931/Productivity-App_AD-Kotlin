package com.example.productivity_app_ad

import java.util.*

class Checklist {
    var messageText: String? = null
    var messageUser: String? = null
    var completedCheck: Boolean? = false

    constructor(messagetext: String?, messageuser: String?) {
        this.messageText = messagetext
        this.messageUser = messageuser
    }
    constructor(isDone: Boolean?){
        completedCheck = isDone
    }

    constructor(){

    }
}