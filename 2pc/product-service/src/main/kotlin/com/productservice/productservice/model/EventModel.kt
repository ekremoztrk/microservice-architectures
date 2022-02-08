package com.productservice.productservice.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

open class EventModel{
    open var status: Status
    open var rollbackName: String

    constructor(status: Status, rollbackName: String){
        this.status = status
        this.rollbackName = rollbackName
    }

    @JsonCreator
    constructor(@JsonProperty("status") status: String, @JsonProperty("rollbackName") rollbackName: String){
        if(status.equals("SUCCESS"))
            this.status = Status.SUCCESS
        else
            this.status = Status.FAILED
        this.rollbackName = rollbackName
    }
}