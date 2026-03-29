import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatChipsModule } from '@angular/material/chips';
import { MatDividerModule } from '@angular/material/divider';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { catchError, map, Observable, of } from 'rxjs';
import { TokenService } from '../auth/token.service';

export interface Permission {
  value: string;
  displayName: string;
}
export interface UserEntityResponse {
  name: string;
  id: string;
  email: string;
  role: string;
  managerName: string;
  orgUnitName: string;
  orgUnitType: string;
  secondaryManagers: string[];
  permissions: Permission[];
}

@Component({
  selector: 'app-profile-view',
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule,
    MatChipsModule,
    MatIconModule,
    MatListModule,
    MatProgressSpinnerModule,
    MatDividerModule,
  ],
  templateUrl: './profile-view.html',
  styleUrl: './profile-view.css',
})
export class ProfileView implements OnInit {
  constructor(
    private http: HttpClient,
    private tokenService: TokenService,
  ) {}

  user$: Observable<UserEntityResponse | null> | null = null;
  showModal: boolean = false;
  @Input() currentImageUrl: string = '';
  @Output() closeModal = new EventEmitter<void>();
  @Output() fileUploaded = new EventEmitter<File>();

  selectedFile: File | null = null;
  previewUrl: string | ArrayBuffer | null = null;
  errorMessage: string | null = null;
  isDragOver: boolean = false;
  uploadState: 'idle' | 'uploading' | 'success' | 'error' = 'idle';

  readonly ALLOWED_TYPES = ['image/jpeg', 'image/png', 'image/webp'];
  readonly MAX_SIZE_BYTES = 5 * 1024 * 1024;

  ngOnInit(): void {
    // this.loadUserData();
    this.loadOrganizations();
  }

  loadOrganizations(): void {
    const url = `api/v1/hierarchy/organization/currentuser`;
    const token = this.tokenService.getToken();

    this.user$ = this.http
      .get<UserEntityResponse>(url, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .pipe(
        // normalize response
        map((res) => ({
          ...res,
          permissions: res.permissions ?? [],
          secondaryManagers: res.secondaryManagers ?? [],
        })),
        catchError(() => of(null)),
      );
  }

  // profile picture upload related methods
  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.handleFile(input.files[0]);
    }
  }

  onDragOver(event: DragEvent): void {
    event.preventDefault();
    this.isDragOver = true;
  }

  onDragLeave(event: DragEvent): void {
    event.preventDefault();
    this.isDragOver = false;
  }

  onDrop(event: DragEvent): void {
    event.preventDefault();
    this.isDragOver = false;
    if (event.dataTransfer?.files && event.dataTransfer.files.length > 0) {
      this.handleFile(event.dataTransfer.files[0]);
    }
  }
// validates file type and size, sets preview URL if valid, otherwise shows error message
  private handleFile(file: File): void {
    this.errorMessage = null;
    this.uploadState = 'idle';

    if (!this.ALLOWED_TYPES.includes(file.type)) {
      this.errorMessage = 'Invalid format. Use JPG, PNG, or WEBP.';
      this.resetSelection();
      return;
    }

    if (file.size > this.MAX_SIZE_BYTES) {
      this.errorMessage = 'File exceeds 5MB limit.';
      this.resetSelection();
      return;
    }

    this.selectedFile = file;
    const reader = new FileReader();
    reader.onload = () => {
      this.previewUrl = reader.result;
    };
    reader.readAsDataURL(file);
  }

  onUpload(): void {
    if (this.selectedFile) {
      this.uploadState = 'uploading';
      this.fileUploaded.emit(this.selectedFile);
    }
  }

  openModal(): void {
    this.showModal = true;
  }

  onClose(): void {
    this.showModal = false;
    this.resetSelection();
    this.uploadState = 'idle';
    this.closeModal.emit();
  }

  private resetSelection(): void {
    this.selectedFile = null;
    this.previewUrl = null;
  }
}
