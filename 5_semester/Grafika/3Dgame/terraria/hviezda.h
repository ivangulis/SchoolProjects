#ifndef PPGSO_SPHERE_H
#define PPGSO_SPHERE_H

#include <memory>

#include <GL/glew.h>

#include "scene.h"
#include "object.h"
#include "mesh.h"
#include "texture.h"
#include "shader.h"

class Hviezda : public Object {
public:
    Hviezda();
  ~Hviezda();

  // Implement object interface
  bool Update(Scene &scene, float dt);
  void Render(Scene &scene);
private:
  // Generate explosion on position and scale
  void Explode(Scene &scene, glm::vec3 explPosition, glm::vec3 explScale);

  // Age of the object in s
  float age;

  // Speed and rotational momentum
  glm::vec3 speed;
  glm::vec3 rotMomentum;

  // Static resources (Shared between instances)
  static MeshPtr mesh;
  static ShaderPtr shader;
  static TexturePtr texture;
};
typedef std::shared_ptr<Hviezda> HviezdaPtr;

#endif //PPGSO_SPHERE_H
