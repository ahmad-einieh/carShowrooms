import { Component } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { AuthService } from '../../../services/auth.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'custom-header',
  standalone: true,
  imports: [CommonModule,RouterLink, RouterLinkActive],
  templateUrl: './header.component.html'
})
export class HeaderComponent  {

  constructor(
    private authService: AuthService,
    private router: Router
  ) { }


  onLogout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  isLoggedIn() {
    return this.authService.isLoggedIn();
  }


  isAdmin() {
    return this.authService.isAdmin();
  }
}