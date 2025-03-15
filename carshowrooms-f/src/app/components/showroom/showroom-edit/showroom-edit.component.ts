import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ShowroomService } from '../../../services/showroom.service';
import { Showroom } from '../../../models/showroom.model';

@Component({
  selector: 'app-showroom-edit',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './showroom-edit.component.html',
  styleUrls: ['./showroom-edit.component.css']
})
export class ShowroomEditComponent implements OnInit {
  showroomId!: number;
  showroom?: Showroom;
  showroomForm!: FormGroup;
  submitted = false;

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private showroomService: ShowroomService
  ) { }

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    this.showroomId = idParam ? +idParam : 0;
    
    this.showroomForm = this.formBuilder.group({
      managerName: ['', [Validators.maxLength(100)]],
      contactNumber: ['', [Validators.required, Validators.pattern('^[0-9]{1,15}$')]],
      address: ['', [Validators.maxLength(255)]]
    });
    
    this.loadShowroom();
  }

  get f() { return this.showroomForm.controls; }

  loadShowroom(): void {
    this.showroomService.getShowroomById(this.showroomId)
      .subscribe({
        next: (data) => {
          this.showroom = data;
          this.showroomForm.patchValue({
            managerName: data.managerName,
            contactNumber: data.contactNumber,
            address: data.address
          });
        },
        error: (error) => {
          console.error('Error loading showroom', error);
        }
      });
  }

  onSubmit(): void {
    this.submitted = true;

    if (this.showroomForm.invalid) {
      return;
    }

    this.showroomService.updateShowroom(this.showroomId, this.showroomForm.value)
      .subscribe({
        next: (response) => {
          this.router.navigate(['/showrooms', this.showroomId]);
        },
        error: (error) => {
          console.error('Error updating showroom', error);
        }
      });
  }
}