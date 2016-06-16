
#include <iostream>
#include <vector>
#include <fstream>
#include <cmath>

#include <GL/glew.h>
#include <GLFW/glfw3.h>

#include <GL/glew.h>
#include <GLFW/glfw3.h>

typedef struct pixel {
    unsigned char r;
    unsigned char g;
    unsigned char b;
}PIXEL;

typedef struct apixel {
    unsigned char r;
    unsigned char g;
    unsigned char b;
    unsigned char a;
}APIXEL;

GLuint ShaderProgram(const std::string &vertex_shader_file, const std::string &fragment_shader_file) {
    // Create shaders
    auto vertex_shader_id = glCreateShader(GL_VERTEX_SHADER);
    auto fragment_shader_id = glCreateShader(GL_FRAGMENT_SHADER);
    auto result = GL_FALSE;
    auto info_length = 0;

    // Load shader code
    std::ifstream vertex_shader_stream(vertex_shader_file);
    std::string vertex_shader_code((std::istreambuf_iterator<char>(vertex_shader_stream)), std::istreambuf_iterator<char>());

    std::ifstream fragment_shader_stream(fragment_shader_file);
    std::string fragment_shader_code((std::istreambuf_iterator<char>(fragment_shader_stream)), std::istreambuf_iterator<char>());

    // Compile vertex shader
    std::cout << "Compiling Vertex Shader ..." << std::endl;
    auto vertex_shader_code_ptr = vertex_shader_code.c_str();
    glShaderSource(vertex_shader_id, 1, &vertex_shader_code_ptr, NULL);
    glCompileShader(vertex_shader_id);

    // Check vertex shader log
    glGetShaderiv(vertex_shader_id, GL_COMPILE_STATUS, &result);
    if (result == GL_FALSE) {
        glGetShaderiv(vertex_shader_id, GL_INFO_LOG_LENGTH, &info_length);
        std::string vertex_shader_log((unsigned int)info_length, ' ');
        glGetShaderInfoLog(vertex_shader_id, info_length, NULL, &vertex_shader_log[0]);
        std::cout << vertex_shader_log << std::endl;
    }

    // Compile fragment shader
    std::cout << "Compiling Fragment Shader ..." << std::endl;
    auto fragment_shader_code_ptr = fragment_shader_code.c_str();
    glShaderSource(fragment_shader_id, 1, &fragment_shader_code_ptr, NULL);
    glCompileShader(fragment_shader_id);

    // Check fragment shader log
    glGetShaderiv(fragment_shader_id, GL_COMPILE_STATUS, &result);
    if (result == GL_FALSE) {
        glGetShaderiv(fragment_shader_id, GL_INFO_LOG_LENGTH, &info_length);
        std::string fragment_shader_log((unsigned long)info_length, ' ');
        glGetShaderInfoLog(fragment_shader_id, info_length, NULL, &fragment_shader_log[0]);
        std::cout << fragment_shader_log << std::endl;
    }

    // Create and link the program
    std::cout << "Linking Shader Program ..." << std::endl;
    auto program_id = glCreateProgram();
    glAttachShader(program_id, vertex_shader_id);
    glAttachShader(program_id, fragment_shader_id);
    glBindFragDataLocation(program_id, 0, "FragmentColor");
    glLinkProgram(program_id);

    // Check program log
    glGetProgramiv(program_id, GL_LINK_STATUS, &result);
    if (result == GL_FALSE) {
        glGetProgramiv(program_id, GL_INFO_LOG_LENGTH, &info_length);
        std::string program_log((unsigned long)info_length, ' ');
        glGetProgramInfoLog(program_id, info_length, NULL, &program_log[0]);
        std::cout << program_log << std::endl;
    }
    glDeleteShader(vertex_shader_id);
    glDeleteShader(fragment_shader_id);

    return program_id;
}


