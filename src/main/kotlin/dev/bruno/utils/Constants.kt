package dev.bruno.utils

object Constants {
    // Validation
    const val EMAIL_REGEX = "^(?=.{1,256})(?=.{1,64}@.{1,255}$)(?:(?:[\\w!#\$%&'*+/=?^`{|}~-]+(?:\\.[\\w!#\$%&'*+/=?^`{|}~-]+)*)|(?:\\x22(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\\x22))@(?:(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-zA-Z0-9-]*[a-zA-Z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])$"
    const val NAME_MAX_LENGTH = 70
    const val DESCRIPTION_MAX_LENGTH = 200
    const val TITLE_MAX_LENGTH = 50
    const val TITLE_MIN_LENGTH = 4
    const val PASSWORD_MIN_LENGTH = 8
    const val PASSWORD_MAX_LENGTH = 200

    // Config
    const val CONFIG_PATH = "src/main/resources/application.conf"
}