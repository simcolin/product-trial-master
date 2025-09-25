import { Component, computed, inject } from '@angular/core';
import { CartService } from 'app/products/data-access/cart.service';
import { Product } from 'app/products/data-access/product.model';
import { ButtonModule } from 'primeng/button';
import { TableModule } from "primeng/table";

@Component({
  selector: 'app-cart-list',
  standalone: true,
  imports: [TableModule, ButtonModule],
  templateUrl: './cart-list.component.html',
  styleUrl: './cart-list.component.scss'
})
export class CartListComponent {
  private cartService = inject(CartService);

  public readonly cart = this.cartService.cart;

  public readonly totalPrice = computed(() => this.cart().reduce((sum, product) => sum + product.price, 0));

  public onDelete(product: Product) {
    this.cartService.remove(product.id!).subscribe();
  }
}
