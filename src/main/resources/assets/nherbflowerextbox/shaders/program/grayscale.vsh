#version 150

uniform mat4 ProjMat;
uniform float InSize;
uniform float OutSize;

in vec2 Position;
in vec2 UV0;

out vec2 texCoord;

void main() {
    gl_Position = vec4(Position.x * 2.0 - 1.0, Position.y * 2.0 - 1.0, 0.0, 1.0);

    texCoord = Position;
}