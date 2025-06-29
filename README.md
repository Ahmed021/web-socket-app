# WebSocket Chat Android App

A modern Android chat application built with Jetpack Compose and WebSocket technology, featuring real-time messaging with a clean Material 3 design.

## 📱 Screenshots

<!-- Add your app screenshots here -->
<!-- ![App Screenshot](screenshots/main_screen.png) -->

## ✨ Features

- **🔗 Real-time Messaging**: Instant WebSocket-based communication
- **🎨 Material 3 Design**: Modern UI with dynamic theming and dark mode support
- **📊 Connection Management**: Visual connection status indicators with connect/disconnect controls
- **💬 Chat Bubbles**: WhatsApp-style message bubbles with timestamps and sender identification
- **📱 Responsive Design**: Adaptive layouts that work across different screen sizes
- **🎯 Custom Theming**: Branded purple and teal color scheme with system color integration

## 🛠️ Tech Stack

- **Jetpack Compose** - Modern Android UI toolkit
- **WebSocket** - Real-time bidirectional communication
- **Material 3** - Latest Material Design components and theming
- **Kotlin** - 100% Kotlin implementation
- **Android Architecture Components** - Following modern Android development patterns

## 🏗️ Project Structure

```
app/
├── src/main/java/com/ahmedonibiyo/websocketapp
│   ├── ui/
│   │   ├── components/
│   │   │   ├── ConnectionStatusCard.kt
│   │   │   └── MessageItem.kt
│   │   └── theme/
│   │       ├── Color.kt
│   │       ├── Theme.kt
│   │       └── Typography.kt
│   ├── model/
│   │   └── WebSocketViewModel.kt
│   └── MainActivity.kt
```

## 🚀 Getting Started

### Prerequisites

- Android Studio Hedgehog | 2023.1.1 or newer
- Android SDK 24 (minimum) / SDK 34 (target)
- Kotlin 1.9.0 or newer

### Installation

1. Clone the repository
   ```bash
   git clone https://github.com/Ahmed021/web-socket-app.git
   ```

2. Open the project in Android Studio

3. Sync the project with Gradle files

4. Update the WebSocket server URL in your configuration

5. Run the app on an emulator or physical device

## 🎨 UI Components

### Connection Status Card
- **Connected**: Green indicator with disconnect option
- **Connecting**: Orange indicator with loading state
- **Failed**: Red indicator with retry option
- **Disconnected**: Gray indicator with connect option

### Message Items
- Sender-based message alignment (left/right)
- Color-coded message bubbles
- Timestamp display
- Sender name identification

### Theme System
- Light and dark mode support
- Custom WebSocket brand colors
- Material 3 dynamic color integration
- Organized color palette for easy maintenance

## 🔧 Configuration

### WebSocket Server
Update your WebSocket server configuration in:
```kotlin
// Add your WebSocket server URL
private const val WEBSOCKET_URL = "ws://your-server-url:port"
```

### Theme Customization
Modify colors in `ui/theme/Color.kt`:
```kotlin
val WebSocketPrimaryLight = Color(0xFF6200EE)
val WebSocketSecondaryLight = Color(0xFF00BCD4)
// ... other colors
```

## 🤝 Contributing

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📋 Roadmap

- [ ] Message encryption
- [ ] File sharing capabilities
- [ ] Push notifications
- [ ] User authentication
- [ ] Group chat functionality
- [ ] Message persistence
- [ ] Emoji reactions

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- [Jetpack Compose](https://developer.android.com/jetpack/compose) team for the amazing UI toolkit
- [Material 3](https://m3.material.io/) design system
- WebSocket protocol contributors

## 📞 Contact

Ahmed Onibiyo - [@AhmedOnibiyo](https://https://x.com/ahmedonibiyo) - ahmedabiodun4@gmail.com

Project Link: [https://github.com/Ahmed021/web-socket-app](https://https://github.com/Ahmed021/web-socket-app)

---

⭐ Don't forget to star this repo if you found it helpful!
