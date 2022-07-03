package com.lelyuh.githubinfo.presentation.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Custom start screen with custom app logo
 *
 * @author Leliukh Aleksandr
 */
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(GitHubLoginActivity.newIntent(this))
        finish()
    }
}
