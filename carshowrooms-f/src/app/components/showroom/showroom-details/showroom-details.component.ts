import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { ShowroomService } from '../../../services/showroom.service';
import { CarService } from '../../../services/car.service';
import { Showroom } from '../../../models/showroom.model';
import { Car } from '../../../models/car.model';
import { CurrencyPipe } from '@angular/common';

@Component({
  selector: 'app-showroom-details',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule, RouterLink, CurrencyPipe],
  templateUrl: './showroom-details.component.html',
  styleUrls: ['./showroom-details.component.css']
})
export class ShowroomDetailsComponent implements OnInit {
  showroomId!: number;
  showroom?: Showroom;
  cars: Car[] = [];
  carForm!: FormGroup;
  isCreateCarLoading: boolean = false;

  // Pagination for cars
  currentCarPage = 0;
  carPageSize = 10;
  totalCars = 0;
  totalCarPages = 0;
  
  // Filters
  filters = {
    maker: '',
    model: '',
    modelYear: null as number | null
  };
  
  private addCarModal: any;

  constructor(
    private route: ActivatedRoute,
    private formBuilder: FormBuilder,
    private showroomService: ShowroomService,
    private carService: CarService
  ) { }

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    this.showroomId = idParam ? +idParam : 0;
    
    this.loadShowroom();
    this.loadCars();
    
    this.carForm = this.formBuilder.group({
      vin: ['', [Validators.required, Validators.maxLength(25)]],
      maker: ['', [Validators.required, Validators.maxLength(25)]],
      model: ['', [Validators.required, Validators.maxLength(25)]],
      modelYear: ['', [Validators.required, Validators.max(9999)]],
      price: ['', [Validators.required]]
    });
  }

  get c() { return this.carForm.controls; }

  loadShowroom(): void {
    this.showroomService.getShowroomById(this.showroomId)
      .subscribe({
        next: (data) => {
          this.showroom = data;
        },
        error: (error) => {
          console.error('Error loading showroom', error);
        }
      });
  }

  loadCars(): void {
    this.carService.getCarsByShowroom(this.showroomId, this.currentCarPage, this.carPageSize)
      .subscribe({
        next: (data) => {
          this.cars = data.content;
          this.totalCars = data.totalElements;
          this.totalCarPages = data.totalPages;
        },
        error: (error) => {
          console.error('Error loading cars', error);
        }
      });
  }

  changeCarPage(page: number): void {
    if (page >= 0 && page < this.totalCarPages) {
      this.currentCarPage = page;
      this.loadCars();
    }
  }

  getCarPageNumbers(): number[] {
    return Array.from({ length: this.totalCarPages }, (_, i) => i);
  }

  openAddCarModal(): void {
    this.carForm.reset();
    const modalElement = document.getElementById('addCarModal');
    if (modalElement) {
      // Use any to bypass type checking for now
      this.addCarModal = new (window as any).bootstrap.Modal(modalElement);
      this.addCarModal.show();
    }
  }

  addCar(): void {
    if (this.carForm.invalid || this.isCreateCarLoading) {
      return;
    }

    const car: Car = {
      ...this.carForm.value,
      showroomId: this.showroomId
    };
    this.isCreateCarLoading = true;
    this.carService.createCar(this.showroomId, car)
      .subscribe({
        next: (response) => {
          this.addCarModal.hide();
          this.loadCars();
          this.isCreateCarLoading = false;
        },
        error: (error) => {
          console.error('Error adding car', error);
        }
      });
  }
}