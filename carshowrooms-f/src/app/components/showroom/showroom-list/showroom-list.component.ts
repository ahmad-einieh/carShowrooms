import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { ShowroomService } from '../../../services/showroom.service';
import { ShowroomListItem } from '../../../models/showroom.model';

@Component({
  selector: 'app-showroom-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './showroom-list.component.html'
})
export class ShowroomListComponent implements OnInit {
  showrooms: ShowroomListItem[] = [];
  currentPage = 0;
  pageSize = 10;
  totalItems = 0;
  totalPages = 0;
  
  // For delete confirmation modal
  showroomToDelete?: number;
  private deleteModal: any;

  constructor(private showroomService: ShowroomService) { }

  ngOnInit(): void {
    this.loadShowrooms();
  }

  loadShowrooms(): void {
    this.showroomService.getShowrooms(this.currentPage, this.pageSize)
      .subscribe({
        next: (data) => {
          this.showrooms = data.content;
          this.totalItems = data.totalElements;
          this.totalPages = data.totalPages;
        },
        error: (error) => {
          console.error('Error loading showrooms', error);
        }
      });
  }

  changePage(page: number): void {
    if (page >= 0 && page < this.totalPages) {
      this.currentPage = page;
      this.loadShowrooms();
    }
  }

  getPageNumbers(): number[] {
    return Array.from({ length: this.totalPages }, (_, i) => i);
  }

  deleteShowroom(id: number): void {
    this.showroomToDelete = id;
    const modalElement = document.getElementById('deleteModal');
    if (modalElement) {
      this.deleteModal = new (window as any).bootstrap.Modal(modalElement);
      this.deleteModal.show();
    }
  }

  confirmDelete(): void {
    if (this.showroomToDelete) {
      this.showroomService.deleteShowroom(this.showroomToDelete)
        .subscribe({
          next: () => {
            this.deleteModal.hide();
            this.loadShowrooms();
          },
          error: (error) => {
            console.error('Error deleting showroom', error);
          }
        });
    }
  }
}