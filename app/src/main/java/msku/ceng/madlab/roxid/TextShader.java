package msku.ceng.madlab.roxid;

import android.graphics.Color;

import android.graphics.LinearGradient;
import android.widget.Button;
import android.widget.TextView;
import android.graphics.Shader;

public class TextShader {


    public void shaderStart(TextView textView,String startColor,String endColor){

        Shader textShader = new LinearGradient(0, 0, 0, textView.getTextSize(),
                new int[]{
                        Color.parseColor(startColor), // Start color
                        Color.parseColor(endColor)  // End color
                },
                null,
                Shader.TileMode.CLAMP);
        textView.getPaint().setShader(textShader);

    }
}
