#version 150
// A texture is expected as program attribute
uniform sampler2D Texture;
uniform vec3 LightSource;
uniform vec3 LightSource2;
uniform float LightSourcePower;
uniform float LightSourcePower2;

uniform vec3 ViewSource;

// The vertex shader fill feed this input
in vec2 FragTexCoord;

// Wordspace normal
in vec4 normal;

// The final color
out vec4 FragmentColor;

//vec3 LightSource = vec3(0,1,0);
//vec3 LightSource2 = vec3(0,1,0);

vec4 lightDirection = normalize(vec4(LightSource,0.0f));
vec4 lightDirection2 = normalize(vec4(LightSource2,0.0f));
vec4 viewDirection =  normalize(vec4(ViewSource,0.0));

void main() {
  //ambient
  vec4 ambientVector = vec4(0.2,0.2,0.2,0.2);

  //spec
  vec4 reflectDir = normalize(lightDirection+viewDirection);
  vec4 reflectDir2 = normalize(lightDirection2+viewDirection);
  float specStr = 3.0f;
  float spec = pow(max(dot(reflectDir,normal), 0.2), 16)*specStr * LightSourcePower;
  float spec2 = pow(max(dot(reflectDir2,normal), 0.2), 16)*specStr * LightSourcePower2;

  //diffuse
  float diffuse = max(dot(normal, lightDirection), 0.4f) * LightSourcePower;
  float diffuse2 = max(dot(normal, lightDirection2), 0.4f) * LightSourcePower2;

  // Lookup the color in Texture on coordinates given by fragTexCoord and apply diffuse lighting
  FragmentColor = texture(Texture, FragTexCoord) * (ambientVector+spec+spec2+diffuse+diffuse2) ;
}
