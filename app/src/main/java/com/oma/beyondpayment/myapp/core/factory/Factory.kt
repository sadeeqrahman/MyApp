package com.oma.beyondpayment.core.factory

interface Factory<T> {
    fun create(): T
}