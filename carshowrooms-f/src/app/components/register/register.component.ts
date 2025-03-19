import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule, FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    RouterLink
  ]
})
export class RegisterComponent implements OnInit {
  registerFrom!: FormGroup;
  isSubmitted = false;
  errorMessage = '';

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.registerFrom = this.formBuilder.group({
      email: ["", Validators.required],
      username: ['', [Validators.required]],
      password: ['', [Validators.required]],
      confirmPassword: ['', [Validators.required]],
      fullName: ['']
    });
  }

  // Getter for easy access to form fields
  get f() { return this.registerFrom.controls; }

  onSubmit(): void {
    this.isSubmitted = true;
    
    if (this.registerFrom.invalid) {
      return;
    }

    if (this.registerFrom.value.password !== this.registerFrom.value.confirmPassword) {
      this.errorMessage = "Passwords do not match.";
      return;
    }

    this.authService.register(
      this.registerFrom.value.username,
      this.registerFrom.value.email,
      this.registerFrom.value.password,
      this.registerFrom.value.fullName,
    ).subscribe({
      next: () => {
        this.router.navigate(['/login']);
      },
      error: (error) => {
        this.errorMessage = error?.error?.message || 'Registration failed. Please check your info.';
      }
    });
  }
}