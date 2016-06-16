#include "generator.h"
#include "hviezda.h"

bool Generator::Update(Scene &scene, float dt) {
  // Accumulate time
  time += dt;

  // Add object to scene when time reaches certain level
  if (time > .01) {
    auto obj = HviezdaPtr(new Hviezda());
    obj->position = this->position;
    obj->position.x += Rand(-40, 40);
    scene.objects.push_back(obj);
    time = 0;
  }

  return true;
}

void Generator::Render(Scene &scene) {
  // Generator will not be rendered
}

Generator::~Generator() {
}

Generator::Generator() {
  time = 0;
}
