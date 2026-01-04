# Active Tab Dot - IntelliJ IDEA Plugin

[![Build](https://github.com/ivenlau/active-dot/workflows/Build/badge.svg)](https://github.com/ivenlau/active-dot/actions)
[![Version](https://img.shields.io/jetbrains/plugin/v/PLUGIN_ID.svg)](https://plugins.jetbrains.com/plugin/PLUGIN_ID)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/PLUGIN_ID.svg)](https://plugins.jetbrains.com/plugin/PLUGIN_ID)

A simple IntelliJ IDEA plugin that displays a colored dot on the active editor tab, making it easier to quickly identify your current file among multiple open tabs.

## Features

- 🎯 **Visual Indicator**: A colored dot appears on the active tab only.
- 🎨 **Customizable**: Choose your preferred indicator color through the settings panel.
- ⚡ **Instant Updates**: The indicator moves instantly when you switch tabs.
- 💾 **Persistent Settings**: Your preferences are saved across IDE sessions.
- 🌓 **Theme Support**: Works seamlessly in both light and dark themes.
- 🚀 **High Performance**: Optimized rendering ensures no UI lag.
- ✅ **WCAG Compliant**: Includes color validation to ensure sufficient contrast.

## Installation

1.  Go to `File` → `Settings` → `Plugins`.
2.  Search for "Active Tab Dot" in the Marketplace.
3.  Click `Install` and restart the IDE.

### From Source

1.  Clone the repository: `git clone https://github.com/ivenlau/active-dot.git`
2.  Build the plugin: `./gradlew buildPlugin`
3.  Install in IDEA: `File` → `Settings` → `Plugins` → ⚙️ → `Install Plugin from Disk...`
4.  Select the generated zip file in `build/distributions/`.
5.  Restart the IDE.

## Usage

### Basic Usage

Once installed, open a few files. A colored dot (bright orange by default) will appear on the active editor tab. As you switch between tabs, the dot will move to the new active tab.

### Customization

1.  Open Settings: `File` → `Settings` → `Appearance & Behavior` → `Active Tab Dot`.
2.  **Indicator Color**: Choose your preferred color.
3.  Click "Apply" to see changes immediately.

## Development

This project is a Kotlin-based Gradle project for the IntelliJ Platform.

### Requirements

- JDK 17+
- Gradle 8.5+
- IntelliJ IDEA

### Key Commands

- **Build the plugin**:
  ```bash
  ./gradlew buildPlugin
  ```
- **Run in a sandbox IDE**:
  ```bash
  ./gradlew runIde
  ```
- **Run tests**:
  ```bash
  ./gradlew test
  ```

## Compatibility

- Designed for IntelliJ Platform 2025.3 and later.
- Compatible with all JetBrains IDEs (IntelliJ IDEA, PyCharm, WebStorm, etc.) on the same platform version.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
