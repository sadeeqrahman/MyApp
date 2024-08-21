package com.oma.beyondpayment.core.mapper

interface Mapper<IN, OUT> {
    fun map(item: IN): OUT
}