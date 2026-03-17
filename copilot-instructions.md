---
description: Workspace instructions for the Soc Ops Social Bingo game built with Spring Boot and Thymeleaf.
---

# Soc Ops Workspace Instructions

## 🎮 Project Overview

**Soc Ops** is a social bingo game designed for in-person mixers and team building events. Players find people who match various prompts and mark them on a bingo card. First to complete 5 in a row wins!

**Tech Stack:**
- Backend: Spring Boot 3.4.2 (Java 21+)
- Frontend: Thymeleaf templates with custom CSS utilities
- Build: Maven 3.9+
- Deployment: GitHub Pages (automatic on push to main)

---

## ✅ Development Checklist

### Before Committing Changes
1. **Lint:** Check code quality
   ```bash
   cd socops && ./mvnw checkstyle:check
   ```
2. **Build:** Compile and package
   ```bash
   cd socops && ./mvnw clean package
   ```
3. **Test:** Run all tests
   ```bash
   cd socops && ./mvnw test
   ```
4. **Format:** Ensure proper code formatting
   ```bash
   cd socops && ./mvnw spotless:apply
   ```

### Running Locally
- **Start Dev Server:** `./mvnw spring-boot:run`
- **Live Reload:** Supported via spring-boot-devtools
- **Access App:** http://localhost:8080 (default)

---

## 📁 Project Structure

```
socops/
├── src/main/
│   ├── java/com/socops/
│   │   ├── SocOpsApplication.java          # Spring Boot entry point
│   │   ├── data/
│   │   │   └── IcebreakerPrompts.java      # Quiz questions/prompts
│   │   ├── model/
│   │   │   ├── BingoCell.java              # Individual cell data
│   │   │   ├── PlayPhase.java              # Game state enum
│   │   │   └── WinningStreak.java          # Win detection
│   │   ├── service/
│   │   │   └── BoardAssembler.java         # Board generation logic
│   │   └── web/
│   │       └── BingoRestController.java    # REST endpoints
│   └── resources/
│       ├── application.properties           # Configuration
│       ├── static/css/app.css              # Utility classes & styles
│       └── templates/game.html             # Main game template
├── src/test/java/com/socops/
│   └── service/BoardAssemblerTests.java    # Unit tests
└── pom.xml                                  # Maven dependencies
```

---

## 🔑 Key Components

### Game Modes
- **Bingo:** Classic 5x5 grid, find 5 in a row
- **Scavenger Hunt:** (To be implemented) Checklist view with progress
- **Card Deck:** (To be implemented) Random card shuffle mode

### Data Models
- `BingoCell`: Represents one square on the bingo card
- `PlayPhase`: Enum for game states (START, PLAYING, WON)
- `WinningStreak`: Logic for detecting 5-in-a-row wins

### API Endpoints
- `GET /`: Render main game page
- `GET /api/board`: Generate new bingo board (JSON)
- `POST /api/mark`: Mark a cell as found

---

## 🎨 Frontend Guidelines & Design

### Design Philosophy
See `.github/instructions/frontend-design.instructions.md` for design principles that guide all UI work.

**Key Principles:**
- **Avoid Generic AI Aesthetics:** Choose distinctive fonts, bold color palettes, and unique visual treatments
- **Embrace Themes:** Commit to cohesive design directions (Cyberpunk, Minimalist, Retro, etc.)
- **Animate with Purpose:** Use CSS-only animations for high-impact moments, not scattered micro-interactions
- **Match Complexity:** Maximalist designs need elaborate code; minimalist designs need precision

### Current Design Status
- See `docs/design-spec.md` for current design and proposed themes
- Available themes: Cyberpunk Neon, Minimalist Mono, Retro Arcade
- All new designs should update both HTML and CSS

### CSS Utilities
See `.github/instructions/css-utilities.instructions.md` for available styling classes.

**Core Classes:**
- Layout: `.flex`, `.grid`, `.grid-cols-5`, `.flex-col`
- Spacing: `.p-*`, `.mb-*`, `.gap-*`
- Colors: `.bg-accent` (customizable), `.bg-marked` (success state), `.text-*` (various)
- Typography: `.text-sm` through `.text-5xl`, `.font-bold`
- Animation: `.transition-all`, `.duration-150`, `.animate-[bounce_...]`

### Design System Variables
Define theme colors in CSS variables at `:root` for easy switching:
```css
:root {
  --color-primary: #2563eb;        /* Main accent */
  --color-success: #dcfce7;        /* Marked/selected */
  --color-bg-primary: #f9fafb;    /* Primary background */
  --color-bg-secondary: #ffffff;   /* Secondary background */
}
```

---

## 🚀 Development Tasks

### Task 1: UI Redesign
- Use Plan Mode to sketch redesign
- Update `templates/game.html` for new layout
- Modify `static/css/app.css` for theme
- Test responsiveness on mobile

### Task 2: Add New Game Mode
- Add mode enum to `PlayPhase.java`
- Create new template for mode
- Implement mode logic in service
- Write tests first (TDD)

### Task 3: Customize Prompts
- Edit `IcebreakerPrompts.java`
- Add theme-specific questions
- Ensure variety and inclusivity

---

## 📝 Commit Guidelines

Follow these conventions:
- `feat:` Add new features (e.g., "feat: add Card Deck mode")
- `fix:` Bug fixes (e.g., "fix: correct win detection logic")
- `style:` CSS/UI changes (e.g., "style: update cyberpunk theme")
- `test:` Test additions (e.g., "test: add mode validation tests")
- `chore:` Build/config changes (e.g., "chore: update dependencies")

---

## 🔀 Git Workflow

1. Create feature branch: `git checkout -b feature/your-feature-name`
2. Make changes following development checklist
3. Push and create PR for review
4. Merge after approval → auto-deploys to GitHub Pages
5. Delete feature branch after merge

---

## 📚 Related Instructions

- **CSS Utilities:** [.github/instructions/css-utilities.instructions.md](../../.github/instructions/css-utilities.instructions.md)
- **Frontend Design:** [.github/instructions/frontend-design.instructions.md](../../.github/instructions/frontend-design.instructions.md)
- **Custom Agents:** See `.github/agents/` directory

---

## 🤖 Custom Agents Available

- **Pixel Jam:** UI/UX design iterations
- **Quiz Master:** Generate themed quiz questions
- **TDD Red/Green/Refactor:** Test-driven development workflow
- **UI Review:** Accessibility and UX review

---

## 💡 Tips for Success

1. **Keep instructions updated** when making architectural changes
2. **Iterate on plans** 2+ times before implementing large features
3. **Test early and often** — use TDD agents for complex logic
4. **Commit frequently** — makes it easier to revert if needed
5. **Use checkpoints** — save known-good states before major changes
