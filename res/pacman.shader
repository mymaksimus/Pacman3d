#version 150

in vec3 vIn;
in vec2 texCoord;

uniform mat4 camera;
uniform mat4 model;

out vec2 fTexCoord;

void main(){
	fTexCoord = texCoord;
	gl_Position = camera * model * vec4(vIn, 1);
}

###

#version 150 

in vec2 fTexCoord;

uniform sampler2D texture;

out vec4 color;

void main(){
	vec4 texColor = texture2D(texture, fTexCoord);	
	//float gray = texColor.x * 0.8 + texColor.y * 0.1+ texColor.z * 0.1;
	color = texColor;//vec4(gray, gray, gray, 1);
}