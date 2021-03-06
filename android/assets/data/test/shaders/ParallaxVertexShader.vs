    attribute vec4 a_position;
	attribute vec4 a_color;
	attribute vec2 a_texCoords;
	uniform mat4 u_worldView;
	uniform mat4 u_projTrans;
	varying vec4 v_color;
	varying vec2 v_texCoords;
	varying vec2 t0c, t1c, t2c, t3c;
	uniform float travelDistance;
	void main()                  
	{                            
	    v_color = a_color; 
	    v_texCoords = a_texCoords; 
	    gl_Position = a_position;
	    vec2 texCoord = v_texCoords;
	    t0c = texCoord;
	    t0c.x = t0c.x + travelDistance / 3.0;
	    t1c = texCoord;
	    t1c.x = t1c.x + travelDistance / 2.5;
	    t2c = v_texCoords;
	    t2c.x = t2c.x + travelDistance / 1.8;
	    t3c = v_texCoords;
	    t3c.x = t3c.x + travelDistance / 1.3;
	}