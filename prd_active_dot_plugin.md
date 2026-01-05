# Active Tab Dot - IntelliJ IDEA Plugin

A simple IntelliJ IDEA plugin that displays a colored dot on the active editor tab, making it easier to quickly identify your current file among multiple open tabs.

## Features

- 🎯 **Visual Indicator**: Colored dot appears on the active tab only
- 🎨 **Customizable Color**: Choose your preferred indicator color via settings
- ⚡ **Instant Updates**: Indicator switches within 100ms when changing tabs
- 💾 **Persistent Settings**: Your color preference is saved across IDE sessions
- 🌓 **Theme Support**: Works seamlessly in both light and dark themes
- 🚀 **High Performance**: Optimized rendering at 60fps (<16ms per frame)
- ✅ **WCAG Compliant**: Color validation ensures sufficient contrast

## Installation

### From Source

1. Clone the repository
2. Build the plugin: `./gradlew buildPlugin`
3. Install in IDEA: File → Settings → Plugins → Gear icon → Install Plugin from Disk
4. Select `build/distributions/active-tab-dot-1.0.0.zip`
5. Restart IDE

## Usage

### Basic Usage

After installation:
1. Open multiple files as editor tabs
2. Notice the colored dot (bright orange by default) on the left side of the active tab
3. Switch between tabs - the dot moves to the newly active tab
4. The dot appears instantly (<100ms) when you switch tabs

### Customization

1. Open Settings: `File → Settings → Tools → Active Tab Indicator`
2. **Indicator Color**: Click the color picker to choose your preferred color
3. **Indicator Size**: Adjust the slider (3-12 pixels) to change dot size
4. **Horizontal Offset**: Set distance from tab edge (0-20 pixels)
5. **Vertical Offset**: Adjust vertical position (-10 to +10 pixels, 0 is centered)
6. **Restore Defaults**: Click to reset all settings to default values
7. Click "Apply" to see changes immediately (no restart needed!)
8. Settings are saved automatically and persist across IDE sessions

### Troubleshooting

**Indicator not visible:**
- Check that you have multiple tabs open
- Ensure the active tab is wide enough (minimum 30 pixels)
- Try increasing the indicator size or adjusting offsets
- Verify the indicator color has sufficient contrast with your theme

**Settings changes not applying:**
- Click "Apply" in the settings dialog (not just "OK")
- Check for low contrast warnings in the settings panel
- Try restarting the IDE if changes don't appear

**Indicator position looks wrong:**
- Adjust Horizontal Offset to move left/right
- Adjust Vertical Offset to move up/down
- For RTL languages, the indicator automatically flips to the right side

**Performance issues:**
- The plugin uses caching and should render at 60fps (<16ms per frame)
- If you experience lag, check IDEA's memory settings
- Disable other heavy plugins to isolate the issue

## Development

### Requirements

- JDK 17+
- Kotlin 1.9+
- Gradle 8.14+

### Build

```bash
./gradlew buildPlugin
```

### Run in Sandbox

```bash
./gradlew runIde
```

### Run Tests

```bash
./gradlew test
```

## Configuration

The indicator comes with sensible defaults:
- **Color**: Bright orange (#FF6B35)
- **Size**: 6 pixels
- **Position**: 8 pixels from tab edge, vertically centered

You can customize the color through Settings → Tools → Active Tab Indicator.

## Compatibility

- IntelliJ IDEA Community Edition 2025.3
- All JetBrains IDEs based on IntelliJ Platform 2025.3

## License

Apache-2.0 License
