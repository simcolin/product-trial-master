import { Routes } from '@angular/router';
import { HomeComponent } from './shared/features/home/home.component';
import { ContactComponent } from './shared/features/contact/contact.component';

export const routes: Routes = [
  {
    path: "home",
    component: HomeComponent,
  },
  {
    path: "contact",
    component: ContactComponent,
  },
  {
    path: "products",
    loadChildren: () =>
      import("./products/products.routes").then((m) => m.PRODUCTS_ROUTES)
  },
  { path: "", redirectTo: "home", pathMatch: "full" },
];
