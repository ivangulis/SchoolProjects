#ifndef _PPGSO_SCENE_H
#define _PPGSO_SCENE_H

#include <memory>
#include <map>
#include <list>

#include "object.h"
#include "camera.h"
#include "motor.h"
#include "slnko.h"


class Scene {
public:
    Scene();
    ~Scene();

    // Animate all objects in scene
    bool Update(float time);
    // Render all objects in scene
    void Render();
    bool koniec;
    int material;
    CameraPtr camera;
    std::list< ObjectPtr > objects;
    std::map< int, int > keyboard;
    bool kocky[45][45];
    struct {
        double x, y;
        bool left, right;
    } mouse;

    MotorPtr lightSource;
    SlnkoPtr lightSource2;
};
typedef std::shared_ptr< Scene > ScenePtr;

#endif // _PPGSO_SCENE_H
