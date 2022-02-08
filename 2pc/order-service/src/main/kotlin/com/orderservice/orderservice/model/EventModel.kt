package com.orderservice.orderservice.model

open class EventModel(
    open val status: Status,
    open val rollbackName: String
)