import {inject, injectable} from "inversify";
import {TYPES} from "../types";
import axios from "axios";
@injectable()
export class RedditOAuthGenerator
{
    private redditUsername: string;
    private redditPassword: string;
    private redditClientID: string;
    private redditClientSecret: string;
    constructor(
        @inject(TYPES.RedditUsername) redditUsername: string,
        @inject(TYPES.RedditPassword) redditPassword: string,
        @inject(TYPES.RedditClientID) redditClientID: string,
        @inject(TYPES.RedditClientSecret) redditClientSecret: string
    ){
        this.redditUsername = redditUsername;
        this.redditPassword = redditPassword;
        this.redditClientID = redditClientID;
        this.redditClientSecret = redditClientSecret;
    }
    generateToken(): Promise<{access_token: string;}> {
        return axios.post(
         "https://www.reddit.com/api/v1/access_token",
         "grant_type=client_credentials", {
            headers: {
               Authorization: `Basic ${Buffer.from(`${this.redditClientID}:${process.env.REDDIT_CLIENT_SECRET}`).toString("base64")}`,
               "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8",
            },
            params: {
               scope: "read",
            },
         }
      ).then((r) => r.data);
    }
}