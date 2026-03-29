import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { ChartModule } from 'primeng/chart';
import { TableModule } from 'primeng/table';
import { TagModule } from 'primeng/tag';
import { Observable } from 'rxjs';
// dashboard.service.ts
import { Injectable } from '@angular/core';
import { delay, of } from 'rxjs';

export interface Kpi {
  title: string;
  value: string | number;
  icon: string;
  growth: number;
  isCurrency?: boolean;
}

export interface Booking {
  id: string;
  customerName: string;
  destination: string;
  status: 'Confirmed' | 'Pending' | 'Cancelled';
  amount: number;
}

export interface DashboardData {
  kpis: {
    totalBookings: Kpi;
    revenue: Kpi;
    activeCustomers: Kpi;
    pendingPayments: Kpi;
  };
  revenueChart: any;
  distributionChart: any;
  recentBookings: Booking[];
}

@Injectable({
  providedIn: 'root',
})
export class DashboardService {
  getDashboardData(): Observable<DashboardData> {
    const mockData: DashboardData = {
      kpis: {
        totalBookings: {
          title: 'Total Bookings',
          value: 1245,
          icon: 'flight_takeoff',
          growth: 12.5,
        },
        revenue: {
          title: 'Revenue',
          value: 154300,
          icon: 'attach_money',
          growth: 8.4,
          isCurrency: true,
        },
        activeCustomers: {
          title: 'Active Customers',
          value: 892,
          icon: 'groups',
          growth: 5.2,
        },
        pendingPayments: {
          title: 'Pending Payments',
          value: 45,
          icon: 'pending_actions',
          growth: -2.1,
        },
      },
      revenueChart: {
        labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun'],
        datasets: [
          {
            label: 'Monthly Revenue ($)',
            data: [65000, 59000, 80000, 81000, 96000, 115000],
            fill: false,
            borderColor: '#3f51b5',
            tension: 0.4,
          },
        ],
      },
      distributionChart: {
        labels: ['Domestic', 'International'],
        datasets: [
          {
            data: [400, 845],
            backgroundColor: ['#ff9800', '#3f51b5'],
            hoverBackgroundColor: ['#fb8c00', '#3949ab'],
          },
        ],
      },
      recentBookings: [
        {
          id: 'TRV-1001',
          customerName: 'Alice Smith',
          destination: 'Paris, France',
          status: 'Confirmed',
          amount: 3200,
        },
        {
          id: 'TRV-1002',
          customerName: 'Bob Jones',
          destination: 'Tokyo, Japan',
          status: 'Pending',
          amount: 4500,
        },
        {
          id: 'TRV-1003',
          customerName: 'Charlie Brown',
          destination: 'New York, USA',
          status: 'Cancelled',
          amount: 1200,
        },
        {
          id: 'TRV-1004',
          customerName: 'Diana Prince',
          destination: 'Rome, Italy',
          status: 'Confirmed',
          amount: 2800,
        },
        {
          id: 'TRV-1005',
          customerName: 'Evan Wright',
          destination: 'Sydney, Australia',
          status: 'Confirmed',
          amount: 5100,
        },
      ],
    };

    return of(mockData).pipe(delay(600));
  }
}
@Component({
  selector: 'app-home',
  imports: [
    CommonModule,
    MatCardModule,
    MatIconModule,
    MatButtonModule,
    ChartModule,
    TableModule,
    TagModule,
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
  standalone: true,
})
export class HomeComponent {
  private readonly dashboardService = inject(DashboardService);

  dashboardData$!: Observable<DashboardData>;

  chartOptions = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: { position: 'bottom' },
    },
  };

  ngOnInit(): void {
    this.dashboardData$ = this.dashboardService.getDashboardData();
  }

  getSeverity(status: string): 'success' | 'warning' | 'danger' | 'info' {
    switch (status) {
      case 'Confirmed':
        return 'success';
      case 'Pending':
        return 'warning';
      case 'Cancelled':
        return 'danger';
      default:
        return 'info';
    }
  }

  trackByBookingId(index: number, booking: Booking): string {
    return booking.id;
  }
}
