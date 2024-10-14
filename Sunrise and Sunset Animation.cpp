#include <graphics.h>
#include <stdlib.h>
#include <time.h>

void drawSun(int x, int y, int color) {
    setcolor(color);
    setfillstyle(SOLID_FILL, color);
    circle(x, y, 50); // Sun with radius 50
    floodfill(x, y, color);
}

void drawSky(int color) {
    setfillstyle(SOLID_FILL, color);
    bar(0, 0, getmaxx(), getmaxy() / 2);  // Sky (upper half of the screen)
}

void drawGround() {
    setfillstyle(SOLID_FILL, GREEN);
    bar(0, getmaxy() / 2, getmaxx(), getmaxy());  // Ground (lower half of the screen)
}

void drawStars() {
    setcolor(WHITE);
    for (int i = 0; i < 100; i++) {
        int x = rand() % getmaxx();
        int y = rand() % (getmaxy() / 2);  // Stars only in the sky
        putpixel(x, y, WHITE);
    }
}

void drawScene(int sunY, int skyColor, int sunColor) {
    cleardevice();
    drawSky(skyColor);
    drawGround();
    drawSun(getmaxx() / 2, sunY, sunColor);
}

int main() {
    int gd = DETECT, gm;
    initgraph(&gd, &gm, "");

    int maxY = getmaxy();
    int sunY;
    int skyColor;
    int sunColor;

    srand(time(0));

    // Sunrise to Noon (Sun moves up)
    for (sunY = maxY; sunY > maxY / 2; sunY -= 5) {
        skyColor = COLOR(255, 153, 51);  // Morning sky (orange)
        sunColor = COLOR(255, 153, 51);  // Orange sun at sunrise
        drawScene(sunY, skyColor, sunColor);
        delay(50);
    }

    // Noon to Afternoon (Sun brightens)
    for (; sunY > maxY / 4; sunY -= 5) {
        skyColor = LIGHTBLUE;  // Bright midday sky
        sunColor = YELLOW;     // Yellow sun at noon
        drawScene(sunY, skyColor, sunColor);
        delay(50);
    }

    // Afternoon to Sunset (Sun moves down)
    for (; sunY < maxY; sunY += 5) {
        skyColor = COLOR(255, 102, 102);  // Evening sky (reddish-pink)
        sunColor = COLOR(255, 153, 51);   // Orange sun at sunset
        drawScene(sunY, skyColor, sunColor);
        delay(50);
    }

    // Night Sky (Optional: Add stars and moon)
    cleardevice();
    drawSky(BLACK);  // Night sky
    drawGround();
    drawStars();
    
    // Draw moon
    setcolor(WHITE);
    setfillstyle(SOLID_FILL, WHITE);
    circle(100, maxY / 4, 30);  // Moon on top left
    floodfill(100, maxY / 4, WHITE);
    
    delay(5000);  // Hold the final scene

    closegraph();
    return 0;
}

