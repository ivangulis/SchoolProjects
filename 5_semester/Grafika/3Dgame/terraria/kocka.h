#ifndef PPGSO_KOCKA_H
#define PPGSO_KOCKA_H

#include <memory>

#include <GL/glew.h>

#include "scene.h"
#include "object.h"
#include "mesh.h"
#include "texture.h"
#include "shader.h"

class Kocka : public Object {
public:
    Kocka();
    ~Kocka();

    // Implement object interface
    bool Update(Scene &scene, float dt);
    void Render(Scene &scene);

    int x,y;
    void Explode(Scene &scene, glm::vec3 explPosition, glm::vec3 explScale);

private:
    // Static resources (Shared between instances)
    static MeshPtr mesh;
    static ShaderPtr shader;
    static TexturePtr texture;
};
typedef std::shared_ptr<Kocka> KockaPtr;

#endif //PPGSO_KOCKA_H
