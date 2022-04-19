import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {DataService} from './data.service';
import {LocalStorageService} from './local-storage.service';
import { ListComponent } from './list/list.component';
import {MatTableModule} from '@angular/material/table';

@NgModule({
    declarations: [
        ListComponent,
    ],
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MatTableModule,
    ],
    exports: [
        ListComponent
    ],
    providers: [
        DataService,
        LocalStorageService
    ]
})
export class SharedModule { }
