#ifndef PPGSO_KEYFRAME_H
#define PPGSO_KEYFRAME_H

struct Keyframe {
    float time;
    glm::vec3 vector;
};

typedef std::shared_ptr< Keyframe > KeyframePtr;

#endif //PPGSO_KEYFRAME_H
