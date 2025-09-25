import { Injectable, inject, signal } from "@angular/core";
import { Product } from "./product.model";
import { HttpClient } from "@angular/common/http";
import { catchError, Observable, of, tap } from "rxjs";

@Injectable({
    providedIn: "root"
}) export class CartService {
    private readonly http = inject(HttpClient);
    private readonly path = "/api/cart";
    
    private readonly _cart = signal<Product[]>([]);

    public readonly cart = this._cart.asReadonly();

    public get(): Observable<Product[]> {
        return this.http.get<Product[]>(this.path).pipe(
            catchError(() => {
                return of([]);
            }),
            tap((cart) => this._cart.set(cart)),
        );
    }

    public add(product: Product): Observable<boolean> {
        return this.http.post<boolean>(`${this.path}/${product.id}`, null).pipe(
            catchError(() => {
                return of(true);
            }),
            tap(() => this._cart.update(cart => [product, ...cart])),
        );
    }

    public remove(productId: number): Observable<boolean> {
        return this.http.delete<boolean>(`${this.path}/${productId}`).pipe(
            catchError(() => {
                return of(true);
            }),
            tap(() => this._cart.update(cart => {
                let firstIndex = cart.findIndex(prod => prod.id === productId);
                return cart.filter((_, index) => index !== firstIndex);
            })),
        );
    }
}