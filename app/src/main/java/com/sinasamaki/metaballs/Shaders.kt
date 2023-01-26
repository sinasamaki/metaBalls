package com.sinasamaki.metaballs

import org.intellij.lang.annotations.Language

//@Language("AGSL")
//const val ShaderSource = """
//    uniform shader composable;
//
//    uniform float2 size;
//    uniform float cutoff;
//
//    half4 main(float2 fragCoord) {
//
//        half4 color = composable.eval(fragCoord);
//
//        float alpha = color.a;
//        float red = color.r;
//        float green = color.g;
//        float blue = color.b;
//        if (alpha > cutoff) {
//            alpha = 1.0;
//        } else {
//            alpha = 0.1;
////            red = 0.0;
////            green = 0.0;
////            blue = 0.0;
//        }
//
//        color = half4(red, green, blue, alpha);
////        color = half4(alpha, 0.0, 0.0, alpha);
//        return color;
//    }
//"""