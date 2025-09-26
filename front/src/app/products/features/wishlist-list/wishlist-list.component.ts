import { Component, inject } from '@angular/core';
import { Product } from 'app/products/data-access/product.model';
import { WishlistService } from 'app/products/data-access/wishlist.service';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';

@Component({
  selector: 'app-wishlist-list',
  standalone: true,
  imports: [TableModule, ButtonModule],
  templateUrl: './wishlist-list.component.html',
  styleUrl: './wishlist-list.component.scss'
})
export class WishlistListComponent {
  private wishlistService = inject(WishlistService);

  public readonly wishlist = this.wishlistService.wishlist;

  public onDelete(product: Product) {
    this.wishlistService.remove(product.id!).subscribe();
  }
}
