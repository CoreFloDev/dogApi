package io.coreflodev.dog.nav

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import io.coreflodev.common.nav.Navigation
import io.coreflodev.dog.BuildConfig

class AndroidNavigation(private val context: Context) : Navigation {

    override fun startDetailsActivity(id: String) {
        context.startActivity(Intent().apply {
            flags = FLAG_ACTIVITY_NEW_TASK
            setClassName(BuildConfig.APPLICATION_ID, "io.coreflodev.dog.details.ui.DetailsActivity")
            putExtra(DetailsActivityNav.ID, id)
        })
    }

    object DetailsActivityNav {
        const val ID = "DOG_ID"
    }
}
