#include "scene.h"

Scene::Scene() {
    for (int j = 0; j < 40; j++) {
        for (int i = 0; i < 40; i++) {
            kocky[j][i] = true;
            if (j>19) kocky[j][i] = false;
        }
    }
    material = 0;
    koniec = false;
}

Scene::~Scene() {
}

bool Scene::Update(float time) {
    camera->Update();

    // Use iterator to update all objects so we can remove while iterating
    auto i = std::begin(objects);

    while (i != std::end(objects)) {
        auto obj = i->get();
        if (!obj->Update(*this, time))
            i = objects.erase(i); // NOTE: no need to call destructors as we store shared pointers in the scene
        else
            ++i;
    }
    if (koniec)
        return false;
    else return true;

}

void Scene::Render() {
    // Simply render all objects
    for (auto obj : objects )
        obj->Render(*this);
}

