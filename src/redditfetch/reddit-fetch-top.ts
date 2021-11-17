import {inject, injectable} from "inversify";
import {TYPES} from "../types";
import axios from "axios";
import { RedditOAuthGenerator } from "./reddit-ouath-generator";

export class RedditFetchTop
{
    private redditOAuthGenerator: RedditOAuthGenerator;
    constructor(
        @inject(TYPES.RedditOAuthGenerator) redditOAuthGenerator: RedditOAuthGenerator
    ){
        this.redditOAuthGenerator = redditOAuthGenerator;
    }
    fetchTopDailyPosts(subreddit: string, token: string): Promise<{response: Object}>{
        return axios.get(
            `https://www.reddit.com/r/${subreddit}/top.json?t=daily`,
            {
                headers: {
                    Authorization: `Basic ${token}`
                }
            }
        ).then((r) => r.data)
    }
}