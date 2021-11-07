#include "Sprite.h"
#include <SDL.h>
#include <SDL_image.h>
#include "System.h"

int Sprite::getX() {
	return rect.x;
}
int Sprite::getY() {
	return rect.y;
}
SDL_Rect Sprite::getRect() const{
	return rect;
}
Sprite::Sprite(int x, int y, int w, int h) : rect{ x,y,w,h } {}

/*int main(int argc, char** argv) {
	/*Pistol* pistol = new Pistol();
	Spaceship* spaceship = new Spaceship();
	Enemy* enemy1 = new Enemy(100);
	Enemy* enemy2 = new Enemy(400);
	//ses.add(pistol);
	ses.add(spaceship);
	ses.add(enemy1);
	ses.add(enemy2);

	ses.run();
	return 0;
}*/