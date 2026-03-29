import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { Router } from '@angular/router';
import { AuthService } from '../auth/auth.service';

@Component({
  selector: 'app-login-page',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css'],
})
export class LoginPageComponent {
  username = '';
  password = '';
  error?: string | null = null;

  constructor(private auth: AuthService, private router: Router) {}

  onSubmit(): void {
    this.auth.login(this.username, this.password).subscribe({
      next: () => {
        this.auth.initialize();
        this.router.navigate(['/home']);
      },
      error: (err) => {
        console.error(err);
        this.error = 'Authentication failed. Please check your credentials.';
      },
    });
  }
}
