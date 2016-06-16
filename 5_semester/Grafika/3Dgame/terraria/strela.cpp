#include "scene.h"
#include "strela.h"

#include "object1_vert.h"
#include "object1_frag.h"

Strela::Strela() {
  // Initialize age to 0
  age = 0;
  scale *= 0.3f;
  smer = 0;

  // Initialize static resources if needed
  if (!shader) shader = ShaderPtr(new Shader{object1_vert, object1_frag});
  if (!texture) texture = TexturePtr(new Texture{"fialova.rgb", 512, 512});
  if (!mesh) mesh = MeshPtr(new Mesh{shader, "strela.obj"});
}

Strela::~Strela() {
}

bool Strela::Update(Scene &scene, float dt) {
  // Increase age
  age += dt;

  //Accelerate
  if (smer == 8)
    speed = glm::vec3(0.0f, 1.0f, 0.0f);
  else if (smer == 2)
    speed = glm::vec3(0.0f, -1.0f, 0.0f);
  else if (smer == 4)
    speed = glm::vec3(1.0f, 0.0f, 0.0f);
  else if (smer == 6)
    speed = glm::vec3(-1.0f, 0.0f, 0.0f);

  // Move the projectile
  position += speed;

  // Die after 5s
  if (age > 5.0f) return false;

  GenerateModelMatrix();
  return true;
}

void Strela::Render(Scene &scene) {
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

ShaderPtr Strela::shader;
MeshPtr Strela::mesh;
TexturePtr Strela::texture;

void Strela::Destroy() {
  // This will destroy the projectile on Update
  age = 100.0f;
}
