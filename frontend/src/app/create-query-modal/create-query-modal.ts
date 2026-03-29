import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import {
  FormArray,
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';

@Component({
  selector: 'app-create-query-modal',
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './create-query-modal.html',
  styleUrl: './create-query-modal.css',
  standalone: true,
})
export class CreateQueryModal implements OnInit {
  @Input() statuses: any[] = [];
  @Input() priorities: any[] = [];
  @Output() close = new EventEmitter<void>();
  @Output() success = new EventEmitter<void>();

  queryForm!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
  ) {}
  trackByName(index: number, item: any): string {
    return item.name;
  }
  ngOnInit(): void {
    this.queryForm = this.fb.group({
      requesterName: ['', Validators.required],
      contactNo: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      fromLocation: ['', Validators.required],
      fromDate: ['', Validators.required],
      toDate: ['', Validators.required],
      quotedPrice: [0, Validators.required],
      queriesStatus: ['', Validators.required],
      queriesPriority: ['', Validators.required],
      destination: this.fb.control<string[]>([], Validators.required),
      requiredServices: this.fb.control<string[]>([], Validators.required),
    });
  }

  get destinationControls(): FormArray {
    return this.queryForm.get('destination') as FormArray;
  }

  get serviceControls(): FormArray {
    return this.queryForm.get('requiredServices') as FormArray;
  }

  addDestination(): void {
    this.destinationControls.push(this.fb.control('', Validators.required));
  }

  addService(): void {
    this.serviceControls.push(this.fb.control('', Validators.required));
  }

  onClose(): void {
    this.close.emit();
  }

  onSubmit(): void {
    if (this.queryForm.valid) {
      this.http
        .post('api/v1/general/quariesSave', this.queryForm.value)
        .subscribe({
          next: () => this.success.emit(),
          error: (err) => console.error(err),
        });
    }
  }
  // fetchDropdowns(): void {
  //   this.http
  //     .get<any[]>('api/v1/response/query/status/response')
  //     .subscribe((data) => (this.statuses = data));
  //   this.http
  //     .get<any[]>('api/v1/response/query/priorities/status/response')
  //     .subscribe((data) => (this.priorities = data));
  // }
}
