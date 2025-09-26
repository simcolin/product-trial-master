import {
  Component,
  computed,
  effect,
  inject,
  OnInit,
  signal,
} from "@angular/core";
import { RouterModule } from "@angular/router";
import { SplitterModule } from 'primeng/splitter';
import { ToolbarModule } from 'primeng/toolbar';
import { BadgeModule } from 'primeng/badge';
import { PanelMenuComponent } from "./shared/ui/panel-menu/panel-menu.component";
import { CartService } from "./products/data-access/cart.service";
import { CartListComponent } from "./products/features/cart-list/cart-list.component";
import { DialogModule } from "primeng/dialog";
import { ButtonModule } from "primeng/button";
import { WishlistService } from "./products/data-access/wishlist.service";
import { WishlistListComponent } from "./products/features/wishlist-list/wishlist-list.component";

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.scss"],
  standalone: true,
  imports: [RouterModule, SplitterModule, ToolbarModule, PanelMenuComponent, BadgeModule, CartListComponent, DialogModule, ButtonModule, WishlistListComponent],
})
export class AppComponent implements OnInit {
  private readonly cartService = inject(CartService);
  private readonly wishlistService = inject(WishlistService);

  public readonly cartSize = computed(() => this.cartService.cart().length);
  public readonly wishlistSize = computed(() => this.wishlistService.wishlist().length);

  public isCartVisible = false;
  public isWishlistVisible = false;

  public title = "ALTEN SHOP";

  ngOnInit(): void {
    fetch("http://localhost:8080/token", {
      method: "POST",
      body: JSON.stringify({ email: "admin@admin.com", password: "1234" }),
      headers: { "Content-Type": "application/json" },
    }).then(async response => {
      if (response.ok) {
        localStorage.setItem("token", await response.text());
        this.cartService.get().subscribe();
        this.wishlistService.get().subscribe();
      }
    });
  }

  public showCart() {
    this.isCartVisible = true;
  }

  public showWishlist() {
    this.isWishlistVisible = true;
  }
}
