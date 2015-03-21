package com.magnetideas.loaders;

/**
 * @author Rowland
 *
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;


@SuppressWarnings("unused")
public final class ShaderLoader {

	public static String BASEPATH = "";
	public static boolean pedantic = true;

	public ShaderLoader()
	{

	}

	public static ShaderProgram fromFile(String vertexFileName, String fragmentFileName)
	{
		return ShaderLoader.fromFile(vertexFileName, fragmentFileName, "");
	}

	public static ShaderProgram fromFile(String vertexFileName, String fragmentFileName, String defines)
	{
		String log = "\"" + vertexFileName + "/" + fragmentFileName + "\"";

		if (defines.length() > 0)
		{
			log += " w/ (" + defines.replace("\n", ", ") + ")";
		}

		log += "...";
		Gdx.app.log("ShaderLoader", "Compiling " + log);

		String vpSrc = Gdx.files.internal(BASEPATH + vertexFileName + ".vs").readString();
		String fpSrc = Gdx.files.internal(BASEPATH + fragmentFileName + ".fs").readString();

		ShaderProgram program = ShaderLoader.fromString(vpSrc, fpSrc, vertexFileName, fragmentFileName, defines);

		return program;
	}

	public static ShaderProgram fromString(String vertex, String fragment, String vertexName, String fragmentName)
	{
		return ShaderLoader.fromString(vertex, fragment, vertexName, fragmentName, "");
	}

	public static ShaderProgram fromString(String vertex, String fragment, String vertexName, String fragmentName, String defines)
	{
		ShaderProgram.pedantic = ShaderLoader.pedantic;
		ShaderProgram shader = new ShaderProgram(defines + "\n" + vertex, defines + "\n" + fragment);

        String log = shader.getLog();

		if (!shader.isCompiled())
		{
			throw new GdxRuntimeException(log);
		}
		if (log!=null && log.length()!=0)
		{
			Gdx.app.log("shader log", log);
		}

		return shader;
	}

	public ShaderProgram createShader(String vertexShader, String horizontalShader, ShaderType blurType)
	{
		pedantic = false;
		BASEPATH = "data/shaders/";

		ShaderProgram blurShader = ShaderLoader.fromFile(vertexShader, horizontalShader);

		switch (blurType)
		{
		case GAUSSIANBLUR:
			blurShader.begin();
			blurShader.setUniformf("dir", 0.5f, 0.5f);
			blurShader.setUniformf("resolution", Gdx.graphics.getWidth());
			blurShader.setUniformf("radius", 2f);
			blurShader.setUniformi("u_texture", 0);
			blurShader.end();
			break;
        case VIGNETTE:

			break;

		default:
			break;
		}
		return blurShader;
	}

}