void InitializeGeometry(GLuint program_id) {
    // Generate a vertex array object
    GLuint vao;
    glGenVertexArrays(1, &vao);
    glBindVertexArray(vao);

    // Setup geometry
    std::vector<GLfloat> vertex_buffer {
            // x, y
            1.0f, 1.0f,
            -1.0f, 1.0f,
            1.0f, -1.0f,
            -1.0f, -1.0f
    };

    // Generate a vertex buffer object
    GLuint vbo;
    glGenBuffers(1, &vbo);
    glBindBuffer(GL_ARRAY_BUFFER, vbo);
    glBufferData(GL_ARRAY_BUFFER, vertex_buffer.size() * sizeof(GLfloat), vertex_buffer.data(), GL_STATIC_DRAW);

    // Setup vertex array lookup
    auto position_attrib = glGetAttribLocation(program_id, "Position");
    glVertexAttribPointer(position_attrib, 2, GL_FLOAT, GL_FALSE, 0, 0);
    glEnableVertexAttribArray(position_attrib);

    // Generate another vertex buffer object for texture coordinates
    std::vector<GLfloat> texcoord_buffer {
            // u, v
            1.0f, 0.0f,
            0.0f, 0.0f,
            1.0f, 1.0f,
            0.0f, 1.0f
    };

    GLuint tbo;
    glGenBuffers(1, &tbo);
    glBindBuffer(GL_ARRAY_BUFFER, tbo);
    glBufferData(GL_ARRAY_BUFFER, texcoord_buffer.size() * sizeof(GLfloat), texcoord_buffer.data(), GL_STATIC_DRAW);

    auto texcoord_attrib = glGetAttribLocation(program_id, "TexCoord");
    glVertexAttribPointer(texcoord_attrib, 2, GL_FLOAT, GL_FALSE, 0, 0);
    glEnableVertexAttribArray(texcoord_attrib);
}

unsigned char zarovnaj(double x){
    if (x < 0) x = 0;
    if (x > 255) x = 255;
    return (unsigned char)x;
}

const int sizeX0 = 512;
const int sizeY0 = 512;
const int sizeX1 = 100;
const int sizeY1 = 512;
const int sizeX2 = 230;
const int sizeY2 = 150;

PIXEL final[sizeY0][sizeX0];
APIXEL frameBufferGuts[sizeY0][sizeX0];
APIXEL frameBufferTower[sizeY1][sizeX1];
APIXEL frameBufferDemon[sizeY2][sizeX2];

void blendInit(char *image1, char *image2, char *image3){

    //inicializácia bufferov
    FILE *guts = fopen(image1, "rb");
    fread(frameBufferGuts, sizeof(APIXEL), sizeY0 * sizeX0, guts);
    fclose(guts);

    FILE *tower = fopen(image2, "rb");
    fread(frameBufferTower, sizeof(APIXEL), sizeY1 * sizeX1, tower);
    fclose(tower);

    FILE *demon = fopen(image3, "rb");
    fread(frameBufferDemon, sizeof(APIXEL), sizeY2 * sizeX2, demon);
    fclose(demon);

    //inicializácia alfa kanálov
    for (int y = 0; y < sizeY2; y++) {
        for (int x = 0; x < sizeX2; x++) {
            if (frameBufferDemon[y][x].r == 255 && frameBufferDemon[y][x].g == 255 && frameBufferDemon[y][x].b == 255)
                frameBufferDemon[y][x].a = 0;
            else
                frameBufferDemon[y][x].a = 150;
        }
    }
    for (int y = 0; y < sizeY1; y++) {
        for (int x = 0; x < sizeX1; x++) {
            if (frameBufferTower[y][x].r == 255 && frameBufferTower[y][x].g == 255 && frameBufferTower[y][x].b == 255)
                frameBufferTower[y][x].a = 0;
        }
    }

    return;
}

