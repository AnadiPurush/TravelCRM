import { CommonModule, DatePipe } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-query-view-modal',
  imports: [DatePipe,CommonModule],
  templateUrl: './query-view-modal.html',
  styleUrl: './query-view-modal.css',
})
export class QueryViewModal {
  @Input() query: any;
  @Output() close = new EventEmitter<void>();

  onClose(): void {
    this.close.emit();
  }
}
