package com.dipien.java.firebase.admin

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import java.io.FileInputStream
import java.io.InputStream

object FirebaseAdminSdkHelper {

    fun init(serviceAccountJsonPath: String) {
        init(FileInputStream(serviceAccountJsonPath))
    }

    fun initWithServiceAccount(serviceAccount: String) {
        init(serviceAccount.byteInputStream())
    }

    private fun init(serviceAccountStream: InputStream) {
        val builder = FirebaseOptions.builder()
        builder.setCredentials(GoogleCredentials.fromStream(serviceAccountStream))
        val options = builder.build()
        FirebaseApp.initializeApp(options)
    }
}
