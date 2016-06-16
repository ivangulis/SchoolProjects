/* Controls:
 space - start hry
 W,A,S,D ovládanie hráèa
 šípky strielanie do príslušného smeru
 1 2 3 poh¾ady kamery
 P vypne motor
 L zapne motor
*/

#include <iostream>
#include <vector>
#include <map>
#include <list>

#include <GL/glew.h>
#include <GLFW/glfw3.h>

#include "scene.h"
#include "camera.h"
#include "generator.h"
#include "player.h"
#include "pozadie.h"
#include "kocka.h"
#include "motor.h"
#include "slnko.h"
#include "keyframe.h"

const unsigned int SIZEX = 950;
const unsigned int SIZEY = 950;

Scene scene;

// Set up the scene
void InitializeScene() {
    scene.objects.clear();

    // Create a camera
    auto camera = CameraPtr(new Camera{ 60.0f, 1.0f, 0.1f, 100.0f});
    camera->position.z = -70;
    scene.camera = camera;

    // Add background
    auto pozadie = PozadiePtr(new Pozadie{});
    scene.objects.push_back(pozadie);

    // Add player to the scene
    auto player = PlayerPtr(new Player{});
    player->position.y = 1.0;
    scene.objects.push_back(player);

    auto motor = MotorPtr(new Motor{});
    motor->position = player->position;
    scene.objects.push_back(motor);
    scene.lightSource=motor;

    auto slnko = SlnkoPtr(new Slnko{});
    scene.objects.push_back(slnko);
    scene.lightSource2=slnko;

    //Add zem
    for (int j=0;j<40;j++) {
        for (int i=0;i<40;i++) {
            auto kocka = KockaPtr(new Kocka{});
            kocka->position.x = (float)(i*2.005 - 39.05);
            kocka->position.y = (float)(j*2.005 - 39.05);
            kocka->x = i;
            kocka->y = j;
            scene.objects.push_back(kocka);
        }
    }
}

// Keyboard press event handler
void OnKeyPress(GLFWwindow* /* window */, int key, int /* scancode */, int action, int /* mods */) {
    scene.keyboard[key] = action;

    // Reset
    if (key == GLFW_KEY_R && action == GLFW_PRESS) {
        InitializeScene();
    }
}

// Mouse move event handler
void OnMouseMove(GLFWwindow* /* window */, double xpos, double ypos) {
    scene.mouse.x = xpos;
    scene.mouse.y = ypos;
}

int main() {

    std::cout << "Stlac space pre start" << std::endl;

    // Initialize GLFW
    if (!glfwInit()) {
        std::cerr << "Failed to initialize GLFW!" << std::endl;
        return EXIT_FAILURE;
    }

    // Setup OpenGL context
    glfwWindowHint(GLFW_SAMPLES, 4);
    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
    glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
    glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

    // Try to create a window
    auto window = glfwCreateWindow(SIZEX, SIZEY, "PPGSO gl_scene", nullptr, nullptr);
    if (!window) {
        std::cerr << "Failed to open GLFW window, your graphics card is probably only capable of OpenGL 2.1" << std::endl;
        glfwTerminate();
        return EXIT_FAILURE;
    }

    // Finalize window setup
    glfwMakeContextCurrent(window);

    // Initialize GLEW
    glewExperimental = GL_TRUE;
    glewInit();
    if (!glewIsSupported("GL_VERSION_3_3")) {
        std::cerr << "Failed to initialize GLEW with OpenGL 3.3!" << std::endl;
        glfwTerminate();
        return EXIT_FAILURE;
    }

    // Add keyboard and mouse handlers
    glfwSetKeyCallback(window, OnKeyPress);
    glfwSetCursorPosCallback(window, OnMouseMove);
    glfwSetInputMode(window, GLFW_CURSOR, GLFW_STICKY_MOUSE_BUTTONS);
    glfwSetInputMode(window, GLFW_STICKY_KEYS, 1);

    // Initialize OpenGL state
    // Enable Z-buffer
    glEnable(GL_DEPTH_TEST);
    glDepthFunc(GL_LEQUAL);

    // Enable polygon culling
    glEnable(GL_CULL_FACE);
    glFrontFace(GL_CCW);
    glCullFace(GL_BACK);

    InitializeScene();

    // Track time
    float time = (float)glfwGetTime();

    // Main execution loop
    while (!glfwWindowShouldClose(window)) {
        // Compute time delta
        float dt = (float)glfwGetTime() - time;
        time = (float)glfwGetTime();

        // Set gray background
        glClearColor(.5f,.5f,.5f,0);
        // Clear depth and color buffers
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        // Update and render all objects
        if (!scene.Update(dt)) {
            break;
        }
        scene.Render();

        if (glfwGetMouseButton(window,GLFW_MOUSE_BUTTON_1))
            scene.mouse.left = true;
        else scene.mouse.left = false;
        if (glfwGetMouseButton(window,GLFW_MOUSE_BUTTON_2))
            scene.mouse.right = true;
        else scene.mouse.right = false;

        // Display result
        glfwSwapBuffers(window);
        glfwPollEvents();
    }

    // Clean up
    glfwTerminate();

    return EXIT_SUCCESS;
}

