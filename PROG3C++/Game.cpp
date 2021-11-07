#include <SDL.h>
//#include "Sprite.h"
#include "System.h"
#include <iostream>
#include "game.h"
#include "Enemy.h"
#include "Spaceship.h"
#include "Bullet.h"
#include <string>

using namespace std;

#define FPS 160


int Game::randomNr() {
	int range = 625 - 0 + 1;
	int num = rand() % range;
	return num;
}

void Game::add(Sprite* sprite) {
	sprites.push_back(sprite);

}
void Game::spawnEnemy() {
	int range = 3 - 1 + 1;
	int num = rand() % range + 1;
	cout << "Level for enemy is: " << num << endl;
	sprites.push_back(Enemy::getInstance(randomNr(), num));
}

void Game::remove(Sprite* sprite) {
	removed.push_back(sprite);
}

bool Game::checkCollision(Sprite* a, Sprite *b) {
	int leftC1, leftC2, rightC1, rightC2, botC1, botC2, topC1, topC2;
	SDL_Rect c1 = a->getRect();
	SDL_Rect c2 = b->getRect();
	leftC1 = c1.x,
		leftC2 = c2.x,
		rightC1 = c1.x + c1.w,
		rightC2 = c2.x + c2.w;
	botC1 = c1.y + c1.h,
		botC2 = c2.y + c2.h,
		topC1 = c1.y,
		topC2 = c2.y;

	if (botC1 <= topC2)
		return false;
	if (topC1 >= botC2)
		return false;
	if (rightC1 <= leftC2)
		return false;
	if (leftC1 >= rightC2)
		return false;

	return true;
}
void Game::handleCollision(Sprite *c1, Sprite *c2) {
	if (Bullet* b = dynamic_cast<Bullet*>(c1)){
		if (Enemy* e = dynamic_cast<Enemy*>(c2)) {
			removed.push_back(b);
			e->takeDamage();
			if (e->getLives() < 1)
				removed.push_back(e);
		}
	}
	else if (Bullet* b = dynamic_cast<Bullet*>(c2)) {
		if (Enemy* e = dynamic_cast<Enemy*>(c1)) {
			removed.push_back(b);
			e->takeDamage();
			if (e->getLives() < 1)
				removed.push_back(e);
		}
	}

}
void Game::outOfWindow(Sprite* s) {
		if (Enemy* e = dynamic_cast<Enemy*>(s)) {
			if (s->getY() > windowHeigth - 30) {
				remove(s);
			}

		}
		else if (Bullet* b = dynamic_cast<Bullet*>(s)) {
			if (s->getY() < 0)
				remove(s);
		}
		else if (Spaceship* sp = dynamic_cast<Spaceship*>(s)) {
			
			if (s->getX() < 0) {
				sp->setLeft(false);
				sp->setRight(true);
			}
			else if (s->getX() > 625) {
				sp->setLeft(true);
				sp->setRight(false);
			}
			else {
				sp->setRight(true);
				sp->setLeft(true);
			}
	}
		
	
	
}

void Game::run() {
	TTF_Init();
	TTF_Font* font = TTF_OpenFont("c:/Windows/Fonts/arial.ttf", 36);
	
	bool quit = false;
	unsigned int startTime = 0, currentTime;
	Uint32 tickInterval = 1000 / FPS;
	while (!quit) {
		Uint32 nextTick = SDL_GetTicks() + tickInterval;
		SDL_Event event;
		while (SDL_PollEvent(&event)) {
			switch (event.type) {
			case SDL_QUIT: quit = true; break;
			case SDL_KEYDOWN:
				switch (event.key.keysym.sym) {
				case SDLK_LEFT:
					for (Sprite* c : sprites) {
						if (Spaceship* s = dynamic_cast<Spaceship*>(c))
							s->moveLeft();
					}

					break;
				case SDLK_RIGHT:
					for (Sprite* c : sprites) {
						if (Spaceship* s = dynamic_cast<Spaceship*>(c))
							s->moveRight();
					}
					break;
				case SDLK_UP:
					for (Sprite* c : sprites) {
						if (Spaceship* s = dynamic_cast<Spaceship*>(c))
							added.push_back(s->shoot());
					}


				}
				break;
			}
		}

		for (Sprite* c : sprites) {
			outOfWindow(c);
			c->tick();
		}

		for (Sprite* c : added) {
			sprites.push_back(c);
		}	
		added.clear();

		for (Sprite* c : removed)
			for (vector<Sprite*>::iterator i = sprites.begin();i != sprites.end();)
				if (*i == c) {
					i = sprites.erase(i);
				}
				else
					i++;
		removed.clear();


		for (std::size_t i = 0; i < sprites.size(); i++) {          //checking for collisions after each tick
			for (std::size_t j = 0; j < sprites.size(); j++) {
				if (checkCollision(sprites[i], sprites[j]))
					handleCollision(sprites[i], sprites[j]);
			}
		}
		
		SDL_SetRenderDrawColor(sys.ren, 255, 255, 255, 255);
		SDL_RenderClear(sys.ren);
		
		for (Sprite* c : sprites)
			c->draw();
		SDL_RenderPresent(sys.ren);
		currentTime = SDL_GetTicks();
		if (currentTime > startTime + 3000) { //Spawn enemies every 3 secounds
			spawnEnemy();
			startTime = currentTime;
		}
		int delay = nextTick - SDL_GetTicks();
		if (delay > 0)
			SDL_Delay(delay);
	} // yttre while
	
	for (Sprite* s : sprites) //deleting pointers in vector before ending program.
		delete s;
	sprites.clear();
	sys.~System();
}



