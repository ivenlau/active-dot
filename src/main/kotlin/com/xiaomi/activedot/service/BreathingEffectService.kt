package com.xiaomi.activedot.service

import com.intellij.openapi.Disposable
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.Service
import com.xiaomi.activedot.settings.ActiveDotSettingsState
import java.awt.Color
import java.util.Random
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit
import kotlin.math.sin

@Service(Service.Level.APP)
class BreathingEffectService : Disposable {

    private val random = Random()
    private var startTime = System.currentTimeMillis()
    private var currentRandomColor: Color? = null
    private var nextRandomColor: Color? = null
    private var lastRandomUpdateTime = 0L

    private val executor = Executors.newSingleThreadScheduledExecutor { r ->
        Thread(r, "BreathingEffectScheduler").apply { isDaemon = true }
    }
    private var scheduledTask: ScheduledFuture<*>? = null

    init {
        // Start the animation loop
        // Optimized to ~10 FPS (100ms) to minimize CPU usage
        scheduledTask = executor.scheduleWithFixedDelay(
            { notifyRepaint() },
            0,
            100,
            TimeUnit.MILLISECONDS
        )
    }

    private fun notifyRepaint() {
        // Performance Fix: Check if breathing is enabled before dispatching to EDT.
        // This prevents flooding the Event Dispatch Thread when the feature is disabled.
        if (!ActiveDotSettingsState.getInstance().breathingLightEnabled) {
            return
        }

        ApplicationManager.getApplication().invokeLater {
            synchronized(listeners) {
                if (listeners.isNotEmpty()) {
                    listeners.forEach { it.onBreathingColorChanged(getCurrentBreathingColor()) }
                }
            }
        }
    }

    /**
     * Get the current breathing color based on settings and time
     */
    fun getCurrentBreathingColor(): Color? {
        val settings = ActiveDotSettingsState.getInstance()

        if (!settings.breathingLightEnabled) {
            return null
        }

        val frequency = settings.breathingFrequency.coerceIn(1, 10)
        val currentTime = System.currentTimeMillis()
        val period = 10000L / frequency  // Higher frequency = shorter period
        val phase = ((currentTime - startTime) % period).toDouble() / period

        // Use sine wave for smooth breathing effect (0.0 to 1.0)
        val breathingValue = (sin(phase * 2 * Math.PI) + 1) / 2

        return when (settings.breathingMode) {
            "fixed" -> {
                val baseColor = Color.decode("#" + settings.dotColor)
                applyBreathingEffect(baseColor, breathingValue)
            }
            "random" -> {
                // Initialize colors on first run
                if (currentRandomColor == null) {
                    currentRandomColor = Color(random.nextInt(256), random.nextInt(256), random.nextInt(256))
                    nextRandomColor = Color(random.nextInt(256), random.nextInt(256), random.nextInt(256))
                    lastRandomUpdateTime = currentTime
                }

                // Update to next random color at the end of each period
                if (currentTime - lastRandomUpdateTime > period) {
                    currentRandomColor = nextRandomColor
                    nextRandomColor = Color(random.nextInt(256), random.nextInt(256), random.nextInt(256))
                    lastRandomUpdateTime = currentTime
                }

                // Calculate interpolation progress
                val progress = ((currentTime - lastRandomUpdateTime).toDouble() / period).coerceIn(0.0, 1.0)

                // Interpolate between current and next random colors
                val interpolatedColor = lerpColor(currentRandomColor!!, nextRandomColor!!, progress)
                applyBreathingEffect(interpolatedColor, breathingValue)
            }
            "custom" -> {
                // Cycle through custom colors with smooth transitions
                val customColors = settings.customColors
                if (customColors.size >= 4) {
                    val colorPeriod = period * 4
                    val totalProgress = ((currentTime - startTime) % colorPeriod).toDouble() / colorPeriod
                    val colorIndex = (totalProgress * 4).toInt()
                    val nextColorIndex = (colorIndex + 1) % 4
                    val colorProgress = (totalProgress * 4) - colorIndex

                    val currentColor = Color.decode("#" + customColors[colorIndex])
                    val nextColor = Color.decode("#" + customColors[nextColorIndex])

                    // Interpolate between colors based on progress
                    val interpolatedColor = lerpColor(currentColor, nextColor, colorProgress)
                    applyBreathingEffect(interpolatedColor, breathingValue)
                } else {
                    val baseColor = Color.decode("#" + settings.dotColor)
                    applyBreathingEffect(baseColor, breathingValue)
                }
            }
            else -> {
                val baseColor = Color.decode("#" + settings.dotColor)
                applyBreathingEffect(baseColor, breathingValue)
            }
        }
    }

    /**
     * Linear interpolation between two colors
     */
    private fun lerpColor(color1: Color, color2: Color, t: Double): Color {
        val r = (color1.red + (color2.red - color1.red) * t).toInt().coerceIn(0, 255)
        val g = (color1.green + (color2.green - color1.green) * t).toInt().coerceIn(0, 255)
        val b = (color1.blue + (color2.blue - color1.blue) * t).toInt().coerceIn(0, 255)
        return Color(r, g, b)
    }

    /**
     * Apply breathing effect to a color by adjusting brightness
     */
    private fun applyBreathingEffect(color: Color, value: Double): Color {
        // Value ranges from 0.0 to 1.0
        // We want the color to breathe from darker to brighter
        // Use a range of 30% to 100% brightness
        val minBrightness = 0.3
        val maxBrightness = 1.0
        val brightness = minBrightness + (maxBrightness - minBrightness) * value

        val r = (color.red * brightness).toInt().coerceIn(0, 255)
        val g = (color.green * brightness).toInt().coerceIn(0, 255)
        val b = (color.blue * brightness).toInt().coerceIn(0, 255)

        return Color(r, g, b)
    }

    // Listener system for notifying UI updates
    interface Listener {
        fun onBreathingColorChanged(color: Color?)
    }

    private val listeners = mutableListOf<Listener>()

    fun addListener(listener: Listener) {
        synchronized(listeners) {
            listeners.add(listener)
        }
    }

    fun removeListener(listener: Listener) {
        synchronized(listeners) {
            listeners.remove(listener)
        }
    }

    override fun dispose() {
        scheduledTask?.cancel(false)
        executor.shutdown()
    }

    companion object {
        fun getInstance(): BreathingEffectService {
            return ApplicationManager.getApplication().getService(BreathingEffectService::class.java)
        }
    }
}
