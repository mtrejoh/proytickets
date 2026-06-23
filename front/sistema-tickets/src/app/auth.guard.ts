import { inject, Injectable } from '@angular/core';
import { CanActivate, Router, UrlTree } from '@angular/router';
import { Roles } from './config/roles-config';

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
  private router = inject(Router);

  ngOnInit() {
  if (!sessionStorage.getItem('userRole')) {
    this.router.navigate(['/login']);
  }
}

  canActivate(): boolean | UrlTree {
    const isBrowser = typeof window !== 'undefined';
    if (!isBrowser) {
      // When rendering on the server, allow the route and let the client decide
      return true;
    }

    const role = sessionStorage.getItem('userRole');

    return role ? true : this.router.parseUrl('/login');
  }
}

@Injectable({ providedIn: 'root' })
export class AdminGuard implements CanActivate {
  private router = inject(Router);

  canActivate(): boolean | UrlTree {
    const isBrowser = typeof window !== 'undefined';
    if (!isBrowser) {
      // Allow server rendering; client will enforce the admin check
      return true;
    }

    const role = sessionStorage.getItem('userRole');
    
    return role === Roles.ADMIN ? true : this.router.parseUrl('/list');
  }
}
