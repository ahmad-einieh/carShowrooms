import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'custom-error',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.css']
})
export class ErrorComponent {
  errorMessage = 'The page you are looking for does not exist.';
}