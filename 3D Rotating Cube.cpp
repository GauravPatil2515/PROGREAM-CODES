#include <graphics.h>
#include <math.h>
#include <stdio.h>
#include <conio.h>

#define PI 3.14159265358979323846

// Function to perform 3D rotation
void rotate3D(float* x, float* y, float* z, float angleX, float angleY, float angleZ) {
    // Rotation along X-axis
    float tempY = *y;
    float tempZ = *z;
    *y = tempY * cos(angleX) - tempZ * sin(angleX);
    *z = tempY * sin(angleX) + tempZ * cos(angleX);

    // Rotation along Y-axis
    float tempX = *x;
    tempZ = *z;
    *x = tempX * cos(angleY) + tempZ * sin(angleY);
    *z = -tempX * sin(angleY) + tempZ * cos(angleY);

    // Rotation along Z-axis
    tempX = *x;
    tempY = *y;
    *x = tempX * cos(angleZ) - tempY * sin(angleZ);
    *y = tempX * sin(angleZ) + tempY * cos(angleZ);
}

// Function to project 3D points to 2D screen
void project3DTo2D(float x, float y, float z, int* screenX, int* screenY) {
    int distance = 200; // Distance of the camera from the screen
    *screenX = (int)(x * distance / (z + distance) + getmaxx() / 2);
    *screenY = (int)(y * distance / (z + distance) + getmaxy() / 2);
}

// Function to draw the 3D cube
void drawCube(float vertices[8][3], int edges[12][2], float angleX, float angleY, float angleZ) {
    int projected[8][2];

    // Rotate and project each vertex
    for (int i = 0; i < 8; i++) {
        float x = vertices[i][0];
        float y = vertices[i][1];
        float z = vertices[i][2];

        rotate3D(&x, &y, &z, angleX, angleY, angleZ);
        project3DTo2D(x, y, z, &projected[i][0], &projected[i][1]);
    }

    // Draw edges between projected vertices
    for (int i = 0; i < 12; i++) {
        int start = edges[i][0];
        int end = edges[i][1];
        line(projected[start][0], projected[start][1], projected[end][0], projected[end][1]);
    }
}

int main() {
    int gd = DETECT, gm;
    initgraph(&gd, &gm, "");

    // Vertices of the cube (8 vertices)
    float vertices[8][3] = {
        {-50, -50, -50}, {50, -50, -50},
        {50, 50, -50}, {-50, 50, -50},
        {-50, -50, 50}, {50, -50, 50},
        {50, 50, 50}, {-50, 50, 50}
    };

    // Edges of the cube (12 edges, each connecting two vertices)
    int edges[12][2] = {
        {0, 1}, {1, 2}, {2, 3}, {3, 0},
        {4, 5}, {5, 6}, {6, 7}, {7, 4},
        {0, 4}, {1, 5}, {2, 6}, {3, 7}
    };

    float angleX = 0, angleY = 0, angleZ = 0;

    // Continuous rotation animation
    while (!kbhit()) {
        cleardevice();
        drawCube(vertices, edges, angleX, angleY, angleZ);
        angleX += 0.05;  // Increment angles to animate rotation
        angleY += 0.05;
        angleZ += 0.05;
        delay(50);
    }

    closegraph();
    return 0;
}

