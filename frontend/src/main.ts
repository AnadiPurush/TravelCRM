import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { bootstrapApplication } from '@angular/platform-browser';
import { providePrimeNG } from 'primeng/config';
import { AppComponent } from './app/app.component';
import { appConfig } from './app/app.config';
import { Interceptor } from './app/auth/interceptor';

bootstrapApplication(AppComponent, {
  ...appConfig,
  providers: [
    ...(appConfig.providers || []),

    provideHttpClient(withInterceptors([Interceptor])),

    providePrimeNG({
      theme: {
        options: {
          darkModeSelector: 'body',
          cssLayer: false,
        },
      },
      ripple: true,
    }),
  ],
}).catch((err) => console.error(err));