// Update framebuffer
void UpdateFramebuffer(int positionX, int positionY) {

    //1. obrázok
    int offsetX = 0, offsetY = 0;
    for (int y = 0; y < sizeY0; y++) {
        for (int x = 0; x < sizeX0; x++) {
            final[y][x].r = frameBufferGuts[y][x].r;
            final[y][x].g = frameBufferGuts[y][x].g;
            final[y][x].b = frameBufferGuts[y][x].b;
        }
    }

    //lietajúci démon
    offsetX = sizeX0 - positionX;
    offsetY -= positionY;
    for (int y = 0; y < sizeY2; y++) {
        for (int x = 0; x < sizeX2; x++) {
                if (x+offsetX > 0 && x+offsetX < sizeX0 && y+offsetY > 0 && y+offsetY < sizeY0) {
                    final[y + offsetY][x + offsetX].r = zarovnaj((frameBufferDemon[y][x].a / 255.0) * frameBufferDemon[y][x].r + final[y + offsetY][x + offsetX].r*(1-(frameBufferDemon[y][x].a / 255.0)));
                    final[y + offsetY][x + offsetX].g = zarovnaj((frameBufferDemon[y][x].a / 255.0) * frameBufferDemon[y][x].g + final[y + offsetY][x + offsetX].g*(1-(frameBufferDemon[y][x].a / 255.0)));
                    final[y + offsetY][x + offsetX].b = zarovnaj((frameBufferDemon[y][x].a / 255.0) * frameBufferDemon[y][x].b + final[y + offsetY][x + offsetX].b*(1-(frameBufferDemon[y][x].a / 255.0)));
                }

        }
    }

    //veža
    offsetX = 20;
    offsetY = 0;
    for (int y = 0; y < sizeY1; y++) {
        for (int x = 0; x < sizeX1; x++) {
                if (x+offsetX > 0 && x+offsetX < sizeX0 && y+offsetY > 0 && y+offsetY < sizeY0) {
                    final[y + offsetY][x + offsetX].r = zarovnaj((frameBufferTower[y][x].a / 255.0) * frameBufferTower[y][x].r + final[y + offsetY][x + offsetX].r * (1 - (frameBufferTower[y][x].a / 255.0)));
                    final[y + offsetY][x + offsetX].g = zarovnaj((frameBufferTower[y][x].a / 255.0) * frameBufferTower[y][x].g + final[y + offsetY][x + offsetX].g * (1 - (frameBufferTower[y][x].a / 255.0)));
                    final[y + offsetY][x + offsetX].b = zarovnaj((frameBufferTower[y][x].a / 255.0) * frameBufferTower[y][x].b + final[y + offsetY][x + offsetX].b * (1 - (frameBufferTower[y][x].a / 255.0)));
                }
        }
    }
}

int main() {

    blendInit((char *) "guts.rgba", (char *) "tower.rgba", (char *) "demon.rgba");

    if (!glfwInit()) {
        fprintf( stderr, "Failed to initialize GLFW\n" );
        return -1;
    }

    glfwWindowHint(GLFW_SAMPLES, 4);
    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
    glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
    glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

    GLFWwindow* window;
    window = glfwCreateWindow(512, 512, "OpenGL", NULL, NULL);
    if( window == NULL ){
        fprintf( stderr, "Failed to open GLFW window, your graphics card is probably only capable of OpenGL 2.1\n" );
        glfwTerminate();
        return -1;
    }
    glfwMakeContextCurrent(window);
    glewExperimental= GL_TRUE;
    glewInit();


    auto program_id = ShaderProgram("gl_texture.vert", "gl_texture.frag");
    glUseProgram(program_id);

    InitializeGeometry(program_id);

    // Create texture
    GLuint texture_id;
    glGenTextures(1, &texture_id);
    glBindTexture(GL_TEXTURE_2D, texture_id);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

    // Bind texture
    auto texture_attrib = glGetUniformLocation(program_id, "Texture");
    glUniform1i(texture_attrib, 0);
    glActiveTexture(GL_TEXTURE0 + 0);
    glBindTexture(GL_TEXTURE_2D, texture_id);

    // Ensure we can capture the escape key being pressed below
    glfwSetInputMode(window, GLFW_STICKY_KEYS, GL_TRUE);

    // Time(position) counter
    int positionX = 0;
    int positionY = -1;
    int smer = 1;

    // Main execution loop
    while (!glfwWindowShouldClose(window)) {

        if (smer > 0) positionY++;
        if (smer < 0) positionY--;
        if (positionY > 50) smer = -1;
        if (positionY < -50) smer = 1;
        if (positionX > 512+230) positionX = 0;
        // Update Framebuffer
        UpdateFramebuffer(positionX++, positionY);

        // Update texture data with framebuffer
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, 512, 512, 0, GL_RGB, GL_UNSIGNED_BYTE, final);

        // Set gray background
        glClearColor(.5f,.5f,.5f,0);
        // Clear depth and color buffers
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        // Draw triangles using the program
        glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);

        // Display result
        glfwSwapBuffers(window);
        glfwPollEvents();
    }

    // Clean up
    glfwTerminate();

    return 0;

}