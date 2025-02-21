package com.neighborly.neighborlyandroid.ui.login

sealed class LoginScreenState{
    data object Idle : LoginScreenState()
    data object Loading: LoginScreenState()
    data object AddressVerified:LoginScreenState()
    data object AddressNotVerified:LoginScreenState()
    //Error
    data class Error(val message:String): LoginScreenState()

}