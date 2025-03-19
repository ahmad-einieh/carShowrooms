import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { CarService } from '../../../services/car.service';
import { CarListItem } from '../../../models/car.model';
import { CurrencyPipe } from '@angular/common';

@Component({
  selector: 'app-car-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, CurrencyPipe],
  templateUrl: './car-list.component.html'
})
export class CarListComponent implements OnInit {
  cars: CarListItem[] = [];
  currentPage = 0;
  pageSize = 10;
  totalItems = 0;
  totalPages = 0;
  
  filters: Record<string, any> = {
    vin: '',
    maker: '',
    model: '',
    modelYear: null,
    carShowroomName: ''
  };

  constructor(private carService: CarService) { }

  ngOnInit(): void {
    this.loadCars();
  }

  loadCars(): void {
    const cleanFilters: Record<string, any> = {};
    Object.keys(this.filters).forEach(key => {
      if (this.filters[key] !== null && this.filters[key] !== '') {
        cleanFilters[key] = this.filters[key];
      }
    });

    this.carService.getCars(this.currentPage, this.pageSize, cleanFilters)
      .subscribe({
        next: (data) => {
          this.cars = data.content;
          this.totalItems = data.totalElements;
          this.totalPages = data.totalPages;
        },
        error: (error) => {
          console.error('Error loading cars', error);
        }
      });
  }

  changePage(page: number): void {
    if (page >= 0 && page < this.totalPages) {
      this.currentPage = page;
      this.loadCars();
    }
  }

  getPageNumbers(): number[] {
    return Array.from({ length: this.totalPages }, (_, i) => i);
  }
}