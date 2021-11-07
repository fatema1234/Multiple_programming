#include "Bullet.h"


Bullet* Bullet::getInstance(int x) {
	return new Bullet(x);
}
Bullet::Bullet(int x) : Sprite(x, 350, 40, 40) {
	texture = IMG_LoadTexture(sys.ren, "C:/Users/didri/source/repos/Game/black_dot.png");
}
Bullet::~Bullet() {
	SDL_DestroyTexture(texture);
}
void Bullet::draw() const {
	SDL_RenderCopy(sys.ren, texture, NULL, &getRect());
}
void Bullet::tick() {
	rect.y--;
}


