#ifndef PPGSO_SLNKO_H
#define PPGSO_SLNKO_H

#include <mesh.h>
#include <shader.h>
#include <texture.h>
#include "object.h"
#include "keyframe.h"

class Slnko : public Object {
public:
    Slnko();
    ~Slnko();

    float lastKeyframeTime;
    Keyframe framy[5];
    Keyframe destination;
    Keyframe begin;
    bool smer;
    int aktFrame;

    virtual bool Update(Scene &scene, float dt);
    virtual void Render(Scene &scene);
    float power;

    float calculateFinalPower(glm::vec3 position);

private:
    // Static resources (Shared between instances)
    static MeshPtr mesh;
    static ShaderPtr shader;
    static TexturePtr texture;
    glm::vec3 rotMomentum;
};
typedef std::shared_ptr< Slnko > SlnkoPtr;


#endif //PPGSO_SLNKO_H
