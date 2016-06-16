#include "player.h"
#include "scene.h"
#include "kocka.h"
#include "strela.h"
#include "hviezda.h"
#include "explosion.h"
#include "generator.h"
#include "motor.h"

#include "object1_frag.h"
#include "object1_vert.h"

#include <GLFW/glfw3.h>

float timeStart = 0;

Player::Player() {
  // Reset fire delay
  fireDelay = 0;
  // Set the rate of fire
  fireRate = 0.5f;
  // Scale the default model
  scale *= 0.05;//3.0f;

  // Initialize static resources if needed
  if (!shader) shader = ShaderPtr(new Shader{object1_vert, object1_frag});
  if (!texture) texture = TexturePtr(new Texture{"robot textura.rgb", 2560, 1440});
  if (!mesh) mesh = MeshPtr(new Mesh{shader, "player.obj"});
}

Player::~Player() {
}

bool Player::Update(Scene &scene, float dt) {
  //pozícia motora
  scene.lightSource->position.x = position.x;
  scene.lightSource->position.y = position.y;
  scene.lightSource->position.z = position.z+1;
  //svetlá
  if (scene.keyboard[GLFW_KEY_P]) {
    scene.lightSource->power = 0;
  }
  if (scene.keyboard[GLFW_KEY_L]) {
    scene.lightSource->power = 10;
  }
  // Fire delay increment
  fireDelay += dt;
  time += dt;
  if (time > .1 && scene.lightSource->power > 0) {
    auto explosion = ExplosionPtr(new Explosion{});
    explosion->position = position;
    explosion->scale = scale * glm::vec3(20.0f, 20.0f, 20.0f);
    scene.objects.push_back(explosion);
    time = 0;
  }
  //spustenie hry
  if (scene.keyboard[GLFW_KEY_SPACE] && timeStart <= 0.0) {
    // Add generator to scene
    auto generator = GeneratorPtr(new Generator{});
    generator->position.y = 42.0f;
    scene.objects.push_back(generator);
    timeStart = (float)glfwGetTime();
  }
  // kamery
  if (scene.keyboard[GLFW_KEY_1]) {
    // Change camera 1
    auto camera = CameraPtr(new Camera{ 60.0f, 1.0f, 0.1f, 100.0f});
    camera->position.z = -70;
    scene.camera = camera;
  }
  if (scene.keyboard[GLFW_KEY_2]) {
    // Change camera 2
    auto camera = CameraPtr(new Camera{ 60.0f, 1.0f, 0.1f, 100.0f});
    camera->position.z = -10;
    scene.camera = camera;
  }
  if (scene.keyboard[GLFW_KEY_3]) {
    // Change camera 3
    auto camera = CameraPtr(new Camera{ 60.0f, 1.0f, 0.1f, 100.0f});
    camera->position.z = -10;
    camera->position.x = -58;
    camera->back.x = -150;
    scene.camera = camera;
  }


  // Hit detection s hviezdami
  for ( auto obj : scene.objects ) {
    // Ignore self in scene
    if (obj.get() == this)
      continue;

    auto hviezda = std::dynamic_pointer_cast<Hviezda>(obj);
    if (!hviezda) continue;

    if (glm::distance(position+glm::vec3(0.0f, 0.0f, 0.0f), obj->position) < (obj->scale.y + scale.y)) {
      std::cout << "Zostalo ti " << scene.material << " kusov materialu! Good job!" << std::endl;
      std::cout << "Vydrzal si " << (float)glfwGetTime() - timeStart << " sekund!" << std::endl;
      //scene.koniec = true;
      return false;
    }
  }

  bool pohyb8 = true;
  bool pohyb2 = true;
  bool pohyb6 = true;
  bool pohyb4 = true;
  if (scene.keyboard[GLFW_KEY_A] && scene.lightSource->power > 0) {
    for ( auto obj : scene.objects ) {
      if (obj.get() == this)
        continue;
      auto kocka = std::dynamic_pointer_cast<Kocka>(obj);
      if (!kocka) continue;
      if (glm::distance(position + glm::vec3(10*dt, 10*dt, 0.0f), obj->position) < (obj->scale.y + scale.y)) {
        if (position.x+10*dt < obj->position.x) pohyb4 = false;
      }
    }
    if (position.x < 39 && pohyb4) position.x += 10 * dt;
    rotation.z = -PI / 2.0f;
  } else if (scene.keyboard[GLFW_KEY_D] && scene.lightSource->power > 0) {
    for ( auto obj : scene.objects ) {
      if (obj.get() == this)
        continue;
      auto kocka = std::dynamic_pointer_cast<Kocka>(obj);
      if (!kocka) continue;
      if (glm::distance(position + glm::vec3(-10*dt, 10*dt, 0.0f), obj->position) < (obj->scale.y + scale.y)) {
        if (position.x-10*dt > obj->position.x) pohyb6 = false;
      }
    }
    if (position.x > -39 && pohyb6) position.x -= 10 * dt;
    rotation.z = PI / 2.0f;
  } else if (scene.keyboard[GLFW_KEY_W] && scene.lightSource->power > 0) {
    for ( auto obj : scene.objects ) {
      if (obj.get() == this)
        continue;
      auto kocka = std::dynamic_pointer_cast<Kocka>(obj);
      if (!kocka) continue;
      if (glm::distance(position + glm::vec3(0.0f, 40*dt, 0.0f), obj->position) < (obj->scale.y + scale.y)) {
        if (position.y+10*dt < obj->position.y) pohyb8 = false;
      }
    }
    if (position.y < 37 && pohyb8) position.y += 10 * dt;
    rotation.z = 0;
  } else if (scene.keyboard[GLFW_KEY_S] && scene.lightSource->power > 0) {
    for ( auto obj : scene.objects ) {
      if (obj.get() == this)
        continue;
      auto kocka = std::dynamic_pointer_cast<Kocka>(obj);
      if (!kocka) continue;
      if (glm::distance(position + glm::vec3(0.0f, -10*dt, 0.0f), obj->position) < (obj->scale.y + scale.y)) {
        if (position.y-10*dt > obj->position.y) pohyb2 = false;
      }
    }
    if (position.y > -40 && pohyb2) position.y -= 10 * dt;
    rotation.z = 0;
  }
  else rotation.z = 0;

  // Firing projectiles
  if (scene.keyboard[GLFW_KEY_LEFT] && fireDelay > fireRate) {
    fireDelay = 0;
    auto strela = StrelaPtr(new Strela{});
    strela->smer = 4;
    strela->position = position + glm::vec3(0.0f, 1.0f, 0.0f);
    scene.objects.push_back(strela);
  } else if (scene.keyboard[GLFW_KEY_RIGHT] && fireDelay > fireRate) {
    fireDelay = 0;
    auto strela = StrelaPtr(new Strela{});
    strela->smer = 6;
    strela->position = position + glm::vec3(0.0f, 1.0f, 0.0f);
    scene.objects.push_back(strela);
  } else if (scene.keyboard[GLFW_KEY_UP] && fireDelay > fireRate) {
    fireDelay = 0;
    auto strela = StrelaPtr(new Strela{});
    strela->smer = 8;
    strela->position = position + glm::vec3(0.0f, 1.0f, 0.0f);
    scene.objects.push_back(strela);
  } else if (scene.keyboard[GLFW_KEY_DOWN] && fireDelay > fireRate) {
    fireDelay = 0;
    auto strela = StrelaPtr(new Strela{});
    strela->smer = 2;
    strela->position = position + glm::vec3(0.0f, 1.0f, 0.0f);
    scene.objects.push_back(strela);
  }

  //Build/destroy blocks
  if(scene.mouse.left && timeStart <= 0.0) {
    if (scene.kocky[40 - ((int)(scene.mouse.y / (950/40)))][40 - ((int)(scene.mouse.x / (950/40)))]) {
      //scene.material++;
      scene.kocky[40 - ((int) (scene.mouse.y / (950 / 40)))][40 - ((int) (scene.mouse.x / (950 / 40)))] = false;
    }
  }
  if(scene.mouse.right && scene.material >= 1) {
    if (!scene.kocky[40 - ((int) (scene.mouse.y / (950 / 40)))][40 - ((int) (scene.mouse.x / (950 / 40)))]) {
      auto kocka = KockaPtr(new Kocka{});
      kocka->position.x = (float)((40 - ((int) scene.mouse.x / (950 / 40))) * 2.005 - 39.05);
      kocka->position.y = (float)((40 - ((int) scene.mouse.y / (950 / 40))) * 2.005 - 39.05);
      kocka->x = 40 - ((int) (scene.mouse.x / (950 / 40)));
      kocka->y = 40 - ((int) (scene.mouse.y / (950 / 40)));
      scene.kocky[40 - ((int) (scene.mouse.y / (950 / 40)))][40 - ((int) (scene.mouse.x / (950 / 40)))] = true;
      scene.objects.push_back(kocka);
      scene.material--;
    }
  }

  GenerateModelMatrix();
  return true;
}

void Player::Render(Scene &scene) {
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
MeshPtr Player::mesh;
ShaderPtr Player::shader;
TexturePtr Player::texture;