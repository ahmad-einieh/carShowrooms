import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { User } from '../../models/user.model';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-user',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './user.component.html'
})
export class UserComponent implements OnInit {
  
  users: User[] = [];
  currentPage = 0;
  pageSize = 10;
  totalItems = 0;
  totalPages = 0;

  userToDelete?: number;
  private deleteModal: any;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    this.userService.getUsers(this.currentPage, this.pageSize)
      .subscribe({

        next: (data) => {
          this.users = data.content;
          this.totalItems = data.totalElements;
          this.totalPages = data.totalPages;
        },
        error: (error) => {
          console.error('Error loading users', error);
        }
      });
  }

  changePage(page: number): void {
    if (page >= 0 && page < this.totalPages) {
      this.currentPage = page;
      this.loadUsers();
    }
  }

  getPageNumbers(): number[] {
    return Array.from({ length: this.totalPages }, (_, i) => i);
  }

  deleteUser(id: number): void {
    this.userToDelete = id;
    const modalElement = document.getElementById('userDeleteModal');
    if (modalElement) {
      this.deleteModal = new (window as any).bootstrap.Modal(modalElement);
      this.deleteModal.show();
    }
  }

  confirmDelete(): void {
    if (this.userToDelete) {
      this.userService.deleteUser(this.userToDelete)
        .subscribe({
          next: () => {
            this.deleteModal.hide();
            this.loadUsers();
          },
          error: (error) => {
            console.error('Error deleting user', error);
          }
        });
    }
  }

}
