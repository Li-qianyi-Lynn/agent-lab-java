# Soc Ops Design Specification

## Current Design
- **Theme:** Minimalist Light
- **Color Scheme:** Gray + Blue accent
- **Fonts:** System font
- **Layout:** Centered, card-based
- **Interactions:** Simple taps, no animations

---

## Redesign Directions

### Option 1: Cyberpunk Neon
**Vision:** High-energy, glowing UI with sharp typography and intense colors
- Colors: Deep purple/black backgrounds, neon cyan/pink text and accents
- Fonts: Monospace or geometric sans-serif for technical feel
- Effects: Glowing shadows, neon borders, pulsing animations
- Grid: Dark game board with glowing cell borders, intense feedback

### Option 2: Minimalist Mono
**Vision:** Refined, elegant design using only black, white, and gray
- Colors: Pure black background, white text, minimal gray accents
- Fonts: High-quality serif + clean sans-serif
- Effects: Subtle transitions, clean whitespace
- Grid: High contrast cells with elegant spacing

### Option 3: Retro Arcade
**Vision:** Nostalgic 8-bit/16-bit era styling
- Colors: Bright primary colors (red, blue, yellow) on darker backgrounds
- Fonts: Pixel fonts or retro gaming fonts
- Effects: Pixelated animations, scan line effects
- Grid: Chunky, colorful tiles with 8-bit inspired feedback

---

## Design Components to Update

### 1. Lobby Screen
- Title styling
- "How to play" card
- Start button treatment
- Background/atmosphere

### 2. Game Board
- 5×5 grid appearance
- Cell styling (unmarked, marked, winning)
- Grid container background
- Mobile responsiveness

### 3. Header
- Title and branding
- Back button
- Controls layout

### 4. Victory Modal
- Celebration design
- Modal backdrop
- Button styling
- Animation sequence

### 5. Color Variables
- Primary colors
- Text colors
- Background colors
- Accent colors
- Interactive states

---

## Implementation Checklist

- [ ] Update CSS color palette and variables
- [ ] Modify button and tile styles
- [ ] Add/update typography
- [ ] Implement new background treatments
- [ ] Add transition and animation effects
- [ ] Test on mobile devices
- [ ] Verify accessibility (color contrast, keyboard nav)
- [ ] Update design guide in copilot-instructions.md

---

## Next Steps

1. **Plan Phase:** Review this spec and choose a theme direction
2. **Iteration 1:** Update CSS utilities and base colors
3. **Iteration 2:** Redesign buttons and interactive elements
4. **Iteration 3:** Add animations and micro-interactions
5. **Polish Phase:** Fine-tune spacing, fonts, and effects
