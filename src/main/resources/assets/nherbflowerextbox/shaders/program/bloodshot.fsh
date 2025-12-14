#version 150

uniform mat4 ProjMat;
uniform float InSize;
uniform float OutSize;

uniform sampler2D DiffuseSampler;
uniform float RedIntensity;
in vec2 texCoord;
out vec4 fragColor;

void main() {
    // 1. 入力テクスチャのサンプリング
    vec4 col = texture(DiffuseSampler, texCoord);

    // 2. ターゲットとなる純粋な赤色を定義 (アルファ値は元の色を保持)
    vec4 red_overlay = vec4(1.0, 0.0, 0.0, col.a);

    // 3. mix 関数で元の色と赤色を混合 (Linear Interpolation)
    // RedIntensity が 0.0 の場合: 元の色 (col) 100%
    // RedIntensity が 1.0 の場合: 赤色 (red_overlay) 100%
    // ここでは JSON で 0.25 (25%) 程度の薄い赤を設定します。
    fragColor = mix(col, red_overlay, RedIntensity);
}
