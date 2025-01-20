package net.alienminds.ethnogram.ui.screens.auth.otp

import cafe.adriel.voyager.navigator.Navigator
import net.alienminds.ethnogram.service.API
import net.alienminds.ethnogram.ui.screens.session.SessionScreen
import net.alienminds.ethnogram.utils.AppScreenModel

class AuthOTPViewModel: AppScreenModel(){


    fun signIn(
        rootNavigator: Navigator,
        verificationId: String,
        otpCode: String
    ) = withLoadingScope{
        val result = API.auth.confirmPhone(verificationId, otpCode)
        error = result.error
        if (result.isSuccess) {
            rootNavigator.replaceAll(SessionScreen())
        }
    }

    fun resendCode(){

    }
}