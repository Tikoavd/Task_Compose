package com.task_compose.task

import com.task_compose.task.BuildConfig
import com.task_compose.network.api.providers.UrlProvider
import org.koin.core.annotation.Single

@Single
class UrlProviderImpl(): UrlProvider {
    override fun baseUrl(): String = BuildConfig.BASE_URL
}
