package com.alenniboris.nba_app.presentation.test_permissions

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

//Accompanist
//implementation("com.google.accompanist:accompanist-permissions:0.23.1")

enum class PermissionType {
    PERMISSION_POST_NOTIFICATION,
    PERMISSION_WRITE_EXTERNAL_STORAGE
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun PermissionType.toPermission(): String = when (this) {
    PermissionType.PERMISSION_POST_NOTIFICATION -> Manifest.permission.POST_NOTIFICATIONS
    PermissionType.PERMISSION_WRITE_EXTERNAL_STORAGE -> Manifest.permission.WRITE_EXTERNAL_STORAGE
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun PermissionType.toPermissionExplanation(): String = when (this) {
    PermissionType.PERMISSION_POST_NOTIFICATION -> "Permission to post notifications"
    PermissionType.PERMISSION_WRITE_EXTERNAL_STORAGE -> "Permission to access external storage"
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun PermissionType.toPermissionName(): String = when (this) {
    PermissionType.PERMISSION_POST_NOTIFICATION -> "Post notifications"
    PermissionType.PERMISSION_WRITE_EXTERNAL_STORAGE -> "Access external storage"
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun TestPermissionScreen() {

    Column {

//        Text("WITH CONTENT AS PARAMETER")
//
//        PermissionRequesterWithContent(
//            typeOfPermission = PermissionType.PERMISSION_POST_NOTIFICATION,
//            onPermissionGrantedAction = { permissionType ->
//                Log.e("!!!", "permission granted = ${permissionType.toPermission()}")
//            },
//            onPermissionNotGrantedAction = { permissionType ->
//                Log.e("!!!", "permission not granted = ${permissionType.toPermission()}")
//            },
//            content = { action ->
//                Button(
//                    onClick = action
//                ) {
//                    Text(text = PermissionType.PERMISSION_POST_NOTIFICATION.toPermissionExplanation())
//                }
//            }
//        )
//
//        Spacer(Modifier.height(10.dp))
//
//        PermissionRequesterWithContent(
//            typeOfPermission = PermissionType.PERMISSION_WRITE_EXTERNAL_STORAGE,
//            onPermissionGrantedAction = { permissionType ->
//                Log.e("!!!", "permission granted = ${permissionType.toPermission()}")
//            },
//            onPermissionNotGrantedAction = { permissionType ->
//                Log.e("!!!", "permission not granted = ${permissionType.toPermission()}")
//            },
//            content = { action ->
//                Button(
//                    onClick = action
//                ) {
//                    Text(text = PermissionType.PERMISSION_WRITE_EXTERNAL_STORAGE.toPermissionExplanation())
//                }
//            }
//        )


// -----------------------------------------------------------------------------------------------------

//        Spacer(Modifier.height(10.dp))
//
//        Text("WITH LAUNCHER AS PARAMETER")
//
//        PermissionRequesterWithLauncher(
//            typeOfPermission = PermissionType.PERMISSION_POST_NOTIFICATION,
//            onPermissionGrantedAction = { permissionType ->
//                Log.e("!!!", "permission granted = ${permissionType.toPermission()}")
//            },
//            onPermissionNotGrantedAction = { permissionType ->
//                Log.e("!!!", "permission not granted = ${permissionType.toPermission()}")
//            }
//        )
//
//        Spacer(Modifier.height(10.dp))
//
//        PermissionRequesterWithLauncher(
//            typeOfPermission = PermissionType.PERMISSION_WRITE_EXTERNAL_STORAGE,
//            onPermissionGrantedAction = { permissionType ->
//                Log.e("!!!", "permission granted = ${permissionType.toPermission()}")
//            },
//            onPermissionNotGrantedAction = { permissionType ->
//                Log.e("!!!", "permission not granted = ${permissionType.toPermission()}")
//            }
//        )


// -----------------------------------------------------------------------------------------------------

        Spacer(Modifier.height(10.dp))

        Text("WITH LAUNCHER AS EXTENSION")

        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = {}
        )


        val context = LocalContext.current
        var isDialogVisible by remember { mutableStateOf(false) }
        var requestedPermission by remember { mutableStateOf<PermissionType?>(null) }

        Button(
            onClick = {
                launchForPermission(
                    permission = PermissionType.PERMISSION_POST_NOTIFICATION,
                    context = context,
                    onPermissionGrantedAction = { permissionType ->
                        Log.e("!!!", "permission granted = ${permissionType.toPermissionName()}")
                    },
                    onPermissionNotGrantedAction = { permissionType ->
                        Log.e(
                            "!!!",
                            "permission not granted = ${permissionType.toPermissionName()}"
                        )
                    },
                    onShowRationale = {
                        isDialogVisible = true
                        requestedPermission = PermissionType.PERMISSION_POST_NOTIFICATION
                    },
                    onLaunchAgain = { requestedPermission ->
                        launcher.launch(requestedPermission.toPermission())
                    }
                )
            }
        ) {
            Text("PERMISSION_POST_NOTIFICATION")
        }

        Button(
            onClick = {
                launchForPermission(
                    permission = PermissionType.PERMISSION_WRITE_EXTERNAL_STORAGE,
                    context = context,
                    onPermissionGrantedAction = { permissionType ->
                        Log.e("!!!", "permission granted = ${permissionType.toPermissionName()}")
                    },
                    onPermissionNotGrantedAction = { permissionType ->
                        Log.e(
                            "!!!",
                            "permission not granted = ${permissionType.toPermissionName()}"
                        )
                    },
                    onShowRationale = { permission ->
                        isDialogVisible = true
                        requestedPermission = permission
                    },
                    onLaunchAgain = { permission ->
                        launcher.launch(permission.toPermission())
                    }
                )
            }
        ) {
            Text("PERMISSION_WRITE_EXTERNAL_STORAGE")
        }

        if (isDialogVisible) {
            requestedPermission?.let {
                PermissionAlertWithLauncher(
                    permissionType = requestedPermission!!,
                    onDismiss = { isDialogVisible = false },
                    onOpenSettings = {
                        val openingIntent =
                            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                data = Uri.fromParts("package", context.packageName, null)
                            }
                        context.startActivity(openingIntent)
                        isDialogVisible = false
                    }
                )
            }
        }

    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun PermissionRequesterWithContent(
    typeOfPermission: PermissionType,
    onPermissionGrantedAction: (PermissionType) -> Unit,
    onPermissionNotGrantedAction: (PermissionType) -> Unit,
    content: @Composable (() -> Unit) -> Unit
) {

    val context = LocalContext.current
    var isDialogHidden by remember { mutableStateOf(true) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            onPermissionGrantedAction(typeOfPermission)
        } else {
            onPermissionNotGrantedAction(typeOfPermission)
            isDialogHidden = context.findActivity()
                .shouldShowRequestPermissionRationale(typeOfPermission.toPermission())
        }
    }

    content { launcher.launch(typeOfPermission.toPermission()) }

    if (!isDialogHidden) {
        PermissionAlert(
            permissionType = typeOfPermission,
            onDismiss = { isDialogHidden = true },
            onOpenSettings = {
                val openingIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", context.packageName, null)
                }
                context.startActivity(openingIntent)
                isDialogHidden = true
            }
        )
    }

}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun PermissionAlert(
    permissionType: PermissionType,
    onDismiss: () -> Unit,
    onOpenSettings: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(
                onClick = onOpenSettings
            ) {
                Text(
                    text = "Go to settings"
                )
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) { Text("Dismiss") }
        },
        title = { Text(permissionType.toPermissionName()) },
        text = { Text(permissionType.toPermissionExplanation()) }
    )
}


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun PermissionRequesterWithLauncher(
    typeOfPermission: PermissionType,
    onPermissionGrantedAction: (PermissionType) -> Unit,
    onPermissionNotGrantedAction: (PermissionType) -> Unit,
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            onPermissionGrantedAction(typeOfPermission)
        } else {
            onPermissionNotGrantedAction(typeOfPermission)
        }
    }
    val context = LocalContext.current

    when {

        typeOfPermission == PermissionType.PERMISSION_WRITE_EXTERNAL_STORAGE
                && (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) -> {
            onPermissionGrantedAction(typeOfPermission)
        }

        ActivityCompat.shouldShowRequestPermissionRationale(
            context.findActivity(), typeOfPermission.toPermission()
        ) -> {
            var isLauncherAlertVisible by remember { mutableStateOf(true) }
            if (isLauncherAlertVisible) {
                PermissionAlertWithLauncher(
                    permissionType = typeOfPermission,
                    onDismiss = { isLauncherAlertVisible = false },
                    onOpenSettings = {
                        val openingIntent =
                            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                data = Uri.fromParts("package", context.packageName, null)
                            }
                        context.startActivity(openingIntent)
                        isLauncherAlertVisible = false
                    }
                )
            }
        }

        else -> {
            SideEffect {
                launcher.launch(typeOfPermission.toPermission())
            }
        }
    }

}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun PermissionAlertWithLauncher(
    permissionType: PermissionType,
    onDismiss: () -> Unit,
    onOpenSettings: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(
                onClick = onOpenSettings
            ) {
                Text(
                    text = "Go to settings"
                )
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) { Text("Dismiss") }
        },
        title = { Text(permissionType.toPermissionName()) },
        text = { Text(permissionType.toPermissionExplanation()) }
    )
}

fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("no activity")
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun launchForPermission(
    permission: PermissionType,
    context: Context,
    onPermissionGrantedAction: (PermissionType) -> Unit,
    onPermissionNotGrantedAction: (PermissionType) -> Unit,
    onShowRationale: (PermissionType) -> Unit,
    onLaunchAgain: (PermissionType) -> Unit
) {
    when {
        permission == PermissionType.PERMISSION_WRITE_EXTERNAL_STORAGE
                && (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) -> {
            onPermissionGrantedAction(permission)
        }

        ContextCompat.checkSelfPermission(
            context,
            permission.toPermission()
        ) == PackageManager.PERMISSION_GRANTED -> {
            onPermissionGrantedAction(permission)
        }

        ActivityCompat.shouldShowRequestPermissionRationale(
            context.findActivity(), permission.toPermission()
        ) -> {
            onShowRationale(permission)
        }

        else -> {
            onPermissionNotGrantedAction(permission)
            onLaunchAgain(permission)
        }
    }
}
