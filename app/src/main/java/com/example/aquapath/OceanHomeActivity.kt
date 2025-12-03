package com.example.aquapath

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope // Import for coroutines
import com.example.aquapath.databinding.ActivityOceanHomeBinding
import kotlinx.coroutines.delay // Import for the delay function
import kotlinx.coroutines.launch // Import for launching the coroutine
import java.text.NumberFormat
import java.util.Locale

// REMOVED all the incorrect private val declarations that were here.

class OceanHomeActivity : AppCompatActivity() {

    // This is the only variable needed for your views.
    private lateinit var binding: ActivityOceanHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOceanHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupButtonListeners()
        animateCounters()
    }

    private fun setupButtonListeners() {
        // Now that the errors are gone, the compiler correctly knows
        // binding.btnVolunteer is a Button, and calls the correct setOnClickListener.
        binding.btnVolunteer.setOnClickListener {
            addButtonAnimation(it)
            Toast.makeText(this, "🙋‍♀️ Opening Volunteer Registration...", Toast.LENGTH_SHORT).show()
            // Example of how you would navigate to another activity
            // val intent = Intent(this, VolunteerActivity::class.java)
            // startActivity(intent)
        }

        binding.btnDonate.setOnClickListener {
            addButtonAnimation(it)
            Toast.makeText(this, "💙 Opening Donation Page...", Toast.LENGTH_SHORT).show()
            // val intent = Intent(this, DonateActivity::class.java)
            // startActivity(intent)
        }

        binding.btnJoinEvent.setOnClickListener {
            addButtonAnimation(it)
            Toast.makeText(this, "🌊 Opening Events List...", Toast.LENGTH_SHORT).show()
            // val intent = Intent(this, EventsActivity::class.java)
            // startActivity(intent)
        }
    }

    private fun addButtonAnimation(view: View) {
        ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.95f, 1f).setDuration(200).start()
        ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.95f, 1f).setDuration(200).start()
    }

    /**
     * Uses a coroutine to orchestrate animations with readable delays.
     * The lifecycleScope ensures the coroutine is automatically cancelled
     * when the activity is destroyed, preventing memory leaks.
     */
    private fun animateCounters() {
        lifecycleScope.launch {
            startCounterAnimation(binding.tvVolunteers, 2847)

            delay(200) // Non-blocking delay for 200ms
            startCounterAnimation(binding.tvTrash, 15629)

            delay(200) // The code waits here before proceeding
            startCounterAnimation(binding.tvDonations, 127, prefix = "$", suffix = "K")

            delay(200)
            startCounterAnimation(binding.tvProjects, 43)
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

// REMOVED the private fun Any.setOnClickListener function that was here.
