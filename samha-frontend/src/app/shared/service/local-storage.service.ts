import {Injectable} from '@angular/core';

@Injectable()
export class LocalStorageService {
  private storage: Storage;

  constructor() {
    this.storage = window.localStorage;
  }

  set(key: string, value: any) {
    if (this.storage) {
      this.storage.setItem(key, JSON.stringify(value));
    }
  }

  get(key: string) {
    if (this.storage) {
      return JSON.parse(this.storage.getItem(key));
    }
  }

  remove(key: string): boolean {
    if (this.storage) {
      this.storage.removeItem(key);
      return true;
    }
    return false;
  }

  clear(): boolean {
    if (this.storage) {
      this.storage.clear();
      return true;
    }
    return false;
  }
}
