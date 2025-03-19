import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { CarService } from '../../../services/car.service';
import { ShowroomService } from '../../../services/showroom.service';
import { ShowroomListItem } from '../../../models/showroom.model';

@Component({
  selector: 'app-car-create',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './car-create.component.html'
})
export class CarCreateComponent implements OnInit {
  carForm!: FormGroup;
  showrooms: ShowroomListItem[] = [];
  submitted = false;

  constructor(
    private formBuilder: FormBuilder,
    private carService: CarService,
    private showroomService: ShowroomService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.carForm = this.formBuilder.group({
      showroomId: ['', [Validators.required]],
      vin: ['', [Validators.required, Validators.maxLength(25)]],
      maker: ['', [Validators.required, Validators.maxLength(25)]],
      model: ['', [Validators.required, Validators.maxLength(25)]],
      modelYear: ['', [Validators.required, Validators.max(9999)]],
      price: ['', [Validators.required]]
    });
    
    this.loadShowrooms();
  }

  get f() { return this.carForm.controls; }

  loadShowrooms(): void {
    this.showroomService.getShowrooms(0, 100)
      .subscribe({
        next: (data) => {
          this.showrooms = data.content;
        },
        error: (error) => {
          console.error('Error loading showrooms', error);
        }
      });
  }

  onSubmit(): void {
    this.submitted = true;

    if (this.carForm.invalid) {
      return;
    }

    const formValues = this.carForm.value;
    const showroomId = formValues.showroomId;
    
    delete formValues.showroomId;

    this.carService.createCar(showroomId, formValues)
      .subscribe({
        next: (response) => {
          this.router.navigate(['/cars']);
        },
        error: (error) => {
          console.error('Error creating car', error);
        }
      });
  }
}