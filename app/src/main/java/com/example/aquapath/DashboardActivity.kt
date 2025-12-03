package com.example.aquapath


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth

class DashboardActivity : AppCompatActivity() {

    // Declare views
    private lateinit var btnGoToOceanHome: CardView
    private lateinit var btnLogout: Button
    private lateinit var tvUserEmail: TextView

    // Firebase Auth
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Initialize Firebase
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        // Initialize Views
        btnGoToOceanHome = findViewById(R.id.btnGoToOceanHome)
        btnLogout = findViewById(R.id.btnLogout)
        tvUserEmail = findViewById(R.id.tvUserEmail)

        // Set User Email in Header
        if (currentUser != null) {
            tvUserEmail.text = currentUser.email
        }

        // 🌊 Navigation to Ocean Home Activity
        btnGoToOceanHome.setOnClickListener {
            val intent = Intent(this, OceanHomeActivity::class.java)
            startActivity(intent)
            // Optional: add transition animation
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        // 🚪 Logout Logic
        btnLogout.setOnClickListener {
            auth.signOut()
            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()

            // Redirect to Login Page and clear back stack
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}
