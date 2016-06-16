
#include <iostream>
#include <vector>
#include <fstream>
#include <stdio.h>
#include <stdlib.h>
#include <conio.h>

#include <GL/glew.h>
#include <GLFW/glfw3.h>

typedef struct pixel {
    unsigned char r;
    unsigned char g;
    unsigned char b;
}PIXEL;

const unsigned int imageSize = 512;
int velkostFiltra = 1;
double factor = 1.0;
double bias = 0.0;

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

PIXEL final[imageSize][imageSize];

void applyFilter(double **f, char *image){

    int w = imageSize;
    int h = imageSize;
    int fH = velkostFiltra;
    int fW = velkostFiltra;

    PIXEL frameBuffer[imageSize][imageSize];

    FILE *obrazok = fopen(image, "rb");
    fread(frameBuffer, sizeof(PIXEL), w * h, obrazok);
    fclose(obrazok);

    //pre vöetky pixely
    for (int y = 0; y < h; y++) {
        for (int x = 0; x < w; x++) {

            double red = 0.0;
            double green = 0.0;
            double blue = 0.0;

            //pre mrieûku filtra
            for (int i = 0; i < fH; i++) {
                for (int j = 0; j < fW; j++) {
                    int fx = x - fW / 2 + j;
                    int fy = y - fH / 2 + i;

                    if (fx < 0) fx = 0;
                    if (fy < 0) fy = 0;
                    if (fy > h-1) fy = h-1;
                    if (fx > w-1) fx = w-1;

                    red += frameBuffer[fx][fy].r * f[i][j];
                    green += frameBuffer[fx][fy].g * f[i][j];
                    blue += frameBuffer[fx][fy].b * f[i][j];
                }
            }
            final[x][y].r = zarovnaj(factor * red + bias);
            final[x][y].g = zarovnaj(factor * green + bias);
            final[x][y].b = zarovnaj(factor * blue + bias);
        }
    }

    //free pam‰ù pre filter
    for (int i = 0; i<velkostFiltra; i++) {
        free(f[i]);
    }
    free(f);

    return;
}

void sharpen(char *image_file) {

    double **f;
    int i;
    f = (double**) malloc (velkostFiltra*sizeof(double*));
    for (i = 0; i<velkostFiltra; i++) {
        f[i] = (double *) malloc(velkostFiltra * sizeof(double));
    }

    //naplnenie filtra
    if (velkostFiltra){
        factor = 1.0;
        bias = 0;
        int j=0;
        for (i = 0; i<velkostFiltra; i++){
            for (j = 0; j<velkostFiltra; j++){
                f[i][j] = -1;
            }
        }
        f[i/2][j/2] = velkostFiltra*velkostFiltra;
    }

    applyFilter(f, image_file);

    return;

}

void emboss(char *image_file) {

    double **f;
    f = (double**) malloc (velkostFiltra*sizeof(double*));
    for (int i = 0; i<velkostFiltra; i++) {
        f[i] = (double *) malloc(velkostFiltra * sizeof(double));
    }

    factor = 1.0;
    bias = 128.0;

    //naplnenie filtra
    if (velkostFiltra){
        for (int i = 0; i<velkostFiltra; i++){
            for (int j = 0; j<velkostFiltra; j++){
                if (velkostFiltra - i -1 <j) f[i][j] = 1;
                if (velkostFiltra - i -1 >j) f[i][j] = -1;
                if (velkostFiltra - i -1 == j) f[i][j] = 0;
            }
        }
    }

    applyFilter(f, image_file);

    return;

}

void motionBlur(char *image_file) {

    double **f;
    f = (double**) malloc (velkostFiltra*sizeof(double*));
    for (int i = 0; i<velkostFiltra; i++) {
        f[i] = (double *) malloc(velkostFiltra * sizeof(double));
    }

    //naplnenie filtra
    if (velkostFiltra){
        factor = 1.0 / velkostFiltra;
        bias = 0;
        for (int i = 0; i<velkostFiltra; i++){
            for (int j = 0; j<velkostFiltra; j++){
                f[i][j] = 0;
                if (i == j) f[i][j] = 1;
            }
        }
    }

    applyFilter(f, image_file);

    return;

}

void edgeDetection(char *image_file) {

    double **f;
    int i;
    f = (double**) malloc (velkostFiltra*sizeof(double*));
    for (i = 0; i<velkostFiltra; i++) {
        f[i] = (double *) malloc(velkostFiltra * sizeof(double));
    }

    //naplnenie filtra
    if (velkostFiltra){
        factor = 1.0;
        bias = 0;
        int j=0;
        for (i = 0; i<velkostFiltra; i++){
            for (j = 0; j<velkostFiltra; j++){
                f[i][j] = -1;
            }
        }
        f[i/2][j/2] = velkostFiltra*velkostFiltra - 1;
    }

    applyFilter(f, image_file);

    return;

}

void blur(char *image_file){
    double **f;
    f = (double**) malloc (velkostFiltra*sizeof(double*));
    for (int i = 0; i<velkostFiltra; i++) {
        f[i] = (double *) malloc(velkostFiltra * sizeof(double));
    }

    //naplnenie filtra
    factor = 1.0 / (velkostFiltra*velkostFiltra);
    bias = 0;
    if (velkostFiltra){
        for (int i = 0; i<velkostFiltra; i++){
            for (int j = 0; j<velkostFiltra; j++){
                f[i][j] = 1;
            }
        }
    }

    applyFilter(f, image_file);

    return;
}

void UpdateFramebuffer(int time, int hranica){

    //premietaËka
    if (time >= 1*hranica)
        motionBlur((char *) "guts.rgb");
    if (time >= 2*hranica)
        edgeDetection((char *) "guts.rgb");
    if (time >= 3*hranica)
        emboss((char *) "guts.rgb");
    if (time >= 4*hranica)
        sharpen((char *) "guts.rgb");
    if (time >= 5*hranica)
        blur((char *) "guts.rgb");

}

int main() {

    int cisloFiltra = 0;

    std::cout << "Velkost filtra: (neparne cislo)" << std::endl;
    std::cin >> velkostFiltra;

    std::cout << "1 - Motion Blur" << std::endl << "2 - Edge Detection" << std::endl << "3 - Emboss" << std::endl << "4 - Blur" << std::endl << "5 - Sharpen" << std::endl << ">5 - Prezentacny mod(rychlost premietania)" << std::endl;
    std::cin >> cisloFiltra;
    switch (cisloFiltra) {
        case 1 :
            motionBlur((char *) "guts.rgb");
            break;
        case 2 :
            edgeDetection((char *) "guts.rgb");
            break;
        case 3:
            emboss((char *) "guts.rgb");
            break;
        case 4:
            blur((char *) "guts.rgb");
            break;
        case 5:
            sharpen((char *) "guts.rgb");
        default:
            break;
    }

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

    int time = 0;
    int hranica = cisloFiltra;

    while (!glfwWindowShouldClose(window)) {
        // Update Framebuffer
        if (cisloFiltra > 5) {
            if (time > 6 * hranica) {
                time = 0;
            }
            UpdateFramebuffer(time++, hranica);

        }

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, 512, 512, 0, GL_RGB, GL_UNSIGNED_BYTE, final);

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