import {Injectable} from "@angular/core";
import notify from "devextreme/ui/notify";

@Injectable()
export class NotificationService {
    public success(message: string): void{
      notify(message, 'success', 2000);
    }

    public warning(message: string): void{
      notify(message, 'warning', 2000);
    }

    public error(message: string): void{
      notify(message, 'error', 2000);
    }
}
