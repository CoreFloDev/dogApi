package io.coreflodev.common.arch

import androidx.activity.ComponentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class AndroidScreen<I : ScreenInput, O : ScreenOutput, N : ScreenNavigation, A : DomainAction, R : DomainResult>(
    private val screen: Screen<I, O, N, A, R>,
    activity: ComponentActivity,
    private val callback: (Attach<I, O, N>) -> Unit
) : DefaultLifecycleObserver {

    init {
        activity.lifecycle.addObserver(this)
    }

    override fun onCreate(owner: LifecycleOwner) {
        callback(screen.attach())
    }

    override fun onDestroy(owner: LifecycleOwner) {
        screen.detach()
    }
}
