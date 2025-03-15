import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { ShowroomService } from '../../../services/showroom.service';

@Component({
  selector: 'app-showroom-create',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './showroom-create.component.html',
  styleUrls: ['./showroom-create.component.css']
})
export class ShowroomCreateComponent implements OnInit {
  showroomForm!: FormGroup;
  submitted = false;

  constructor(
    private formBuilder: FormBuilder,
    private showroomService: ShowroomService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.showroomForm = this.formBuilder.group({
      name: ['', [Validators.required, Validators.maxLength(100)]],
      commercialRegistrationNumber: ['', [Validators.required, Validators.pattern('^[0-9]{10}$')]],
      managerName: ['', [Validators.maxLength(100)]],
      contactNumber: ['', [Validators.required, Validators.pattern('^[0-9]{1,15}$')]],
      address: ['', [Validators.maxLength(255)]]
    });
  }

  get f(): any { return this.showroomForm.controls; }

  onSubmit(): void {
    this.submitted = true;

    if (this.showroomForm.invalid) {
      return;
    }

    this.showroomService.createShowroom(this.showroomForm.value)
      .subscribe({
        next: (response) => {
          this.router.navigate(['/showrooms']);
        },
        error: (error) => {
          console.error('Error creating showroom', error);
        }
      });
  }
}