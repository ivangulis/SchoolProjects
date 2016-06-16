#include "hviezda.h"
#include "kocka.h"
#include "explosion.h"

#include "object1_frag.h"
#include "object1_vert.h"

Hviezda::Hviezda() {
  // Reset the age to 0
  age = 0;
  position.z = 0;

  // Set random scale speed and rotation
  scale *= 1.5f;
  speed = glm::vec3(Rand(-2.0f, 2.0f), Rand(-10.0f, -30.0f), 0.0f);
  rotation = glm::vec3(Rand(-PI, PI), Rand(-PI, PI), Rand(-PI, PI));
  rotMomentum = glm::vec3(Rand(-PI, PI), Rand(-PI, PI), Rand(-PI, PI));

  // Initialize static resources if needed
  if (!shader) shader = ShaderPtr(new Shader{object1_vert, object1_frag});
  if (!texture) texture = TexturePtr(new Texture{"gold.rgb", 1024, 977});
  if (!mesh) mesh = MeshPtr(new Mesh{shader, "hviezda.obj"});
}

Hviezda::~Hviezda() {
}

bool Hviezda::Update(Scene &scene, float dt) {
  // Count time alive
  age += dt;

  // Animate position according to time
  position += speed * dt;

  // Rotate the object
  rotation += rotMomentum * dt;

  // Delete when alive longer than 10s or out of visibility
  if (age > 50.0f || position.y < -42) return false;

  // Collide with scene
  for (auto obj : scene.objects) {
    // Ignore self in scene
    if (obj.get() == this) continue;

    // We only need to collide with asteroids and projectiles, ignore other objects
    auto kocka = std::dynamic_pointer_cast<Kocka>(obj);
    if (!kocka) continue;

    // Compare distance to approximate size of the asteroid estimated from scale.
    if (glm::distance(position, obj->position) < (obj->scale.y + scale.y)) {

      // Generate smaller asteroids
      Explode(scene, (obj->position+position)/2.0f, (obj->scale+scale)/2.0f);

      //kocka->Explode(scene, (kocka->position)/2.0f, (kocka->scale+scale));
      scene.kocky[kocka->y][kocka->x] = false;

      // Destroy self
      return false;
    }
  }

  // Generate modelMatrix from position, rotation and scale
  GenerateModelMatrix();

  return true;
}

void Hviezda::Explode(Scene &scene, glm::vec3 explPosition, glm::vec3 explScale) {
  // Generate explosion
  auto explosion = ExplosionPtr(new Explosion{});
  explosion->position = explPosition;
  explosion->scale = explScale;
  explosion->speed = speed/2.0f;
  scene.objects.push_back(explosion);
}

void Hviezda::Render(Scene &scene) {
  shader->Use();

  // use camera
  shader->SetMatrix(scene.camera->projectionMatrix, "ProjectionMatrix");
  shader->SetMatrix(scene.camera->viewMatrix, "ViewMatrix");

  // render mesh
  shader->SetMatrix(modelMatrix, "ModelMatrix");
  shader->SetTexture(texture, "Texture");
  mesh->Render();

  shader->SetVector((scene.lightSource->position - position), "LightSource");
  shader->SetVector((scene.lightSource2->position - position), "LightSource2");

  shader->SetFloat(scene.lightSource->calculateFinalPower(position), "LightSourcePower");
  shader->SetFloat(scene.lightSource2->calculateFinalPower(position), "LightSourcePower2");


  shader->SetVector(glm::normalize(scene.camera->position - position), "ViewSource");
}

// shared resources
MeshPtr Hviezda::mesh;
ShaderPtr Hviezda::shader;
TexturePtr Hviezda::texture;
