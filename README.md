# Arkanoid Game - Object-Oriented Programming Project

## Author
Group [4] - Class [INT2204 5 25-26]

1. [Nguyễn Hữu Thành Đạt] - [24020062]
2. [Lê Xuân Bắc] - [24020035]
3. [Phan Duy Mạnh] - [24020223]
4. [Trần Minh Hiếu] - [24020131]

**Instructor**: [Kiều Văn Tuyên, Trương Xuân Hiếu]  
**Semester**: [HK1 - Năm học 2025-2026]

---

## Description
This is a classic Arkanoid game developed in Java as a final project for Object-Oriented Programming course. The project demonstrates the implementation of OOP principles and design patterns.

**Key features:**
1. The game is developed using Java 17+ with Swing for GUI.
2. Implements core OOP principles: Encapsulation, Inheritance, Polymorphism, and Abstraction.
3. Applies multiple design patterns: Singleton
4. Features multithreading for smooth gameplay and responsive UI.
5. Includes sound effects, animations, and power-up systems.
6. Supports save/load game functionality and leaderboard system.

**Game mechanics:**
- Control a paddle to bounce a ball and destroy bricks
- Collect power-ups for special abilities
- Progress through multiple levels with increasing difficulty
- Score points and compete on the leaderboard

---

## UML Diagram

### Class Diagram
![Class Diagram](docs/uml/class-diagram.png)

_Có thể sử dụng IntelliJ để generate ra Class Diagrams: https://www.youtube.com/watch?v=yCkTqNxZkbY_

*Complete UML diagrams are available in the `docs/uml/` folder*

---

## Design Patterns Implementation

_Có dùng hay không và dùng ở đâu_

### 1. Singleton Pattern
**Used in:** `GamePanel`

**Purpose:** Ensure only one instance exists throughout the application.

---

## Multithreading Implementation

The game uses multiple threads to ensure smooth performance:

1. **Sound Thread Pool**: Plays sound effects asynchronously
2. **Music Thread Pool**: Plays music effects asynchronously
3. **GameLoop Thread in GamePanel**: Smooth Game Loop Execution
4. **Main Thread**: Program Initialization

---

## Installation

1. Clone the project from the repository.
2. Open the project in the IDE.
3. Run the project.

## Usage

### Controls
| Key | Action |
|-----|--------|
| `←` or `A` | Move paddle left |
| `→` or `D` | Move paddle right |
| `SPACE` | Launch ball / Resume game |
| `P` | Pause game |
| `R` | Restart game |
| `ESC` | Quit to menu |

### How to Play
1. **Start the game**: Click "New Game" from the main menu.
2. **Control the paddle**: Use arrow keys or A/D to move left and right.
3. **Launch the ball**: Press SPACE to launch the ball from the paddle.
4. **Destroy bricks**: Bounce the ball to hit and destroy bricks.
5. **Collect power-ups**: Catch falling power-ups for special abilities.
6. **Avoid losing the ball**: Keep the ball from falling below the paddle.
7. **Complete the level**: Destroy all destructible bricks to advance.

### Power-ups
| Icon | Name | Effect |
|------|------|--------|
| W | Widen Paddle | Increases paddle width, can accumulate in a level |
| M | Multi Ball | Spawns 2 additional balls |
| S | Laser Gun | Shoot lasers to destroy bricks for short time |
| F | Fire Ball | Ball passes through bricks for short time |
| L | Extra life | Heal 1 health if not full |

### Scoring System
- Hit brick: 10 points
- Break brick: 50 points
- Over heal when collect extra life: 50 points
- Lose hp: -200 points (skill issue)

---

## Demo

### Screenshots

**Main Menu**  
![menu_bg](https://github.com/user-attachments/assets/ce64fef4-b068-4e27-aeb8-9bba7f89dcc4)

**Leaderboard**  
<img width="5426" height="3586" alt="leaderboard" src="https://github.com/user-attachments/assets/84bacfd3-de21-4e92-a9d1-4b473ff8c197" />


### Video Demo

https://drive.google.com/drive/u/0/folders/1p330aptIBViThqxaJxlsvVHCOaHronoo

---

## Future Improvements

### Planned Features
1. **Additional game modes**
   - Time attack mode
   - Survival mode with endless levels
   - Co-op multiplayer mode

2. **Enhanced gameplay**
   - Boss battles at end of worlds
   - More power-up varieties (freeze time, shield wall, etc.)

3. **Technical improvements**
   - Migrate to LibGDX or JavaFX for better graphics
   - Add particle effects and advanced animations
   - Implement AI opponent mode
   - Add online leaderboard with database backend

---

## Technologies Used

| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 17+ | Core language |
| Swing |  | GUI Framework |
| Maven | 3.9+ | Build tool |

---

## License

This project is developed for educational purposes only.

**Academic Integrity:** This code is provided as a reference. Please follow your institution's academic integrity policies.

---

## Notes

- The game was developed as part of the Object-Oriented Programming with Java course curriculum.
- All code is written by group members with guidance from the instructor.
- Some assets (images, sounds) may be used for educational purposes under fair use.
- The project demonstrates practical application of OOP concepts and design patterns.

---

*Last updated: [12/11/2025]*
