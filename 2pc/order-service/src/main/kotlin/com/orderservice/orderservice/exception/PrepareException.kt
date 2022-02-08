package com.orderservice.orderservice.exception

class PrepareException: Exception {

    private var rollBackName: List<String> = mutableListOf()

    constructor(message: String, rollBackName: List<String>){
        this.rollBackName = rollBackName
    }

    fun rollBackName(): List<String>{
        return rollBackName
    }
}