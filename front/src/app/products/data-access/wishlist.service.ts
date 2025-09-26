import { Injectable, inject, signal } from "@angular/core";
import { Product } from "./product.model";
import { HttpClient } from "@angular/common/http";
import { catchError, Observable, of, tap } from "rxjs";

@Injectable({
    providedIn: "root"
}) export class WishlistService {
    private readonly http = inject(HttpClient);
    private readonly path = "http://localhost:8080/api/wishlist";
    
    private readonly _wishlist = signal<Product[]>([]);

    public readonly wishlist = this._wishlist.asReadonly();

    public get(): Observable<Product[]> {
        return this.http.get<Product[]>(this.path).pipe(
            catchError(() => {
                return of([]);
            }),
            tap((wishlist) => this._wishlist.set(wishlist)),
        );
    }

    public add(product: Product): Observable<boolean> {
        return this.http.post<boolean>(`${this.path}/${product.id}`, null).pipe(
            catchError(() => {
                return of(true);
            }),
            tap(() => this._wishlist.update(wishlist => [product, ...wishlist])),
        );
    }

    public remove(productId: number): Observable<boolean> {
        return this.http.delete<boolean>(`${this.path}/${productId}`).pipe(
            catchError(() => {
                return of(true);
            }),
            tap(() => this._wishlist.update(wishlist => {
                let firstIndex = wishlist.findIndex(prod => prod.id === productId);
                return wishlist.filter((_, index) => index !== firstIndex);
            })),
        );
    }
}