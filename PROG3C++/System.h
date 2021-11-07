#ifndef SYSTEM_H
#define SYSTEM_H
#include <SDL.h>
#include <SDL_mixer.h>
#include <SDL_ttf.h>

struct System
{
	System();
	~System();

	SDL_Window* win;
	SDL_Renderer* ren;
	Mix_Chunk* musik;
	TTF_Font* font;
};

extern System sys;

#endif


