#ifndef PPGSO_PROJECTILE_H
#define PPGSO_PROJECTILE_H

#include "shader.h"
#include "mesh.h"
#include "object.h"

class Strela : public Object {
public:
    Strela();
  ~Strela();
    int smer;
  bool Update(Scene &scene, float dt) override;
  void Render(Scene &scene) override;

  void Destroy();
private:
  float age;
  glm::vec3 speed;

  static ShaderPtr shader;
  static MeshPtr mesh;
  static TexturePtr texture;
};
typedef std::shared_ptr< Strela > StrelaPtr;

#endif //PPGSO_PROJECTILE_H
