#include <graphics.h>
#include <stdlib.h>
#include <time.h>

// Function to create fireworks effect
void createFirework(int x, int y) {
    int numParticles = 20;  // Number of particles in the explosion
    int i, color, particleX, particleY;
    int radius = 2;  // Size of the particles

    for (i = 0; i < numParticles; i++) {
        color = rand() % 15 + 1;  // Random colors
        setcolor(color);
        particleX = x + rand() % 40 - 20;  // Random scatter X position
        particleY = y + rand() % 40 - 20;  // Random scatter Y position
        circle(particleX, particleY, radius);  // Draw the firework particle
    }
}

int main() {
    int gd = DETECT, gm;
    initgraph(&gd, &gm, "");

    srand(time(0));  // Seed random function

    int fireworkX, fireworkY;
    int screenHeight = getmaxy();
    
    while (!kbhit()) {
        cleardevice();

        // Firework starting point (bottom of the screen)
        fireworkX = rand() % getmaxx();
        fireworkY = screenHeight;

        // Animate the firework shooting upwards
        while (fireworkY > screenHeight / 2) {
            cleardevice();
            setcolor(WHITE);
            circle(fireworkX, fireworkY, 5);  // Firework rocket
            fireworkY -= 5;  // Move upwards
            delay(20);
        }

        // Explosion effect when it reaches the middle
        createFirework(fireworkX, fireworkY);
        delay(200);  // Pause after explosion
    }

    closegraph();
    return 0;
}

