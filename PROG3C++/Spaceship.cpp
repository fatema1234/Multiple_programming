#include "Spaceship.h"
#include <iostream>
#include "Game.h"

Spaceship* Spaceship::getInstance() {
	return new Spaceship();
}

Spaceship::Spaceship() : Sprite(300, 400, 100, 100), left(true), right(true) {
	texture = IMG_LoadTexture(sys.ren, "C:/Users/didri/source/repos/Game/space_ship.png");
}
Spaceship::~Spaceship() {
	SDL_DestroyTexture(texture);
}
void Spaceship::draw() const {
	SDL_RenderCopy(sys.ren, texture, NULL, &getRect());
}

void Spaceship::moveLeft() {
	std::cout << "from moveleft, rect.x is = " << rect.x << "Left bool = " << left << std::endl;
	if (left)
		rect.x = rect.x - 8;
}
void Spaceship::moveRight() {
	std::cout << "from moveRight, rect.x is = " << rect.x << "right bool = " << right << std::endl;
	if (right)
		rect.x = rect.x + 8;
}

void Spaceship::tick() {

}
void Spaceship::setLeft(bool b) {
	left = b;
}
void Spaceship::setRight(bool b) {
	right = b;
}

Bullet* Spaceship::shoot() {
	std::cout << "invoked from Spaceship::shoot" << std::endl;
	return Bullet::getInstance(rect.x+29);
	
	

}

void Spaceship::takeDamage() {
	lives--;
}
int Spaceship::getLives() {
	return lives;
}


