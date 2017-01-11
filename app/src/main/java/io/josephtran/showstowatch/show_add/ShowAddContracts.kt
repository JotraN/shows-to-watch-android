package io.josephtran.showstowatch.show_add

interface ShowFormView {

    fun showMessage(message: String);

    fun showErrorMessage(errorMessage: String);

    fun closeView();
}