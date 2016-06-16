
#include <iostream>
#include <vector>
#include <fstream>
#include <cmath>

#include <GL/glew.h>
#include <GLFW/glfw3.h>

typedef struct pixel {
    unsigned char r = 0;
    unsigned char g = 0;
    unsigned char b = 0;
}PIXEL;

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

const int size = 512;
PIXEL framebuffer[size][size];
int rohy;
int input;

void vyplnPixel(int x, int y){
    if (x > 0 && x < size && y > 0 && y < size) {
        framebuffer[y][x].r = 255;
        framebuffer[y][x].g = 255;
        framebuffer[y][x].b = 255;
    }
}

void bresen(int x0, int y0, int x1, int y1) {

    int dx = abs(x1-x0);
    int sx = x0<x1 ? 1 : -1;
    int dy = abs(y1-y0);
    int sy = y0<y1 ? 1 : -1;
    int chyba = (dx>dy ? dx : -dy)/2;
    int e2;

    while(1){
        vyplnPixel(x0,y0);
        if (x0==x1 && y0==y1) break;
        e2 = chyba;
        if (e2 >-dx){
            chyba -= dy;
            x0 += sx;
        }
        if (e2 < dy){
            chyba += dx;
            y0 += sy;
        }
    }
}

void bezier(int x[4],int y[4]){

    /*vyplnPixel(x[0],y[0]);
    vyplnPixel(x[1],y[1]);
    vyplnPixel(x[2],y[2]);
    vyplnPixel(x[3],y[3]);*/

    int px, py, Lx,Ly;
    double length = 0.0;
    rohy = input;
    int px1 = x[0], py1 = y[0];

    //zistím si približnú dåžku krivky
    for(int i=0;i<rohy;++i) {

        //parameter
        float t = (float) i / (rohy-1);

        //1-t
        float p = 1.0f - t;

        //ohýbacie funkcie
        float v0 = t * t * t;
        float v1 = 3 * t * t * p;
        float v2 = 3 * t * p * p;
        float v3 = p * p * p;

        //generovanie bodov
        px=(int)(v3 *x[0]+ v2 *x[1]+ v1 *x[2]+ v0 *x[3]);
        py=(int)(v3 *y[0]+ v2 *y[1]+ v1 *y[2]+ v0 *y[3]);

        Lx = abs(px - px1);
        Ly = abs(py - py1);
        length = length + sqrt(Lx * Lx + Ly * Ly);

        px1 = px;
        py1 = py;
    }

    //ak je dåžka menšia než by som kreslil bodou
    if ((int)length < rohy)
        rohy = (int)length;

    px1 = x[0];
    py1 = y[0];

    //už výpoèet s kreslením
    for(int i=0;i<rohy;++i) {

        //parameter
        float t = (float) i / (rohy-1);

        //1-t
        float p = 1.0f - t;

        //ohýbacie funkcie
        float v0 = t * t * t;
        float v1 = 3 * t * t * p;
        float v2 = 3 * t * p * p;
        float v3 = p * p * p;

        //generovanie bodov
        px=(int)(v3 *x[0]+ v2 *x[1]+ v1 *x[2]+ v0 *x[3]);
        py=(int)(v3 *y[0]+ v2 *y[1]+ v1 *y[2]+ v0 *y[3]);
        bresen(px1, py1, px, py);
        px1 = px;
        py1 = py;
    }
    //std::cout << rohy << " vs " << length << std::endl;
}

