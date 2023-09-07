package com.korusell.korusell.ui.auth

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.korusell.korusell.ui.RootRoutes
import com.korusell.korusell.ui.auth.code.Code
import com.korusell.korusell.ui.auth.onboarding.Onboarding
import com.korusell.korusell.ui.auth.phone.Phone

enum class AuthRoutes(
    val route: String,
    val content: @Composable (
        navController: NavHostController,
        onResult: () -> Unit
    ) -> Unit
) {
    ONBOARDING(
        route = "${RootRoutes.AUTH.route}_onboarding",
        content = { controller, _ ->
            Onboarding(controller)
        }
    ),
    PHONE(
        route = "${RootRoutes.AUTH.route}_phone",
        content = { controller, _ ->
            Phone(controller)
        }
    ),
    CODE(
        route = "${RootRoutes.AUTH.route}_code",
        content = { controller, onResult ->
            Code(controller, onResult)
        }
    )
}