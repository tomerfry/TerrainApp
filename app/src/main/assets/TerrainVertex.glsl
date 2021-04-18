
attribute vec4 vPosition;

uniform mat4 transformationMatrix;

void main() {
    gl_Position = transformationMatrix * vPosition;
}