void nakresliClovekChrobak(){
    int x[4], y[4];
    int oX,oY;

    //had1
    oX = 0;
    oY = 0;
    x[0] = 20+oX;
    x[1] = 30+oX;
    x[2] = 100+oX;
    x[3] = 110+oX;
    y[0] = 511+oY;
    y[1] = 350+oY;
    y[2] = 350+oY;
    y[3] = 511+oY;
    bezier(x,y);

    //had2
    oX = 0;
    oY = -100;
    x[0] = 20+oX+12;
    x[1] = 30+oX+10;
    x[2] = 100+oX+40;
    x[3] = 110+oX-15;
    y[0] = 511+oY+20;
    y[1] = 350+oY;
    y[2] = 350+oY;
    y[3] = 511+oY+20;
    bezier(x,y);

    //had3
    oX = 15;
    oY = -200;
    x[0] = 20+oX+12;
    x[1] = 30+oX+45;
    x[2] = 100+oX+65;
    x[3] = 110+oX-20;
    y[0] = 511+oY+25;
    y[1] = 350+oY+15;
    y[2] = 350+oY+15;
    y[3] = 511+oY+25;
    bezier(x,y);

    //had4
    oX = 50;
    oY = -300;
    x[0] = 20+oX+12;
    x[1] = 30+oX+80;
    x[2] = 100+oX+150;
    x[3] = 110+oX-28;
    y[0] = 511+oY+30;
    y[1] = 350+oY+35;
    y[2] = 350+oY+75;
    y[3] = 511+oY+35;
    bezier(x,y);

    //had5
    oX = 150;
    oY = -350;
    x[0] = 20+oX+12;
    x[1] = 30+oX+180;
    x[2] = 100+oX+150;
    x[3] = 110+oX-55;
    y[0] = 511+oY-22;
    y[1] = 350+oY+35;
    y[2] = 350+oY+300;
    y[3] = 511+oY+13;
    bezier(x,y);

    //kusadla dolne
    oX = 310;
    oY = 205;
    x[0] = 0+oX;
    x[1] = 0+oX;
    x[2] = 100+oX-50;
    x[3] = 100+oX-10;
    y[0] = 0+oY;
    y[1] = 25+oY+10;
    y[2] = 25+oY+50;
    y[3] = 0+oY+50;
    bezier(x,y);

    //kusadla horne
    oX = 333;
    oY = 180;
    x[0] = 0+oX+2;
    x[1] = 25+oX+20;
    x[2] = 25+oX+60;
    x[3] = 0+oX+88;
    y[0] = 0+oY-10;
    y[1] = 0+oY-35;
    y[2] = 100+oY-90;
    y[3] = 100+oY-60;
    bezier(x,y);

    //oko hada
    oX = 270;
    oY = 150;
    x[0] = 0+oX;
    x[1] = 0+oX;
    x[2] = 50+oX-20;
    x[3] = 50+oX;
    y[0] = 0+oY;
    y[1] = 20+oY+20;
    y[2] = 20+oY+30;
    y[3] = 0+oY+30;
    bezier(x,y);
    oX = 270;
    oY = 150;
    x[0] = 0+oX;
    x[1] = 0+oX;
    x[2] = 50+oX;
    x[3] = 50+oX;
    y[0] = 0+oY;
    y[1] = 0+oY;
    y[2] = 0+oY+30;
    y[3] = 0+oY+30;
    bezier(x,y);

    //hlava
    x[0] = 425;
    x[1] = 425;
    x[2] = 450;
    x[3] = 450;
    y[0] = 400;
    y[1] = 375;
    y[2] = 375;
    y[3] = 400;
    bezier(x,y);

    //hlava
    x[0] = 450;
    x[1] = 450;
    x[2] = 425;
    x[3] = 425;
    y[0] = 400;
    y[1] = 425;
    y[2] = 425;
    y[3] = 400;
    bezier(x,y);

    //telo
    x[0] = 438;
    x[3] = 438;
    y[0] = 419;
    y[3] = 475;
    bresen(x[0], y[0], x[3], y[3]);
    //noha lava
    x[0] = 438;
    x[3] = 420;
    y[0] = 475;
    y[3] = 511;
    bresen(x[0], y[0], x[3], y[3]);
    //noha prava
    x[0] = 438;
    x[3] = 456;
    y[0] = 475;
    y[3] = 511;
    bresen(x[0], y[0], x[3], y[3]);

    //ruka horna
    x[0] = 438;
    x[1] = 438;
    x[2] = 410;
    x[3] = 405;
    y[0] = 450;
    y[1] = 450;
    y[2] = 430;
    y[3] = 415;
    bezier(x,y);

    //ruka spodna
    x[0] = 438;
    x[1] = 438;
    x[2] = 410;
    x[3] = 395;
    y[0] = 450;
    y[1] = 450;
    y[2] = 430;
    y[3] = 435;
    bezier(x,y);

}

