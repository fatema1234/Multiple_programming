#include "System.h"
System::System() {
	SDL_Init(SDL_INIT_EVERYTHING);
	TTF_Init();
	win = SDL_CreateWindow("Space Invaders - The worse version in the world", 100, 100, 700, 500, 0);
	TTF_Font* font = TTF_OpenFont("c:/Windows/Fonts/arial.ttf", 36);
	ren = SDL_CreateRenderer(win, -1, 0);
	Mix_OpenAudio(20050, AUDIO_S16SYS, 2, 4096);
	musik = Mix_LoadWAV("c:/Users/didri/source/repos/Game/my_computer.wav");
	Mix_PlayChannel(-1, musik, -1);
}

System::~System() {
	Mix_FreeChunk(musik);
	Mix_CloseAudio();
	SDL_DestroyWindow(win);
	SDL_DestroyRenderer(ren);
	TTF_CloseFont(font);
	TTF_Quit();
	SDL_Quit();
}

System sys;
