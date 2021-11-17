import {inject, injectable} from "inversify";
import {TYPES} from "../types";
import axios from "axios";
@injectable()
export class RedditOAuthGenerator
{
    private redditClientID: string;
    private redditClientSecret: string;
    constructor(
        @inject(TYPES.RedditClientID) redditClientID: string,
        @inject(TYPES.RedditClientSecret) redditClientSecret: string
    ){
        this.redditClientID = redditClientID;
        this.redditClientSecret = redditClientSecret;
    }
    generateToken(): Promise<string> {
        return axios.post(
         "https://www.reddit.com/api/v1/access_token",
         "grant_type=client_credentials", {
            headers: {
               Authorization: `Basic ${Buffer.from(`${this.redditClientID}:${this.redditClientSecret}`).toString("base64")}`,
               "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8",
            },
            params: {
               scope: "read",
            },
         }
      ).then((r) => r.data.access_token);
    }
}