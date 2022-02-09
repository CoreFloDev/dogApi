package io.coreflodev.dog.common.arch

interface DomainResult {
    interface Navigation: DomainResult
    interface UiUpdate: DomainResult
}
