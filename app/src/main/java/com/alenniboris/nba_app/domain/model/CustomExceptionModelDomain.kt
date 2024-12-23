package com.alenniboris.nba_app.domain.model

sealed class CustomExceptionModelDomain() : Throwable() {

    /* Thrown when string is not suited for email type */
    data object NotEmailTypeValueException : CustomExceptionModelDomain()

    /* Thrown when password and password check are not equal */
    data object PasswordIsNotEqualWithItsCheckException : CustomExceptionModelDomain()

    /* Thrown when using a weak password (less than 6 chars) */
    data object WeakPasswordException : CustomExceptionModelDomain()

    /* Thrown when one or more of the credentials passed to a method fail to identify
    // and/or authenticate the user subject of that operation
    */
    data object InvalidCredentialsException : CustomExceptionModelDomain()

    /*
    Thrown when performing an operation on a FirebaseUser instance that is no longer valid.

    This could happen in the following cases:

    ERROR_USER_DISABLED
        if the user has been disabled (for example, in the Firebase console)

    ERROR_USER_NOT_FOUND
        if the user has been deleted (for example, in the Firebase console, or in another instance
        of this app)

    ERROR_USER_TOKEN_EXPIRED
        if the user's token has been revoked in the backend. This happens automatically if the user's
        credentials change in another device (for example, on a password change event).

    ERROR_INVALID_USER_TOKEN
        if the user's token is malformed. This should not happen under normal circumstances.
    */
    data object InvalidUserException : CustomExceptionModelDomain()

    /*
    Thrown when a web operation couldn't be completed.

    This could happen in the following cases:

    ERROR_WEB_CONTEXT_ALREADY_PRESENTED
        thrown when another web operation is still in progress.

    ERROR_WEB_CONTEXT_CANCELED
        thrown when the pending operation was canceled by the user.

    ERROR_WEB_STORAGE_UNSUPPORTED
        thrown when the browser is not supported, or when 3rd party cookies or data are disabled
         in the browser.

    ERROR_WEB_INTERNAL_ERROR
        when there was a problem that occurred inside of the web widget that hosts the operation.
        Details should always accompany this message.
    */
    data object WebException : CustomExceptionModelDomain()

    /*
    Thrown when an operation on a FirebaseUser instance couldn't be completed due to a conflict with another existing user.

    This could happen in the following cases:

    ERROR_EMAIL_ALREADY_IN_USE
        when trying to create a new account with createUserWithEmailAndPassword or to change a user's
        email address, if the email is already in use by a different account

    ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL
        when calling signInWithCredential with a credential that asserts an email address in use by
        another account. This error will only be thrown if the "One account per email address" setting
        is enabled in the Firebase console (recommended).

    ERROR_CREDENTIAL_ALREADY_IN_USE
        when trying to link a user with an corresponding to another account already in use.
     */
    data object UserCollisionException : CustomExceptionModelDomain()

    /* Thrown when some unpredicted situation occurred */
    data object Other : CustomExceptionModelDomain()

}