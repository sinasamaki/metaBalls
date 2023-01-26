package com.sinasamaki.metaballs

import android.graphics.RenderEffect
import android.graphics.RuntimeShader
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import org.intellij.lang.annotations.Language

@Language("AGSL")
const val ShaderSource = """
    uniform shader composable;
    
    uniform float2 size;
    uniform float cutoff;
    uniform float r;
    uniform float g;
    uniform float b;
    
    half4 main(float2 fragCoord) {
    
        half4 color = composable.eval(fragCoord);
        
        float alpha = color.a;
        if (alpha > cutoff) {
            alpha = 1.0;
        } else {
            alpha = 0.0;
        }
        
        float red = color.r;
        float green = color.g;
        float blue = color.b;
        if (red == 0.0 && green == 0.0 && blue == 0.0 && alpha == 1.0) {
            red = r;
            green = g;
            blue = b;
        }
        
        color = half4(red, green, blue, alpha);
        return color;
    }
"""

@Composable
fun MetaContainer(
    modifier: Modifier = Modifier,
    cutoff: Float = .5f,
    color: Color = Color.Black,
    content: @Composable BoxScope.() -> Unit,
) {
    val metaShader = remember {
        RuntimeShader(ShaderSource)
    }
    Box(
        modifier
//            .clipToBounds()
            .onSizeChanged {
                metaShader.setFloatUniform(
                    "size",
                    it.width.toFloat(),
                    it.height.toFloat(),
                )
            }
            .graphicsLayer {
                alpha = .99f
                metaShader.setFloatUniform("cutoff", cutoff)
                metaShader.setFloatUniform("r", color.red)
                metaShader.setFloatUniform("g", color.green)
                metaShader.setFloatUniform("b", color.blue)
                renderEffect = RenderEffect
                    .createRuntimeShaderEffect(
                        metaShader, "composable"
                    )
                    .asComposeRenderEffect()
            }
                ,
        content = content,
    )
}