package io.coreflodev.dog.common.nav

import android.content.Intent
import io.coreflodev.dog.BuildConfig

object Nav {
    object DetailsActivityNav {
        fun getStartingIntent(id: String) =
            Intent().apply {
                setClassName(BuildConfig.APPLICATION_ID, "io.coreflodev.dog.details.ui.DetailsActivity")
                putExtra(ID, id)
            }

        const val ID = "DOG_ID"
    }
}
