import { Component, inject, signal, ViewEncapsulation } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { ToastModule } from 'primeng/toast';
import { InputTextModule } from 'primeng/inputtext';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-contact',
  standalone: true,
  imports: [CardModule, ButtonModule, FormsModule, InputTextModule, InputTextareaModule, ToastModule],
  templateUrl: './contact.component.html',
  styleUrl: './contact.component.scss',
  encapsulation: ViewEncapsulation.None
})
export class ContactComponent {
  private messageService = inject(MessageService);

  public formContent = signal({
    email: "",
    message: "",
  });

  public onSubmit() {
    this.messageService.add({ severity: 'success', summary: 'Merci !', detail: 'Demande de contact envoyée avec succès' });
    // do something with this.formContent()
  }
}
