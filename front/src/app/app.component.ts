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

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.scss"],
  standalone: true,
  imports: [RouterModule, SplitterModule, ToolbarModule, PanelMenuComponent, BadgeModule, CartListComponent, DialogModule, ButtonModule],
})
export class AppComponent implements OnInit {
  private readonly cartService = inject(CartService);

  public readonly cartSize = computed(() => this.cartService.cart().length);

  public isCartVisible = false;

  public title = "ALTEN SHOP";

  ngOnInit(): void {
    this.cartService.get().subscribe();
  }

  public showCart() {
    this.isCartVisible = true;
  }
}
