package net.alienminds.ethnogram.service.auth.entities


/**
 * @see Completed
 * @see NeedOTP
 */
sealed class SignInByPhoneResult{

    /**
     * Authorization was successful, no OTP code required
     */
    data class Completed(
        val userId: String
    ): SignInByPhoneResult()

    /**
     * OTP code has been sent to the phone number
     */
    data class NeedOTP(
        val verificationId: String,
    ): SignInByPhoneResult()
}