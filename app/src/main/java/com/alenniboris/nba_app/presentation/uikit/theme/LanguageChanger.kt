package com.alenniboris.nba_app.presentation.uikit.theme

import android.content.Context
import android.content.res.Configuration
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Locale

enum class AppLanguage(val locale: Locale, val langString: String) {
    Russian(Locale("ru"), "Русский"),
    English(Locale.ENGLISH, "English")
}

sealed class LanguageMode(val language: AppLanguage) {
    class English : LanguageMode(AppLanguage.English)
    class Russian : LanguageMode(AppLanguage.Russian)
}

private val _currentLanguageMode = MutableStateFlow<LanguageMode>(LanguageMode.English())
val currentLanguageMode = _currentLanguageMode.asStateFlow()

private val PREFERENCIES_NAME = "LAST_LANGUAGE"
private val LANGUAGE_STRING_NAME = "LANGUAGE"

private fun AppLanguage.update() {
    _currentLanguageMode.update {
        when (this) {
            AppLanguage.Russian -> LanguageMode.Russian()
            AppLanguage.English -> LanguageMode.English()
        }
    }
}

fun Context.setLanguage(
    language: AppLanguage,
) {
    applicationContext.getSharedPreferences(PREFERENCIES_NAME, Context.MODE_PRIVATE)
        .edit()
        .putString(LANGUAGE_STRING_NAME, language.name)
        .apply()

    applyLanguage(language)
    language.update()
}


private fun Context.applyLanguage(
    language: AppLanguage
) {
    val locale = language.locale
    Locale.setDefault(locale)
    val config = Configuration()
    config.setLocale(locale)
    resources.updateConfiguration(config, resources.displayMetrics)
    applicationContext.resources.updateConfiguration(config, resources.displayMetrics)
}

fun Context.getLastLanguageAndApply(): AppLanguage {
    val language = applicationContext.getSharedPreferences(PREFERENCIES_NAME, Context.MODE_PRIVATE)
        .getString(LANGUAGE_STRING_NAME, null)?.let { lastLanguage ->
            AppLanguage.entries.find { it.name == lastLanguage }
        } ?: AppLanguage.English
    language.update()
    applyLanguage(language)
    return _currentLanguageMode.value.language
}