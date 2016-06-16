#include "kocka.h"
#include "explosion.h"
#include "strela.h"
#include "player.h"

#include "object1_frag.h"
#include "object1_vert.h"

#include <GLFW/glfw3.h>

Kocka::Kocka() {
    position.z = 0;
    // Initialize static resources if needed
    if (!shader) shader = ShaderPtr(new Shader{object1_vert, object1_frag});
    if (!texture) texture = TexturePtr(new Texture{"dirt.rgb", 100, 100});
    if (!mesh) mesh = MeshPtr(new Mesh{shader, "kocka.obj"});
}

Kocka::~Kocka() {
}

bool Kocka::Update(Scene &scene, float dt) {

    if (!scene.kocky[y][x]) {
        return false;
    }

    // Collide with scene
    for (auto obj : scene.objects) {
        // Ignore self in scene
        if (obj.get() == this) continue;

        // We only need to collide with asteroids and projectiles, ignore other objects
        auto strela = std::dynamic_pointer_cast<Strela>(obj);

        if (!strela) continue;

        // Compare distance to approximate size of the asteroid estimated from scale.
        if (glm::distance(position, obj->position) < (obj->scale.y + scale.y)) {

            // The projectile will be destroyed
            if (strela) strela->Destroy();

            scene.material++;
            scene.kocky[y][x] = false;

            Explode(scene, (obj->position+position)/2.0f, (obj->scale+scale));

            // Destroy self
            return false;
        }
    }

    // Generate modelMatrix from position, rotation and scale
    GenerateModelMatrix();

    return true;
}

void Kocka::Explode(Scene &scene, glm::vec3 explPosition, glm::vec3 explScale) {
    // Generate explosion
    auto explosion = ExplosionPtr(new Explosion{});
    explosion->position = explPosition;
    explosion->scale = explScale;
    scene.objects.push_back(explosion);
}

void Kocka::Render(Scene &scene) {
    shader->Use();

    // use camera
    shader->SetMatrix(scene.camera->projectionMatrix, "ProjectionMatrix");
    shader->SetMatrix(scene.camera->viewMatrix, "ViewMatrix");

    // render mesh
    shader->SetMatrix(modelMatrix, "ModelMatrix");
    shader->SetTexture(texture, "Texture");
    mesh->Render();

    shader->SetVector(scene.lightSource->position - position, "LightSource");
    shader->SetVector(scene.lightSource2->position - position, "LightSource2");

    shader->SetFloat(scene.lightSource->calculateFinalPower(position), "LightSourcePower");
    shader->SetFloat(scene.lightSource2->calculateFinalPower(position), "LightSourcePower2");


    shader->SetVector(glm::normalize(scene.camera->position - position), "ViewSource");
}

// shared resources
MeshPtr Kocka::mesh;
ShaderPtr Kocka::shader;
TexturePtr Kocka::texture;