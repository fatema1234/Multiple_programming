#include "Enemy.h"
#include <iostream>

Enemy* Enemy::getInstance(int x, int l) {
	return new Enemy(x, l);
}
Enemy::Enemy(int x, int l) : Sprite(x, 0, 75, 75) {
	texture = IMG_LoadTexture(sys.ren, "C:/Users/didri/source/repos/Game/enemy.png");
	//texture = IMG_LoadTexture(sys.ren, "enemy.png");
	level = l;
	lives = 1;
	
}
Enemy::~Enemy() {
	SDL_DestroyTexture(texture);
}
void Enemy::draw() const {
	SDL_RenderCopy(sys.ren, texture, NULL, &getRect());
}
void Enemy::tick() {
	//std::cout << "Level from enemy tick is" << level << std::endl;
	counter++;
	if (level == 1) {
		if (counter % 5 == 0) {
			rect.y++;
		}
	}
	else if (level == 2) {
		if (counter % 3 == 0) {
			rect.y++;
		}
	}else{
		if (counter % 2 == 0) {
			rect.y++;
		}
	}


}

void Enemy::takeDamage() {
	lives--;
}
int Enemy::getLives() {
	return lives;
}
int Enemy::getLevel() {
	return level;
}
