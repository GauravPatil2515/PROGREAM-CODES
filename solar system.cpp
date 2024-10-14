#include <graphics.h>
#include <math.h>
#include <stdio.h>
#include <conio.h>

#define PI 3.14159265358979323846

// Function to draw planets in their orbits
void drawPlanet(int centerX, int centerY, int radius, float angle, int planetRadius, int color) {
    int x = centerX + radius * cos(angle);  // Calculate X position of the planet
    int y = centerY + radius * sin(angle);  // Calculate Y position of the planet
    setcolor(color);  // Set color of the planet
    setfillstyle(SOLID_FILL, color);
    fillellipse(x, y, planetRadius, planetRadius);  // Draw the planet
}

int main() {
    int gd = DETECT, gm;
    initgraph(&gd, &gm, "");

    int centerX = getmaxx() / 2;
    int centerY = getmaxy() / 2;

    float angleMercury = 0, angleVenus = 0, angleEarth = 0, angleMars = 0;

    while (!kbhit()) {
        cleardevice();

        // Draw the Sun
        setcolor(YELLOW);
        setfillstyle(SOLID_FILL, YELLOW);
        fillellipse(centerX, centerY, 30, 30);

        // Draw Mercury orbiting
        drawPlanet(centerX, centerY, 80, angleMercury, 8, LIGHTGRAY);
        angleMercury += 0.04;  // Mercury moves faster

        // Draw Venus orbiting
        drawPlanet(centerX, centerY, 120, angleVenus, 10, LIGHTRED);
        angleVenus += 0.03;

        // Draw Earth orbiting
        drawPlanet(centerX, centerY, 160, angleEarth, 12, BLUE);
        angleEarth += 0.02;

        // Draw Mars orbiting
        drawPlanet(centerX, centerY, 200, angleMars, 10, RED);
        angleMars += 0.01;

        delay(50);
    }

    closegraph();
    return 0;
}

