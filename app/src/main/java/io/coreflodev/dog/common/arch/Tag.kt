package io.coreflodev.dog.common.arch

interface ScreenInput

interface DomainAction

interface DomainResult {
    interface Navigation: DomainResult
    interface UiUpdate: DomainResult
}

interface ScreenNavigation

interface ScreenOutput
