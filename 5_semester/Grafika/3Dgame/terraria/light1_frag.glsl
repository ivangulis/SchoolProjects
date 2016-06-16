#version 150
// A texture is expected as program attribute
uniform sampler2D Texture;
uniform vec3 ViewSource;

// The vertex shader fill feed this input
in vec2 FragTexCoord;

vec4 viewDirection =  vec4(ViewSource,0);

// The final color
out vec4 FragmentColor;

in vec4 normal;

void main() {

  vec4 reflectDir = reflect(viewDirection, normal);
  float spec = pow(max(dot(viewDirection, reflectDir), 0.0), 2)*3.0f;

  FragmentColor = texture(Texture, FragTexCoord)*(1.2f+spec);



}
