import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-query-table-component',
  imports: [CommonModule],
  templateUrl: './query-table-component.html',
  styleUrl: './query-table-component.css',
  standalone: true,
})
export class QueryTableComponent {
  @Input() queries: any[] = [];
  @Input() page: number = 0;
  @Input() totalPages: number = 0;

  @Output() pageChange = new EventEmitter<number>();
  @Output() view = new EventEmitter<any>();

  changePage(newPage: number): void {
    if (newPage >= 0 && newPage < this.totalPages) {
      this.pageChange.emit(newPage);
    }
  }

  onView(query: any): void {
    this.view.emit(query);
  }

  trackBySerialNumber(index: number, item: any): string {
    return item.serialNumber;
  }
}
