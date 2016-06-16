#ifndef PPGSO_MOTOR_H
#define PPGSO_MOTOR_H

#include <mesh.h>
#include <shader.h>
#include <texture.h>
#include "object.h"

class Motor : public Object {
public:
    Motor();
    ~Motor();

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
typedef std::shared_ptr< Motor > MotorPtr;


#endif //PPGSO_MOTOR_H