void nakresliText(double positionY){
    int x[4], y[4];
    int oX,oY;

    //B
    oX = 220;
    oY = int(60+positionY);
    x[0] = 0+oX;
    x[3] = 0+oX;
    y[0] = 0+oY;
    y[3] = 40+oY;
    bresen(x[0], y[0], x[3], y[3]);
    //B
    x[0] = 0+oX;
    x[1] = 20+oX;
    x[2] = 20+oX;
    x[3] = 0+oX;
    y[0] = 0+oY;
    y[1] = 0+oY;
    y[2] = 20+oY;
    y[3] = 20+oY;
    bezier(x,y);
    //B
    x[0] = 0+oX;
    x[1] = 20+oX;
    x[2] = 20+oX;
    x[3] = 0+oX;
    y[0] = 0+oY+20;
    y[1] = 0+oY+20;
    y[2] = 20+oY+20;
    y[3] = 20+oY+20;
    bezier(x,y);

    //O
    oX = 240;
    oY = int(60+positionY);
    x[0] = 0+oX;
    x[1] = 0+oX;
    x[2] = 20+oX;
    x[3] = 20+oX;
    y[0] = 20+oY;
    y[1] = 0+oY-5;
    y[2] = 0+oY-5;
    y[3] = 20+oY;
    bezier(x,y);
    x[0] = 0+oX;
    x[1] = 0+oX;
    x[2] = 20+oX;
    x[3] = 20+oX;
    y[0] = 20+oY;
    y[1] = 40+oY+5;
    y[2] = 40+oY+5;
    y[3] = 20+oY;
    bezier(x,y);

    //S
    oX = 265;
    oY = int(60+positionY);
    x[0] = 20+oX;
    x[1] = 15+oX;
    x[2] = -10+oX;
    x[3] = 10+oX;
    y[0] = 10+oY;
    y[1] = -10+oY;
    y[2] = 10+oY;
    y[3] = 20+oY;
    bezier(x,y);
    x[0] = 10+oX;//
    x[1] = 30+oX;
    x[2] = 5+oX;
    x[3] = 0+oX;
    y[0] = 20+oY;//
    y[1] = 30+oY;
    y[2] = 50+oY;
    y[3] = 30+oY;
    bezier(x,y);

    //S2
    oX = 290;
    oY = int(60+positionY);
    x[0] = 20+oX;
    x[1] = 15+oX;
    x[2] = -10+oX;
    x[3] = 10+oX;
    y[0] = 10+oY;
    y[1] = -10+oY;
    y[2] = 10+oY;
    y[3] = 20+oY;
    bezier(x,y);
    x[0] = 10+oX;//
    x[1] = 30+oX;
    x[2] = 5+oX;
    x[3] = 0+oX;
    y[0] = 20+oY;//
    y[1] = 30+oY;
    y[2] = 50+oY;
    y[3] = 30+oY;
    bezier(x,y);



}

void UpdateFramebuffer(double time, double positionY){

    //refresh buffera
    for (int i = 0; i<size; i++ ) {
        for (int j = 0; j < size; j++) {
            framebuffer[i][j].r = 0;
            framebuffer[i][j].g = 0;
            framebuffer[i][j].b = 0;
        }
    }


    nakresliClovekChrobak();
    nakresliText(positionY);

    int x[4], y[4];
    int oX,oY;

    //hadoken
    oX = (int)(340-time);
    oY = (int)(340-time/2+35);
    x[0] = 70+oX;
    x[1] = 40+oX;
    x[2] = 20+oX;
    x[3] = 70+oX;
    y[0] = 55+oY;
    y[1] = 20+oY;
    y[2] = 40+oY;
    y[3] = 55+oY;
    bezier(x,y);
}

int main() {

    std::cout << "Max pocet bodov na krivku (optimalne do 20):" << std::endl;
    std::cin >> input;


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

    double time = 0;
    double positionY = 0;
    int smer = 1;

    // Main execution loop
    while (!glfwWindowShouldClose(window)) {
        // Update Framebuffer
        if (smer > 0) positionY+=0.05;
        if (smer < 0) positionY-=0.05;
        if (positionY > 10) smer = -1;
        if (positionY < -10) smer = 1;
        if (time > 260) time = 0;
        UpdateFramebuffer(time+=0.5, positionY);


        // Update texture data with framebuffer
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, 512, 512, 0, GL_RGB, GL_UNSIGNED_BYTE, framebuffer);

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