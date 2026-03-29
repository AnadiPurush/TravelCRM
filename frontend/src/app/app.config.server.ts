import { ApplicationConfig } from '@angular/core';
import { provideServerRendering } from '@angular/platform-server';
import { appConfig } from './app.config';
import * as PrimeThemes from '@primeuix/themes';
import { providePrimeNG } from 'primeng/config';

export const serverConfig: ApplicationConfig = {
  providers: [
    ...(appConfig.providers || []), 
    provideServerRendering(),
    providePrimeNG({
      theme: {
        preset: PrimeThemes,
      },
    }),
  ],
};
