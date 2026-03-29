import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Subject, debounceTime, distinctUntilChanged } from 'rxjs';
import { QueryTableComponent } from "../query-table-component/query-table-component";
import { QueryViewModal } from "../query-view-modal/query-view-modal";
import { CreateQueryModal } from "../create-query-modal/create-query-modal";

@Component({
  selector: 'app-quaries',
  imports: [CommonModule, FormsModule, QueryTableComponent, QueryViewModal, CreateQueryModal],
  templateUrl: './quaries.component.html',
  styleUrl: './quaries.component.css',
  standalone: true,
})
export class QuariesComponent implements OnInit {
  queries: any[] = [];
  statuses: any[] = [];
  priorities: any[] = [];

  currentPage = 0;
  pageSize = 10;
  totalPages = 0;
  totalElements = 0;

  filters: any = {
    requesterName: '',
    contactNo: '',
    email: '',
    queriesStatus: '',
    queriesPriority: '',
    destination: [],
    fromLocation: '',
    toLocation: '',
  };

  isViewModalOpen = false;
  isCreateModalOpen = false;
  selectedQuery: any = null;

  private filterSubject = new Subject<void>();

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.fetchDropdowns();
    this.fetchQueries();

    this.filterSubject
      .pipe(debounceTime(300), distinctUntilChanged())
      .subscribe(() => {
        this.currentPage = 0;
        this.fetchQueries();
      });
  }

  fetchDropdowns(): void {
    this.http
      .get<any[]>('api/v1/response/query/status/response')
      .subscribe((data) => (this.statuses = data));
    this.http
      .get<any[]>('api/v1/response/query/priorities/status/response')
      .subscribe((data) => (this.priorities = data));
  }

  fetchQueries(): void {
    let url = `api/v1/return/Quaries?page=${this.currentPage}&size=${this.pageSize}`;

    // Append filters
    Object.keys(this.filters).forEach((key) => {
      if (this.filters[key] && this.filters[key].length > 0) {
        url += `&${key}=${this.filters[key]}`;
      }
    });

    this.http.get<any>(url).subscribe((response) => {
      this.queries = response.content;
      this.totalPages = response.totalPages;
      this.totalElements = response.totalElements;
    });
  }

  onFilterChange(): void {
    this.filterSubject.next();
  }

  onPageChange(page: number): void {
    this.currentPage = page;
    this.fetchQueries();
  }

  openViewModal(query: any): void {
    this.selectedQuery = query;
    this.isViewModalOpen = true;
  }

  openCreateModal(): void {
    this.isCreateModalOpen = true;
  }

  closeModals(): void {
    this.isViewModalOpen = false;
    this.isCreateModalOpen = false;
    this.selectedQuery = null;
  }

  onQueryCreated(): void {
    this.closeModals();
    this.currentPage = 0;
    this.fetchQueries();
  }

  trackByName(index: number, item: any): string {
    return item.name;
  }
}
