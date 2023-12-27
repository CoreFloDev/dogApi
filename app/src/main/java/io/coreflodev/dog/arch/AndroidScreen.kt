package io.coreflodev.dog.arch

import androidx.activity.ComponentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import io.coreflodev.common.arch.Attach
import io.coreflodev.common.arch.DomainAction
import io.coreflodev.common.arch.DomainResult
import io.coreflodev.common.arch.Screen
import io.coreflodev.common.arch.ScreenInput
import io.coreflodev.common.arch.ScreenNavigation
import io.coreflodev.common.arch.ScreenOutput

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
