package com.oma.beyondpayment.core.resourceprovider

import android.content.Context
import com.oma.beyondpayment.core.factory.Factory

class ResourcesProviderFactory(private val context: Context) : Factory<ResourcesProvider> {
    override fun create(): ResourcesProvider {
        return ResourcesProviderImpl(context)
    }
}