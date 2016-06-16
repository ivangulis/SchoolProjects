#ifndef PPGSO_SPACE_H
#define PPGSO_SPACE_H

#include "mesh.h"
#include "object.h"

class Pozadie : public Object {
public:
  Pozadie();
  ~Pozadie();

  bool Update(Scene &scene, float dt) override;
  void Render(Scene &scene) override;

private:
  float offset;
  // Static resources (Shared between instances)
  static MeshPtr mesh;
  static ShaderPtr shader;
  static TexturePtr texture;
};
typedef std::shared_ptr< Pozadie > PozadiePtr;

#endif //PPGSO_SPACE_H
