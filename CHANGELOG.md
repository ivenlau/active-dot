# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.7] - 2026-01-23

### Fixed
- **Critical Performance Fix**: Resolved severe UI lag and code completion delays caused by the breathing light effect.
- **Optimization**: Reduced breathing animation frame rate to 10 FPS to minimize CPU usage.
- **Resource Usage**: Stopped animation loop completely when breathing effect is disabled.

## [1.0.6] - 2026-01-15

### Added
- **Breathing Light Effect**: Add breathing light feature with smooth color transitions and adjustable brightness
  - **Enable/Disable**: Toggle breathing light on/off (default: off)
  - **Three Modes**:
    - Fixed: Single color with breathing effect
    - Random: Random colors with smooth transitions
    - Custom: Cycle through 4 custom colors with smooth transitions
  - **Frequency Control**: Adjustable breathing speed (1-10)
  - Custom color selectors appear inline when Custom mode is selected

## [1.0.5] - 2026-01-07

### Added
- Add logo icon.

## [1.0.4] - 2026-01-05

### Added
- **Dot Size customization**: Adjust the indicator size between 3-10 pixels in settings.
- **Position Offset controls**: Fine-tune dot position with horizontal (0-10px) and vertical (-10 to +10px) offset adjustments.

## [1.0.3] - 2026-01-05

### Changed
- Update for compatibility with IntelliJ IDEA 2023.2 and above.

## [1.0.2] - 2026-01-05

### Changed
- Replaced internal API usage with public API to ensure long-term compatibility.
- Enhanced dot rendering with anti-aliasing for smoother, vector-quality appearance.

## [1.0.1] - 2026-01-05

### Fixed
- Mouse cursor now behaves correctly and is no longer stuck in the pointer state.

## [1.0.0] - 2026-01-04

### Added

- Initial release of the Active Tab Dot plugin.
- Displays a colored dot on the active editor tab.
- Customizable indicator color.
- Instant updates when changing tabs.
- Persistent settings for color preference.
- Light and dark theme support.
- High performance rendering.
- WCAG compliant color validation.
