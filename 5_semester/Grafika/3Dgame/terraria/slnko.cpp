#include "slnko.h"
#include <light1_frag.h>
#include <light1_vert.h>
#include "scene.h"


Slnko::Slnko() {
    scale *= 3.0f;
    position=glm::vec3(0,40.0,0.0f);
    rotation=glm::vec3(M_PI,0.0f,0.0f);
    rotMomentum = glm::vec3(0,  0 , M_PI/1000.0f );
    power = 60.0f;

    // Initialize static resources if needed
    if (!shader) shader = ShaderPtr(new Shader{light1_vert, light1_frag});
    if (!texture) texture = TexturePtr(new Texture{"silver.rgb", 1500, 1500});
    if (!mesh) mesh = MeshPtr(new Mesh{shader, "strela.obj"});

    smer = true;
    lastKeyframeTime = 10;
    aktFrame = 0;

    begin.time = 0;
    begin.vector = glm::vec3(-45, 20, 0.0f);
    framy[0].vector = begin.vector;
    framy[0].time = begin.time;

    framy[1].vector = glm::vec3(-25, 35, 0.0f);
    framy[1].time = 0;

    framy[2].vector = glm::vec3(0, 40, 0.0f);
    framy[2].time = 0;

    framy[3].vector = glm::vec3(25, 35, 0.0f);
    framy[3].time = 0;

    destination.time = 0;
    destination.vector = glm::vec3(45, 20, 0.0f);
    framy[4].vector = destination.vector;
    framy[4].time = destination.time;

    position.x = framy[0].vector.x;
    position.y = framy[0].vector.y;
    position.z = framy[0].vector.z;
}


Slnko::~Slnko() {
}

bool Slnko::Update(Scene &scene, float dt) {
    rotation +=rotMomentum*dt;
    if (smer) {
        position.x += ((framy[aktFrame].vector.x - framy[aktFrame+1].vector.x) / (framy[aktFrame].time - lastKeyframeTime)) *
                      (dt);
        position.y += ((framy[aktFrame].vector.y - framy[aktFrame+1].vector.y) / (framy[aktFrame].time - lastKeyframeTime)) *
                      (dt);
        position.z += ((framy[aktFrame].vector.z - framy[aktFrame+1].vector.z) / (framy[aktFrame].time - lastKeyframeTime)) *
                      (dt);
        if (position.x >= framy[aktFrame+1].vector.x) {
            aktFrame++;
        }
        if (position.x >= framy[4].vector.x) {
            aktFrame = 4;
            smer = false;
            texture = TexturePtr(new Texture{"gold.rgb", 512, 512});
        }
    }
    else if (!smer) {
        position.x -= ((framy[aktFrame-1].vector.x - framy[aktFrame].vector.x) / (framy[aktFrame].time - lastKeyframeTime)) *
                      (dt);
        position.y -= ((framy[aktFrame-1].vector.y - framy[aktFrame].vector.y) / (framy[aktFrame].time - lastKeyframeTime)) *
                      (dt);
        position.z -= ((framy[aktFrame-1].vector.z - framy[aktFrame].vector.z) / (framy[aktFrame].time - lastKeyframeTime)) *
                      (dt);
        if (position.x <= framy[aktFrame-1].vector.x) {
            aktFrame--;
        }
        if (position.x <= framy[0].vector.x) {
            aktFrame = 0;
            smer = true;
            texture = TexturePtr(new Texture{"silver.rgb", 1500, 1500});
        }
    }

    GenerateModelMatrix();
    return true;
}

void Slnko::Render(Scene &scene) {
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
float Slnko::calculateFinalPower(glm::vec3 position) {
    float dist = glm::distance(this->position,position);

    return power/dist;
}
// shared resources
MeshPtr Slnko::mesh;
ShaderPtr Slnko::shader;
TexturePtr Slnko::texture;