import { Injectable, PLATFORM_ID, inject } from '@angular/core';
import { Router, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';
import { isPlatformBrowser } from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard {
  private authService = inject(AuthService);
  private router = inject(Router);
  private platformId = inject(PLATFORM_ID);
  
  canActivate(): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    if (!isPlatformBrowser(this.platformId)) {
      return true;
    }
    
    if (this.authService.isLoggedIn()) {
      return true;
    }
    
    return this.router.createUrlTree(['/login']);
  }
}