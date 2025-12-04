package com.example.aquapath

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.aquapath.databinding.ActivityOceanHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

class OceanHomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOceanHomeBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOceanHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        setupUserProfile()
        setupButtonListeners()
        animateCounters()
    }

    private fun setupUserProfile() {
        val currentUser = auth.currentUser

        if (currentUser != null) {
            // Set Email immediately
            binding.tvProfileEmail.text = currentUser.email

            // Fetch Full Name from Firestore to generate Initials
            val userId = currentUser.uid
            db.collection("users").document(userId).get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        val fullName = document.getString("fullName") ?: "Volunteer"
                        binding.tvProfileName.text = fullName

                        // Generate Initials (e.g., "John Doe" -> "JD")
                        val initials = fullName.split(" ")
                            .mapNotNull { it.firstOrNull()?.toString() }
                            .take(2)
                            .joinToString("")
                            .uppercase()

                        binding.tvProfileInitials.text = initials
                    }
                }
                .addOnFailureListener {
                    // Fallback on error
                    binding.tvProfileName.text = "Volunteer"
                    binding.tvProfileInitials.text = "V"
                }
        }
    }

    private fun setupButtonListeners() {
        // FLOATING LOGOUT BUTTON LOGIC
        binding.fabLogout.setOnClickListener {
            auth.signOut()
            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            // Clear back stack to prevent going back to home
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        binding.btnVolunteer.setOnClickListener {
            addButtonAnimation(it)
            Toast.makeText(this, "🙋‍♀️ Opening Volunteer Registration...", Toast.LENGTH_SHORT).show()
        }

        binding.btnDonate.setOnClickListener {
            addButtonAnimation(it)
            Toast.makeText(this, "💙 Opening Donation Page...", Toast.LENGTH_SHORT).show()
        }

        binding.btnJoinEvent.setOnClickListener {
            addButtonAnimation(it)
            Toast.makeText(this, "🌊 Opening Events List...", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addButtonAnimation(view: View) {
        ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.95f, 1f).setDuration(200).start()
        ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.95f, 1f).setDuration(200).start()
    }

    private fun animateCounters() {
        lifecycleScope.launch {
            startCounterAnimation(binding.tvVolunteers, 2847)
            delay(200)
            startCounterAnimation(binding.tvTrash, 15629)
            delay(200)
            startCounterAnimation(binding.tvDonations, 127, prefix = "$", suffix = "K")
        }
    }

    private fun startCounterAnimation(
        textView: TextView,
        endValue: Int,
        duration: Long = 2000,
        prefix: String = "",
        suffix: String = ""
    ) {
        val animator = ValueAnimator.ofInt(0, endValue)
        animator.duration = duration
        animator.interpolator = AccelerateDecelerateInterpolator()

        animator.addUpdateListener { animation ->
            val currentValue = animation.animatedValue as Int
            val formattedText = if (prefix.isEmpty() && suffix.isEmpty()) {
                NumberFormat.getNumberInstance(Locale.US).format(currentValue)
            } else {
                "$prefix$currentValue$suffix"
            }
            textView.text = formattedText
        }
        animator.start()
    }
}

