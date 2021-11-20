import { injectable } from "inversify";

// Class to determine whether a response is a valid ping
@injectable()
export class PingFinder {
  private regexp = "ping";

  public isPing(stringToSearch: string): boolean {
    return stringToSearch.search(this.regexp) >= 0;
  }
}
