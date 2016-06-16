#include "pozadie.h"
#include "scene.h"

#include "pozadie1_vert.h"
#include "pozadie1_frag.h"


#include <GLFW/glfw3.h>

Pozadie::Pozadie() {
  offset = 0;
  position.z = 1;

  // Initialize static resources if needed
  if (!shader) shader = ShaderPtr(new Shader{pozadie1_vert, pozadie1_frag}); //space_vert, space_frag object_vert, object_frag
  if (!texture) texture = TexturePtr(new Texture{"pozadie.rgb", 1696, 1056}); //1696, 1056
  if (!mesh) mesh = MeshPtr(new Mesh{shader, "quad.obj"});
}

Pozadie::~Pozadie() {
}

bool Pozadie::Update(Scene &scene, float dt) {
  GenerateModelMatrix();
  return true;
}

void Pozadie::Render(Scene &scene) {
  // NOTE: this object does not use camera, just renders the entire quad as is
  shader->Use();

  // Pass UV mapping offset to the shader
  shader->SetFloat(offset, "Offset");

  // Render mesh
  shader->SetMatrix(modelMatrix, "ModelMatrix");
  shader->SetTexture(texture, "Texture");
  mesh->Render();
}

// Static resources
MeshPtr Pozadie::mesh;
ShaderPtr Pozadie::shader;
TexturePtr Pozadie::texture;
