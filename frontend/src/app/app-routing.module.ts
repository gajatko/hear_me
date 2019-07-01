import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {TestComponent} from './test/test.component';
import {SheetComponent} from './sheet/sheet.component';

const routes: Routes = [
  {path: 'test', component: TestComponent },
  {path: 'sheet', component: SheetComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
