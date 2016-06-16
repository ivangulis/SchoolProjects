#include <object_vert.h>
#include <object_frag.h>
#include <light1_frag.h>
#include <light1_vert.h>
#include "motor.h"
#include "scene.h"

Motor::Motor() {
    scale *= 0.5f;
    position=glm::vec3(0.0,0.0,0.0f);
    rotation=glm::vec3(M_PI,0.0f,0.0f);
    rotMomentum = glm::vec3(0,  0 , M_PI/1000.0f );

    power = 10.0f;

    // Initialize static resources if needed
    if (!shader) shader = ShaderPtr(new Shader{light1_vert, light1_frag});
    if (!texture) texture = TexturePtr(new Texture{"gold.rgb", 512, 512});
    if (!mesh) mesh = MeshPtr(new Mesh{shader, "strela.obj"});
}


Motor::~Motor() {

}

bool Motor::Update(Scene &scene, float dt) {
    rotation +=rotMomentum*dt;

    GenerateModelMatrix();
    return true;
}

void Motor::Render(Scene &scene) {
    shader->Use();

    // use camera
    shader->SetMatrix(scene.camera->projectionMatrix, "ProjectionMatrix");
    shader->SetMatrix(scene.camera->viewMatrix, "ViewMatrix");

    // render mesh
    shader->SetMatrix(modelMatrix, "ModelMatrix");
    shader->SetTexture(texture, "Texture");


    shader->SetVector(glm::normalize(scene.camera->position-position),"ViewSource");

    mesh->Render();
}
float Motor::calculateFinalPower(glm::vec3 position) {
    float dist = glm::distance(this->position,position);

    return power/dist;
}
// shared resources
MeshPtr Motor::mesh;
ShaderPtr Motor::shader;
TexturePtr Motor::texture;