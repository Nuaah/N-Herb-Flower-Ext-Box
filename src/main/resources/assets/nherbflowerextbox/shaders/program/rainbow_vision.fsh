#version 150

uniform sampler2D DiffuseSampler; // 元の画面のテクスチャ
uniform float u_Time;             // Javaから受け取る時間変数 (名前は任意)

in vec2 texCoord;                 // 画面上の座標
out vec4 fragColor;               // 最終的な出力色

// --- RGBからHSVへの変換関数 (一般的な計算式) ---
vec3 rgb2hsv(vec3 c) {
    vec4 K = vec4(0.0, -1.0 / 3.0, 2.0 / 3.0, -1.0);
    vec4 p = mix(vec4(c.bg, K.wz), vec4(c.gb, K.xy), step(c.b, c.g));
    vec4 q = mix(vec4(p.xyw, c.r), vec4(c.yzx, K.x), step(p.x, c.r));

    float d = q.x - min(q.w, q.y);
    float e = 1.0e-10;
    return vec3(abs(q.z + (q.w - q.y) / (6.0 * d + e)), d / (q.x + e), q.x);
}

// --- HSVからRGBへの変換関数 ---
vec3 hsv2rgb(vec3 c) {
    vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);
    vec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www);
    return c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y);
}

void main(){
    // 1. 元の画面の色を取得
    vec4 originalColor = texture(DiffuseSampler, texCoord);

    // 2. RGBをHSVに変換
    vec3 hsv = rgb2hsv(originalColor.rgb);

    // 3. 時間経過に基づいて色相(Hue)を回転させる
    // u_Timeにかける数字を大きくすると変化が速くなり、小さくするとゆっくりになります。
    // fract()を使って 0.0～1.0 の範囲に収めます。
    hsv.x = fract(hsv.x + u_Time * 0.05);

    // 4. HSVをRGBに戻す
    vec3 adjustedRGB = hsv2rgb(hsv);

    // 5. 最終的な色を出力 (アルファ値は元のまま)
    fragColor = vec4(adjustedRGB, originalColor.a);
}