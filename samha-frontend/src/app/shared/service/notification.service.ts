import {Injectable} from "@angular/core";
import {ToastrService} from "ngx-toastr";
import {first} from "rxjs/operators";
import {Router} from "@angular/router";

@Injectable()
export class NotificationService {
    constructor(private toastr: ToastrService,
                private router: Router) {}

    public success(message: string, title?: string, onClick?: Function): void{
        if(onClick) this.toastr.success(message, title).onTap.pipe(first()).subscribe(onClick.call);

        this.toastr.success(message, title);
    }

    public warning(message: string, title?: string, onClick?: Function): void{
      if(onClick) this.toastr.warning(message, title).onTap.pipe(first()).subscribe(onClick.call);

      this.toastr.warning(message, title);
    }

    public error(message: string, title?: string, onClick?: Function): void{
      if(onClick) this.toastr.error(message, title).onTap.pipe(first()).subscribe(onClick.call);

      this.toastr.error(message, title);
    }

    public handleErrorResponse(error: Response) {
      if (error) {
        let errorString = '';
        let errorData: any = null;
        try {
          errorData = error.json();
        } catch (e) {
          errorString = error.toString();
        }
        if (errorData) {
          // Handle Bean Validations
          let errors: string[] = [];
          if (errorData.status === '403') {
            this.router.navigate(['/login']);
          } else if (errorData.errors) {
            errors = errorData.errors.map((e: any) => (e.field ? e.field + ' - ' : '') + e.defaultMessage);
          } else if (errorData.message) { // Handle Other Exceptions
            errors.push(errorData.message);
            while (errorData.cause) {
              errorData = errorData.cause;
              errors.push(errorData.message);
            }
          } else {
            errors.push('Ocorreu um erro desconhecido.');
            errors.push(errorData);
          }
          for (let i = 0; i < errors.length; i++) {
            if (i > 0) {
              errorString += '<br/>';
            }
            errorString += errors[i];
          }
        }
        this.error(errorString);
      }
    }
}
